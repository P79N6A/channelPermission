package com.haier.distribute.util;

public class Request {
    private String productCode;
    private String warehouseCode;
    private Integer quantity;

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setLockQuantity(Integer lockQuantity) {
        this.lockQuantity = lockQuantity;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getLockQuantity() {
        return lockQuantity;
    }

    private Integer lockQuantity;
}
