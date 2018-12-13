package com.haier.afterSale.model;

public class RelatedOrder {
    private String orderType="";

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    private String orderCode="";

    public String getOrderType() {
        return orderType;
    }

    public String getOrderCode() {
        return orderCode;
    }
}
