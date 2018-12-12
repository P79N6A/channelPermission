package com.haier.shop.model;

public class OrderrepairHPrecordsVO extends OrderrepairHPrecords{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6838745131882812880L;
	private String add2;//备用字段  1机器完好换好包装箱； 2非正品需买单； 3机器完好无包装箱可换 
	private String repairSn;//退货单号
	private String cOrderSn;//网单号
	private String sourceOrderSn;//外部订单号(也就是来源订单号)
	private String source;//渠道
	private String sku;//物料
	private double price;//单价
	private int number;//数量
	private String reason;//退款原因
	private String isReceipt;//是否需要发票
	private String tbOrderSn;//Tb单号
	
	public String getTbOrderSn() {
		return tbOrderSn;
	}

	public void setTbOrderSn(String tbOrderSn) {
		this.tbOrderSn = tbOrderSn;
	}

	public String getRepairSn() {
		return repairSn;
	}

	public void setRepairSn(String repairSn) {
		this.repairSn = repairSn;
	}

	public String getcOrderSn() {
		return cOrderSn;
	}

	public void setcOrderSn(String cOrderSn) {
		this.cOrderSn = cOrderSn;
	}

	public String getSourceOrderSn() {
		return sourceOrderSn;
	}

	public void setSourceOrderSn(String sourceOrderSn) {
		this.sourceOrderSn = sourceOrderSn;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}


	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getIsReceipt() {
		return isReceipt;
	}

	public void setIsReceipt(String isReceipt) {
		this.isReceipt = isReceipt;
	}

	public String getAdd2() {
		return add2;
	}

	public void setAdd2(String add2) {
		this.add2 = add2;
	}
	
}
