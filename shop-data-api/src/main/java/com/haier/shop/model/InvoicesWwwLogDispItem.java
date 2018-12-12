package com.haier.shop.model;

import java.io.Serializable;

/**
 * 3w发票待人工处理 前台显示类
 */
public class InvoicesWwwLogDispItem implements Serializable {

    private static final long serialVersionUID = -5238684870514897964L;

    private Integer id;
    private Integer orderProductId;
    private Integer orderId;
    private String orderSn;
    private String sourceSn;
    private String source;
    private Integer success;
    private Integer flag;
    private String addTime;
    private String processTime;
    private String lastMessage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(Integer orderProductId) {
        this.orderProductId = orderProductId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getSourceSn() {
        return sourceSn;
    }

    public void setSourceSn(String sourceSn) {
        this.sourceSn = sourceSn;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getProcessTime() {
        return processTime;
    }

    public void setProcessTime(String processTime) {
        this.processTime = processTime;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}