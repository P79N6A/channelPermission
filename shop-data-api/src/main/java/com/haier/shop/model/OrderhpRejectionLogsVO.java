package com.haier.shop.model;

public class OrderhpRejectionLogsVO extends OrderhpRejectionLogs{
	
	private String productId;//网单主键
	private String cOrderSn;//网单号
	private String repairId;//退货单Id
	private String quantity;//数量
	private String sku;//物料号
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getcOrderSn() {
		return cOrderSn;
	}
	public void setcOrderSn(String cOrderSn) {
		this.cOrderSn = cOrderSn;
	}
	public String getRepairId() {
		return repairId;
	}
	public void setRepairId(String repairId) {
		this.repairId = repairId;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	
	
	

	
	
}
