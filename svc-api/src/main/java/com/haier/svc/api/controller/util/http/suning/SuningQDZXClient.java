package com.haier.svc.api.controller.util.http.suning;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.haier.eop.data.model.OrdersQueue;
import com.haier.svc.api.controller.util.DateUtil;
import com.haier.svc.api.controller.util.JsonUtils;
import com.haier.svc.api.controller.util.StringUtil;
import com.haier.svc.api.controller.util.http.AbstractHaierHttpClient;
import com.haier.svc.api.controller.util.http.JobException;
import com.haier.svc.api.controller.util.http.ShopStockEnum;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SuningQDZXClient extends AbstractHaierHttpClient {
    private static final Logger log = LogManager.getLogger(SuningQDZXClient.class);
    public final static String METHOD_ORDERS_GET = "suning.custom.batchorder.query";
    public final static String METHOD_ORDERS_GET_ALL = "suning.custom.orderid.query";
    public final static String METHOD_ORDER_DETAIL_GET = "suning.custom.oneorder.get";
    public final static String METHOD_ORDERDELIVERY_GET = "suning.custom.sendgoods.modify";
    public final static String METHOD_ORDERS_CLOSE_GET = "suning.custom.unpaidorder.query";
    public final static String METHOD_ORDER_REJECTED_GET = "suning.custom.returngoods.query";
    public final static String METHOD_STOCK_SYNC = "suning.custom.syncinventory.modify";
//    public final static String METHOD_INVOICE_GET="suning.custom.electronicinvoice.add";
    public static final String REFUND2EHAIER_URL = "http://www.ehaier.com/api/orderrepair.php";//php订单退货接口
    public static final String AUTH_NAME_VAL = "ehaier2011";
    public static final String AUTH_PASS_VAL = "ehaier&2ab*(_";

    @Value("${snqdzx.url}")
    private String serverUrl;
    @Value("${snqdzx.appKey}")
    private String appKey;
    @Value("${snqdzx.appSecret}")
    private String appSecret;
    private final static String ORDERS_QUERY_STR = "\"orderQuery\":";
    private final static String ORDERS_CODE_QUERY_STR = "\"orderCodeQuery\":";
    private final static String ORDERS_GET_STR = "\"getOneorder\":";
    private final static String ORDERDELIVERY_STR = "\"modifySendgoods\":";
    private final static String STOCK_SYNC_STR = "\"modifySyncinventory\":";


    @Override
    public String getOrderByOrderId(String orderId) {
        JSONObject json = new JSONObject();
        json.put("sdcsOrderId", orderId);
        return excuteMehtod(METHOD_ORDER_DETAIL_GET, ORDERS_GET_STR + json.toJSONString());
    }





    @Override
    public OrdersQueue conventOrdersQueue(String jsonStr) {
        OrdersQueue ordersQueue = null;
        try {
            JsonObject jo = JsonUtils.toJsonObject(jsonStr, "sn_responseContent").getAsJsonObject("sn_body").getAsJsonObject("getOneorder");
            SuningQDZXOrder order = JSON.parseObject(jo.toString(), SuningQDZXOrder.class);
            BigDecimal tempAmount = BigDecimal.ZERO;
            ordersQueue = new OrdersQueue();
            ordersQueue.setSourceOrderSn(order.getSdcsOrderId() + "");
            ordersQueue.setOrderInfo(JsonUtils.toJsonString(order));
            ordersQueue.setOrderTime(DateUtil.toParse(order.getSdcsCreateTime(),DateUtil.DATE_FORMAT_YYYY_MM_DD_HMS));
            ordersQueue.setModifyTime(DateUtil.toParse(order.getOmsCreateTime(),DateUtil.DATE_FORMAT_YYYY_MM_DD_HMS));
            ordersQueue.setOrderTotalPrice(null);
            ordersQueue.setOrderType(order.getOperateStyle() + "");
            //订单状态。01待发货、02已发货、03确认收货、04退货退款、05退款、06发货失败
            if (order.getOperateStyle().equals("10")) {
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
            ordersQueue.setSource(ShopStockEnum.SNQDZX.getSource());
        } catch (Exception e) {
            throw new JobException("SNQDZX解析JSON异常");
        }
        return ordersQueue;
    }





    /***
     * 执行远程操作
     *
     * @param method,body
     * @return
     */
    public String excuteMehtod(String method, String body) {
        //Map<String, String> headParams=new TreeMap<String, String>();
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
            
            log.info("易购渠道中心" + method + "请求报文：" + req);
            log.info("易购渠道中心" + method + "返回报文：" + content);
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
