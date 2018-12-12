package com.haier.shop.model;

import java.io.Serializable;

/*
*
* 作者:张波
* 2018/1/3
* */
public class YiHaoDianOrderStateSyncLogs implements Serializable{
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 2703768512509175846L;

    private Integer           id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer siteId;

    public Integer getSiteId() {
        return this.siteId;
    }

    public void setSiteId(Integer value) {
        this.siteId = value;
    }

    private Long addTime;

    /**
     * 获取 添加时间。
     */
    public Long getAddTime() {
        return this.addTime;
    }

    /**
     * 设置 添加时间。
     *
     * @param value 属性值
     */
    public void setAddTime(Long value) {
        this.addTime = value;
    }

    private Integer count;

    /**
     * 获取 推送次数。
     */
    public Integer getCount() {
        return this.count;
    }

    /**
     * 设置 推送次数。
     *
     * @param value 属性值
     */
    public void setCount(Integer value) {
        this.count = value;
    }

    private String pushData;

    /**
     * 获取 推送数据。
     */
    public String getPushData() {
        return this.pushData;
    }

    /**
     * 设置 推送数据。
     *
     * @param value 属性值
     */
    public void setPushData(String value) {
        this.pushData = value;
    }

    private String orderSn;

    /**
     * 获取 一号店订单号。
     */
    public String getOrderSn() {
        return this.orderSn;
    }

    /**
     * 设置 一号店订单号。
     *
     * @param value 属性值
     */
    public void setOrderSn(String value) {
        this.orderSn = value;
    }

    private String outping;

    /**
     * 获取 les出库号。
     */
    public String getOutping() {
        return this.outping;
    }

    /**
     * 设置 les出库号。
     *
     * @param value 属性值
     */
    public void setOutping(String value) {
        this.outping = value;
    }

    private Integer success;

    /**
     * 获取 是否成功。
     */
    public Integer getSuccess() {
        return this.success;
    }

    /**
     * 设置 是否成功。
     *
     * @param value 属性值
     */
    public void setSuccess(Integer value) {
        this.success = value;
    }

    private String returnData;

    /**
     * 获取 接收数据。
     */
    public String getReturnData() {
        return this.returnData;
    }

    /**
     * 设置 接收数据。
     *
     * @param value 属性值
     */
    public void setReturnData(String value) {
        this.returnData = value;
    }

    private String errMsg;

    /**
     * 获取 失败信息。
     */
    public String getErrMsg() {
        return this.errMsg;
    }

    /**
     * 设置 失败信息。
     *
     * @param value 属性值
     */
    public void setErrMsg(String value) {
        this.errMsg = value;
    }

    private Long lastTryTime;

    /**
     * 获取 上次推送时间。
     */
    public Long getLastTryTime() {
        return this.lastTryTime;
    }

    /**
     * 设置 上次推送时间。
     *
     * @param value 属性值
     */
    public void setLastTryTime(Long value) {
        this.lastTryTime = value;
    }
}
