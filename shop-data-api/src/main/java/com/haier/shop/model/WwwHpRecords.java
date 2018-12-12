package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class WwwHpRecords implements Serializable {

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private Integer addTime;

	public Integer getAddTime() {
		return addTime;
	}

	public void setAddTime(Integer addTime) {
		this.addTime = addTime;
	}

	private Integer orderProductId;

	public Integer getOrderProductId() {
		return orderProductId;
	}

	public void setOrderProductId(Integer orderProductId) {
		this.orderProductId = orderProductId;
	}

	private Integer orderRepairId;

	public Integer getOrderRepairId() {
		return orderRepairId;
	}

	public void setOrderRepairId(Integer orderRepairId) {
		this.orderRepairId = orderRepairId;
	}

	private String sku;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	private String cOrderSn;

	public String getCOrderSn() {
		return cOrderSn;
	}

	public void setCOrderSn(String cOrderSn) {
		this.cOrderSn = cOrderSn;
	}

	private String lbxSn;

	public String getLbxSn() {
		return lbxSn;
	}

	public void setLbxSn(String lbxSn) {
		this.lbxSn = lbxSn;
	}

	private Integer count;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	private Integer success;

	public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	private Integer flag;

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	private String modifytime;

	public String getModifytime() {
		return modifytime;
	}

	public void setModifytime(String modifytime) {
		this.modifytime = modifytime;
	}

	private String hpTbSn;

	public String getHpTbSn() {
		return hpTbSn;
	}

	public void setHpTbSn(String hpTbSn) {
		this.hpTbSn = hpTbSn;
	}

	private String vomRepairSn;

	public String getVomRepairSn() {
		return vomRepairSn;
	}

	public void setVomRepairSn(String vomRepairSn) {
		this.vomRepairSn = vomRepairSn;
	}
}