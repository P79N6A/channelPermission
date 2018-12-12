package com.haier.shop.model;

import java.io.Serializable;
/*
*
* 作者:张波
* 2017/12/20
* */
public class InvoicesReady implements Serializable{
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -331133794304977633L;
    private Integer           id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer orderProductId;

    /**
     * 获取 网单id。
     */
    public Integer getOrderProductId() {
        return this.orderProductId;
    }

    /**
     * 设置 网单id。
     *
     * @param value 属性值
     */
    public void setOrderProductId(Integer value) {
        this.orderProductId = value;
    }

    private Integer status;

    /**
     * 获取 状态0-未处理 1-成功 2-失败。
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置 状态0-未处理 1-成功 2-失败。
     *
     * @param value 属性值
     */
    public void setStatus(Integer value) {
        this.status = value;
    }

    private Integer addTime;

    /**
     * 获取 加入时间。
     */
    public Integer getAddTime() {
        return this.addTime;
    }

    /**
     * 设置 加入时间。
     *
     * @param value 属性值
     */
    public void setAddTime(Integer value) {
        this.addTime = value;
    }

    private Integer doTime;

    /**
     * 获取 处理时间。
     */
    public Integer getDoTime() {
        return this.doTime;
    }

    /**
     * 设置 处理时间。
     *
     * @param value 属性值
     */
    public void setDoTime(Integer value) {
        this.doTime = value;
    }

    private String message;

    /**
     * 获取 处理信息信息。
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * 设置 处理信息信息。
     *
     * @param value 属性值
     */
    public void setMessage(String value) {
        this.message = value;
    }
}
