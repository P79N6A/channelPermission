package com.haier.shop.model;

import java.io.Serializable;
/*
*
* 作者:张波
* 2017/12/19
* */
public class OrderRepairHPQueues implements Serializable {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -393672086988107726L;
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

    private Integer addTime;

    /**
     * 获取 创建时间。
     */
    public Integer getAddTime() {
        return this.addTime;
    }

    /**
     * 设置 创建时间。
     *
     * @param value 属性值
     */
    public void setAddTime(Integer value) {
        this.addTime = value;
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

    private Integer orderRepairId;

    /**
     * 获取 退货申请单ID。
     */
    public Integer getOrderRepairId() {
        return this.orderRepairId;
    }

    /**
     * 设置 退货申请单ID。
     *
     * @param value 属性值
     */
    public void setOrderRepairId(Integer value) {
        this.orderRepairId = value;
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

    private String returnData;

    /**
     * 获取 返回数据。
     */
    public String getReturnData() {
        return this.returnData;
    }

    /**
     * 设置 返回数据。
     *
     * @param value 属性值
     */
    public void setReturnData(String value) {
        this.returnData = value;
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

    private Integer count;

    /**
     * 获取 总共传递的次数。
     */
    public Integer getCount() {
        return this.count;
    }

    /**
     * 设置 总共传递的次数。
     *
     * @param value 属性值
     */
    public void setCount(Integer value) {
        this.count = value;
    }

    private String lastMessage;

    /**
     * 获取 最后返回信息。
     */
    public String getLastMessage() {
        return this.lastMessage;
    }

    /**
     * 设置 最后返回信息。
     *
     * @param value 属性值
     */
    public void setLastMessage(String value) {
        this.lastMessage = value;
    }

    private Integer successTime;

    /**
     * 获取 成功时间。
     */
    public Integer getSuccessTime() {
        return this.successTime;
    }

    /**
     * 设置 成功时间。
     *
     * @param value 属性值
     */
    public void setSuccessTime(Integer value) {
        this.successTime = value;
    }

    private String vomReturnData;

    public String getVomReturnData() {
        return vomReturnData;
    }

    public void setVomReturnData(String vomReturnData) {
        this.vomReturnData = vomReturnData;
    }

    private Integer vomSuccess;

    public Integer getVomSuccess() {
        return vomSuccess;
    }

    public void setVomSuccess(Integer vomSuccess) {
        this.vomSuccess = vomSuccess;
    }

    private Integer vomCount;

    public Integer getVomCount() {
        return vomCount;
    }

    public void setVomCount(Integer vomCount) {
        this.vomCount = vomCount;
    }

    private String vomLastMessage;

    public String getVomLastMessage() {
        return vomLastMessage;
    }

    public void setVomLastMessage(String vomLastMessage) {
        this.vomLastMessage = vomLastMessage;
    }

    private Integer vomSuccessTime;

    public Integer getVomSuccessTime() {
        return vomSuccessTime;
    }

    public void setVomSuccessTime(Integer vomSuccessTime) {
        this.vomSuccessTime = vomSuccessTime;
    }

    private Integer typeFlag;

    public Integer getTypeFlag() {
        return typeFlag;
    }

    public void setTypeFlag(Integer typeFlag) {
        this.typeFlag = typeFlag;
    }
}
