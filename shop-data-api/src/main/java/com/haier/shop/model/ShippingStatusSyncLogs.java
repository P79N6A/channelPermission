package com.haier.shop.model;

import java.io.Serializable;

/*
* 作者:张波
* 2018/1/3
/ */
public class ShippingStatusSyncLogs implements Serializable{
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 687578035584576066L;

    private Integer           id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer orderId;

    /**
     * 获取 订单ID。
     */
    public Integer getOrderId() {
        return this.orderId;
    }

    /**
     * 设置 订单ID。
     *
     * @param value 属性值
     */
    public void setOrderId(Integer value) {
        this.orderId = value;
    }

    private Integer orderProductId;

    /**
     * 获取 网单ID。
     */
    public Integer getOrderProductId() {
        return this.orderProductId;
    }

    /**
     * 设置 网单ID。
     *
     * @param value 属性值
     */
    public void setOrderProductId(Integer value) {
        this.orderProductId = value;
    }

    private String shippingNum;

    /**
     * 获取 LES发货单号。
     */
    public String getShippingNum() {
        return this.shippingNum;
    }

    /**
     * 设置 LES发货单号。
     *
     * @param value 属性值
     */
    public void setShippingNum(String value) {
        this.shippingNum = value;
    }

    private String customerCode;

    /**
     * 获取 客户编码。
     */
    public String getCustomerCode() {
        return this.customerCode;
    }

    /**
     * 设置 客户编码。
     *
     * @param value 属性值
     */
    public void setCustomerCode(String value) {
        this.customerCode = value;
    }

    private String customerName;

    /**
     * 获取 客户名称。
     */
    public String getCustomerName() {
        return this.customerName;
    }

    /**
     * 设置 客户名称。
     *
     * @param value 属性值
     */
    public void setCustomerName(String value) {
        this.customerName = value;
    }

    private Long addTime;

    /**
     * 获取 日志添加时间。
     */
    public Long getAddTime() {
        return this.addTime;
    }

    /**
     * 设置 日志添加时间。
     *
     * @param value 属性值
     */
    public void setAddTime(Long value) {
        this.addTime = value;
    }

    private String httpStatus;

    /**
     * 获取 HTTP状态码。
     */
    public String getHttpStatus() {
        return this.httpStatus;
    }

    /**
     * 设置 HTTP状态码。
     *
     * @param value 属性值
     */
    public void setHttpStatus(String value) {
        this.httpStatus = value;
    }

    private Integer count;

    /**
     * 获取 同步次数。
     */
    public Integer getCount() {
        return this.count;
    }

    /**
     * 设置 同步次数。
     *
     * @param value 属性值
     */
    public void setCount(Integer value) {
        this.count = value;
    }

    private String requestData;

    /**
     * 获取 发送的加密数据。
     */
    public String getRequestData() {
        return this.requestData;
    }

    /**
     * 设置 发送的加密数据。
     *
     * @param value 属性值
     */
    public void setRequestData(String value) {
        this.requestData = value;
    }

    private String requestXmlData;

    /**
     * 获取 发送的数据对应的XML数据。
     */
    public String getRequestXmlData() {
        return this.requestXmlData;
    }

    /**
     * 设置 发送的数据对应的XML数据。
     *
     * @param value 属性值
     */
    public void setRequestXmlData(String value) {
        this.requestXmlData = value;
    }

    private Long lastRequestTime;

    /**
     * 获取 最后一次同步时间。
     */
    public Long getLastRequestTime() {
        return this.lastRequestTime;
    }

    /**
     * 设置 最后一次同步时间。
     *
     * @param value 属性值
     */
    public void setLastRequestTime(Long value) {
        this.lastRequestTime = value;
    }
}
