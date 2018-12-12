package com.haier.stock.model;

import java.io.Serializable;

public class StoreInfo implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 496507904386575031L;
    private Integer           storeId;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    private Integer ownerId;

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    private String storeName;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    private Integer storeStatus;

    public Integer getStoreStatus() {
        return storeStatus;
    }

    public void setStoreStatus(Integer storeStatus) {
        this.storeStatus = storeStatus;
    }

    private String storeAlias;

    public String getStoreAlias() {
        return storeAlias;
    }

    public void setStoreAlias(String storeAlias) {
        this.storeAlias = storeAlias;
    }

    private String qrcode;

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    private String qrcodeUrl;

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    private String approveTime;

    public String getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(String approveTime) {
        this.approveTime = approveTime;
    }

    private String approveUser;

    public String getApproveUser() {
        return approveUser;
    }

    public void setApproveUser(String approveUser) {
        this.approveUser = approveUser;
    }

    private String shareUrl;

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    private String shareUrlQrcode;

    public String getShareUrlQrcode() {
        return shareUrlQrcode;
    }

    public void setShareUrlQrcode(String shareUrlQrcode) {
        this.shareUrlQrcode = shareUrlQrcode;
    }

    private String postfixStoreName;

    public String getPostfixStoreName() {
        return postfixStoreName;
    }

    public void setPostfixStoreName(String postfixStoreName) {
        this.postfixStoreName = postfixStoreName;
    }

    private Integer isO2o;

    public Integer getIsO2o() {
        return isO2o;
    }

    public void setIsO2o(Integer isO2o) {
        this.isO2o = isO2o;
    }

    private String hrCode;

    public String getHrCode() {
        return hrCode;
    }

    public void setHrCode(String hrCode) {
        this.hrCode = hrCode;
    }
}