package com.haier.eis.model;

import java.util.List;

public class InventoryListOutput {

    private String             storeCode;
    List<OrderInventoryDetail> orderInventoryDetailList;

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public List<OrderInventoryDetail> getOrderInventoryDetailList() {
        return orderInventoryDetailList;
    }

    public void setOrderInventoryDetailList(List<OrderInventoryDetail> orderInventoryDetailList) {
        this.orderInventoryDetailList = orderInventoryDetailList;
    }

}
