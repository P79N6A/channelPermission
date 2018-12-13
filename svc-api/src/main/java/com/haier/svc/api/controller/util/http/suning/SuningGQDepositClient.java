package com.haier.svc.api.controller.util.http.suning;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.haier.eop.data.model.OrdersQueue;
import com.haier.svc.api.controller.util.JsonUtils;
import com.haier.svc.api.controller.util.StringUtil;
import com.haier.svc.api.controller.util.http.AbstractHaierHttpClient;
import com.haier.svc.api.controller.util.http.JobException;
import com.haier.svc.api.controller.util.http.ShopStockEnum;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SuningGQDepositClient extends AbstractHaierHttpClient {

    private static final Logger log = LogManager.getLogger(SuningGQDepositClient.class);
    public final static String METHOD_ORDERS_GET = "suning.custom.ord.query";
    public final static String METHOD_ORDERS_GET_ALL = "suning.custom.order.query";
    public final static String METHOD_ORDER_DETAIL_GET = "suning.custom.order.get";
    public final static String METHOD_ORDERDELIVERY_GET = "suning.custom.orderdelivery.add";
    public final static String METHOD_DEPOSIT_GET = "suning.custom.unpaidorder.query";
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



    @Override
    public String getOrderByOrderId(String orderId) {
        JSONObject json = new JSONObject();
        json.put("orderCode", orderId);
        return excuteMehtod(METHOD_ORDER_DETAIL_GET, ORDERS_GET_STR + json.toJSONString());
    }



//

    @Override
    public OrdersQueue conventOrdersQueue(String jsonStr) {
        OrdersQueue ordersQueue = null;
        try {
            JsonObject jo = JsonUtils.toJsonObject(jsonStr, "sn_responseContent").getAsJsonObject("sn_body").getAsJsonObject("orderGet");
            SuningOrders order = JSON.parseObject(jo.toString(), SuningOrders.class);
            if (!order.getOrderDetail().get(0).getActivitytype().equals("05")) {//05表示非订金订单
                return ordersQueue;
            }
            ordersQueue = new OrdersQueue();
            ordersQueue.setSourceOrderSn(order.getOrderCode() + "");
            ordersQueue.setOrderInfo(JsonUtils.toJsonString(order));
            ordersQueue.setOrderTime(new Date());
            ordersQueue.setModifyTime(new Date());
            ordersQueue.setOrderTotalPrice(null);
            ordersQueue.setOrderType(order.getOrderTotalStatus() + "");
            //订单行项目状态。10=待发货；20=已发货；30=交易成功
            if (order.getOrderTotalStatus().equals("5") && order.getOrderDetail().get(0).getActivitytype().equals("05")) {
        		ordersQueue.setOrdersState("1007");//已接完订金的订单
        	} else {
        		ordersQueue.setOrdersState("1005");
        	}
            //默认值
            ordersQueue.setSyncTime((System.currentTimeMillis() / 1000));
            ordersQueue.setAddTime((System.currentTimeMillis() / 1000));
            ordersQueue.setCreator("系统_手动同步订单接入");
            ordersQueue.setSyncCount(0);
            ordersQueue.setErrorLog("");
            ordersQueue.setSource(ShopStockEnum.SNYG.getSource());
        } catch (Exception e) {
            throw new JobException("SNYG解析JSON异常");
        }
        return ordersQueue;
    }




    /***
     * 执行远程操作
     *
     * @param method
     * @param body
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
            log.info("苏宁订金" + method + "请求报文：" + req);
            log.info("苏宁订金" + method + "返回报文：" + content);
            
            //如果返回码有错误信息则返回error,orderQueueJob做判断不会修改Lasttime,避免漏单
            if(content.contains("\"sn_error\"") && !content.contains("\"biz.handler.data-get:no-result\""))
            	return "error";
            else if(StringUtil.isEmpty(content))
            	return "error";
            else
            	return content;
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }


}
