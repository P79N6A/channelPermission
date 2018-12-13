package com.haier.shop.model;

import java.io.Serializable;

public class SettlementInvoiceQueue implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -968809534263182688L;

    private Integer           id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer invoicesId;

    public Integer getInvoicesId() {
        return invoicesId;
    }

    public void setInvoicesId(Integer invoicesId) {
        this.invoicesId = invoicesId;
    }

    private Integer statusType;

    public Integer getStatusType() {
        return statusType;
    }

    public void setStatusType(Integer statusType) {
        this.statusType = statusType;
    }

    private Integer success;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    private String addTime;

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    private String modifyTime;

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    private String lastMessage;

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}