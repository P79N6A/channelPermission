package com.haier.distribute.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TSaleProductPrice implements Serializable {
    private static final long serialVersionUID = 5595622765093487568L;

    private Integer id;

    private Integer saleId;

    private BigDecimal supplyPrice;

    private BigDecimal salePrice;

    private BigDecimal limitPrice;

    private String priceStartTime;

    private String priceEndTime;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private String remark;

    /**
     * 以下为联查字段
     */
    /**
     * 网单编号
     */
    private String cOrderSn;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品编号
     */
    private String sku;
    /**
     * 商品分类
     */
    private String typeName;
    /**
     * 产品组
     */
    private String productType;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 数量
     */
    private String number;
    /**
     * 网单金额
     */
    private BigDecimal pAmount;
    /**
     * 订单来源
     */
    private String source;
    /**
     * 来源订单号
     */
    private String sourceOrderSn;
    /**
     * 首次确认时间
     */
    private long firstConfirmTime;
    private String firstConfirmTimeStr;
    /**
     * 确认月份
     */
    private String sureYearMonth;
    private Long firstConfirmTimeS;
    private Long firstConfirmTimeE;
    private String firstSureTimeS;
    private String firstSureTimeE;
    private BigDecimal supplyAmount;
    private BigDecimal commission;
    private BigDecimal monthPolicy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public BigDecimal getSupplyPrice() {
        return supplyPrice;
    }

    public void setSupplyPrice(BigDecimal supplyPrice) {
        this.supplyPrice = supplyPrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getLimitPrice() {
        return limitPrice;
    }

    public void setLimitPrice(BigDecimal limitPrice) {
        this.limitPrice = limitPrice;
    }

    public String getPriceStartTime() {
        return priceStartTime;
    }

    public void setPriceStartTime(String priceStartTime) {
        this.priceStartTime = priceStartTime;
    }

    public String getPriceEndTime() {
        return priceEndTime;
    }

    public void setPriceEndTime(String priceEndTime) {
        this.priceEndTime = priceEndTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getcOrderSn() {
        return cOrderSn;
    }

    public void setcOrderSn(String cOrderSn) {
        this.cOrderSn = cOrderSn;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getpAmount() {
        return pAmount;
    }

    public void setpAmount(BigDecimal pAmount) {
        this.pAmount = pAmount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceOrderSn() {
        return sourceOrderSn;
    }

    public void setSourceOrderSn(String sourceOrderSn) {
        this.sourceOrderSn = sourceOrderSn;
    }

    public BigDecimal getSupplyAmount() {
        return supplyAmount;
    }

    public void setSupplyAmount(BigDecimal supplyAmount) {
        this.supplyAmount = supplyAmount;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public Long getFirstConfirmTimeS() {
        return firstConfirmTimeS;
    }

    public void setFirstConfirmTimeS(Long firstConfirmTimeS) {
        this.firstConfirmTimeS = firstConfirmTimeS;
    }

    public Long getFirstConfirmTimeE() {
        return firstConfirmTimeE;
    }

    public void setFirstConfirmTimeE(Long firstConfirmTimeE) {
        this.firstConfirmTimeE = firstConfirmTimeE;
    }

    public String getFirstSureTimeS() {
        return firstSureTimeS;
    }

    public void setFirstSureTimeS(String firstSureTimeS) {
        this.firstSureTimeS = firstSureTimeS;
    }

    public String getFirstSureTimeE() {
        return firstSureTimeE;
    }

    public void setFirstSureTimeE(String firstSureTimeE) {
        this.firstSureTimeE = firstSureTimeE;
    }

    public BigDecimal getMonthPolicy() {
        return monthPolicy;
    }

    public void setMonthPolicy(BigDecimal monthPolicy) {
        this.monthPolicy = monthPolicy;
    }

    public long getFirstConfirmTime() {
        return firstConfirmTime;
    }

    public void setFirstConfirmTime(long firstConfirmTime) {
        this.firstConfirmTime = firstConfirmTime;
    }

    public String getFirstConfirmTimeStr() {
        return firstConfirmTimeStr;
    }

    public void setFirstConfirmTimeStr(String firstConfirmTimeStr) {
        this.firstConfirmTimeStr = firstConfirmTimeStr;
    }

    public String getSureYearMonth() {
        return sureYearMonth;
    }

    public void setSureYearMonth(String sureYearMonth) {
        this.sureYearMonth = sureYearMonth;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}