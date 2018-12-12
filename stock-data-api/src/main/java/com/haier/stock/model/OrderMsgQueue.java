package com.haier.stock.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderMsgQueue implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer msgQueueId;

    private String corderSn;

    private BigDecimal orderAmount;

    private Integer isCod;

    private String receiverName;

    private String receiverMobile;

    private String receiverAddress;

    private String storeName;

    private String memberMobile;

    private Date addTime;

    private Byte isWechatSuccess;

    private Byte isAppPushSuccess;

    private Date lastSendTime;

    private String lastWechatErrmsg;

    private String lastAppErrmsg;

    private Byte status;

    private Boolean retryTimes;

    private Integer memberId;

    private String sku;

    private Integer num;

    private String productName;

    private Integer orderProductId;

    public Integer getMsgQueueId() {
        return msgQueueId;
    }

    public void setMsgQueueId(Integer msgQueueId) {
        this.msgQueueId = msgQueueId;
    }


    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getIsCod() {
        return isCod;
    }

    public void setIsCod(Integer isCod) {
        this.isCod = isCod;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName == null ? null : receiverName.trim();
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile == null ? null : receiverMobile.trim();
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress == null ? null : receiverAddress.trim();
    }

    public String getCorderSn() {
		return corderSn;
	}

	public void setCorderSn(String corderSn) {
		this.corderSn = corderSn;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile == null ? null : memberMobile.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Byte getIsWechatSuccess() {
        return isWechatSuccess;
    }

    public void setIsWechatSuccess(Byte isWechatSuccess) {
        this.isWechatSuccess = isWechatSuccess;
    }

    public Byte getIsAppPushSuccess() {
        return isAppPushSuccess;
    }

    public void setIsAppPushSuccess(Byte isAppPushSuccess) {
        this.isAppPushSuccess = isAppPushSuccess;
    }

    public Date getLastSendTime() {
        return lastSendTime;
    }

    public void setLastSendTime(Date lastSendTime) {
        this.lastSendTime = lastSendTime;
    }

    public String getLastWechatErrmsg() {
        return lastWechatErrmsg;
    }

    public void setLastWechatErrmsg(String lastWechatErrmsg) {
        this.lastWechatErrmsg = lastWechatErrmsg == null ? null : lastWechatErrmsg.trim();
    }

    public String getLastAppErrmsg() {
        return lastAppErrmsg;
    }

    public void setLastAppErrmsg(String lastAppErrmsg) {
        this.lastAppErrmsg = lastAppErrmsg == null ? null : lastAppErrmsg.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Boolean getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(Boolean retryTimes) {
        this.retryTimes = retryTimes;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku == null ? null : sku.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

	public Integer getOrderProductId() {
		return orderProductId;
	}

	public void setOrderProductId(Integer orderProductId) {
		this.orderProductId = orderProductId;
	}

}