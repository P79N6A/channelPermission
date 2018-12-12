package com.haier.shop.model;

import java.io.Serializable;

public class OrderLockResult implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -1659299976894603270L;

    private String  sku;
    private String  productName;
    private String  message;
    private boolean hasStock;

    public OrderLockResult(String sku, String productName, boolean hasStock, String message) {
        this.sku = sku;
        this.productName = productName;
        this.hasStock = hasStock;
        this.message = message;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isHasStock() {
        return hasStock;
    }

    public void setHasStock(boolean hasStock) {
        this.hasStock = hasStock;
    }

}