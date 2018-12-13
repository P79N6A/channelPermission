package com.haier.shop.model;

import java.io.Serializable;
import java.util.Date;

public class TransactionSap implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5210927320974646758L;

	private Long id;

    private Date createTime;

    private String supplier;

    private String businessType;

    private Integer startMoney;

    private Integer endMoney;

    private String pushNotes;

    private String accountNumber;

    private Byte pushResult;
    
    private String time;

    public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Long getId() {
        return id;
    }
    private String requestResult;

    private String responseResult;

    public String getRequestResult() {
        return requestResult;
    }

    public void setRequestResult(String requestResult) {
        this.requestResult = requestResult == null ? null : requestResult.trim();
    }

    public String getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(String responseResult) {
        this.responseResult = responseResult == null ? null : responseResult.trim();
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    public Integer getStartMoney() {
        return startMoney;
    }

    public void setStartMoney(Integer startMoney) {
        this.startMoney = startMoney;
    }

    public Integer getEndMoney() {
        return endMoney;
    }

    public void setEndMoney(Integer endMoney) {
        this.endMoney = endMoney;
    }

    public String getPushNotes() {
        return pushNotes;
    }

    public void setPushNotes(String pushNotes) {
        this.pushNotes = pushNotes == null ? null : pushNotes.trim();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber == null ? null : accountNumber.trim();
    }

    public Byte getPushResult() {
        return pushResult;
    }

    public void setPushResult(Byte pushResult) {
        this.pushResult = pushResult;
    }
}