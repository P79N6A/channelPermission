package com.haier.shop.model;

import java.util.Date;

/**
 * 接收VOM主动推送过来解析之后的数据。（主表单）
 * @author wukunyang
 *
 */
public class OrderVOMReturnAnalysis {
    /***/
    private Integer id;

    private String orderNo;

    private String expNo;

    private String bustYpe;

    private String orderType;

    private Date outinDate;

    private String storeCode;

    private String isComPlete;

    private String remark;

    private String attriButes;

    private String remark1;

    private String remark2;

    private String remark3;

    private Date addTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getExpNo() {
        return expNo;
    }

    public void setExpNo(String expNo) {
        this.expNo = expNo == null ? null : expNo.trim();
    }

    public String getBustYpe() {
        return bustYpe;
    }

    public void setBustYpe(String bustYpe) {
        this.bustYpe = bustYpe == null ? null : bustYpe.trim();
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public Date getOutinDate() {
        return outinDate;
    }

    public void setOutinDate(Date outinDate) {
        this.outinDate = outinDate;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode == null ? null : storeCode.trim();
    }

    public String getIsComPlete() {
        return isComPlete;
    }

    public void setIsComPlete(String isComPlete) {
        this.isComPlete = isComPlete == null ? null : isComPlete.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getAttriButes() {
        return attriButes;
    }

    public void setAttriButes(String attriButes) {
        this.attriButes = attriButes == null ? null : attriButes.trim();
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2 == null ? null : remark2.trim();
    }

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3 == null ? null : remark3.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}