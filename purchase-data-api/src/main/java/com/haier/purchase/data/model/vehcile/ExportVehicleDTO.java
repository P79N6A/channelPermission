package com.haier.purchase.data.model.vehcile;

import java.io.Serializable;
import java.util.Date;

public class ExportVehicleDTO implements Serializable {

	private static final long serialVersionUID = 9160645058014653871L;
	private String orderNo; // 整车订单编号
	private String itemNo; // 行号（小单）
	private String vbelnDn1;// 一次85单号
	private String vbelnDn5;// 二次85单号
	private String vbelnSpare;// 预约LBX备用DN
	private String lbx;
	private Date zspdt;// lbx入库时间
	private Integer rows; // 明细行数
	private String zkStatus; // 小单状态(zk)
	private String zqStatus; // 扣款状态(zq)
	private String materielCode; // 物料编号
	private String materielName; // 物料名称
	private String productGroup; // 产品组
	private String productGroupName; // 产品组名称
	private String brand; // 品牌
	private Integer qty; // 数量
	private double unitPrice; // 含税开票价
	private double amount; // 总金额
	private double volume; // 体积
	private double totalVolume; // 总体积
	private String paymentCode; // 付款方编码
	private String paymentName; // 付款方名称
	private String zqItemNo; // 行号（小单）
	private String zqRemark;	//扣款错误信息
	private String zkRemark;	//订单错误信息
	private String jdName; //生产基地
	private String deliveryCode; //送达方编码
	private String deliveryName; //送达方名称
	private String distributionCentre; //配送中心编码
	private String distributionCentreName; //配送中心名称
	private String whCode;//电商库位码
	private Date orderTime; //开单日期
	private Date dateOfArrival; //到货日期
	private String lbxStatus;	//lbx入库状态
	private String sapStatus;	//推送sap状态

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getVbelnDn1() {
		return vbelnDn1;
	}

	public void setVbelnDn1(String vbelnDn1) {
		this.vbelnDn1 = vbelnDn1;
	}

	public String getLbx() {
		return lbx;
	}

	public void setLbx(String lbx) {
		this.lbx = lbx;
	}

	public Date getZspdt() {
		return zspdt;
	}

	public void setZspdt(Date zspdt) {
		this.zspdt = zspdt;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getZkStatus() {
		return zkStatus;
	}

	public void setZkStatus(String zkStatus) {
		this.zkStatus = zkStatus;
	}

	public String getMaterielCode() {
		return materielCode;
	}

	public void setMaterielCode(String materielCode) {
		this.materielCode = materielCode;
	}

	public String getMaterielName() {
		return materielName;
	}

	public void setMaterielName(String materielName) {
		this.materielName = materielName;
	}

	public String getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}

	public String getProductGroupName() {
		return productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getTotalVolume() {
		return totalVolume;
	}

	public void setTotalVolume(double totalVolume) {
		this.totalVolume = totalVolume;
	}

	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	public String getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	public String getZqItemNo() {
		return zqItemNo;
	}

	public void setZqItemNo(String zqItemNo) {
		this.zqItemNo = zqItemNo;
	}

	public String getZqRemark() {
		return zqRemark;
	}

	public void setZqRemark(String zqRemark) {
		this.zqRemark = zqRemark;
	}

	public String getJdName() {
		return jdName;
	}

	public void setJdName(String jdName) {
		this.jdName = jdName;
	}

	public String getDeliveryCode() {
		return deliveryCode;
	}

	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}

	public String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public String getDistributionCentre() {
		return distributionCentre;
	}

	public void setDistributionCentre(String distributionCentre) {
		this.distributionCentre = distributionCentre;
	}

	public String getDistributionCentreName() {
		return distributionCentreName;
	}

	public void setDistributionCentreName(String distributionCentreName) {
		this.distributionCentreName = distributionCentreName;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getDateOfArrival() {
		return dateOfArrival;
	}

	public void setDateOfArrival(Date dateOfArrival) {
		this.dateOfArrival = dateOfArrival;
	}

	public String getWhCode() {
		return whCode;
	}

	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}

	public String getLbxStatus() {
		return lbxStatus;
	}

	public void setLbxStatus(String lbxStatus) {
		this.lbxStatus = lbxStatus;
	}

	public String getVbelnDn5() {
		return vbelnDn5;
	}

	public void setVbelnDn5(String vbelnDn5) {
		this.vbelnDn5 = vbelnDn5;
	}

	public String getSapStatus() {
		return sapStatus;
	}

	public void setSapStatus(String sapStatus) {
		this.sapStatus = sapStatus;
	}

	public String getZqStatus() {
		return zqStatus;
	}

	public void setZqStatus(String zqStatus) {
		this.zqStatus = zqStatus;
	}

	public String getZkRemark() {
		return zkRemark;
	}

	public void setZkRemark(String zkRemark) {
		this.zkRemark = zkRemark;
	}

	public String getVbelnSpare() {
		return vbelnSpare;
	}

	public void setVbelnSpare(String vbelnSpare) {
		this.vbelnSpare = vbelnSpare;
	}
}
