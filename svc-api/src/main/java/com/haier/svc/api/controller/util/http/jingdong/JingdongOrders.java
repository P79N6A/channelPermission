package com.haier.svc.api.controller.util.http.jingdong;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;


public class JingdongOrders {
	@JSONField(name="order_id")
	private String orderId;	//订单ID
	
	@JSONField(name="vender_id")
	private String venderId;	//商家id  
	
	@JSONField(name="pay_type")
	private String payType;	//支付方式（1-货到付款, 2-邮局汇款, 3-自提, 4-在线支付, 5-公司转账, 6-银行转账）
	
	@JSONField(name="order_total_price")
	private String orderTotalPrice;	//订单总金额
	
	@JSONField(name="order_seller_price")
	private String orderSellerPrice;	//订单货款金额（订单总金额-商家优惠金额
	
	@JSONField(name="order_payment")
	private String orderPayment;	//用户应付金额
	
	@JSONField(name="freight_price")
	private String freightPrice;	//商品的运费
	
	@JSONField(name="seller_discount")
	private String sellerDiscount;	//商家优惠金额
	
	@JSONField(name="order_state")
	private String orderState;	//订单状态（英文
	
	@JSONField(name="order_state_remark")
	private String orderStateRemark;	//订单状态说明（中文）

	@JSONField(name="delivery_type")
	private String deliveryType;	//送货（日期）类型（1-只工作日送货(双休日、假日不用送);2-只双休日、假日送货(工作日不用送);3-工作日、双休日与假日均可送货;其他值-返回‘任意时间’）
	
	@JSONField(name="invoice_info")
	private String invoiceInfo;	//发票信息 返回‘不需要开具发票’时无需开具发票；其它返回值请正常开具发票
	
	@JSONField(name="order_remark")
	private String orderRemark;	//买家下单时订单备注
	
	@JSONField(name="order_start_time")
	private String orderStartTime;	//下单时间
	
	@JSONField(name="order_end_time")
	private String orderEndTime;	//结单时间
	
	private String modified;	//订单更新时间
	
	@JSONField(name="consignee_info")
	private UserInfo consigneeInfo;	//收货人基本信息
	
	@JSONField(name="item_info_list")
	private List<ItemInfo> itemInfoList;	//订单商品列表
	
	@JSONField(name="coupon_detail_list")
	private List<CouponDetail> couponDetailList;	//订单商家优惠列表
	
	@JSONField(name="vender_remark")
	private String venderRemark;	//商家订单备注
	
	@JSONField(name="balance_used")
	private String balanceUsed;	//余额支付金额
	
	@JSONField(name="payment_confirm_time")
	private String paymentConfirmTime;	//付款确认时间
	
	private String waybill;	//运单号
	
	@JSONField(name="logistics_id")
	private String logisticsId;	//物流公司ID
	
	@JSONField(name="order_source")
	private String orderSource;	
	
	@JSONField(name="vat_invoice_info")
	private String vatInvoiceInfo;	//增值税发票

	@JSONField(name="parent_order_id")
	private String parentOrderId;	//父订单号
	
	private String customs;	//保税区信息 
	
	@JSONField(name="customs_model")
	private String customsModel;	//保税模型：直邮，保税集货，保税备货
	
	@JSONField(name="order_sign")
	private String orderSign;	
	
	@JSONField(name="store_order")
	private String storeOrder;	//京仓订单.如果是京仓订单，结果返回文字“京仓订单”；否则返回结果为空值“”
	
	private String pin;	//即买家的账号信息
	
