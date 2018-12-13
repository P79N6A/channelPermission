package com.haier.shop.model;

import java.io.Serializable;
import java.util.Date;

public class AllocateDefectLogs implements Serializable {

    /** Comment for <code>serialVersionUID</code> */

    private static final long serialVersionUID = -5078942387640253047L;
    private int id;
    private int omsId;//调拨残次Id
    private String operator;//操作人
    private String operate;//操作
    private Date addtime;//添加时间
    private String db;//db单号
    private String orderCode;//lbx号
    public int getOmsId() {
        return omsId;
    }

    public void setOmsId(int omsId) {
        this.omsId = omsId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
}