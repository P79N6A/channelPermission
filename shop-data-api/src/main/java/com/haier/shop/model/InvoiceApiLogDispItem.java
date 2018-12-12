package com.haier.shop.model;

import java.io.Serializable;

/**
 * 纸质发票日志展示类
 **/
public class InvoiceApiLogDispItem implements Serializable{

    private static final long serialVersionUID = -742432515461433531L;

    private String            cOrderSn;                                //网单编号cOrderSn
    private Integer           type;                                    //开票类型type
    private Integer           isSuccess;                               //是否成功isSuccess
    private String            addTimeMin;                              //记录起始时间addTime
    private String            addTimeMax;                              //记录截止时间addTime
    private String            addTime;                                 //时间
    private Integer           countNum;                                //次数countNum
    private String            lastMessage;                             //提示信息lastMessage

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getcOrderSn() {
        return cOrderSn;
    }

    public void setcOrderSn(String cOrderSn) {
        this.cOrderSn = cOrderSn;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Integer isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getAddTimeMin() {
        return addTimeMin;
    }

    public void setAddTimeMin(String addTimeMin) {
        this.addTimeMin = addTimeMin;
    }

    public String getAddTimeMax() {
        return addTimeMax;
    }

    public void setAddTimeMax(String addTimeMax) {
        this.addTimeMax = addTimeMax;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public Integer getCountNum() {
        return countNum;
    }

    public void setCountNum(Integer countNum) {
        this.countNum = countNum;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
