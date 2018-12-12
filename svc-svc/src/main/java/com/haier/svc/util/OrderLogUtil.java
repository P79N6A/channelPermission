package com.haier.svc.util;


public class OrderLogUtil {
	
	public static final String ORDER_ITEM_STATUS = "订单状态变更";
	
	public static final String CANCEL_STATUS_CHANGE = "订单取消状态变更";
	
	public static final String EDITREMARK = "修改备注信息";

	public static final String EDITOID = "修改关联订单号";

	public static final String EDITEXPRESSNO = "修改物流编号";
	
	public static final String EDITORIGIN = "修改收货人信息";
	
	public static final String UPDATEPRICE = "从限定金额中扣除订单金额";
	
	public static final String UNCONFIRMED_TO_SURE = "【订单状态】：未确认-->已确认";
	
	public static final String UNCONFIRMED_TO_STOCKOUT = "【订单状态】：未确认-->缺货";
	
	public static final String UNCONFIRMED_TO_CANCEL = "【订单状态】：未确认-->已取消";
	
	public static final String SURE_TO_FINISH = "【订单状态】：已确认-->已完成";
	
	public static final String SURE_TO_CANCEL = "【订单状态】：已确认-->已取消";
	
	public static final String SURE_TO_SEND = "【订单状态】：已确认-->配送中";
	
	public static final String STOCKOUT_TO_CANCEL = "【订单状态】：缺货-->已取消";
	
	public static final String STOCKOUT_TO_SURE = "【订单状态】：缺货-->已确认";
	
	public static final String SEND_TO_CANCEL = "【订单状态】：配送中-->已取消";
	
	public static final String SEND_TO_FINISH = "【订单状态】：配送中-->已完成";
	
	public static final String APPLY_TO_SUCCESS = "【订单取消状态】：订单取消申请中-->订单取消成功";
	
	public static final String APPLY_TO_FAIL = "【订单取消状态】：订单取消申请中-->订单取消失败";

	public static final String CHANNELMODULE = "渠道列表";

	public static final String CHANNELUPDATE = "修改渠道";
	public static final String CHANNELREMOVE = "删除渠道";

}
