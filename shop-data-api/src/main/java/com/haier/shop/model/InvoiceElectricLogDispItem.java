package com.haier.shop.model;

import java.io.Serializable;

/**
 * 电子发票展示模型
 **/
public class InvoiceElectricLogDispItem implements Serializable {

    private static final long serialVersionUID = -5957604772164831281L;

    private String            cOrderSn;                                 //网单编号cOrderSn
    private String            addTime;                                  //时间
    private String            addTimeMin;                               //记录起始时间addTimeMin
    private String            addTimeMax;                               //记录截止时间addTimeMax
    private Integer           type;                                     //次数count
    private Integer           success;                                  //操作类型type
    private Integer           count;                                    //是否成功success
    private String            lastMessage;                              //提示信息lastMessage

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getcOrderSn() {
        return cOrderSn;
    }

    public void setcOrderSn(String cOrderSn) {
        this.cOrderSn = cOrderSn;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
}
