package com.haier.shop.model;

import java.io.Serializable;

public class ExpressInfos implements Serializable{
    private Integer id;
    private Long    addTime;
    private Integer orderId;
    private Integer OrderProductId;
    private String  corderSn;
    private String  expressCompany;
    private String  expressNumber;
    private Integer flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderProductId() {
        return OrderProductId;
    }

    public void setOrderProductId(Integer orderProductId) {
        OrderProductId = orderProductId;
    }

    public String getCorderSn() {
        return corderSn;
    }

    public void setCorderSn(String corderSn) {
        this.corderSn = corderSn;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
