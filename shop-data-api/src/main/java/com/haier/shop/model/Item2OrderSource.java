package com.haier.shop.model;

import java.io.Serializable;
import java.util.Date;

public class Item2OrderSource implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer           id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer itemProperty;

    /**
     * 获取 批次。
     */
    public Integer getItemProperty() {
        return this.itemProperty;
    }

    /**
     * 设置 批次。
     *
     * @param value 属性值
     */
    public void setItemProperty(Integer value) {
        this.itemProperty = value;
    }

    private String orderSource;

    /**
     * 获取 订单来源。
     */
    public String getOrderSource() {
        return this.orderSource;
    }

    /**
     * 设置 订单来源。
     *
     * @param value 属性值
     */
    public void setOrderSource(String value) {
        this.orderSource = value;
    }

    private String sourceName;

    /**
     * 获取 订单来源名称。
     */
    public String getSourceName() {
        return this.sourceName;
    }

    /**
     * 设置 订单来源名称。
     *
     * @param value 属性值
     */
    public void setSourceName(String value) {
        this.sourceName = value;
    }

    private Date createTime = null;

    /**
     * 获取 创建时间。
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置 创建时间。
     *
     * @param value 属性值
     */
    public void setCreateTime(Date value) {
        this.createTime = value;
    }
}
