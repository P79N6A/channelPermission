package com.haier.shop.model;

import java.io.Serializable;
import java.util.Date;

/**
 * HP回传信息 实体类（已开箱正品 为开箱正品）
 * 吴坤洋 2017-11-20
 * @author wukunyang
 *
 */
public class OrderRepairshpLogs implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 656902495134472189L;
	private int id;//主键
	private String orderRejectSn;//退货单号
	private String netPointCode;//客户编码 网点86码
	private String checkResult;//鉴定结果 1:符合退机条件 2:不符合退机条件
	private String quality; //产品状态 1: 未开箱。2:已开箱正品。3:不良品  4：已使用正品 5:不良品换机 6:不良品退机（现电商只保有1256四个状态）
	private String machineNum;//机器编码
	private String inspector;//质检员
	private String inspectTime;//质检时间
	private String add1;//是否一次质检 1一次；2二次
	private String add2;//备用字段 1机器完好换好包装箱； 2非正品需买单； 3机器完好无包装箱可换 
	private String add3;//"1网点；2物流	
	private String add4;//备用字段
	private String add5;//备用字段
	private String procType;//处理状态
	private String woId;//工单号
	private Date createdDate;//创建时间
	private Date EstablishDate;//当前数据插入数据库时间
	
	

	
	
	
	
	
	
	 
	public Date getEstablishDate() {
		return EstablishDate;
	}
	public void setEstablishDate(Date establishDate) {
		EstablishDate = establishDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrderRejectSn() {
		return orderRejectSn;
	}
	public void setOrderRejectSn(String orderRejectSn) {
		this.orderRejectSn = orderRejectSn;
	}
	public String getNetPointCode() {
		return netPointCode;
	}
	public void setNetPointCode(String netPointCode) {
		this.netPointCode = netPointCode;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	
	public String getMachineNum() {
		return machineNum;
	}
	public void setMachineNum(String machineNum) {
		this.machineNum = machineNum;
	}
	
	
	public String getInspector() {
		return inspector;
	}
	public void setInspector(String inspector) {
		this.inspector = inspector;
	}
	public String getInspectTime() {
		return inspectTime;
	}
	public void setInspectTime(String inspectTime) {
		this.inspectTime = inspectTime;
	}
	public String getAdd1() {
		return add1;
	}
	public void setAdd1(String add1) {
		this.add1 = add1;
	}
	public String getAdd2() {
		return add2;
	}
	public void setAdd2(String add2) {
		this.add2 = add2;
	}
	public String getAdd3() {
		return add3;
	}
	public void setAdd3(String add3) {
		this.add3 = add3;
	}
	public String getAdd4() {
		return add4;
	}
	public void setAdd4(String add4) {
		this.add4 = add4;
	}
	public String getAdd5() {
		return add5;
	}
	public void setAdd5(String add5) {
		this.add5 = add5;
	}
	public String getProcType() {
		return procType;
	}
	public void setProcType(String procType) {
		this.procType = procType;
	}
	public String getWoId() {
		return woId;
	}
	public void setWoId(String woId) {
		this.woId = woId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
}
