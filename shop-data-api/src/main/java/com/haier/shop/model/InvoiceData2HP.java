package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class InvoiceData2HP implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private String            orderNo;

    private String            fpSkh;

    private String            fpNo;

    private BigDecimal        fpPrice;

    private Date              fpDate;

    private BigDecimal        fpCount;

    private String            fpType;

    private String            procFlag;

    private String            procRemark;

    private Date              createdDate;

    private String            fpStatus;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getFpSkh() {
        return fpSkh;
    }

    public void setFpSkh(String fpSkh) {
        this.fpSkh = fpSkh;
    }

    public String getFpNo() {
        return fpNo;
    }

    public void setFpNo(String fpNo) {
        this.fpNo = fpNo;
    }

    public BigDecimal getFpPrice() {
        return fpPrice;
    }

    public void setFpPrice(BigDecimal fpPrice) {
        this.fpPrice = fpPrice;
    }

    public Date getFpDate() {
        return fpDate;
    }

    public void setFpDate(Date fpDate) {
        this.fpDate = fpDate;
    }

    public BigDecimal getFpCount() {
        return fpCount;
    }

    public void setFpCount(BigDecimal fpCount) {
        this.fpCount = fpCount;
    }

    public String getFpType() {
        return fpType;
    }

    public void setFpType(String fpType) {
        this.fpType = fpType;
    }

    public String getProcFlag() {
        return procFlag;
    }

    public void setProcFlag(String procFlag) {
        this.procFlag = procFlag;
    }

    public String getProcRemark() {
        return procRemark;
    }

    public void setProcRemark(String procRemark) {
        this.procRemark = procRemark;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getFpStatus() {
        return fpStatus;
    }

    public void setFpStatus(String fpStatus) {
        this.fpStatus = fpStatus;
    }

}
