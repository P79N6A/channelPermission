package com.haier.purchase.data.model.vehcile;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 李超 on 2018/3/26.
 */
public class OrderLines implements Serializable {
    private static final long serialVersionUID = 6569838538355258586L;
    private String actualQty;
    private String inventoryType;
    private String itemCode;
    private String itemId;
    private String planQty;

    private List<OrderLines> orderLine;

    public String getActualQty() {
        return actualQty;
    }

    public void setActualQty(String actualQty) {
        this.actualQty = actualQty;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getPlanQty() {
        return planQty;
    }

    public void setPlanQty(String planQty) {
        this.planQty = planQty;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public List<OrderLines> getOrderLine() {
        return orderLine;
    }

    public void setOrderLine(List<OrderLines> orderLine) {
        this.orderLine = orderLine;
    }
}
