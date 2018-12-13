package com.haier.purchase.data.model;

import java.io.Serializable;

public class CrmOrderRejectItem implements Serializable {

	private static final long serialVersionUID = -8072674546573960378L;

	private String orderId;
	
	private String sourceOrderId;
	
	private String storageId;
	
	private String materialsId;
	
	private String categoryId;
	
	private Integer quantity;
	
	private String vomReverseInWaNo;
 
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSourceOrderId() {
		return sourceOrderId;
	}

	public void setSourceOrderId(String sourceOrderId) {
		this.sourceOrderId = sourceOrderId;
	}

	public String getStorageId() {
		return storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}

	public String getMaterialsId() {
		return materialsId;
	}

	public void setMaterialsId(String materialsId) {
		this.materialsId = materialsId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getVomReverseInWaNo() {
		return vomReverseInWaNo;
	}

	public void setVomReverseInWaNo(String vomReverseInWaNo) {
		this.vomReverseInWaNo = vomReverseInWaNo;
	}
}
