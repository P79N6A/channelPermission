package com.haier.shop.model;

import java.io.Serializable;

public class MemberInvoicesDispItem implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 502751086914885403L;
    private Integer id;
    private Integer orderId;                               //订单编号
    private Integer orderStatus;                           //订单状态
    private Integer paymentStatus;                         //支付状态
    private String source;                                //订单来源
    private Integer invoiceType;                           //发票类型
    private Integer electricFlag;                          //是否电子发票
    private String memberName;                            //会员名称
    private String invoiceTitle;                          //注册公司名称
    private String taxPayerNumber;                        //纳税人识别号
    private String registerAddress;                       //注册地址
    private String registerPhone;                         //注册电话
    private String bankName;                              //开户行
    private String bankCardNumber;                        //银行卡号
    private String addTime;                               //添加时间
    private String addTimeMin;                            //生成时间min
    private String addTimeMax;                            //生成时间max
    private Integer state;                                 //审核状态
    private Integer isLock;                                //是否锁定
    private String auditTime;                             //审核时间
    private String auditTimeMin;                          //审核时间
    private String auditTimeMax;                          //审核时间
    private String auditor;                               //审核人
    private String orderSn;                               //订单号
    private String message;                               //信息

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public Integer getElectricFlag() {
        return electricFlag;
    }

    public void setElectricFlag(Integer electricFlag) {
        this.electricFlag = electricFlag;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getTaxPayerNumber() {
        return taxPayerNumber;
    }

    public void setTaxPayerNumber(String taxPayerNumber) {
        this.taxPayerNumber = taxPayerNumber;
    }

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    public String getRegisterPhone() {
        return registerPhone;
    }

    public void setRegisterPhone(String registerPhone) {
        this.registerPhone = registerPhone;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCardNumber() {
        return bankCardNumber;
    }

    public void setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditTimeMin() {
        return auditTimeMin;
    }

    public void setAuditTimeMin(String auditTimeMin) {
        this.auditTimeMin = auditTimeMin;
    }

    public String getAuditTimeMax() {
        return auditTimeMax;
    }

    public void setAuditTimeMax(String auditTimeMax) {
        this.auditTimeMax = auditTimeMax;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getAddTimeMin() {
        return addTimeMin;
    }

    public void setAddTimeMin(String addTimeMin) {
        this.addTimeMin = addTimeMin;
    }

    public String getAddTimeMax() {
        return addTimeMax;
    }

    public void setAddTimeMax(String addTimeMax) {
        this.addTimeMax = addTimeMax;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
