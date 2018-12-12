package com.haier.shop.model;

import java.io.Serializable;
import java.util.Date;

/*
* 作者:张波
* 2017/12/20
* */
public class InvoiceQueue implements Serializable{
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 2153291055966247114L;

    /**
     * 是否成功 - 否
     */
    public static Integer     SUCCESS_NO       = 0;
    /**
     * 是否成功 - 是
     */
    public static Integer     SUCCESS_YES      = 1;

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

    private Integer success;

    /**
     * 获取 状态0-未处理 1-成功。
     */
    public Integer getSuccess() {
        return this.success;
    }

    /**
     * 设置 状态0-未处理 1-成功。
     *
     * @param value 属性值
     */
    public void setSuccess(Integer value) {
        this.success = value;
    }

    private Date addTime;

    /**
     * 获取 创建时间
     * @return
     */
    public Date getAddTime() {
        return this.addTime;
    }

    /**
     * 设置 创建时间
     * @param value
     */
    public void setAddTime(Date value) {
        this.addTime = value;
    }

    private Date processTime;

    /**
     * 获取 处理时间
     * @return
     */
    public Date getProcessTime() {
        return this.processTime;
    }

    /**
     * 设置 处理时间
     * @param value
     */
    public void setProcessTime(Date value) {
        this.processTime = value;
    }

    private String lastMessage;

    /**
     * 获取 处理结果
     * @return
     */
    public String getLastMessage() {
        return this.lastMessage;
    }

    /**
     * 设置 处理结果
     * @param value
     */
    public void setLastMessage(String value) {
        this.lastMessage = value;
    }
}
