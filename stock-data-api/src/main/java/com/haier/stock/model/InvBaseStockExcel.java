package com.haier.stock.model;


import java.io.Serializable;

public class InvBaseStockExcel implements Serializable{
   private String secCode;
   private String secName;
   private String sku;
   private String lesSecCode;
   private Integer stockQty;
   private Integer frozenQty;
   private String createTime;
   private String updateTime;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        endDate = endDate;
    }

    private String endDate;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    private String startDate;

    public Integer getAvaiableQty() {
        return avaiableQty;
    }

    public void setAvaiableQty(Integer avaiableQty) {
        this.avaiableQty = avaiableQty;
    }

    private Integer avaiableQty;
    public void setSecCode(String secCode) {
        this.secCode = secCode;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setLesSecCode(String lesSecCode) {
        this.lesSecCode = lesSecCode;
    }

    public void setStockQty(Integer stockQty) {
        this.stockQty = stockQty;
    }

    public void setFrozenQty(Integer frozenQty) {
        this.frozenQty = frozenQty;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setCbsCategory(String cbsCategory) {
        this.cbsCategory = cbsCategory;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setItemProperty(String itemProperty) {
        this.itemProperty = itemProperty;
    }

    private String cbsCategory;
   private String productName;

    public String getSecCode() {
        return secCode;
    }

    public String getSecName() {
        return secName;
    }

    public String getSku() {
        return sku;
    }

    public String getLesSecCode() {
        return lesSecCode;
    }

    public Integer getStockQty() {
        return stockQty;
    }

    public Integer getFrozenQty() {
        return frozenQty;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getCbsCategory() {
        return cbsCategory;
    }

    public String getProductName() {
        return productName;
    }

    public String getItemProperty() {
        return itemProperty;
    }

    private String itemProperty;
}