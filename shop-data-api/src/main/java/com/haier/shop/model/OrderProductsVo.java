package com.haier.shop.model;



/**
 * 吴坤洋 2017-10-25
 * @author wukunyang
 *
 */
public class OrderProductsVo extends  OrderProducts{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8926009407561108073L;
	private Integer orowId; //订单主键
//	private String cOrderSn; //网单号
	private String orderSn;//订单号
	private double money; //金额
	private String statusTs;
	private String receiptAddTimeMin; //开票时间开始
	private String receiptAddTimeMax;//开票时间结束
	private String isSelfSell;//是否自营
	private String numberMin;//数量 起始
	private String numberMax;//数量 终止
	private Integer rows; //分页用
	private Integer page;//分页
	private String mobile;//手机号
	private String consignee;//收货人
	private String source;//订单来源
	private String paymentCode;//支付方式code
	private String paymentStatus;//	支付状态
	private String orderStatus;//订单状态
	private String firstConfirmTime;//首次确认时间
	private String isNotConfirm;//是否是无需确认的订单
	private String isTsCode;//是否转运
	private String isBackend;//是否为后台添加的订单
	private String autoConfirmNumMin;//已确认次数
	private String autoConfirmNumMax;//已确认次数 结束
	private String payTimeMin;//付款时间开始
	private String payTimeMax;// 付款时间结束
	private String confirmTimeMin;//订单确认时间 开始
	private String confirmTimeMax;//订单确认时间 结束
	private String sellPeople;//销售代表
	private String isProduceDaily;//是否日日单
	private String lessShipTimeMin;//LES出库时间
	private String lessShipTimeMax;//LES出库时间 结束
	private String addTimeMin;//下单时间
	private String addTimeMix;//下单时间结束
	private String remark;//备注
	private String smManualTime;//转人工确认时间
	private String shippingTimeTs;//发货时间用来接收string类型的时间格式
	private String cpayTimeTs;//子订单付款时间 用来接收string类型的时间格式
	private String sourceOrderSn;//外部订单号
	private String tradeSn;//交易流水号
	private String paymentName;//支付方式名称
	private String type;//发票类型
	private String istate;//发票 审核状态0-待审核,1-审核通过,2-拒绝
	private String invoiceTitle;//发票抬头
	private String electricFlag;//电子发票标志，1：电子发票；0：纸质发票
	private String productTypeTs;//商品类型 用来接收String类型字符串
	private String eaiWriteState;//开票状态(KPZT)
	private String checkCode;//校验码
	private byte isCod;//是否货到付款
	private String firstConfirmPerson;//首次确认人
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIstate() {
		return istate;
	}
	public void setIstate(String istate) {
		this.istate = istate;
	}
	public String getEaiWriteState() {
		return eaiWriteState;
	}
	public void setEaiWriteState(String eaiWriteState) {
		this.eaiWriteState = eaiWriteState;
	}
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	public String getProductTypeTs() {
		return productTypeTs;
	}
	public void setProductTypeTs(String productTypeTs) {
		this.productTypeTs = productTypeTs;
	}
	public Integer getOrowId() {
		return orowId;
	}
	public void setOrowId(Integer orowId) {
		this.orowId = orowId;
	}
	public String getShippingTimeTs() {
		return shippingTimeTs;
	}
	public void setShippingTimeTs(String shippingTimeTs) {
		this.shippingTimeTs = shippingTimeTs;
	}
	public String getCpayTimeTs() {
		return cpayTimeTs;
	}
	public void setCpayTimeTs(String cpayTimeTs) {
		this.cpayTimeTs = cpayTimeTs;
	}
	public String getSourceOrderSn() {
		return sourceOrderSn;
	}
	public void setSourceOrderSn(String sourceOrderSn) {
		this.sourceOrderSn = sourceOrderSn;
	}
	public String getTradeSn() {
		return tradeSn;
	}
	public void setTradeSn(String tradeSn) {
		this.tradeSn = tradeSn;
	}
	public String getPaymentName() {
		return paymentName;
	}
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	public String getElectricFlag() {
		return electricFlag;
	}
	public void setElectricFlag(String electricFlag) {
		this.electricFlag = electricFlag;
	}
	public String getSmManualTime() {
		return smManualTime;
	}
	public void setSmManualTime(String smManualTime) {
		this.smManualTime = smManualTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAddTimeMin() {
		return addTimeMin;
	}
	public void setAddTimeMin(String addTimeMin) {
		this.addTimeMin = addTimeMin;
	}
	public String getAddTimeMix() {
		return addTimeMix;
	}
	public void setAddTimeMix(String addTimeMix) {
		this.addTimeMix = addTimeMix;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getPaymentCode() {
		return paymentCode;
	}
	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getFirstConfirmTime() {
		return firstConfirmTime;
	}
	public void setFirstConfirmTime(String firstConfirmTime) {
		this.firstConfirmTime = firstConfirmTime;
	}
	public String getIsNotConfirm() {
		return isNotConfirm;
	}
	public void setIsNotConfirm(String isNotConfirm) {
		this.isNotConfirm = isNotConfirm;
	}
	public String getIsTsCode() {
		return isTsCode;
	}
	public void setIsTsCode(String isTsCode) {
		this.isTsCode = isTsCode;
	}
	public String getIsBackend() {
		return isBackend;
	}
	public void setIsBackend(String isBackend) {
		this.isBackend = isBackend;
	}
	public String getAutoConfirmNumMin() {
		return autoConfirmNumMin;
	}
	public void setAutoConfirmNumMin(String autoConfirmNumMin) {
		this.autoConfirmNumMin = autoConfirmNumMin;
	}
	public String getAutoConfirmNumMax() {
		return autoConfirmNumMax;
	}
	public void setAutoConfirmNumMax(String autoConfirmNumMax) {
		this.autoConfirmNumMax = autoConfirmNumMax;
	}
	public String getPayTimeMin() {
		return payTimeMin;
	}
	public void setPayTimeMin(String payTimeMin) {
		this.payTimeMin = payTimeMin;
	}
	public String getPayTimeMax() {
		return payTimeMax;
	}
	public void setPayTimeMax(String payTimeMax) {
		this.payTimeMax = payTimeMax;
	}
	public String getConfirmTimeMin() {
		return confirmTimeMin;
	}
	public void setConfirmTimeMin(String confirmTimeMin) {
		this.confirmTimeMin = confirmTimeMin;
	}
	public String getConfirmTimeMax() {
		return confirmTimeMax;
	}
	public void setConfirmTimeMax(String confirmTimeMax) {
		this.confirmTimeMax = confirmTimeMax;
	}
	public String getSellPeople() {
		return sellPeople;
	}
	public void setSellPeople(String sellPeople) {
		this.sellPeople = sellPeople;
	}
	public String getIsProduceDaily() {
		return isProduceDaily;
	}
	public void setIsProduceDaily(String isProduceDaily) {
		this.isProduceDaily = isProduceDaily;
	}
	public String getLessShipTimeMin() {
		return lessShipTimeMin;
	}
	public void setLessShipTimeMin(String lessShipTimeMin) {
		this.lessShipTimeMin = lessShipTimeMin;
	}
	public String getLessShipTimeMax() {
		return lessShipTimeMax;
	}
	public void setLessShipTimeMax(String lessShipTimeMax) {
		this.lessShipTimeMax = lessShipTimeMax;
	}
	public String getIsSelfSell() {
		return isSelfSell;
	}
	public void setIsSelfSell(String isSelfSell) {
		this.isSelfSell = isSelfSell;
	}
	public String getNumberMin() {
		return numberMin;
	}
	public void setNumberMin(String numberMin) {
		this.numberMin = numberMin;
	}
	public String getNumberMax() {
		return numberMax;
	}
	public void setNumberMax(String numberMax) {
		this.numberMax = numberMax;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public String getReceiptAddTimeMin() {
		return receiptAddTimeMin;
	}
	public void setReceiptAddTimeMin(String receiptAddTimeMin) {
		this.receiptAddTimeMin = receiptAddTimeMin;
	}
	public String getReceiptAddTimeMax() {
		return receiptAddTimeMax;
	}
	public void setReceiptAddTimeMax(String receiptAddTimeMax) {
		this.receiptAddTimeMax = receiptAddTimeMax;
	}
	public String getStatusTs() {
		return statusTs;
	}
	public void setStatusTs(String statusTs) {
		this.statusTs = statusTs;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
//	
//	public String getcOrderSn() {
//		return cOrderSn;
//	}
//	public void setcOrderSn(String cOrderSn) {
//		this.cOrderSn = cOrderSn;
//	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public byte getIsCod() {
		return isCod;
	}
	public void setIsCod(byte isCod) {
		this.isCod = isCod;
	}
	public String getFirstConfirmPerson() {
		return firstConfirmPerson;
	}
	public void setFirstConfirmPerson(String firstConfirmPerson) {
		this.firstConfirmPerson = firstConfirmPerson;
	}

}
