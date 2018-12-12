package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderPriceGate implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -7852366227425754932L;
    private Integer           id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String orderSource;

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    private String channelCode;

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    private String industryCode;

    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }

    private String productGroup;

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    private String corderSn;

    public String getCorderSn() {
        return corderSn;
    }

    public void setCorderSn(String corderSn) {
        this.corderSn = corderSn;
    }

    private String orderSn;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    private String sku;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    private Integer cateId;

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    private String cateName;

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    private Integer brandId;

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    private String brandName;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    private BigDecimal orderProductPrice;

    public BigDecimal getOrderProductPrice() {
        return orderProductPrice;
    }

    public void setOrderProductPrice(BigDecimal orderProductPrice) {
        this.orderProductPrice = orderProductPrice;
    }

    private BigDecimal couponAmount;

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    private BigDecimal platformCouponAmount;

    public BigDecimal getPlatformCouponAmount() {
        return platformCouponAmount;
    }

    public void setPlatformCouponAmount(BigDecimal platformCouponAmount) {
        this.platformCouponAmount = platformCouponAmount;
    }

    private BigDecimal orderProductAmount;

    public BigDecimal getOrderProductAmount() {
        return orderProductAmount;
    }

    public void setOrderProductAmount(BigDecimal orderProductAmount) {
        this.orderProductAmount = orderProductAmount;
    }

    private Integer orderProductNumber;

    public Integer getOrderProductNumber() {
        return orderProductNumber;
    }

    public void setOrderProductNumber(Integer orderProductNumber) {
        this.orderProductNumber = orderProductNumber;
    }

    private BigDecimal guaranteePrice;

    public BigDecimal getGuaranteePrice() {
        return guaranteePrice;
    }

    public void setGuaranteePrice(BigDecimal guaranteePrice) {
        this.guaranteePrice = guaranteePrice;
    }

    private BigDecimal subductionPrice;

    public BigDecimal getSubductionPrice() {
        return subductionPrice;
    }

    public void setSubductionPrice(BigDecimal subductionPrice) {
        this.subductionPrice = subductionPrice;
    }

    private String orderAddTime;

    public String getOrderAddTime() {
        return orderAddTime;
    }

    public void setOrderAddTime(String orderAddTime) {
        this.orderAddTime = orderAddTime;
    }

    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    private String lockReason;

    public String getLockReason() {
        return lockReason;
    }

    public void setLockReason(String lockReason) {
        this.lockReason = lockReason;
    }

    private String operator;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    private String responsiblePerson;

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    private String unlockReason;

    public String getUnlockReason() {
        return unlockReason;
    }

    public void setUnlockReason(String unlockReason) {
        this.unlockReason = unlockReason;
    }

    private String unlockTime;

    public String getUnlockTime() {
        return unlockTime;
    }

    public void setUnlockTime(String unlockTime) {
        this.unlockTime = unlockTime;
    }

    private Integer gateStatus;

    public Integer getGateStatus() {
        return gateStatus;
    }

    public void setGateStatus(Integer gateStatus) {
        this.gateStatus = gateStatus;
    }

    private Integer gateType;

    public Integer getGateType() {
        return gateType;
    }

    public void setGateType(Integer gateType) {
        this.gateType = gateType;
    }

    private String orderStatus;

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    private String orderProductStatus;

    public String getOrderProductStatus() {
        return orderProductStatus;
    }

    public void setOrderProductStatus(String orderProductStatus) {
        this.orderProductStatus = orderProductStatus;
    }

    private String payStatus;

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    private String productName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    private String updateTime;

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}