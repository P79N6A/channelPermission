package com.haier.stock.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class InvThOrder implements Serializable {

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private String channelSn;

	public String getChannelSn() {
		return channelSn;
	}

	public void setChannelSn(String channelSn) {
		this.channelSn = channelSn;
	}

	private String orderRepairSn;

	public String getOrderRepairSn() {
		return orderRepairSn;
	}

	public void setOrderRepairSn(String orderRepairSn) {
		this.orderRepairSn = orderRepairSn;
	}

	private String lesSn;

	public String getLesSn() {
		return lesSn;
	}

	public void setLesSn(String lesSn) {
		this.lesSn = lesSn;
	}

	private Integer lesTime;

	public Integer getLesTime() {
		return lesTime;
	}

	public void setLesTime(Integer lesTime) {
		this.lesTime = lesTime;
	}

	private Integer returnTime;

	public Integer getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Integer returnTime) {
		this.returnTime = returnTime;
	}

	private String sku;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	private Integer number;

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	private BigDecimal price;

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	private String channel;

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	private String secCode;

	public String getSecCode() {
		return secCode;
	}

	public void setSecCode(String secCode) {
		this.secCode = secCode;
	}

	private Integer success;

	public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	private Integer count;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private String successTime;

	public String getSuccessTime() {
		return successTime;
	}

	public void setSuccessTime(String successTime) {
		this.successTime = successTime;
	}

	private String createTime;

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	private String updateTime;

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}