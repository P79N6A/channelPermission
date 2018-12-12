package com.haier.shop.model;

import java.io.Serializable;
/*
* 作者:张波
* 2017/12/19
* */
public class OrdersAttributes implements Serializable{
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer iswww;

    public Integer getIswww() {
        return iswww;
    }

    public void setIswww(Integer iswww) {
        this.iswww = iswww;
    }

    private Integer orderId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    private String lbx;

    public String getLbx() {
        return lbx;
    }

    public void setLbx(String lbx) {
        this.lbx = lbx;
    }

    private String sourceOrderSn;

    public String getSourceOrderSn() {
        return sourceOrderSn;
    }

    public void setSourceOrderSn(String sourceOrderSn) {
        this.sourceOrderSn = sourceOrderSn;
    }

    private Integer orderYwType;

    public Integer getOrderYwType() {
        return orderYwType;
    }

    public void setOrderYwType(Integer orderYwType) {
        this.orderYwType = orderYwType;
    }

    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    private String updateTime;

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