	@JSONField(name="return_order")
	private String returnOrder;	//售后订单标记 0:不是换货订单 1返修发货,直接赔偿,客服补件 2售后调货
	
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getVenderId() {
		return venderId;
	}
	public void setVenderId(String venderId) {
		this.venderId = venderId;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getOrderTotalPrice() {
		return orderTotalPrice;
	}
	public void setOrderTotalPrice(String orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}
	public String getOrderSellerPrice() {
		return orderSellerPrice;
	}
	public void setOrderSellerPrice(String orderSellerPrice) {
		this.orderSellerPrice = orderSellerPrice;
	}
	public String getOrderPayment() {
		return orderPayment;
	}
	public void setOrderPayment(String orderPayment) {
		this.orderPayment = orderPayment;
	}
	public String getFreightPrice() {
		return freightPrice;
	}
	public void setFreightPrice(String freightPrice) {
		this.freightPrice = freightPrice;
	}
	public String getSellerDiscount() {
		return sellerDiscount;
	}
	public void setSellerDiscount(String sellerDiscount) {
		this.sellerDiscount = sellerDiscount;
	}
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	public String getOrderStateRemark() {
		return orderStateRemark;
	}
	public void setOrderStateRemark(String orderStateRemark) {
		this.orderStateRemark = orderStateRemark;
	}
	public String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	public String getInvoiceInfo() {
		return invoiceInfo;
	}
	public void setInvoiceInfo(String invoiceInfo) {
		this.invoiceInfo = invoiceInfo;
	}
	public String getOrderRemark() {
		return orderRemark;
	}
	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}
	public String getOrderStartTime() {
		return orderStartTime;
	}
	public void setOrderStartTime(String orderStartTime) {
		this.orderStartTime = orderStartTime;
	}
	public String getOrderEndTime() {
		return orderEndTime;
	}
	public void setOrderEndTime(String orderEndTime) {
		this.orderEndTime = orderEndTime;
	}
	public String getModified() {
		return modified;
	}
	public void setModified(String modified) {
		this.modified = modified;
	}
	public UserInfo getConsigneeInfo() {
		return consigneeInfo;
	}
	public void setConsigneeInfo(UserInfo consigneeInfo) {
		this.consigneeInfo = consigneeInfo;
	}
	public List<ItemInfo> getItemInfoList() {
		return itemInfoList;
	}
	public void setItemInfoList(List<ItemInfo> itemInfoList) {
		this.itemInfoList = itemInfoList;
	}
	public List<CouponDetail> getCouponDetailList() {
		return couponDetailList;
	}
	public void setCouponDetailList(List<CouponDetail> couponDetailList) {
		this.couponDetailList = couponDetailList;
	}
	public String getVenderRemark() {
		return venderRemark;
	}
	public void setVenderRemark(String venderRemark) {
		this.venderRemark = venderRemark;
	}
	public String getBalanceUsed() {
		return balanceUsed;
	}
	public void setBalanceUsed(String balanceUsed) {
		this.balanceUsed = balanceUsed;
	}
	public String getPaymentConfirmTime() {
		return paymentConfirmTime;
	}
	public void setPaymentConfirmTime(String paymentConfirmTime) {
		this.paymentConfirmTime = paymentConfirmTime;
	}
	public String getWaybill() {
		return waybill;
	}
	public void setWaybill(String waybill) {
		this.waybill = waybill;
	}
	public String getLogisticsId() {
		return logisticsId;
	}
	public void setLogisticsId(String logisticsId) {
		this.logisticsId = logisticsId;
	}
	public String getOrderSource() {
		return orderSource;
	}
	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}
	public String getVatInvoiceInfo() {
		return vatInvoiceInfo;
	}
	public void setVatInvoiceInfo(String vatInvoiceInfo) {
		this.vatInvoiceInfo = vatInvoiceInfo;
	}
	public String getParentOrderId() {
		return parentOrderId;
	}
	public void setParentOrderId(String parentOrderId) {
		this.parentOrderId = parentOrderId;
	}
	public String getCustoms() {
		return customs;
	}
	public void setCustoms(String customs) {
		this.customs = customs;
	}
	public String getCustomsModel() {
		return customsModel;
	}
	public void setCustomsModel(String customsModel) {
		this.customsModel = customsModel;
	}
	public String getOrderSign() {
		return orderSign;
	}
	public void setOrderSign(String orderSign) {
		this.orderSign = orderSign;
	}
	public String getStoreOrder() {
		return storeOrder;
	}
	public void setStoreOrder(String storeOrder) {
		this.storeOrder = storeOrder;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getReturnOrder() {
		return returnOrder;
	}
	public void setReturnOrder(String returnOrder) {
		this.returnOrder = returnOrder;
	}
	
}
