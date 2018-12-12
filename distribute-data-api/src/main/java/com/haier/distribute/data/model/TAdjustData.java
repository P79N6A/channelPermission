package com.haier.distribute.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TAdjustData implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String cOrderSn;

    private String cOrderSnOld;

    private String sourceOrderSn;

    private String source;

    private String sellPeople;

    private String category;

    private String brand;

    private String sku;

    private String productName;

    private String consignee;

    private Date cPayTime;

    private Short number;

    private BigDecimal productAmount;

    private String cOrderStatus;

    private int period;

    private int year;

    private String invoiceStatus;

    private String settleType;

    private String sendStatus;

    private String dataStatus;

    private Date invoiceTime;

    private String auditStatus;

    private Date sendTime;

    private String businessAuditPeople;

    private String businessAuditTime;

    private String financeAuditPeople;

    private String financeAuditTime;
    
    private String attYearMonth; //核算月份
    
    private String cPayTimeStart; //付款时间开始(接收参数使用前台无法直接赋值date类型)
	private String cPayTimeEnd; //付款时间结束(接收参数使用前台无法直接赋值date类型)
	private Date payTimeStart;
	private Date payTimeEnd;
	private String channelName;
	
	
	private String invoiceTimeStart; //发票时间开始(接收参数使用前台无法直接赋值date类型)
	private String invoiceTimeEnd; //发票时间结束(接收参数使用前台无法直接赋值date类型)
	private Date fpTimeStart;
	private Date fpTimeEnd;
	
	private BigDecimal pAmount;

	public String getcOrderSn() {
		return cOrderSn;
	}

	public void setcOrderSn(String cOrderSn) {
		this.cOrderSn = cOrderSn;
	}

	public String getcOrderSnOld() {
		return cOrderSnOld;
	}

	public void setcOrderSnOld(String cOrderSnOld) {
		this.cOrderSnOld = cOrderSnOld;
	}

	public String getSourceOrderSn() {
		return sourceOrderSn;
	}

	public void setSourceOrderSn(String sourceOrderSn) {
		this.sourceOrderSn = sourceOrderSn;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSellPeople() {
		return sellPeople;
	}

	public void setSellPeople(String sellPeople) {
		this.sellPeople = sellPeople;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public Date getcPayTime() {
		return cPayTime;
	}

	public void setcPayTime(Date cPayTime) {
		this.cPayTime = cPayTime;
	}

	public Short getNumber() {
		return number;
	}

	public void setNumber(Short number) {
		this.number = number;
	}

	public BigDecimal getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(BigDecimal productAmount) {
		this.productAmount = productAmount;
	}

	public String getcOrderStatus() {
		return cOrderStatus;
	}

	public void setcOrderStatus(String cOrderStatus) {
		this.cOrderStatus = cOrderStatus;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getSettleType() {
		return settleType;
	}

	public void setSettleType(String settleType) {
		this.settleType = settleType;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	public String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}

	public Date getInvoiceTime() {
		return invoiceTime;
	}

	public void setInvoiceTime(Date invoiceTime) {
		this.invoiceTime = invoiceTime;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getBusinessAuditPeople() {
		return businessAuditPeople;
	}

	public void setBusinessAuditPeople(String businessAuditPeople) {
		this.businessAuditPeople = businessAuditPeople;
	}

	public String getBusinessAuditTime() {
		return businessAuditTime;
	}

	public void setBusinessAuditTime(String businessAuditTime) {
		this.businessAuditTime = businessAuditTime;
	}

	public String getFinanceAuditPeople() {
		return financeAuditPeople;
	}

	public void setFinanceAuditPeople(String financeAuditPeople) {
		this.financeAuditPeople = financeAuditPeople;
	}

	public String getFinanceAuditTime() {
		return financeAuditTime;
	}

	public void setFinanceAuditTime(String financeAuditTime) {
		this.financeAuditTime = financeAuditTime;
	}

	public String getAttYearMonth() {
		return attYearMonth;
	}

	public void setAttYearMonth(String attYearMonth) {
		this.attYearMonth = attYearMonth;
	}

	public String getcPayTimeStart() {
		return cPayTimeStart;
	}

	public void setcPayTimeStart(String cPayTimeStart) {
		this.cPayTimeStart = cPayTimeStart;
	}

	public String getcPayTimeEnd() {
		return cPayTimeEnd;
	}

	public void setcPayTimeEnd(String cPayTimeEnd) {
		this.cPayTimeEnd = cPayTimeEnd;
	}

	public String getInvoiceTimeStart() {
		return invoiceTimeStart;
	}

	public void setInvoiceTimeStart(String invoiceTimeStart) {
		this.invoiceTimeStart = invoiceTimeStart;
	}

	public String getInvoiceTimeEnd() {
		return invoiceTimeEnd;
	}

	public void setInvoiceTimeEnd(String invoiceTimeEnd) {
		this.invoiceTimeEnd = invoiceTimeEnd;
	}

	public Date getPayTimeStart() {
		return payTimeStart;
	}

	public void setPayTimeStart(Date payTimeStart) {
		this.payTimeStart = payTimeStart;
	}

	public Date getPayTimeEnd() {
		return payTimeEnd;
	}

	public void setPayTimeEnd(Date payTimeEnd) {
		this.payTimeEnd = payTimeEnd;
	}

	public Date getFpTimeStart() {
		return fpTimeStart;
	}

	public void setFpTimeStart(Date fpTimeStart) {
		this.fpTimeStart = fpTimeStart;
	}

	public Date getFpTimeEnd() {
		return fpTimeEnd;
	}

	public void setFpTimeEnd(Date fpTimeEnd) {
		this.fpTimeEnd = fpTimeEnd;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public BigDecimal getpAmount() {
		return pAmount;
	}

	public void setpAmount(BigDecimal pAmount) {
		this.pAmount = pAmount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
}