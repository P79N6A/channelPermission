package com.haier.shop.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class QueryZFBOrderParameter implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer smConfirmStatus;		//是否转人工处理   1是，2否
	private Integer isCod;					//货到付款订单   1是，2否
	private Integer codConfirmState;		//货到付款确认状态   0无需审核，1待审核，2审核确认通过，3审核确认无效
	private String orderSn;					//请输入订单号
	private String sourceOrderNo;			//来源订单号
	private String mobile;					//手机号码
	private String consignee;				//收货人
	private String productName;				//商品名称
	private Integer isGiftCardOrder;		//礼品卡订单  1是，2否
	private String source;					//订单来源
	private String orderMoney;				//订单金额
	private Integer orderStatus;			//订单状态   200未确认，201已确认，202已取消，203已完成
	private String paymentName;				//支付方式
	private String paymentCode;				//支付码
	private Integer paymentStatus;			//支付状态  100未付款,101已付款,102待退款,103已退款
	private Integer timesBegine;			//已确认次数开始
	private Integer timesEnd;				//已确认次数结束
	private String addTimeBegine;			//订单生成时间开始
	private String addTimeEnd;				//订单生成时间结束
	private String modifiedBegine;			//订单最后更新时间开始
	private String modifiedEnd;				//订单最后更新时间结束
	private String orderAddTime;			//订单时间开始
	private String orderModified;			//订单更新时间结束
	private Integer orderType;				//订单类型    0普通订单，2定金尾款订单
	private Integer invoiceType;			//发票类型    1增票，2普票
	private Integer provinceId;				//省份id
	private String payTimeSort;				//asc按付款时间升序显示,desc按付款时间降序显示
	private Integer isUsePoint;				//是否使用积分   1是，0否
	private Integer isProduceDaily;			//是否日日单   1是，0否
	private Integer rows;					//行数
	private Integer page;					//页数
	
	private String payTime;
	private String syncTime;
	private String firstConfirmTime;
	private Integer autoConfirmNum;
	private Integer differenceStatus;
	private String productId;//网单id（主键）


	private String memberLoginId;			//对方账户
	private String businessTypeCode;
	private String businessTypeName;			//业务类型
	private String incomeMoneymin;			//收入金额最小值
	private String incomeMoneymax;			//收入金额最大值
	private String expenditureMoneymin;			//支出金额最小值
	private String expenditureMoneymax;			//支出金额最大值
	private String  remake ;			//备注
	private String shopName;
	private String supplier; //供应商
	private Integer pushResult; //推送结果
	
	
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public Integer getPushResult() {
		return pushResult;
	}
	public void setPushResult(Integer pushResult) {
		this.pushResult = pushResult;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Integer getDifferenceStatus() {
		return differenceStatus;
	}
	public void setDifferenceStatus(Integer differenceStatus) {
		this.differenceStatus = differenceStatus;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Integer getSmConfirmStatus() {
		return smConfirmStatus;
	}
	public void setSmConfirmStatus(Integer smConfirmStatus) {
		this.smConfirmStatus = smConfirmStatus;
	}
	public Integer getIsCod() {
		return isCod;
	}
	public void setIsCod(Integer isCod) {
		this.isCod = isCod;
	}
	public Integer getCodConfirmState() {
		return codConfirmState;
	}
	public void setCodConfirmState(Integer codConfirmState) {
		this.codConfirmState = codConfirmState;
	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public String getSourceOrderNo() {
		return sourceOrderNo;
	}
	public void setSourceOrderNo(String sourceOrderNo) {
		this.sourceOrderNo = sourceOrderNo;
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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getIsGiftCardOrder() {
		return isGiftCardOrder;
	}
	public void setIsGiftCardOrder(Integer isGiftCardOrder) {
		this.isGiftCardOrder = isGiftCardOrder;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPaymentName() {
		return paymentName;
	}
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}
	
	public String getBusinessTypeCode() {
		return businessTypeCode;
	}
	public void setBusinessTypeCode(String businessTypeCode) {
		this.businessTypeCode = businessTypeCode;
	}
	public Integer getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public Integer getTimesBegine() {
		return timesBegine;
	}
	public void setTimesBegine(Integer timesBegine) {
		this.timesBegine = timesBegine;
	}
	public Integer getTimesEnd() {
		return timesEnd;
	}
	public void setTimesEnd(Integer timesEnd) {
		this.timesEnd = timesEnd;
	}
	public String getAddTimeBegine() {
		return addTimeBegine;
	}
	public void setAddTimeBegine(String addTimeBegine) {
		this.addTimeBegine = addTimeBegine;
	}
	public String getAddTimeEnd() {
		return addTimeEnd;
	}
	public void setAddTimeEnd(String addTimeEnd) {
		this.addTimeEnd = addTimeEnd;
	}
	public String getModifiedBegine() {
		return modifiedBegine;
	}
	public void setModifiedBegine(String modifiedBegine) {
		this.modifiedBegine = modifiedBegine;
	}
	public String getModifiedEnd() {
		return modifiedEnd;
	}
	public void setModifiedEnd(String modifiedEnd) {
		this.modifiedEnd = modifiedEnd;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public Integer getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(Integer invoiceType) {
		this.invoiceType = invoiceType;
	}
	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	public String getPayTimeSort() {
		return payTimeSort;
	}
	public void setPayTimeSort(String payTimeSort) {
		this.payTimeSort = payTimeSort;
	}
	public Integer getIsUsePoint() {
		return isUsePoint;
	}
	public void setIsUsePoint(Integer isUsePoint) {
		this.isUsePoint = isUsePoint;
	}
	public Integer getIsProduceDaily() {
		return isProduceDaily;
	}
	public void setIsProduceDaily(Integer isProduceDaily) {
		this.isProduceDaily = isProduceDaily;
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
	public String getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(String orderMoney) {
		this.orderMoney = orderMoney;
	}
	public String getOrderAddTime() {
		return orderAddTime;
	}
	public void setOrderAddTime(String orderAddTime) {
		this.orderAddTime = orderAddTime;
	}
	public String getOrderModified() {
		return orderModified;
	}
	public void setOrderModified(String orderModified) {
		this.orderModified = orderModified;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getSyncTime() {
		return syncTime;
	}
	public void setSyncTime(String syncTime) {
		this.syncTime = syncTime;
	}
	public String getFirstConfirmTime() {
		return firstConfirmTime;
	}
	public void setFirstConfirmTime(String firstConfirmTime) {
		this.firstConfirmTime = firstConfirmTime;
	}
	public Integer getAutoConfirmNum() {
		return autoConfirmNum;
	}
	public void setAutoConfirmNum(Integer autoConfirmNum) {
		this.autoConfirmNum = autoConfirmNum;
	}
	public String getPaymentCode() {
		return paymentCode;
	}
	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	public String getMemberLoginId() {
		return memberLoginId;
	}

	public void setMemberLoginId(String memberLoginId) {
		this.memberLoginId = memberLoginId;
	}

	public String getBusinessTypeName() {
		return businessTypeName;
	}

	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}

	public String getIncomeMoneymin() {
		return incomeMoneymin;
	}

	public void setIncomeMoneymin(String incomeMoneymin) {
		this.incomeMoneymin = incomeMoneymin;
	}

	public String getIncomeMoneymax() {
		return incomeMoneymax;
	}

	public void setIncomeMoneymax(String incomeMoneymax) {
		this.incomeMoneymax = incomeMoneymax;
	}

	public String getExpenditureMoneymin() {
		return expenditureMoneymin;
	}

	public void setExpenditureMoneymin(String expenditureMoneymin) {
		this.expenditureMoneymin = expenditureMoneymin;
	}

	public String getExpenditureMoneymax() {
		return expenditureMoneymax;
	}

	public void setExpenditureMoneymax(String expenditureMoneymax) {
		this.expenditureMoneymax = expenditureMoneymax;
	}

	public String getRemake() {
		return remake;
	}

	public void setRemake(String remake) {
		this.remake = remake;
	}
}
