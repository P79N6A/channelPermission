package com.haier.purchase.data.model;

import java.io.Serializable;
import java.util.Date;

public class CnReplenishEntryOrder implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5361765859629259474L;
	
	private Long id;
	/**
	 * 物流云上记录的主键
	 */
	private String messageId;
	/**
	 * 物流云上记录的主键顺序拼接
	 */
	private String messageIdStr;
	/**
	 * 出库单lbx号
	 */
	private String storeOrderCode;
	/**
	 * 补货入库单状态
	 */
	private Integer state;
	/**
	 * 货主ID
	 */
	private String ownerUserId;
	/**
	 * 85单号
	 */
	private String orderCode;
	/**
	 * 单据类型
	 */
	private Integer orderType;
	/**
	 * 仓库编码
	 */
	private String storeCode;
	/**
	 * 目标仓
	 */
	private String toStoreCode;
	/**
	 * 订单来源
	 */
	private Integer orderSource;
	/**
	 * 订单创建时间
	 */
	private Date orderCreateTime;
	/**
	 * 收件方省份
	 */
	private String receiverProvince;
	/**
	 * 收件方城市
	 */
	private String receiverCity;
	/**
	 * 收件方区县
	 */
	private String receiverArea;
	/**
	 * 收件方地址
	 */
	private String receiverAddress;
	/**
	 * 收件方名称
	 */
	private String receiverName;
	/**
	 * 发件方省份
	 */
	private String senderProvince;
	/**
	 * 发件方城市
	 */
	private String senderCity;
	/**
	 * 发件方区县
	 */
	private String senderArea;
	/**
	 * 发件方地址
	 */
	private String senderAddress;
	/**
	 * 发件方名称
	 */
	private String senderName;
	/**
	 * 供应商编码
	 */
	private String supplierCode;
	/**
	 * 供应商名称
	 */
	private String supplierName;
	/**
	 * 仓库订单完成时间
	 */
	private Date orderConfirmTime;
	/**
	 * 时区
	 */
	private String timeZone;
	/**
	 * 错误码
	 */
	private String errorCode;
	/**
	 * 错误信息
	 */
	private String errorMsg;
	/**
	 * 记录生成时间
	 */
	private Date insertTime;
	/**
	 * 记录修改时间
	 */
	private Date modifyTime;
	/**
	 * 尝试次数
	 */
	private Integer tryCount;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 类型
	 */
	private Integer type;
	/**
	 * TB单号
	 */
	private String tbNo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessageIdStr() {
		return messageIdStr;
	}

	public void setMessageIdStr(String messageIdStr) {
		this.messageIdStr = messageIdStr;
	}

	public String getStoreOrderCode() {
		return storeOrderCode;
	}

	public void setStoreOrderCode(String storeOrderCode) {
		this.storeOrderCode = storeOrderCode;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(String ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getToStoreCode() {
		return toStoreCode;
	}

	public void setToStoreCode(String toStoreCode) {
		this.toStoreCode = toStoreCode;
	}

	public Integer getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(Integer orderSource) {
		this.orderSource = orderSource;
	}

	public Date getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public String getReceiverProvince() {
		return receiverProvince;
	}

	public void setReceiverProvince(String receiverProvince) {
		this.receiverProvince = receiverProvince;
	}

	public String getReceiverCity() {
		return receiverCity;
	}

	public void setReceiverCity(String receiverCity) {
		this.receiverCity = receiverCity;
	}

	public String getReceiverArea() {
		return receiverArea;
	}

	public void setReceiverArea(String receiverArea) {
		this.receiverArea = receiverArea;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getSenderProvince() {
		return senderProvince;
	}

	public void setSenderProvince(String senderProvince) {
		this.senderProvince = senderProvince;
	}

	public String getSenderCity() {
		return senderCity;
	}

	public void setSenderCity(String senderCity) {
		this.senderCity = senderCity;
	}

	public String getSenderArea() {
		return senderArea;
	}

	public void setSenderArea(String senderArea) {
		this.senderArea = senderArea;
	}

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Date getOrderConfirmTime() {
		return orderConfirmTime;
	}

	public void setOrderConfirmTime(Date orderConfirmTime) {
		this.orderConfirmTime = orderConfirmTime;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getTryCount() {
		return tryCount;
	}

	public void setTryCount(Integer tryCount) {
		this.tryCount = tryCount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTbNo() {
		return tbNo;
	}

	public void setTbNo(String tbNo) {
		this.tbNo = tbNo;
	}
}
