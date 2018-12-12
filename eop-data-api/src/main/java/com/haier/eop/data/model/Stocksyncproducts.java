package com.haier.eop.data.model;

import java.io.Serializable;
import java.util.Date;

public class Stocksyncproducts  implements Serializable{
	  private static final long serialVersionUID = -3117914630224183116L;
    private Integer id;

    private String source;

    private Integer productId;

    private String sku;

    private String sourceProductId;

    private String tzSku;

    private Integer stype;

    private Date addTime;

    private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getSourceProductId() {
		return sourceProductId;
	}

	public void setSourceProductId(String sourceProductId) {
		this.sourceProductId = sourceProductId;
	}

	public String getTzSku() {
		return tzSku;
	}

	public void setTzSku(String tzSku) {
		this.tzSku = tzSku;
	}

	public Integer getStype() {
		return stype;
	}

	public void setStype(Integer stype) {
		this.stype = stype;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    
}