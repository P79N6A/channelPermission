package com.haier.eis.model;

import java.util.List;

public class OrderInventoryDetail {

    private String                orderCode;
    private List<InventoryDetail> inventoryDetailList;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public List<InventoryDetail> getInventoryDetailList() {
        return inventoryDetailList;
    }

    public void setInventoryDetailList(List<InventoryDetail> inventoryDetailList) {
        this.inventoryDetailList = inventoryDetailList;
    }

}
