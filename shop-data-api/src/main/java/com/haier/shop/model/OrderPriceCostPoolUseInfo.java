package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderPriceCostPoolUseInfo implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -5711129383224707460L;
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

    private BigDecimal costPoolUseAmount;

    public BigDecimal getCostPoolUseAmount() {
        return costPoolUseAmount;
    }

    public void setCostPoolUseAmount(BigDecimal costPoolUseAmount) {
        this.costPoolUseAmount = costPoolUseAmount;
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

    private Integer createTime;

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    private String updateTime;

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}