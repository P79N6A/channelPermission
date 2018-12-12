package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderDifferencePriceRefund implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 4126092677843287427L;
    private Integer           id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    private Integer memberId;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    private String sku;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    private String productName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    private BigDecimal orderProductPrice;

    public BigDecimal getOrderProductPrice() {
        return orderProductPrice;
    }

    public void setOrderProductPrice(BigDecimal orderProductPrice) {
        this.orderProductPrice = orderProductPrice;
    }

    private Integer orderProductNumber;

    public Integer getOrderProductNumber() {
        return orderProductNumber;
    }

    public void setOrderProductNumber(Integer orderProductNumber) {
        this.orderProductNumber = orderProductNumber;
    }

    private BigDecimal orderProductDifferencePrice;

    public BigDecimal getOrderProductDifferencePrice() {
        return orderProductDifferencePrice;
    }

    public void setOrderProductDifferencePrice(BigDecimal orderProductDifferencePrice) {
        this.orderProductDifferencePrice = orderProductDifferencePrice;
    }

    private BigDecimal totalRefundPrice;

    public BigDecimal getTotalRefundPrice() {
        return totalRefundPrice;
    }

    public void setTotalRefundPrice(BigDecimal totalRefundPrice) {
        this.totalRefundPrice = totalRefundPrice;
    }

    private String alipayAccount;

    public String getAlipayAccount() {
        return alipayAccount;
    }

    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
    }

    private String alipayName;

    public String getAlipayName() {
        return alipayName;
    }

    public void setAlipayName(String alipayName) {
        this.alipayName = alipayName;
    }

    private Integer accptedStatus;

    public Integer getAccptedStatus() {
        return accptedStatus;
    }

    public void setAccptedStatus(Integer accptedStatus) {
        this.accptedStatus = accptedStatus;
    }

    private String applyReason;

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
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