package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class SuningGroups implements Serializable {
    private static final long serialVersionUID = -3117914630224183116L;
    private Integer id;

    private String source;

    private Integer siteId;

    private String groupName;

    private String sku;

    private Integer depositStartTime;

    private Integer depositEndTime;

    private Integer balanceStartTime;

    private Integer balanceEndTime;

    private BigDecimal depositAmount;

    private BigDecimal balanceAmount;

    private Boolean status;

    private Byte shippingOpporunity;

    private String productSpecs;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getDepositStartTime() {
        return depositStartTime;
    }

    public void setDepositStartTime(Integer depositStartTime) {
        this.depositStartTime = depositStartTime;
    }

    public Integer getDepositEndTime() {
        return depositEndTime;
    }

    public void setDepositEndTime(Integer depositEndTime) {
        this.depositEndTime = depositEndTime;
    }

    public Integer getBalanceStartTime() {
        return balanceStartTime;
    }

    public void setBalanceStartTime(Integer balanceStartTime) {
        this.balanceStartTime = balanceStartTime;
    }

    public Integer getBalanceEndTime() {
        return balanceEndTime;
    }

    public void setBalanceEndTime(Integer balanceEndTime) {
        this.balanceEndTime = balanceEndTime;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Byte getShippingOpporunity() {
        return shippingOpporunity;
    }

    public void setShippingOpporunity(Byte shippingOpporunity) {
        this.shippingOpporunity = shippingOpporunity;
    }

    public String getProductSpecs() {
        return productSpecs;
    }

    public void setProductSpecs(String productSpecs) {
        this.productSpecs = productSpecs;
    }
}