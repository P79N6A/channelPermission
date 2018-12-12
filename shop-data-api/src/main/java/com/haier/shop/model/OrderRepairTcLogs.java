package com.haier.shop.model;

public class OrderRepairTcLogs {
	
	private int id;

	private long addTime;//添加时间
	
	private int orderRepairTcId;//退仓单id，OrderRepairTcs

	private int tcRecordsId;//退仓入库id， OrderRepairTcRecords

	private String operator;//操作人：默认，系统

	private String operate;//操作名称

	private String remark;//备注
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getAddTime() {
		return addTime;
	}
	public void setAddTime(long addTime) {
		this.addTime = addTime;
	}
	public int getOrderRepairTcId() {
		return orderRepairTcId;
	}
	public void setOrderRepairTcId(int orderRepairTcId) {
		this.orderRepairTcId = orderRepairTcId;
	}
	public int getTcRecordsId() {
		return tcRecordsId;
	}
	public void setTcRecordsId(int tcRecordsId) {
		this.tcRecordsId = tcRecordsId;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
