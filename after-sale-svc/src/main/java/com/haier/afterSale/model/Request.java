package com.haier.afterSale.model;

public class Request {
    public DeliveryOrder getDeliveryOrder() {
        return deliveryOrder;
    }

    public OrderLines getOrderLines() {
        return orderLines;
    }

    public void setDeliveryOrder(DeliveryOrder deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
    }

    public void setOrderLines(OrderLines orderLines) {
        this.orderLines = orderLines;
    }

    private DeliveryOrder deliveryOrder;
    private OrderLines orderLines;
}
