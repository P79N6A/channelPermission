/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.purchase.data.model;

import java.io.Serializable;

public class WwwStockSaveRequire implements Serializable {

    private String material;//物料
    private String amount;  //数量
    private String createdDate;  //创建时间
    private String price;//价格
    private String productTypeName;
    private String enChannelId;

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getEnChannelId() {
        return enChannelId;
    }

    public void setEnChannelId(String enChannelId) {
        this.enChannelId = enChannelId;
    }
}
