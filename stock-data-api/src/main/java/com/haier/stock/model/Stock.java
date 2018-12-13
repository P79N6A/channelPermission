package com.haier.stock.model;

import java.io.Serializable;
import java.util.Date;

public class Stock implements Serializable {

	private static final long serialVersionUID = 962100284884907994L;

	/**
     * 物料编码
     */
    private String sku;

    /**
     * 库位
     */
    private String secCode;

    /**
     * WA/EC
     */
    private String stockType;

    /**
     * 店铺码
     */
    private String storeCode;
    /**
     * 可用数
     */
    private int    avaibleQty;
    /**
     * 转运库位
     */
    private String tsSecCode;

    /**
     * 物料编码
     * @return
     */
    public String getSku() {
        return sku;
    }

    /**
     * 物料编码
     * @param sku
     */
    public void setSku(String sku) {
        this.sku = sku;
    }

    /**
     * 库位
     * @return
     */
    public String getSecCode() {
        return secCode;
    }

    /**
     * 库位
     * @param secCode
     */
    public void setSecCode(String secCode) {
        this.secCode = secCode;
    }

    /**
     * 可用数
     * @return
     */
    public int getAvaibleQty() {
        return avaibleQty;
    }

    /**
     * 可用数
     * @param avaibleQty
     */
    public void setAvaibleQty(int avaibleQty) {
        this.avaibleQty = avaibleQty;
    }

    /**
     * 转运库位
     * @return
     */
    public String getTsSecCode() {
        return tsSecCode;
    }

    /**
     * 转运库位
     * @param tsSecCode
     */
    public void setTsSecCode(String tsSecCode) {
        this.tsSecCode = tsSecCode;
    }

    /**
     * 转运时效
     */
    private Integer tsShippingTime;

    /**
     * 设置转运时效
     * @param tsShippingTime
     */
    public void setTsShippingTime(Integer tsShippingTime) {
        this.tsShippingTime = tsShippingTime;
    }

    /**
     * 获取转运时效
     * @return
     */
    public Integer getTsShippingTime() {
        return tsShippingTime;
    }

    private Date updateTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取 库存类型
     * @return
     */
    public String getStockType() {
        return stockType;
    }

    /**
     * 设置 库存类型
     * @param stockType
     */
    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    /**
     * 获取 EC库存编码
     * @return
     */
    public String getStoreCode() {
        return storeCode;
    }

    /**
     * 设置 EC库存编码
     * @param storeCode
     */
    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

}
