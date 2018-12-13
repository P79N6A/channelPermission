package com.haier.distribute.data.model;

import java.io.Serializable;

/*
* 规格参数
* */
public class ProductSpecificationsDTO implements Serializable{
    private Integer id;
    private Integer productId;
    private String tag;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getProductId() {
        return productId;

    }

    public String getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    private String value;
}
