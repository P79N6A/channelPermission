package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ZfbOrdersDetailsMatching implements Serializable{
    private Long id;

    private String accountsNo;

    private String businessNo;

    private String ordersn;

    private String productName;

    private Date createTime;

    private String memberLoginId;

    private BigDecimal incomeMoney;

    private BigDecimal expenditureMoney;

    private BigDecimal accountBalance;

    private String paymentCode;

    private String paymentName;

    private String businessTypeCode;

    private String businessTypeName;

    private String remake;

    private String taobaoBusinessType;

    private String source;

    private BigDecimal orderamount;
    private Integer differenceStatus;
    private Integer oId;
    private BigDecimal billAmount;
    public Long getId() {
        return id;
    }

    public Integer getDifferenceStatus() {
		return differenceStatus;
	}

	public void setDifferenceStatus(Integer differenceStatus) {
		this.differenceStatus = differenceStatus;
	}

	public void setId(Long id) {
        this.id = id;
    }

    public String getAccountsNo() {
        return accountsNo;
    }

    public void setAccountsNo(String accountsNo) {
        this.accountsNo = accountsNo == null ? null : accountsNo.trim();
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo == null ? null : businessNo.trim();
    }

    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn == null ? null : ordersn.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public BigDecimal getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(BigDecimal billAmount) {
		this.billAmount = billAmount;
	}

	public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMemberLoginId() {
        return memberLoginId;
    }

    public void setMemberLoginId(String memberLoginId) {
        this.memberLoginId = memberLoginId == null ? null : memberLoginId.trim();
    }

 

    public BigDecimal getIncomeMoney() {
		return incomeMoney;
	}

	public void setIncomeMoney(BigDecimal incomeMoney) {
		this.incomeMoney = incomeMoney;
	}

	public BigDecimal getExpenditureMoney() {
		return expenditureMoney;
	}

	public void setExpenditureMoney(BigDecimal expenditureMoney) {
		this.expenditureMoney = expenditureMoney;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode == null ? null : paymentCode.trim();
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName == null ? null : paymentName.trim();
    }

    public String getBusinessTypeCode() {
        return businessTypeCode;
    }

    public void setBusinessTypeCode(String businessTypeCode) {
        this.businessTypeCode = businessTypeCode == null ? null : businessTypeCode.trim();
    }

    public String getBusinessTypeName() {
        return businessTypeName;
    }

    public void setBusinessTypeName(String businessTypeName) {
        this.businessTypeName = businessTypeName == null ? null : businessTypeName.trim();
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake == null ? null : remake.trim();
    }

    public String getTaobaoBusinessType() {
        return taobaoBusinessType;
    }

    public void setTaobaoBusinessType(String taobaoBusinessType) {
        this.taobaoBusinessType = taobaoBusinessType == null ? null : taobaoBusinessType.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public BigDecimal getOrderamount() {
        return orderamount;
    }

    public void setOrderamount(BigDecimal orderamount) {
        this.orderamount = orderamount;
    }

	public Integer getoId() {
		return oId;
	}

	public void setoId(Integer oId) {
		this.oId = oId;
	}
}