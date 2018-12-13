package com.haier.svc.api.controller.util.http.jingdong;

import com.alibaba.fastjson.annotation.JSONField;

public class CouponDetail {
	@JSONField(name="order_id")
	private String orderId;	//订单编号
	
	@JSONField(name="sku_id")
	private String skuId;	//京东sku编号
	
	@JSONField(name="coupon_type")
	private String couponType;	//优惠类型: 20-套装优惠, 28-闪团优惠, 29-团购优惠, 30-单品促销优惠, 34-手机红包, 35-满返满送(返现), 39-京豆优惠,41-京东券优惠, 52-礼品卡优惠,100-店铺优惠
	
	@JSONField(name="coupon_price")
	private String couponPrice;	//优惠金额
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getCouponType() {
		return couponType;
	}
	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
	public String getCouponPrice() {
		return couponPrice;
	}
	public void setCouponPrice(String couponPrice) {
		this.couponPrice = couponPrice;
	}
	
	
}
