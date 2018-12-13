package com.haier.svc.api.controller.util.http.suning;

public class SuningQDZXRejectedOrders {

	public String orderCode;	//采购订单号
	public String refundCode;	//采购订单退货单号
	public String resellerName;	//分销商名称
	public String distributourCode;	//分销商编码
	public String productName;	//商品名称
	public String productCode;	//苏宁商品编码
	public String dealMoney;	//采购订单金额
	public String returnMoney;	//实际退款金额
	public String applyTime;	//申请时间
	public String statusPassTime; 	//倒计时时间
	public String statusDesc;	//订单状态 1-退款待处理； 2-已拒绝退款 ； 3-待买家发货 ； 4-待商家收货 ； 5-退款失败； 6-退款处理中 ； 7-退款关闭 ； 8-退款成功；C030-申请退货
	public String returnReason;	//退货原因
	public String expressCompanyCode;	//物流公司代码
	public String expressNo;	//物流公司运单号
	public String returntype;	//退货发货状态
	public String refundtype;	//退货退款类型
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getRefundCode() {
		return refundCode;
	}
	public void setRefundCode(String refundCode) {
		this.refundCode = refundCode;
	}
	public String getResellerName() {
		return resellerName;
	}
	public void setResellerName(String resellerName) {
		this.resellerName = resellerName;
	}
	public String getDistributourCode() {
		return distributourCode;
	}
	public void setDistributourCode(String distributourCode) {
		this.distributourCode = distributourCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getDealMoney() {
		return dealMoney;
	}
	public void setDealMoney(String dealMoney) {
		this.dealMoney = dealMoney;
	}
	public String getReturnMoney() {
		return returnMoney;
	}
	public void setReturnMoney(String returnMoney) {
		this.returnMoney = returnMoney;
	}
	public String getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	public String getStatusPassTime() {
		return statusPassTime;
	}
	public void setStatusPassTime(String statusPassTime) {
		this.statusPassTime = statusPassTime;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public String getReturnReason() {
		return returnReason;
	}
	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}
	public String getExpressCompanyCode() {
		return expressCompanyCode;
	}
	public void setExpressCompanyCode(String expressCompanyCode) {
		this.expressCompanyCode = expressCompanyCode;
	}
	public String getExpressNo() {
		return expressNo;
	}
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	public String getReturntype() {
		return returntype;
	}
	public void setReturntype(String returntype) {
		this.returntype = returntype;
	}
	public String getRefundtype() {
		return refundtype;
	}
	public void setRefundtype(String refundtype) {
		this.refundtype = refundtype;
	}
	
}
