package com.haier.shop.model;

import java.io.Serializable;
import java.util.List;

public class VOMSynOrderRequire implements Serializable {

	// 订单号
	private String orderNo = "";
	// 来源订单号
	private String sourceSn = "";
	// 订单类型
	private String orderType = "";
	// 业务类型
	private String busType = "";
	// 订单时间
	private String orderDate = "";
	// 仓库编码
	private String storeCode = "";
	// 收货人所在省
	private String province = "";
	// 收货人所在市
	private String city = "";
	// 收货人所在区
	private String county = "";
	// 详细地址
	private String addr = "";
	// 国标码
	private String gbCode = "";
	// 收货人姓名
	private String name = "";
	// 联系方式
	private String mobile = "";
	// 固定电话
	private String tel = "";
	// 邮政编码
	private String postCode = "";
	// 预约送货时间
	private String delDate = "";
	// 预约预约时间
	private String insDate = "";
	// 前续订单号
	private String reorder = "";
	// 运费
	private Double freight = 0.0;
	// 订单总金额
	private Double billSum = 0.0;
	// 代收金额：应收欠款
	private Double billOwe = 0.0;
	// 付款状态：P1-已付款，P2-代收货款
	private String payState = "";
	// 付款时间
	private String payTime = "";
	// 支付类别
	private String payType = "";
	// 是否需要开具发票：1.是 2.否
	private String isInv = "";
	// 发票类型
	private String invType = "";
	// 发票抬头
	private String invRise = "";
	// 纳税人登记号
	private String taxBearer = "";
	// 发票地址
	private String recAddr = "";
	// 发票开户行帐号
	private String recAcc = "";
	// 发票开户行
	private String recBank = "";
	// 发货人姓名
	private String sname = "";
	// 发货人姓名
	private String sprovince = "";
	// 发货人所在市
	private String scity = "";
	// 发货人所在县/区
	private String scounty = "";
	// 发货人详细地址
	private String saddr = "";
	// 发货人联系电话
	private String smobile = "";
	// 发货人固定电话
	private String stel = "";
	// 订单标记
	private String busFlag = "";
	//备注
	private String remark = "";
	//子订单 
	private List<VOMSynSubOrderRequire> subOrderList;
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getSourceSn() {
		return sourceSn;
	}
	public void setSourceSn(String sourceSn) {
		this.sourceSn = sourceSn;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getBusType() {
		return busType;
	}
	public void setBusType(String busType) {
		this.busType = busType;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getGbCode() {
		return gbCode;
	}
	public void setGbCode(String gbCode) {
		this.gbCode = gbCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getDelDate() {
		return delDate;
	}
	public void setDelDate(String delDate) {
		this.delDate = delDate;
	}
	public String getInsDate() {
		return insDate;
	}
	public void setInsDate(String insDate) {
		this.insDate = insDate;
	}
	public String getReorder() {
		return reorder;
	}
	public void setReorder(String reorder) {
		this.reorder = reorder;
	}
	public Double getFreight() {
		return freight;
	}
	public void setFreight(Double freight) {
		this.freight = freight;
	}
	public Double getBillSum() {
		return billSum;
	}
	public void setBillSum(Double billSum) {
		this.billSum = billSum;
	}
	public Double getBillOwe() {
		return billOwe;
	}
	public void setBillOwe(Double billOwe) {
		this.billOwe = billOwe;
	}
	public String getPayState() {
		return payState;
	}
	public void setPayState(String payState) {
		this.payState = payState;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getIsInv() {
		return isInv;
	}
	public void setIsInv(String isInv) {
		this.isInv = isInv;
	}
	public String getInvType() {
		return invType;
	}
	public void setInvType(String invType) {
		this.invType = invType;
	}
	public String getInvRise() {
		return invRise;
	}
	public void setInvRise(String invRise) {
		this.invRise = invRise;
	}
	public String getTaxBearer() {
		return taxBearer;
	}
	public void setTaxBearer(String taxBearer) {
		this.taxBearer = taxBearer;
	}
	public String getRecAddr() {
		return recAddr;
	}
	public void setRecAddr(String recAddr) {
		this.recAddr = recAddr;
	}
	public String getRecAcc() {
		return recAcc;
	}
	public void setRecAcc(String recAcc) {
		this.recAcc = recAcc;
	}
	public String getRecBank() {
		return recBank;
	}
	public void setRecBank(String recBank) {
		this.recBank = recBank;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getSprovince() {
		return sprovince;
	}
	public void setSprovince(String sprovince) {
		this.sprovince = sprovince;
	}
	public String getScity() {
		return scity;
	}
	public void setScity(String scity) {
		this.scity = scity;
	}
	public String getScounty() {
		return scounty;
	}
	public void setScounty(String scounty) {
		this.scounty = scounty;
	}
	public String getSaddr() {
		return saddr;
	}
	public void setSaddr(String saddr) {
		this.saddr = saddr;
	}
	public String getSmobile() {
		return smobile;
	}
	public void setSmobile(String smobile) {
		this.smobile = smobile;
	}
	public String getStel() {
		return stel;
	}
	public void setStel(String stel) {
		this.stel = stel;
	}
	public String getBusFlag() {
		return busFlag;
	}
	public void setBusFlag(String busFlag) {
		this.busFlag = busFlag;
	}
	public List<VOMSynSubOrderRequire> getSubOrderList() {
		return subOrderList;
	}
	public void setSubOrderList(List<VOMSynSubOrderRequire> subOrderList) {
		this.subOrderList = subOrderList;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	
	
	
}
