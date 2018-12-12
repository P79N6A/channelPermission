package com.haier.shop.model;

import java.io.Serializable;

public class VOMCancelOrderRequire implements Serializable {

	//订单号
	private String orderNo = "";
	//取消类型
	private String cancelType = "";
	//取消说明
	private String cancelExplain = "";
	//属性备注
	private String attributes = "";
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getCancelType() {
		return cancelType;
	}
	public void setCancelType(String cancelType) {
		this.cancelType = cancelType;
	}
	public String getCancelExplain() {
		return cancelExplain;
	}
	public void setCancelExplain(String cancelExplain) {
		this.cancelExplain = cancelExplain;
	}
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	
	
}
