package com.haier.svc.api.controller.util.http.suning;

public class SuningQDZXOrder {

	public String sdcsOrderId;	//采购订单号
	public String sdcsCreateTime;	//采购订单创建时间
	public String omsCreateTime;	//采购订单支付时间
	public String distributourCode;	//分销商编码
	public String mobPhoneNum;	//买家联系电话
	public String phoneNum;	//买家联系电话（座机）
	public String provinceName;	//收件人地址（省）中文
	public String cityName;	//收件人地址（市）中文
	public String districtName;	//收件人地址（区）中文
	public String address;	//收件人地址
	public String orderRemark;	//订单备注
	public String customerName;	//顾客姓名
	public 	String cmmdtyCode;	//商品编码
	public String cmmdtyName;	//商品名称
	public String sapProductCode;	//商品类目
	public String cmmdtyBand;	//品牌
	public String purchasePrice;	//采购供价
	public String price;	//销售价
	public String saleQty;	//销售数量
	public String totalAmount;	//销售额
	public String invoiceHeader;	//发票抬头
	public String dutyParagraph;	//税号
	public String bankAccount;	//开户行
	public String bankAccountNumber;	//银行账号
	public String registeredPhone;	//注册电话
	public String registeredAddress;	//注册地址
	public String operateStyle;	//订单状态10-待发货；20-已发货；30-采购成功；40-退货中；50-关闭
	public ConsumerInvoice consumerInvoice;
	public String getSdcsOrderId() {
		return sdcsOrderId;
	}
	public void setSdcsOrderId(String sdcsOrderId) {
		this.sdcsOrderId = sdcsOrderId;
	}
	public String getSdcsCreateTime() {
		return sdcsCreateTime;
	}
	public void setSdcsCreateTime(String sdcsCreateTime) {
		this.sdcsCreateTime = sdcsCreateTime;
	}
	public String getOmsCreateTime() {
		return omsCreateTime;
	}
	public void setOmsCreateTime(String omsCreateTime) {
		this.omsCreateTime = omsCreateTime;
	}
	public String getDistributourCode() {
		return distributourCode;
	}
	public void setDistributourCode(String distributourCode) {
		this.distributourCode = distributourCode;
	}
	public String getMobPhoneNum() {
		return mobPhoneNum;
	}
	public void setMobPhoneNum(String mobPhoneNum) {
		this.mobPhoneNum = mobPhoneNum;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOrderRemark() {
		return orderRemark;
	}
	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCmmdtyCode() {
		return cmmdtyCode;
	}
	public void setCmmdtyCode(String cmmdtyCode) {
		this.cmmdtyCode = cmmdtyCode;
	}
	public String getCmmdtyName() {
		return cmmdtyName;
	}
	public void setCmmdtyName(String cmmdtyName) {
		this.cmmdtyName = cmmdtyName;
	}
	public String getSapProductCode() {
		return sapProductCode;
	}
	public void setSapProductCode(String sapProductCode) {
		this.sapProductCode = sapProductCode;
	}
	public String getCmmdtyBand() {
		return cmmdtyBand;
	}
	public void setCmmdtyBand(String cmmdtyBand) {
		this.cmmdtyBand = cmmdtyBand;
	}
	public String getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getSaleQty() {
		return saleQty;
	}
	public void setSaleQty(String saleQty) {
		this.saleQty = saleQty;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getInvoiceHeader() {
		return invoiceHeader;
	}
	public void setInvoiceHeader(String invoiceHeader) {
		this.invoiceHeader = invoiceHeader;
	}
	public String getDutyParagraph() {
		return dutyParagraph;
	}
	public void setDutyParagraph(String dutyParagraph) {
		this.dutyParagraph = dutyParagraph;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}
	public String getRegisteredPhone() {
		return registeredPhone;
	}
	public void setRegisteredPhone(String registeredPhone) {
		this.registeredPhone = registeredPhone;
	}
	public String getRegisteredAddress() {
		return registeredAddress;
	}
	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}
	public String getOperateStyle() {
		return operateStyle;
	}
	public void setOperateStyle(String operateStyle) {
		this.operateStyle = operateStyle;
	}

	public ConsumerInvoice getConsumerInvoice() {
		return consumerInvoice;
	}

	public void setConsumerInvoice(ConsumerInvoice consumerInvoice) {
		this.consumerInvoice = consumerInvoice;
	}
}
