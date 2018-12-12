package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;
/*
* 作者:张波
* 2017/12/19
* */
public class OrderProductsAttributes implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -4717107667884337341L;
    private Integer           id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer orderProductId;

    public Integer getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(Integer orderProductId) {
        this.orderProductId = orderProductId;
    }

    private Integer orderId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    private BigDecimal commissionProportion;

    public BigDecimal getCommissionProportion() {
        return commissionProportion;
    }

    public void setCommissionProportion(BigDecimal commissionProportion) {
        this.commissionProportion = commissionProportion;
    }

    private BigDecimal platformProportion;

    public BigDecimal getPlatformProportion() {
        return platformProportion;
    }

    public void setPlatformProportion(BigDecimal platformProportion) {
        this.platformProportion = platformProportion;
    }

    private String oid;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    private String isNofrozenstock;

    public String getIsNofrozenstock() {
        return isNofrozenstock;
    }

    public void setIsNofrozenstock(String isNofrozenstock) {
        this.isNofrozenstock = isNofrozenstock;
    }

    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    private String updateTime;

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    private String tbOrderSn;

    public String getTbOrderSn() {
        return tbOrderSn;
    }

    public void setTbOrderSn(String tbOrderSn) {
        this.tbOrderSn = tbOrderSn;
    }

    private int isTz; //'是否套装订单：是：1；不是：0。默认0'

    public int getIsTz() {
        return isTz;
    }

    public void setIsTz(int isTz) {
        this.isTz = isTz;
    }

    private String tzSku; //'如果套装订单，则存储套装sku,如果不是套装订单，则存储普通sku'

    public String getTzSku() {
        return tzSku;
    }

    public void setTzSku(String tzSku) {
        this.tzSku = tzSku;
    }

    private int isSelfSell; //是否自营   0：否     1：是

    public int getIsSelfSell() {
        return isSelfSell;
    }

    public void setIsSelfSell(int isSelfSell) {
        this.isSelfSell = isSelfSell;
    }

    private int customerId; //客户id

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    private Integer isCd;

    public Integer getIsCd() {
        return isCd;
    }

    public void setIsCd(Integer isCd) {
        this.isCd = isCd;
    }

    private Integer isDispatching; //是否指定派工 0不指定  1指定 默认0

    public Integer getIsDispatching() {
        return isDispatching;
    }

    public void setIsDispatching(Integer isDispatching) {
        this.isDispatching = isDispatching;
    }

    private BigDecimal diamondCount;

    public BigDecimal getDiamondCount() {
        return diamondCount;
    }

    public void setDiamondCount(BigDecimal diamondCount) {
        this.diamondCount = diamondCount;
    }

    private BigDecimal diamondAmt;

    public BigDecimal getDiamondAmt() {
        return diamondAmt;
    }

    public void setDiamondAmt(BigDecimal diamondAmt) {
        this.diamondAmt = diamondAmt;
    }

    private String diamondCommiSettle;

    public String getDiamondCommiSettle() {
        return diamondCommiSettle;
    }

    public void setDiamondCommiSettle(String diamondCommiSettle) {
        this.diamondCommiSettle = diamondCommiSettle;
    }

    private String diamondPaymentSettle;

    public String getDiamondPaymentSettle() {
        return diamondPaymentSettle;
    }

    public void setDiamondPaymentSettle(String diamondPaymentSettle) {
        this.diamondPaymentSettle = diamondPaymentSettle;
    }

    private String diamondBilling;

    public String getDiamondBilling() {
        return diamondBilling;
    }

    public void setDiamondBilling(String diamondBilling) {
        this.diamondBilling = diamondBilling;
    }

    private BigDecimal seashellCount;

    public BigDecimal getSeashellCount() {
        return seashellCount;
    }

    public void setSeashellCount(BigDecimal seashellCount) {
        this.seashellCount = seashellCount;
    }

    private BigDecimal seashellAmt;

    public BigDecimal getSeashellAmt() {
        return seashellAmt;
    }

    public void setSeashellAmt(BigDecimal seashellAmt) {
        this.seashellAmt = seashellAmt;
    }

    private String seashellCommiSettle;

    public String getSeashellCommiSettle() {
        return seashellCommiSettle;
    }

    public void setSeashellCommiSettle(String seashellCommiSettle) {
        this.seashellCommiSettle = seashellCommiSettle;
    }

    private String seashellPaymentSettle;

    public String getSeashellPaymentSettle() {
        return seashellPaymentSettle;
    }

    public void setSeashellPaymentSettle(String seashellPaymentSettle) {
        this.seashellPaymentSettle = seashellPaymentSettle;
    }

    private String seashellBilling;

    public String getSeashellBilling() {
        return seashellBilling;
    }

    public void setSeashellBilling(String seashellBilling) {
        this.seashellBilling = seashellBilling;
    }

    private BigDecimal insuranceCount;

    public BigDecimal getInsuranceCount() {
        return insuranceCount;
    }

    public void setInsuranceCount(BigDecimal insuranceCount) {
        this.insuranceCount = insuranceCount;
    }

    private BigDecimal insuranceAmt;

    public BigDecimal getInsuranceAmt() {
        return insuranceAmt;
    }

    public void setInsuranceAmt(BigDecimal insuranceAmt) {
        this.insuranceAmt = insuranceAmt;
    }

    private String insuranceCommiSettle;

    public String getInsuranceCommiSettle() {
        return insuranceCommiSettle;
    }

    public void setInsuranceCommiSettle(String insuranceCommiSettle) {
        this.insuranceCommiSettle = insuranceCommiSettle;
    }

    private String insurancePaymentSettle;

    public String getInsurancePaymentSettle() {
        return insurancePaymentSettle;
    }

    public void setInsurancePaymentSettle(String insurancePaymentSettle) {
        this.insurancePaymentSettle = insurancePaymentSettle;
    }

    private String insuranceBilling;

    public String getInsuranceBilling() {
        return insuranceBilling;
    }

    public void setInsuranceBilling(String insuranceBilling) {
        this.insuranceBilling = insuranceBilling;
    }
}