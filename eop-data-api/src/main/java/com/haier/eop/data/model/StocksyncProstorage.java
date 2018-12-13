package com.haier.eop.data.model;

import java.io.Serializable;
import java.util.Date;

public class StocksyncProstorage implements Serializable {
	private static final long serialVersionUID = -3117914630224183116L;
    private Integer id;

    private Integer syncProductId;

    private Integer syncStorageId;

    private String sku;

    private String tzSku;

    private Integer stype;

    private String sCode;

    private String source;

    private Integer isOn;

    private Date addTime;

    private Date updateTime;
    
    private int kuwei;

	public int getKuwei() {
		return kuwei;
	}

	public void setKuwei(int kuwei) {
		this.kuwei = kuwei;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSyncProductId() {
		return syncProductId;
	}

	public void setSyncProductId(Integer syncProductId) {
		this.syncProductId = syncProductId;
	}

	public Integer getSyncStorageId() {
		return syncStorageId;
	}

	public void setSyncStorageId(Integer syncStorageId) {
		this.syncStorageId = syncStorageId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
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

	public String getsCode() {
		return sCode;
	}

	public void setsCode(String sCode) {
		this.sCode = sCode;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getIsOn() {
		return isOn;
	}

	public void setIsOn(Integer isOn) {
		this.isOn = isOn;
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

    
}