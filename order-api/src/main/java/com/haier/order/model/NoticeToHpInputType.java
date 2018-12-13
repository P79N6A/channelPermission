package com.haier.order.model;

import java.io.Serializable;
import javax.xml.datatype.XMLGregorianCalendar;

public class NoticeToHpInputType implements Serializable {
    private String               orderNo;
    private String               tailSectionNo;
    private String               tailSectionDate;
    private XMLGregorianCalendar createdDate;
    private String               userCode;
    private String               reserverDate;
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getTailSectionNo() {
        return tailSectionNo;
    }
    public void setTailSectionNo(String tailSectionNo) {
        this.tailSectionNo = tailSectionNo;
    }
    public String getTailSectionDate() {
        return tailSectionDate;
    }
    public void setTailSectionDate(String tailSectionDate) {
        this.tailSectionDate = tailSectionDate;
    }
    public XMLGregorianCalendar getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(XMLGregorianCalendar createdDate) {
        this.createdDate = createdDate;
    }
    public String getUserCode() {
        return userCode;
    }
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
    public String getReserverDate() {
        return reserverDate;
    }
    public void setReserverDate(String reserverDate) {
        this.reserverDate = reserverDate;
    }
}
