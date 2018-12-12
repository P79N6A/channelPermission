package com.haier.shop.model;

import java.io.Serializable;
import java.util.Date;

public class EStoreStock implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -2127468933988455215L;

    private String            itemNo;
    private String            itemProperty;
    private Integer           itemQuantity;
    private Date              updateTime;

    public EStoreStock() {

    }

    public EStoreStock(String itemNo, String itemProperty, Integer itemQuantity, Date updateTime) {
        this.itemNo = itemNo;
        this.itemProperty = itemProperty;
        this.itemQuantity = itemQuantity;
        this.updateTime = updateTime;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getItemProperty() {
        return itemProperty;
    }

    public void setItemProperty(String itemProperty) {
        this.itemProperty = itemProperty;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
