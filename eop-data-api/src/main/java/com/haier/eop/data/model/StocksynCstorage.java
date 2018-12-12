package com.haier.eop.data.model;

import java.io.Serializable;
import java.util.Date;

public class StocksynCstorage implements Serializable{
	private static final long serialVersionUID = -3117914630224183116L;
    private Integer id;

    private Integer storageId;

    private String sCode;

    private String sourceStoreCode;

    private String source;

    private Date addTime;

    private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStorageId() {
		return storageId;
	}

	public void setStorageId(Integer storageId) {
		this.storageId = storageId;
	}

	public String getsCode() {
		return sCode;
	}

	public void setsCode(String sCode) {
		this.sCode = sCode;
	}

	public String getSourceStoreCode() {
		return sourceStoreCode;
	}

	public void setSourceStoreCode(String sourceStoreCode) {
		this.sourceStoreCode = sourceStoreCode;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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