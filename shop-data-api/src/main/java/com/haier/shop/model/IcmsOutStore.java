package com.haier.shop.model;

import java.io.Serializable;
/*
* 作者:张波
* 2017/12/19
* */
public class IcmsOutStore implements Serializable {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1L;

    private Integer           id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer havesync;

    /**
     * 获取 1已同步/0未同步。
     */
    public Integer getHavesync() {
        return this.havesync;
    }

    /**
     * 设置 1已同步/0未同步。
     *
     * @param value 属性值
     */
    public void setHavesync(Integer value) {
        this.havesync = value;
    }

    private Integer itemstatus;

    /**
     * 获取 1已出库/0未出库。
     */
    public Integer getItemstatus() {
        return this.itemstatus;
    }

    /**
     * 设置 1已出库/0未出库。
     *
     * @param value 属性值
     */
    public void setItemstatus(Integer value) {
        this.itemstatus = value;
    }

    private String cordersn;

    /**
     * 获取 网单号。
     */
    public String getCordersn() {
        return this.cordersn;
    }

    /**
     * 设置 网单号。
     *
     * @param value 属性值
     */
    public void setCordersn(String value) {
        this.cordersn = value;
    }

    private String outping;

    /**
     * 获取 出库单号。
     */
    public String getOutping() {
        return this.outping;
    }

    /**
     * 设置 出库单号。
     *
     * @param value 属性值
     */
    public void setOutping(String value) {
        this.outping = value;
    }

    private String upddate;

    /**
     * 获取 出库日期。
     */
    public String getUpddate() {
        return this.upddate;
    }

    /**
     * 设置 出库日期。
     *
     * @param value 属性值
     */
    public void setUpddate(String value) {
        this.upddate = value;
    }

    private String updtime;

    /**
     * 获取 出库时间。
     */
    public String getUpdtime() {
        return this.updtime;
    }

    /**
     * 设置 出库时间。
     *
     * @param value 属性值
     */
    public void setUpdtime(String value) {
        this.updtime = value;
    }

    private String invoicenumber;

    /**
     * 获取 物流单号。
     */
    public String getInvoicenumber() {
        return this.invoicenumber;
    }

    /**
     * 设置 物流单号。
     *
     * @param value 属性值
     */
    public void setInvoicenumber(String value) {
        this.invoicenumber = value;
    }

    private String invoicename;

    /**
     * 获取 物流名称。
     */
    public String getInvoicename() {
        return this.invoicename;
    }

    /**
     * 设置 物流名称。
     *
     * @param value 属性值
     */
    public void setInvoicename(String value) {
        this.invoicename = value;
    }

    private Integer orderType;

    /**
     * 获取 订单类型
     * 0：京东
     * 1：易迅
     * @return
     */
    public Integer getOrderType() {
        return orderType;
    }

    /**
     * 设置 京东类型
     * 0：京东
     * 1：易迅
     * @param orderType
     */
    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }
}
