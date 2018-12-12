package com.haier.shop.model;

import java.io.Serializable;
/*
* 作者:张波
* 2017/12/20
* */
public class OrderShippedQueue implements Serializable{
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -8215607035733325958L;

    private Integer           id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer orderLineId;

    /**
     * 获取 网单id。
     */
    public Integer getOrderLineId() {
        return this.orderLineId;
    }

    /**
     * 设置 网单id。
     *
     * @param value 属性值
     */
    public void setOrderLineId(Integer value) {
        this.orderLineId = value;
    }
}
