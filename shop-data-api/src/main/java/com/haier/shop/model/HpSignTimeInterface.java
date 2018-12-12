package com.haier.shop.model;

import java.io.Serializable;

/*
* 作者:张波
* 2017/12/26
*/
public class HpSignTimeInterface implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String orderSn;

    private String statusTime;

    private Integer status;

    private String rowId;

    private String tbNo;

    private String wwwMark;

    private String lbxNo;

    private String sku;

    private Integer count;

    private String lastTime;

    private Integer dataStatus;

    private Integer vomCount;

    private Integer acceptCount;

    private String oprName;

    private String oprType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }

    public String getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(String statusTime) {
        this.statusTime = statusTime == null ? null : statusTime.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getTbNo() {
        return tbNo;
    }

    public void setTbNo(String tbNo) {
        this.tbNo = tbNo == null ? null : tbNo.trim();
    }

    public String getWwwMark() {
        return wwwMark;
    }

    public void setWwwMark(String wwwMark) {
        this.wwwMark = wwwMark == null ? null : wwwMark.trim();
    }

    public String getLbxNo() {
        return lbxNo;
    }

    public void setLbxNo(String lbxNo) {
        this.lbxNo = lbxNo == null ? null : lbxNo.trim();
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku == null ? null : sku.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime == null ? null : lastTime.trim();
    }

    public Integer getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(Integer dataStatus) {
        this.dataStatus = dataStatus;
    }

    public Integer getVomCount() {
        return vomCount;
    }

    public void setVomCount(Integer vomCount) {
        this.vomCount = vomCount;
    }

    public Integer getAcceptCount() {
        return acceptCount;
    }

    public void setAcceptCount(Integer acceptCount) {
        this.acceptCount = acceptCount;
    }

    public String getOprName() {
        return oprName;
    }

    public void setOprName(String oprName) {
        this.oprName = oprName;
    }

    public String getOprType() {
        return oprType;
    }

    public void setOprType(String oprType) {
        this.oprType = oprType;
    }
}
