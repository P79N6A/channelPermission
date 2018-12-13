package com.haier.shop.model;

import java.io.Serializable;
import java.util.Date;

public class ZfbOrdersDetails implements Serializable{
    private Long id;

    private String accountsNo;

    private String businessNo;

    private String ordersn;

    private String productName;

    private Date createTime;

    private String memberLoginId;

    private Long incomeMoney;

    private Long expenditureMoney;

    private Long accountBalance;

    private String paymentCode;

    private String paymentName;

    private String businessTypeCode;

    private String businessTypeName;

    private String remake;

    private String taobaoBusinessType;

    private Byte matchingStatus;

    private String fileName;
    
    private String shopName;
    
    private String time;
    
    
    
    

    public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Long getId() {
        return id;
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

    public Long getIncomeMoney() {
        return incomeMoney;
    }

    public void setIncomeMoney(Long incomeMoney) {
        this.incomeMoney = incomeMoney;
    }

    public Long getExpenditureMoney() {
        return expenditureMoney;
    }

    public void setExpenditureMoney(Long expenditureMoney) {
        this.expenditureMoney = expenditureMoney;
    }

    public Long getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Long accountBalance) {
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

    public Byte getMatchingStatus() {
        return matchingStatus;
    }

    public void setMatchingStatus(Byte matchingStatus) {
        this.matchingStatus = matchingStatus;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }
}