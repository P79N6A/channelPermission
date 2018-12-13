package com.haier.shop.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Code generated by CodeGen
 * Desc: 天猫分销明细表Model类
 * Date: 2018-05-22
 */
public class DistributionDetails implements Serializable {

    private static final long serialVersionUID = 7568164973575258613L;
    private Long id;
    
    /**
     * 网单号
     */
    private String netSn;
    
    /**
     * 原网单
     */
    private String orderSn;
    
    /**
     * 来源订单编号
     */
    private String sourceOrderSn;
    
    /**
     * 生态店
     */
    private String ecologyShop;
    
    /**
     * 品类
     */
    private String category;
    
    /**
     * 品牌
     */
    private String brand;
    
    /**
     * SKU
     */
    private String sku;
    
    /**
     * 宝贝型号
     */
    private String type;
    
    /**
     * 收货人姓名
     */
    private String consigneeName;
    
    /**
     * 订单付款时间
     */
    private Date paymentTime;
    
    /**
     * 销售数量
     */
    private Integer salesVolume;
    
    /**
     * 总价（发票金额）
     */
    private BigDecimal totalPrice;
    
    /**
     * 期间
     */
    private String month;
    
    /**
     * 年度
     */
    private String year;
    
    /**
     * 发票时间
     */
    private Date invoiceTime;
    
    /**
     * 发票状态 1 开票  4 作废
     */
    private String invoiceState;
    
    /**
     * 创建时间
     */
    private Date crteateTime;
    
    /**
     * 修改时间
     */
    private Date updateTime;
    
    /**
     * 订单来源
     */
    private String orderSource;

    /**
     * 产业
     */
    private String industry;
    
    /**
     * 是否生成月度明细标志
     */
    private String createFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNetSn() {
        return netSn;
    }

    public void setNetSn(String netSn) {
        this.netSn = netSn;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getSourceOrderSn() {
        return sourceOrderSn;
    }

    public void setSourceOrderSn(String sourceOrderSn) {
        this.sourceOrderSn = sourceOrderSn;
    }

    public String getEcologyShop() {
        return ecologyShop;
    }

    public void setEcologyShop(String ecologyShop) {
        this.ecologyShop = ecologyShop;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Integer getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(Integer salesVolume) {
        this.salesVolume = salesVolume;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Date getInvoiceTime() {
        return invoiceTime;
    }

    public void setInvoiceTime(Date invoiceTime) {
        this.invoiceTime = invoiceTime;
    }

    public String getInvoiceState() {
        return invoiceState;
    }

    public void setInvoiceState(String invoiceState) {
        this.invoiceState = invoiceState;
    }

    public Date getCrteateTime() {
        return crteateTime;
    }

    public void setCrteateTime(Date crteateTime) {
        this.crteateTime = crteateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getCreateFlag() {
        return createFlag;
    }

    public void setCreateFlag(String createFlag) {
        this.createFlag = createFlag;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }
}
