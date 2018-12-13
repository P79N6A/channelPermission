package com.haier.svc.api.controller.util.http.gome;

import java.util.Date;

public class GomeOrder {
private static final long serialVersionUID = -4508243378348425525L;
	 
	private String order_id;
	 
	private String vendor_id;
	 
	private String pay_type; 
	private String status;
	 
	private String customer_remark;
	 
	private Double freight_price;
	 
	private Double item_total_price; 
	private Double order_total_price;
	 
	private Double theAllMoney;// 搴斾粯閲戦
	 
	private Double payment;
	 
	private Double discount_amount;
 
	private Double promotion_amount;
 
	private Date order_time;
 
	private Date pay_time;
 
	private String pay_status;
 
	private Date end_order_time; 
	private Date order_change_time;
 
	private Double gome_freight;
 
	private String address_short_name;
 
	private String package_num;
 
	private String tracking_company;
 
	private String warehouse_id;
	 
	private String warehouse_name;
	
 
	//private Consignee consignee;
	 
	private String username;
	 
	private String userid;
	 
	//private List<OrderDetail> order_detail;
	 
	private Double part_discount_price;  
	private Double coupon_value;
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getVendor_id() {
		return vendor_id;
	}
	public void setVendor_id(String vendor_id) {
		this.vendor_id = vendor_id;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCustomer_remark() {
		return customer_remark;
	}
	public void setCustomer_remark(String customer_remark) {
		this.customer_remark = customer_remark;
	}
	public Double getFreight_price() {
		return freight_price;
	}
	public void setFreight_price(Double freight_price) {
		this.freight_price = freight_price;
	}
	public Double getItem_total_price() {
		return item_total_price;
	}
	public void setItem_total_price(Double item_total_price) {
		this.item_total_price = item_total_price;
	}
	public Double getOrder_total_price() {
		return order_total_price;
	}
	public void setOrder_total_price(Double order_total_price) {
		this.order_total_price = order_total_price;
	}
	public Double getTheAllMoney() {
		return theAllMoney;
	}
	public void setTheAllMoney(Double theAllMoney) {
		this.theAllMoney = theAllMoney;
	}
	public Double getPayment() {
		return payment;
	}
	public void setPayment(Double payment) {
		this.payment = payment;
	}
	public Double getDiscount_amount() {
		return discount_amount;
	}
	public void setDiscount_amount(Double discount_amount) {
		this.discount_amount = discount_amount;
	}
	public Double getPromotion_amount() {
		return promotion_amount;
	}
	public void setPromotion_amount(Double promotion_amount) {
		this.promotion_amount = promotion_amount;
	}
	public Date getOrder_time() {
		return order_time;
	}
	public void setOrder_time(Date order_time) {
		this.order_time = order_time;
	}
	public Date getPay_time() {
		return pay_time;
	}
	public void setPay_time(Date pay_time) {
		this.pay_time = pay_time;
	}
	public String getPay_status() {
		return pay_status;
	}
	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}
	public Date getEnd_order_time() {
		return end_order_time;
	}
	public void setEnd_order_time(Date end_order_time) {
		this.end_order_time = end_order_time;
	}
	public Date getOrder_change_time() {
		return order_change_time;
	}
	public void setOrder_change_time(Date order_change_time) {
		this.order_change_time = order_change_time;
	}
	public Double getGome_freight() {
		return gome_freight;
	}
	public void setGome_freight(Double gome_freight) {
		this.gome_freight = gome_freight;
	}
	public String getAddress_short_name() {
		return address_short_name;
	}
	public void setAddress_short_name(String address_short_name) {
		this.address_short_name = address_short_name;
	}
	public String getPackage_num() {
		return package_num;
	}
	public void setPackage_num(String package_num) {
		this.package_num = package_num;
	}
	public String getTracking_company() {
		return tracking_company;
	}
	public void setTracking_company(String tracking_company) {
		this.tracking_company = tracking_company;
	}
	public String getWarehouse_id() {
		return warehouse_id;
	}
	public void setWarehouse_id(String warehouse_id) {
		this.warehouse_id = warehouse_id;
	}
	public String getWarehouse_name() {
		return warehouse_name;
	}
	public void setWarehouse_name(String warehouse_name) {
		this.warehouse_name = warehouse_name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Double getPart_discount_price() {
		return part_discount_price;
	}
	public void setPart_discount_price(Double part_discount_price) {
		this.part_discount_price = part_discount_price;
	}
	public Double getCoupon_value() {
		return coupon_value;
	}
	public void setCoupon_value(Double coupon_value) {
		this.coupon_value = coupon_value;
	}
	@Override
	public String toString() {
		return "GomeOrder [order_id=" + order_id + ", vendor_id=" + vendor_id
				+ ", pay_type=" + pay_type + ", status=" + status
				+ ", customer_remark=" + customer_remark + ", freight_price="
				+ freight_price + ", item_total_price=" + item_total_price
				+ ", order_total_price=" + order_total_price + ", theAllMoney="
				+ theAllMoney + ", payment=" + payment + ", discount_amount="
				+ discount_amount + ", promotion_amount=" + promotion_amount
				+ ", order_time=" + order_time + ", pay_time=" + pay_time
				+ ", pay_status=" + pay_status + ", end_order_time="
				+ end_order_time + ", order_change_time=" + order_change_time
				+ ", gome_freight=" + gome_freight + ", address_short_name="
				+ address_short_name + ", package_num=" + package_num
				+ ", tracking_company=" + tracking_company + ", warehouse_id="
				+ warehouse_id + ", warehouse_name=" + warehouse_name
				+ ", username=" + username + ", userid=" + userid
				+ ", part_discount_price=" + part_discount_price
				+ ", coupon_value=" + coupon_value + "]";
	} 
	
	
}
