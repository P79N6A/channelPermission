package com.haier.stock.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 胡万来 on 2018/1/26 0026.
 */
public class StockArea implements Serializable {
    private static final long serialVersionUID = -3117914630224183116L;

    private Integer provinceCode;
    private Integer cityCode;
    private Integer regionCode;
    private String channelCode;
    private String secCode;
    private String cityName;
    private String regionName;
    private String stockType;
    private String storeCode;
    private String sku;
    private int avaibleQty;
    private Date updateTime;

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getSecCode() {
        return secCode;
    }

    public void setSecCode(String secCode) {
        this.secCode = secCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(Integer provinceCode) {
        this.provinceCode = provinceCode;
    }

    public Integer getCityCode() {
        return cityCode;
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(Integer regionCode) {
        this.regionCode = regionCode;
    }

    public int getAvaibleQty() {
        return avaibleQty;
    }

    public void setAvaibleQty(int avaibleQty) {
        this.avaibleQty = avaibleQty;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
