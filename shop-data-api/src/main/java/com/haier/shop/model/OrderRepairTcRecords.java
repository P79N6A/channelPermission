package com.haier.shop.model;

public class OrderRepairTcRecords {

	private Integer id; 
	private Integer addTime;//添加时间
	private String vomTcSn;//vom退仓单号
	private String sku;//货品编码
	private Integer num;//计划退仓数量
	private String sCode;//库位编码
	private String scodeName;//库位名称
	private Integer orderRepairTcId;//退仓单id
	private String productName;//货品名称
	private Integer orderRepairId;//退货申请单ID
	private String orderRepairSn;//退货单号
	private String relationSn;//退仓关联单号
	private String lesOrderSn;//提单号
	private Integer lesOrderSnTime;//开提单成功时间
	private String lesOutPing;//出入库凭证号
	private Integer lesOutPingTime;//回传凭证号时间
	private Integer hpCheckResult;//hp质检结果
	private String caiNiaoCheckResult;//菜鸟库存类型
	private Integer isTj;//是否套机
	private Integer success;//处理状态
	private Integer hpCheckType;//hp质检责任批次
	private String wwwScode;//库位编码
	private String wwwSku;//商城编码
	private Integer realNum;//实际退仓数量
	private Integer vomNum;//物流入库数量
	private String machineNum;//机编代码
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAddTime() {
		return addTime;
	}
	public void setAddTime(Integer addTime) {
		this.addTime = addTime;
	}
	public String getVomTcSn() {
		return vomTcSn;
	}
	public void setVomTcSn(String vomTcSn) {
		this.vomTcSn = vomTcSn;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getsCode() {
		return sCode;
	}
	public void setsCode(String sCode) {
		this.sCode = sCode;
	}
	public String getScodeName() {
		return scodeName;
	}
	public void setScodeName(String scodeName) {
		this.scodeName = scodeName;
	}
	public Integer getOrderRepairTcId() {
		return orderRepairTcId;
	}
	public void setOrderRepairTcId(Integer orderRepairTcId) {
		this.orderRepairTcId = orderRepairTcId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getOrderRepairId() {
		return orderRepairId;
	}
	public void setOrderRepairId(Integer orderRepairId) {
		this.orderRepairId = orderRepairId;
	}
	public String getOrderRepairSn() {
		return orderRepairSn;
	}
	public void setOrderRepairSn(String orderRepairSn) {
		this.orderRepairSn = orderRepairSn;
	}
	public String getRelationSn() {
		return relationSn;
	}
	public void setRelationSn(String relationSn) {
		this.relationSn = relationSn;
	}
	public String getLesOrderSn() {
		return lesOrderSn;
	}
	public void setLesOrderSn(String lesOrderSn) {
		this.lesOrderSn = lesOrderSn;
	}
	public Integer getLesOrderSnTime() {
		return lesOrderSnTime;
	}
	public void setLesOrderSnTime(Integer lesOrderSnTime) {
		this.lesOrderSnTime = lesOrderSnTime;
	}
	public String getLesOutPing() {
		return lesOutPing;
	}
	public void setLesOutPing(String lesOutPing) {
		this.lesOutPing = lesOutPing;
	}
	public Integer getLesOutPingTime() {
		return lesOutPingTime;
	}
	public void setLesOutPingTime(Integer lesOutPingTime) {
		this.lesOutPingTime = lesOutPingTime;
	}
	public Integer getHpCheckResult() {
		return hpCheckResult;
	}
	public void setHpCheckResult(Integer hpCheckResult) {
		this.hpCheckResult = hpCheckResult;
	}
	public String getCaiNiaoCheckResult() {
		return caiNiaoCheckResult;
	}
	public void setCaiNiaoCheckResult(String caiNiaoCheckResult) {
		this.caiNiaoCheckResult = caiNiaoCheckResult;
	}
	public Integer getIsTj() {
		return isTj;
	}
	public void setIsTj(Integer isTj) {
		this.isTj = isTj;
	}
	public Integer getSuccess() {
		return success;
	}
	public void setSuccess(Integer success) {
		this.success = success;
	}
	public Integer getHpCheckType() {
		return hpCheckType;
	}
	public void setHpCheckType(Integer hpCheckType) {
		this.hpCheckType = hpCheckType;
	}
	public String getWwwScode() {
		return wwwScode;
	}
	public void setWwwScode(String wwwScode) {
		this.wwwScode = wwwScode;
	}
	public String getWwwSku() {
		return wwwSku;
	}
	public void setWwwSku(String wwwSku) {
		this.wwwSku = wwwSku;
	}
	public Integer getRealNum() {
		return realNum;
	}
	public void setRealNum(Integer realNum) {
		this.realNum = realNum;
	}
	public Integer getVomNum() {
		return vomNum;
	}
	public void setVomNum(Integer vomNum) {
		this.vomNum = vomNum;
	}
	public String getMachineNum() {
		return machineNum;
	}
	public void setMachineNum(String machineNum) {
		this.machineNum = machineNum;
	}
}
