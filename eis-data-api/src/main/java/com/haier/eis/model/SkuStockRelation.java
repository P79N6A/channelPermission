package com.haier.eis.model;

import java.io.Serializable;

/**
 * Created by hanhaoyang@ehaier.com on 2017/11/26 0026.
 */
public class SkuStockRelation implements Serializable {
    private int id;
    // <itemCode>菜鸟商品编码,  string (50)，必填</itemCode>
    private String sku;
    // <warehouseCode>仓库编码, string (50)，必填</warehouseCode>
    private String stockCode;
    // <channelCode>渠道编码，String(50) ，必填</channelCode>
    private String channelCode;
    // <quantity>商品数量，int，必填</quantity>
    private int quantity;
    // <lockQuantity>锁库存数量，int，必填</lockQuantity >
    private int lockQuantity;
    // 数据同步的日期
    private String createDate;
    // 数据同步的时间
    private String createTime;
    //展示数量字段
    private String showNum;
    //sku产品总数（当天24小时内的sku对应的产品总数，适用于3W，其他情况慎用）
    private String skuNum;

    public String getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(String skuNum) {
        this.skuNum = skuNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getLockQuantity() {
        return lockQuantity;
    }

    public void setLockQuantity(int lockQuantity) {
        this.lockQuantity = lockQuantity;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getShowNum() {
        return showNum;
    }

    public void setShowNum(String showNum) {
        this.showNum = showNum;
    }
}
