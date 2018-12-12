package com.haier.shop.model;

import java.io.Serializable;
/*
* 作者:张波
* 2017/12/25
*/
public class HpReservationDateLogs implements Serializable {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer orderId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    private String cOrderSn;

    public String getCOrderSn() {
        return cOrderSn;
    }

    public void setCOrderSn(String cOrderSn) {
        this.cOrderSn = cOrderSn;
    }

    private Integer orderProductId;

    public Integer getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(Integer orderProductId) {
        this.orderProductId = orderProductId;
    }

    private Integer hpReservationDate;

    public Integer getHpReservationDate() {
        return hpReservationDate;
    }

    public void setHpReservationDate(Integer hpReservationDate) {
        this.hpReservationDate = hpReservationDate;
    }

    private Integer addTime;

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }
}
