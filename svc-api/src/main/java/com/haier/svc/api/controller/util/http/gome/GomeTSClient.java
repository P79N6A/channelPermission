package com.haier.svc.api.controller.util.http.gome;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.haier.eop.data.model.OrdersQueue;
import com.haier.svc.api.controller.util.JsonUtils;
import com.haier.svc.api.controller.util.http.AbstractHaierHttpClient;
import com.haier.svc.api.controller.util.http.HttpUtils;
import com.haier.svc.api.controller.util.http.JobException;
import com.haier.svc.api.controller.util.http.ShopStockEnum;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


@Component
public class GomeTSClient extends AbstractHaierHttpClient {
	private static final Logger log = LogManager.getLogger(GomeTSClient.class);
	public final static String METHOD_ORDER_GET="coo8.order.get";
	public final static String METHOD_ORDERS_GET="coo8.orders.get";
	public final static String METHOD_STOCK_UPDATE="coo8.item.quantity.update";
	public final static String METHOD_INVOICE_GET="coo8.order.invoice.update";
	
	public final static String PREFIX_JSON_FEILD="orders_get_response";
	public final static String PREFIX_SIGN_JSON_FEILD="order_get_response";
	public final static String PREFIX_STOCK_SYNC_UPDATE="item_quantity_update_response";
	
	public final static String RETURN_STOCK_RESULT_SUCCESS="success";
	public final static String RETURN_STOCK_RESULT_MSG="msg";
	
	public final static String CLASS_JSON_FEILD="order";
	@Value("${gmzxts.url}")
	private String serverUrl;
	@Value("${gmzxts.appKey}")
	private String appKey;
	@Value("${gmzxts.venderId}")
	private String venderId; 

	// 查询国美单个订单
	@Override
	public String getOrderByOrderId(String orderId) {
		Map<String, String> formparams = new TreeMap<String, String>();
		formparams.put("orderId", orderId);
		formparams.put("method",METHOD_ORDER_GET);
		return excuteMehtod(formparams); 
	}

	/***
	 * 执行远程操作
	 * @param formparams
	 * @return
	 */
	private String excuteMehtod(Map<String, String> formparams) {   
		formparams.put("timestamp",df.format(new Date()));
		formparams.put("v", "2.0");
		formparams.put("venderId", this.venderId);
		formparams.put("signMethod", "md5");
		formparams.put("format", "json");
		try {
			formparams.put("sign", HttpUtils.getGMSign(formparams, this.appKey, false));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String content=super.excute(formparams,this.serverUrl); 
		log.info("国美网请求报文："+formparams);
		log.info("国美网返回报文："+content);
		return content;
	}



	public OrdersQueue conventOrdersQueue(String respStr) {
		OrdersQueue gomeQueue =null;
		try {
			JsonObject jo = JsonUtils.toJsonObject(respStr, "order_get_response").getAsJsonObject("order");
			GomeOrder order = JSON.parseObject(jo.toString(), GomeOrder.class);
			gomeQueue = new OrdersQueue();
			gomeQueue.setSourceOrderSn(order.getOrder_id());
			gomeQueue.setOrderInfo(JsonUtils.toJsonString(order));
			gomeQueue.setOrderTime(order.getOrder_time());
			gomeQueue.setModifyTime(order.getOrder_change_time());
			gomeQueue.setOrderTotalPrice(BigDecimal.valueOf(order.getOrder_total_price()));
			gomeQueue.setOrderType(order.getStatus());
			//此处根据获取国美订单的状态来设置，目前只有此状态为待同步
			if(order.getStatus().equals("PR") || order.getStatus().equals("PP")){
				gomeQueue.setOrdersState("1001");
			}else{
				gomeQueue.setOrdersState("1005");
			}
			//默认值
			gomeQueue.setSyncTime((new Date().getTime() / 1000));
			gomeQueue.setAddTime((new Date().getTime() / 1000));
			gomeQueue.setCreator("系统_手动同步订单接入");
			gomeQueue.setSyncCount(0);
			gomeQueue.setErrorLog("");
			gomeQueue.setType("fixed");
			gomeQueue.setSource(ShopStockEnum.GMZXTS.getSource());
		} catch (Exception e) {
			throw new JobException("GOMETS解析JSON异常");
		}
		return gomeQueue;
	}

}
