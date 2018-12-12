package com.haier.purchase.data.model;

import java.io.Serializable;

public class GoodsBackInfoResponse implements Serializable {

	private String orderNo;
    private String sourceOrderNo;
    private String materialsId;
    private String locationCode;
    private Integer quantity;
    private String siOuSlipNo;
    private String siOuSlipLineNo;
    private String channel;
    private String type;
    
    public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getSourceOrderNo() {
		return sourceOrderNo;
	}
	public void setSourceOrderNo(String sourceOrderNo) {
		this.sourceOrderNo = sourceOrderNo;
	}
	public String getMaterialsId() {
		return materialsId;
	}
	public void setMaterialsId(String materialsId) {
		this.materialsId = materialsId;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getSiOuSlipNo() {
		return siOuSlipNo;
	}
	public void setSiOuSlipNo(String siOuSlipNo) {
		this.siOuSlipNo = siOuSlipNo;
	}
	public String getSiOuSlipLineNo() {
		return siOuSlipLineNo;
	}
	public void setSiOuSlipLineNo(String siOuSlipLineNo) {
		this.siOuSlipLineNo = siOuSlipLineNo;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
