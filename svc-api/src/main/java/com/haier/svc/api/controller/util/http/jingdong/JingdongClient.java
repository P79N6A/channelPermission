package com.haier.svc.api.controller.util.http.jingdong;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.haier.eop.data.model.OrdersQueue;
import com.haier.svc.api.controller.util.JsonUtils;
import com.haier.svc.api.controller.util.http.AbstractHaierHttpClient;
import com.haier.svc.api.controller.util.http.JobException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.TreeMap;

@Component
public class JingdongClient extends AbstractHaierHttpClient {
	private static final Logger log = LogManager.getLogger(JingdongClient.class);
	public final static String METHOD_ORDER_DETAIL_GET = "jingdong.pop.order.get";

	@Value("${jingdong.url}")
	private String serverUrl;
	@Value("${jingdong.appKey}")
	private String appKey;
	@Value("${jingdong.appSecret}")
	private String appSecret;
	@Value("${jingdong.accessToken}")
	private String accessToken;

	@Override
	public OrdersQueue conventOrdersQueue(String respStr) {
		List<OrdersQueue> queues = Lists.newArrayList();
		OrdersQueue ordersQueue = null;
		try {
			JsonObject orderDetailInfo = JsonUtils.toJsonObject(respStr, "jingdong_pop_order_get_responce").getAsJsonObject("orderDetailInfo");
			JsonObject jo = orderDetailInfo.getAsJsonObject("orderInfo");
			JingdongOrderInfo orderget = JsonUtils.toObject(jo.toString(), JingdongOrderInfo.class);

			ordersQueue = new OrdersQueue();
			ordersQueue.setSourceOrderSn(orderget.getOrderId() + "");
			ordersQueue.setOrderInfo(JsonUtils.toJsonString(orderget));
			ordersQueue.setOrderTime(DateUtils.parseDate(orderget.getOrderStartTime()));
			ordersQueue.setModifyTime(DateUtils.parseDate(orderget.getModified()));
			ordersQueue.setOrderTotalPrice(null);
			ordersQueue.setOrderType(orderget.getOrderState() + "");
			if (orderget.getOrderState().equals("WAIT_SELLER_STOCK_OUT")) {
				ordersQueue.setOrdersState("1001");
			} else {
				ordersQueue.setOrdersState("1005");
			}
			// 默认值
			ordersQueue.setSyncTime((new Date().getTime() / 1000));
			ordersQueue.setAddTime((new Date().getTime() / 1000));
			ordersQueue.setCreator("系统");
			ordersQueue.setSyncCount(0);
			ordersQueue.setErrorLog("");
			ordersQueue.setType("fixed");
			ordersQueue.setSource("JDHEGQ");
		} catch (Exception e) {
			e.printStackTrace();
			throw new JobException("京东海尔官方旗舰店解析JSON异常");
		}
		return ordersQueue;
	}

	@Override
	public String getOrderByOrderId(String orderId) {
		TreeMap<String, String> formparams = new TreeMap<String, String>();
		formparams.put("order_id", orderId);
		// 指定返回增值税发票 可选字段
		formparams.put("optional_fields", "orderId,venderId,payType,orderTotalPrice,"
				+ "orderSellerPrice,orderPayment,freightPrice,sellerDiscount,orderState,"
				+ "orderStateRemark,deliveryType,invoiceInfo,invoiceCode,orderRemark,orderStartTime,"
				+ "orderEndTime,consigneeInfo,itemInfoList,couponDetailList,"
				+ "pin,returnOrder,paymentConfirmTime,vatInfo," + "modified,orderType,storeOrder");
		return excuteMehtod(METHOD_ORDER_DETAIL_GET, formparams);
	}

	/***
	 * 执行远程操作
	 * 
	 * @param formparams
	 * @return
	 */
	public String excuteMehtod(String method, TreeMap<String, String> formparams) {
		String content = "";
		try {
			JingdongApiClient client = new JingdongApiClient(this.serverUrl, this.accessToken, this.appKey,
					this.appSecret, method);
			content = client.execute(formparams);

			log.info("京东海尔官方旗舰店" + method + "请求报文：" + JSON.toJSONString(formparams));
			log.info("京东海尔官方旗舰店" + method + "返回报文：" + content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}


}
