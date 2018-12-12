package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 差异订单修正表
 *                       
 * @Filename: Order2ths.java
 * @Version: 1.0
 * @Author: weiyunjun
 * @Email: weiyunjun@ehaier.com
 *
 */
public class Order2ths implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    /*主键*/
    private Integer           id;
    /*订单号*/
    private String            orderSn;
    /*来源订单号*/
    private String            orderSourceSn;
    /*下单时间*/
    private String            addTime;
    /*订单来源*/
    private String            orderSource;
    /*库位编码*/
    private String            sCode;
    /*工贸编码*/
    private String            industryCode;
    /*工贸名称*/
    private String            industryName;
    /*产品类别*/
    private String            productType;
    /*物料编码*/
    private String            sku;
    /*产品名称*/
    private String            productName;
    /*产品单价*/
    private BigDecimal        price;
    /*数量*/
    private Integer           number;
    /*运费*/
    private BigDecimal        shippingAmount;
    /*订单金额*/
    private BigDecimal        orderAmount;
    /*收货人*/
    private String            consignee;
    /*电话1*/
    private String            mobile1;
    /*电话2*/
    private String            mobile2;
    /*提货单号*/
    private String            deliverySn;
    /*备注*/
    private String            remark;
    /*订单状态*/
    private String            status;
    /*开票户头*/
    private String            receiptTitle;
    /*纳税人登记号*/
    private String            taxSpotNum;
    /*发票电话*/
    private String            regPhone;
    /*发票地址*/
    private String            regAddress;
    /*发票银行*/
    private String            receiptBank;
    /*银行帐号*/
    private String            bankAccount;
    /*发票类别*/
    private String            receiptType;
    /*发票号码*/
    private String            receiptNumber;
    /*添加或导入时间时间*/
    private Integer           createTime;
    /*金税开票日期*/
    private String            jSDate;
    /*是否已开票（未开1，已开2）*/
    private Integer           isReceipted;
    /*发票信息表id*/
    private Integer           memberInvoiceId;
    /*添加或导入人*/
    private String            creator;
    /*SAP推送成功标志，0：失败；1：成功*/
    private Integer           sapSuccess;
    /*SAP推送次数*/
    private Integer           sapCount;
    /*SAP推送时间*/
    private Integer           sapLastTime;
    /*SAP推送内容*/
    private String            sapPushData;
    /*SAP推送结果*/
    private String            sapReturnData;
    /*支付方式*/
    private String            paymentCode;

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
        this.orderSn = orderSn;
    }

    public String getOrderSourceSn() {
        return orderSourceSn;
    }

    public void setOrderSourceSn(String orderSourceSn) {
        this.orderSourceSn = orderSourceSn;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getsCode() {
        return sCode;
    }

    public void setsCode(String sCode) {
        this.sCode = sCode;
    }

    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getShippingAmount() {
        return shippingAmount;
    }

    public void setShippingAmount(BigDecimal shippingAmount) {
        this.shippingAmount = shippingAmount;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getMobile1() {
        return mobile1;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
    }

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }

    public String getDeliverySn() {
        return deliverySn;
    }

    public void setDeliverySn(String deliverySn) {
        this.deliverySn = deliverySn;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReceiptTitle() {
        return receiptTitle;
    }

    public void setReceiptTitle(String receiptTitle) {
        this.receiptTitle = receiptTitle;
    }

    public String getTaxSpotNum() {
        return taxSpotNum;
    }

    public void setTaxSpotNum(String taxSpotNum) {
        this.taxSpotNum = taxSpotNum;
    }

    public String getRegPhone() {
        return regPhone;
    }

    public void setRegPhone(String regPhone) {
        this.regPhone = regPhone;
    }

    public String getRegAddress() {
        return regAddress;
    }

    public void setRegAddress(String regAddress) {
        this.regAddress = regAddress;
    }

    public String getReceiptBank() {
        return receiptBank;
    }

    public void setReceiptBank(String receiptBank) {
        this.receiptBank = receiptBank;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(String receiptType) {
        this.receiptType = receiptType;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public String getJSDate() {
        return jSDate;
    }

    public void setJSDate(String jSDate) {
        this.jSDate = jSDate;
    }

    public Integer getIsReceipted() {
        return isReceipted;
    }

    public void setIsReceipted(Integer isReceipted) {
        this.isReceipted = isReceipted;
    }

    public Integer getMemberInvoiceId() {
        return memberInvoiceId;
    }

    public void setMemberInvoiceId(Integer memberInvoiceId) {
        this.memberInvoiceId = memberInvoiceId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getSapSuccess() {
        return sapSuccess;
    }

    public void setSapSuccess(Integer sapSuccess) {
        this.sapSuccess = sapSuccess;
    }

    public Integer getSapCount() {
        return sapCount;
    }

    public void setSapCount(Integer sapCount) {
        this.sapCount = sapCount;
    }

    public Integer getSapLastTime() {
        return sapLastTime;
    }

    public void setSapLastTime(Integer sapLastTime) {
        this.sapLastTime = sapLastTime;
    }

    public String getSapPushData() {
        return sapPushData;
    }

    public void setSapPushData(String sapPushData) {
        this.sapPushData = sapPushData;
    }

    public String getSapReturnData() {
        return sapReturnData;
    }

    public void setSapReturnData(String sapReturnData) {
        this.sapReturnData = sapReturnData;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

}
