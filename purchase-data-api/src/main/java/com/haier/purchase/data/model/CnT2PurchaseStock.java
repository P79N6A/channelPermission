package com.haier.purchase.data.model;

import java.io.Serializable;
import java.util.Date;



public class CnT2PurchaseStock implements Serializable{
	
	private static final long serialVersionUID = 6623481866134743433L;

	public String getVbeln() {
		return vbeln;
	}

	public void setVbeln(String vbeln) {
		this.vbeln = vbeln;
	}

	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	public String getMenge() {
		return menge;
	}

	public void setMenge(String menge) {
		this.menge = menge;
	}

	public String getLgort() {
		return lgort;
	}

	public void setLgort(String lgort) {
		this.lgort = lgort;
	}

	private String vbeln;
	private String matnr;

	private String menge;

	private String lgort;


	private Integer id;
	
	private String cnStockSyncsId;
	
	private String pushData;
	
	private String returnData;
	
	private Integer status;
	
	private String message;
	
	private Date addTime;
	
	private Date processTime;

	private String cnStockDnId;

	public String getCnStockDnId() {
		return cnStockDnId;
	}

	public void setCnStockDnId(String cnStockDnId) {
		this.cnStockDnId = cnStockDnId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCnStockSyncsId() {
		return cnStockSyncsId;
	}

	public void setCnStockSyncsId(String cnStockSyncsId) {
		this.cnStockSyncsId = cnStockSyncsId;
	}

	public String getPushData() {
		return pushData;
	}

	public void setPushData(String pushData) {
		this.pushData = pushData;
	}

	public String getReturnData() {
		return returnData;
	}

	public void setReturnData(String returnData) {
		this.returnData = returnData;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}
	
}
