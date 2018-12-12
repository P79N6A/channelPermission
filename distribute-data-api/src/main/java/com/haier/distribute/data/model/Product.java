package com.haier.distribute.data.model;

import java.util.Date;

import java.io.Serializable;
import java.math.BigDecimal;

public class Product  implements Serializable{
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3431631072818009195L;
    
    private Integer id;

    private Integer channelId;

    private String sku;

    private String skuName;

    private Integer productTypeId;

    private String productCode;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private String remark;

    private String onSale;

    private String channelName;

    private String productTypeName;

    private String departmentName;

    private String productName;

    private String productCodeName;

    private String citys;

    private String rowstatus;

    private String provinces;
    private String ids;
    private String saleIds;
    private String regionIds;
    private String countys;
    private String supplyPrices;
    private String salePrices;
    private String limitPrices;
    private String priceStartTimes;
    private String priceEndTimes;
    private BigDecimal salePrice;
    
    public String getCitys() {
        return citys;
    }


    public String getRowstatus() {
        return rowstatus;
    }


    public void setRowstatus(String rowstatus) {
        this.rowstatus = rowstatus;
    }


    public void setCitys(String citys) {
        this.citys = citys;
    }

    public String getProvinces() {
        return provinces;
    }

    public void setProvinces(String provinces) {
        this.provinces = provinces;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getSaleIds() {
        return saleIds;
    }

    public void setSaleIds(String saleIds) {
        this.saleIds = saleIds;
    }

    public String getRegionIds() {
        return regionIds;
    }

    public void setRegionIds(String regionIds) {
        this.regionIds = regionIds;
    }

    public String getCountys() {
        return countys;
    }

    public void setCountys(String countys) {
        this.countys = countys;
    }

    public String getSupplyPrices() {
        return supplyPrices;
    }

    public void setSupplyPrices(String supplyPrices) {
        this.supplyPrices = supplyPrices;
    }

    public String getSalePrices() {
        return salePrices;
    }

    public void setSalePrices(String salePrices) {
        this.salePrices = salePrices;
    }

    public String getLimitPrices() {
        return limitPrices;
    }

    public void setLimitPrices(String limitPrices) {
        this.limitPrices = limitPrices;
    }

    public String getPriceStartTimes() {
        return priceStartTimes;
    }

    public void setPriceStartTimes(String priceStartTimes) {
        this.priceStartTimes = priceStartTimes;
    }

    public String getPriceEndTimes() {
        return priceEndTimes;
    }

    public void setPriceEndTimes(String priceEndTimes) {
        this.priceEndTimes = priceEndTimes;
    }


    public String getProductCodeName() {
        return productCodeName;
    }

    public void setProductCodeName(String productCodeName) {
        this.productCodeName = productCodeName;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Integer productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOnSale() {
        return onSale;
    }

    public void setOnSale(String onSale) {
        this.onSale = onSale;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }
    public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
    public BigDecimal getSalePrice() {
		return salePrice;
	}

}