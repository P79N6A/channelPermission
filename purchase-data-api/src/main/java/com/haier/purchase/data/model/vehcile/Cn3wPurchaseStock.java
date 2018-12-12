package com.haier.purchase.data.model.vehcile;

import java.io.Serializable;
import java.util.Date;



public class Cn3wPurchaseStock implements Serializable{
	
	private Integer id;
	
	private String cnStockSyncsId;
	
	private String pushData;
	
	private String returnData;
	
	private Integer status;
	
	private String message;
	
	private Date addTime;
	
	private Date processTime;
	
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
