package com.haier.purchase.data.model.vehcile;

import java.io.Serializable;
import java.util.Date;

public class VehiclePushToSAP implements Serializable {
	private Integer id;

	/**
	 * 订单号
	 */
	private String cnStockSyncsId;

	/**
	 * 推送数据
	 */
	private String pushData;

	/**
	 * 返回数据
	 */
	private String returnData;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 信息
	 */
	private String message;

	/**
	 * 添加时间
	 */
	private Date addTime;

	/**
	 * 处理时间
	 */
	private Date processTime;

	/**
	 * 一次DN1
	 */
	private String vbelnDn1;

	/**
	 * 二次DN5
	 */
	private String vbelnDn5;

	/**
	 * 预约LBX备用DN
	 */
	private String vbelnSpare;

	/**
	 * LBX单号
	 */
	private String lbx;

	/**
	 * 数量
	 */
	private Integer qty;

	/**
	 * LBX入库数量
	 */
	private Integer actualQty;

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

	public String getVbelnDn1() {
		return vbelnDn1;
	}

	public void setVbelnDn1(String vbelnDn1) {
		this.vbelnDn1 = vbelnDn1;
	}

	public String getVbelnDn5() {
		return vbelnDn5;
	}

	public void setVbelnDn5(String vbelnDn5) {
		this.vbelnDn5 = vbelnDn5;
	}

	public String getVbelnSpare() {
		return vbelnSpare;
	}

	public void setVbelnSpare(String vbelnSpare) {
		this.vbelnSpare = vbelnSpare;
	}

	public String getLbx() {
		return lbx;
	}

	public void setLbx(String lbx) {
		this.lbx = lbx;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getActualQty() {
		return actualQty;
	}

	public void setActualQty(Integer actualQty) {
		this.actualQty = actualQty;
	}
}
