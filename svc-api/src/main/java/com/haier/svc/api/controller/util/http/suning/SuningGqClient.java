package com.haier.svc.api.controller.util.http.suning;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.haier.eop.data.model.OrdersQueue;
import com.haier.eop.data.model.Salesettings;
import com.haier.eop.data.service.SalesettingsService;
import com.haier.shop.model.Products;
import com.haier.shop.service.ProductsService;
import com.haier.svc.api.controller.util.DateUtil;
import com.haier.svc.api.controller.util.JsonUtils;
import com.haier.svc.api.controller.util.StringUtil;
import com.haier.svc.api.controller.util.http.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SuningGqClient extends AbstractHaierHttpClient {
    private static final Logger log = LogManager.getLogger(SuningGqClient.class);
    public final static String METHOD_ORDERS_GET = "suning.custom.ord.query";
    public final static String METHOD_ORDERS_GET_ALL = "suning.custom.order.query";
    public final static String METHOD_ORDER_DETAIL_GET = "suning.custom.order.get";
    public final static String METHOD_ORDERDELIVERY_GET = "suning.custom.orderdelivery.add";
    public final static String METHOD_ORDERS_CLOSE_GET = "suning.custom.unpaidorder.query";
    public final static String METHOD_ORDER_REJECTED_GET = "suning.custom.batchrejected.query";
    public final static String METHOD_INVOICE_GET="suning.custom.electronicinvoice.add";
    public final static String METHOD_STOCK_SYNC = "suning.custom.parallelinventory.modify";
    public static final String REFUND2EHAIER_URL = "http://www.ehaier.com/api/orderrepair.php";//php订单退货接口
    public static final String AUTH_NAME_VAL = "ehaier2011";
    public static final String AUTH_PASS_VAL = "ehaier&2ab*(_";


    @Value("${suninggq.url}")
    private String serverUrl;
    @Value("${suninggq.appKey}")
    private String appKey;
    @Value("${suninggq.appSecret}")
    private String appSecret;
    private final static String ORDERS_QUERY_STR = "\"orderQuery\":";
    private final static String ORDERS_CODE_QUERY_STR = "\"orderCodeQuery\":";
    private final static String ORDERS_GET_STR = "\"orderGet\":";
    private final static String ORDERDELIVERY_STR = "\"orderDelivery\":";
    private final static String STOCK_SYNC_STR = "\"parallelInventory\":";

    @Autowired
    private ProductsService productsService;
    @Autowired
    private SalesettingsService salesettingsService;


    @Override
    public String getOrderByOrderId(String orderId) {
        JSONObject json = new JSONObject();
        json.put("orderCode", orderId);
        return excuteMehtod(METHOD_ORDER_DETAIL_GET, ORDERS_GET_STR + json.toJSONString());
    }


    @Override
    public OrdersQueue conventOrdersQueue(String jsonStr) {
        OrdersQueue ordersQueue = null;
        try {
            JsonObject jo = JsonUtils.toJsonObject(jsonStr, "sn_responseContent").getAsJsonObject("sn_body").getAsJsonObject("orderGet");
            SuningOrders order = JSON.parseObject(jo.toString(), SuningOrders.class);
            BigDecimal tempAmount = BigDecimal.ZERO;
            if(StringUtil.areNotEmpty(order.getOrderDetail().get(0).getReservedepositamount())){
                if(checkPayAmount(order.getOrderDetail().get(0).getReservedepositamount()).compareTo(tempAmount) > 0){
                    return ordersQueue;
                }
            }
            if(StringUtil.areNotEmpty(order.getOrderDetail().get(0).getReservebalanceamount())){
                if(checkPayAmount(order.getOrderDetail().get(0).getReservebalanceamount()).compareTo(tempAmount) > 0){
                    return ordersQueue;
                }
            }
            if(order.getOrderDetail().get(0).getActivitytype().equals("05")
                    || StringUtil.areNotEmpty(order.getOrderDetail().get(0).getReservestatus())){//05表示订金订单
                return ordersQueue;
            }
            ordersQueue = new OrdersQueue();
            ordersQueue.setSourceOrderSn(order.getOrderCode() + "");
            ordersQueue.setOrderInfo(JsonUtils.toJsonString(order));
            ordersQueue.setOrderTime(DateUtil.toParse(order.getOrderSaleTime(),DateUtil.DATE_FORMAT_YYYY_MM_DD_HMS));
            ordersQueue.setModifyTime(DateUtil.toParse(order.getOrderSaleTime(),DateUtil.DATE_FORMAT_YYYY_MM_DD_HMS));
            ordersQueue.setOrderTotalPrice(null);
            ordersQueue.setOrderType(order.getOrderTotalStatus() + "");
            //订单行项目状态。10=待发货；20=已发货；30=交易成功
            if (order.getOrderTotalStatus().equals("10")) {
                ordersQueue.setOrdersState("1001");
            } else {
                ordersQueue.setOrdersState("1005");
            }
            //默认值
            ordersQueue.setSyncTime((System.currentTimeMillis() / 1000));
            ordersQueue.setAddTime((System.currentTimeMillis() / 1000));
            ordersQueue.setCreator("系统_手动同步订单接入");
            ordersQueue.setSyncCount(0);
            ordersQueue.setErrorLog("");
            ordersQueue.setType("fixed");
            ordersQueue.setSource(ShopStockEnum.SNHEGQ.getSource());
        } catch (Exception e) {
            throw new JobException("SNHEGQ解析JSON异常");
        }
        return ordersQueue;
    }

    public void doCloseOrders(String data) {
        JsonObject jsonObject = JsonUtils.toJsonObject(data, "sn_responseContent").getAsJsonObject("sn_body").getAsJsonObject("orderGet");
        SuningOrders order = JsonUtils.toObject(jsonObject.toString(), SuningOrders.class);
        List<SuningOrderDetail> details = order.getOrderDetail();
        //处理返回结果集并发给php接口处理
        List<ItemBean> list = new ArrayList<ItemBean>();
        for (SuningOrderDetail detail:details) {
            String sku = detail.getItemCode().trim();
            if (StringUtil.isEmpty(sku)) {
                throw new JobException("SNHEGQ关闭订单同步异常：商品物料编码不能为空");
            }
            Products product = productsService.getBySku(sku);
            if (product == null) {
                Salesettings sale = salesettingsService.findByWhere(sku);
                if (sale == null) {
                    log.info("SNHEGQ关闭订单"+order.getOrderCode()+"同步异常：套装SKU查询失败;sku：" + sku);
                    continue;
//                            throw new JobException("SNHEGQ关闭订单同步异常：套装SKU查询失败;sku：" + sku);
                }
                //获取套装内商品
                List<Products> saleproducts = productsService.getProductList(sale.getProductSpecs());
                //套装里面的商品数量
                int saleNum = saleproducts.size();
                for (int i = 0; i < saleNum; i++) {
                    Products saleProduct = saleproducts.get(i);
                    if (saleProduct == null) {
                        log.info("SNHEGQ关闭订单"+order.getOrderCode()+"同步异常：商品对应的sku不匹配;sku：" + sku);
                        continue;
                    }
                    ItemBean _itemBean = new ItemBean();
                    _itemBean.setSourceOrderSn(order.getOrderCode() + "");
                    _itemBean.setSku(saleProduct.getSku());
                    //订单关闭订单不需要退款
                    _itemBean.setRefundFee(0L);
                    _itemBean.setCount(detail.getSaleNum() + "");
                    _itemBean.setDescription(detail.getProductName() + "");
                    _itemBean.setReason("我不想买了");
                    list.add(_itemBean);
                }
            }else{
                ItemBean _itemBean = new ItemBean();
                _itemBean.setSourceOrderSn(order.getOrderCode() + "");
                _itemBean.setSku(sku);
                //订单关闭订单不需要退款
                _itemBean.setRefundFee(0L);
                _itemBean.setCount(detail.getSaleNum() + "");
                _itemBean.setDescription(detail.getProductName() + "");
                _itemBean.setReason("我不想买了");
                list.add(_itemBean);
            }
            Refund2EhaierXMLBean _bean = new Refund2EhaierXMLBean(list);
            String xmlData = XmlUtils.bean2XML(_bean);
            String returnData = executePHPMethod(REFUND2EHAIER_URL,xmlData);
            log.info("SNHEGQ取消订单同步php返回结果==>" + returnData);
            list.clear();
        }
    }
    private String executePHPMethod(String url, String xmlData) {
        Map<String, String> headMap = new HashMap<String, String>();
        BASE64Encoder be = new BASE64Encoder();
        String _auth = "Basic " + be.encode((AUTH_NAME_VAL + ":" + AUTH_PASS_VAL).getBytes());
        headMap.put("Authorization", _auth);
        String content = super.excute(url, xmlData, "xml", headMap);
        return content;

    }

    /***
     * 执行远程操作
     *
     * @param method,body
     * @return
     */
    public String excuteMehtod(String method, String body) {
        String content = "";
        try {
            String req = "{\"sn_request\":{\"sn_body\":{" + body + "}}}";
            SuningApiClient client = new SuningApiClient(this.serverUrl, appKey, appSecret, method, req);
            content = client.postApiRequest();
            int count = 0;
            while(StringUtil.isEmpty(content) && count<3){
        		content = client.postApiRequest();
        		count++;
            }
            log.info("苏宁海尔集团官方旗舰店" + method + "请求报文：" + req);
            log.info("苏宁海尔集团官方旗舰店" + method + "返回报文：" + content);
            //如果返回码有错误信息则返回error,orderQueueJob做判断不会修改Lasttime,避免漏单
            if(content.contains("\"sn_error\"") && !content.contains("\"biz.handler.data-get:no-result\"")){
            	return "error";
            }else if(StringUtil.isEmpty(content)){
            	return "error";
            }else{
            	return content;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

}
