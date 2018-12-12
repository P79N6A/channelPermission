package com.haier.shop.model;

import java.io.Serializable;

/*
* 作者:张波
* 2017/12/22
* */
public class OrderRepairLESQueues implements Serializable{
    private Integer id;
    private Integer siteId;
    private Long    addTime;
    private Integer orderProductId;
    private Integer orderRepairId;
    private Integer recordId;
    private String  pushData;
    private String  returnData;
    private Integer success;
    private Integer count;
    private Integer vomCount;
    private String  lastMessage;
    private Long    successTime;
    private String  vomLastMessage;
    private Integer vomSuccess;
    private Long    vomSuccessTime;
    private String  vomReturnData;
    private String  vomPushData;
    private Integer flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSiteId() {
        return siteId;
    }



    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public Integer getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(Integer orderProductId) {
        this.orderProductId = orderProductId;
    }

    public Integer getOrderRepairId() {
        return orderRepairId;
    }

    public void setOrderRepairId(Integer orderRepairId) {
        this.orderRepairId = orderRepairId;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getPushData() {
        return pushData;
    }

    public void setPushData(String pushData) {
        this.pushData = pushData;
    }

    public String getReturnData() {
        return returnData;
    }

    public void setReturnData(String returnData) {
        this.returnData = returnData;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Long getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Long successTime) {
        this.successTime = successTime;
    }

    public String getVomLastMessage() {
        return vomLastMessage;
    }

    public void setVomLastMessage(String vomLastMessage) {
        this.vomLastMessage = vomLastMessage;
    }

    public Integer getVomSuccess() {
        return vomSuccess;
    }

    public void setVomSuccess(Integer vomSuccess) {
        this.vomSuccess = vomSuccess;
    }

    public Long getVomSuccessTime() {
        return vomSuccessTime;
    }

    public void setVomSuccessTime(Long vomSuccessTime) {
        this.vomSuccessTime = vomSuccessTime;
    }

    public String getVomReturnData() {
        return vomReturnData;
    }

    public void setVomReturnData(String vomReturnData) {
        this.vomReturnData = vomReturnData;
    }

    public Integer getVomCount() {
        return vomCount;
    }

    public void setVomCount(Integer vomCount) {
        this.vomCount = vomCount;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getVomPushData() {
        return vomPushData;
    }

    public void setVomPushData(String vomPushData) {
        this.vomPushData = vomPushData;
    }
}
