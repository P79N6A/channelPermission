package com.haier.shop.model;

import java.util.Date;

public class Adjust extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -572853565627033014L;

	private String orderNo; // 网单号
	private String afterPay; // 旧网单号
	private String book; // 来源订单号
	private String orderSource; // 订单来源
	private String sourceOrder; // 销售代表
	private String invoiceType; // 品类
	private String makeOrderTime; // 品牌
	private String syncShopTime; // SKU
	private String payTime; // 宝贝型号
	private String firstConfirmTime; // 收货人姓名
	private Date memberLevel; // 订单付款时间
	private Integer orderMoney; // 销售数量
	private Long orderType; // 总价(发票金额)
	private String orderStatus; // 网单状态
	private Integer payStatus; // 期间
	private String haveConfirmTime; // 年度
	private Date field0; // 发票时间
	private String field1; // 开票状态
	private String field2; // 状态
	
	private String jsfs; // 结算方式
	private String tszt; // 推送状态
	private String shzt; // 审核状态
	private Date tssj; // 推送时间
	
	private String attYearMonth; //核算月份
	private Date memberLevels; //付款时间开始
	private Date memberLevele; //付款时间结束
	private String memberLevels1; //付款时间开始(接收参数使用前台无法直接赋值date类型)
	private String memberLevele1; //付款时间结束(接收参数使用前台无法直接赋值date类型)
	private Date field0s; //发票时间开始
	private Date field0e; //发票时间结束
	private String field0s1; //发票时间开始(接收参数使用前台无法直接赋值date类型)
	private String field0e1; //发票时间结束(接收参数使用前台无法直接赋值date类型)
	
	
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getAfterPay() {
		return afterPay;
	}
	public void setAfterPay(String afterPay) {
		this.afterPay = afterPay;
	}
	public String getBook() {
		return book;
	}
	public void setBook(String book) {
		this.book = book;
	}
	public String getOrderSource() {
		return orderSource;
	}
	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}
	public String getSourceOrder() {
		return sourceOrder;
	}
	public void setSourceOrder(String sourceOrder) {
		this.sourceOrder = sourceOrder;
	}
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	public String getMakeOrderTime() {
		return makeOrderTime;
	}
	public void setMakeOrderTime(String makeOrderTime) {
		this.makeOrderTime = makeOrderTime;
	}
	public String getSyncShopTime() {
		return syncShopTime;
	}
	public void setSyncShopTime(String syncShopTime) {
		this.syncShopTime = syncShopTime;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getFirstConfirmTime() {
		return firstConfirmTime;
	}
	public void setFirstConfirmTime(String firstConfirmTime) {
		this.firstConfirmTime = firstConfirmTime;
	}
	public Date getMemberLevel() {
		return memberLevel;
	}
	public void setMemberLevel(Date memberLevel) {
		this.memberLevel = memberLevel;
	}
	public Integer getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(Integer orderMoney) {
		this.orderMoney = orderMoney;
	}
	public Long getOrderType() {
		return orderType;
	}
	public void setOrderType(Long orderType) {
		this.orderType = orderType;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public String getHaveConfirmTime() {
		return haveConfirmTime;
	}
	public void setHaveConfirmTime(String haveConfirmTime) {
		this.haveConfirmTime = haveConfirmTime;
	}
	public Date getField0() {
		return field0;
	}
	public void setField0(Date field0) {
		this.field0 = field0;
	}
	public String getField1() {
		return field1;
	}
	public void setField1(String field1) {
		this.field1 = field1;
	}
	public String getField2() {
		return field2;
	}
	public void setField2(String field2) {
		this.field2 = field2;
	}
	public String getJsfs() {
		return jsfs;
	}
	public void setJsfs(String jsfs) {
		this.jsfs = jsfs;
	}
	public String getTszt() {
		return tszt;
	}
	public void setTszt(String tszt) {
		this.tszt = tszt;
	}
	public String getShzt() {
		return shzt;
	}
	public void setShzt(String shzt) {
		this.shzt = shzt;
	}
	public Date getTssj() {
		return tssj;
	}
	public void setTssj(Date tssj) {
		this.tssj = tssj;
	}
	public String getAttYearMonth() {
		return attYearMonth;
	}
	public void setAttYearMonth(String attYearMonth) {
		this.attYearMonth = attYearMonth;
	}
	public Date getMemberLevels() {
		return memberLevels;
	}
	public void setMemberLevels(Date memberLevels) {
		this.memberLevels = memberLevels;
	}
	public Date getMemberLevele() {
		return memberLevele;
	}
	public void setMemberLevele(Date memberLevele) {
		this.memberLevele = memberLevele;
	}
	public Date getField0s() {
		return field0s;
	}
	public void setField0s(Date field0s) {
		this.field0s = field0s;
	}
	public Date getField0e() {
		return field0e;
	}
	public void setField0e(Date field0e) {
		this.field0e = field0e;
	}
	public String getMemberLevels1() {
		return memberLevels1;
	}
	public void setMemberLevels1(String memberLevels1) {
		this.memberLevels1 = memberLevels1;
	}
	public String getMemberLevele1() {
		return memberLevele1;
	}
	public void setMemberLevele1(String memberLevele1) {
		this.memberLevele1 = memberLevele1;
	}
	public String getField0s1() {
		return field0s1;
	}
	public void setField0s1(String field0s1) {
		this.field0s1 = field0s1;
	}
	public String getField0e1() {
		return field0e1;
	}
	public void setField0e1(String field0e1) {
		this.field0e1 = field0e1;
	}
	

}
