package com.haier.shop.model;


import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Code generated by CodeGen
 * Desc: 苏宁分销-返利日期明细表Model类
 * Date: 2018-05-24
 */
public class SNOdsTMFXRebatesDateDetail implements Serializable {

    private String id;
    
    /**
     * 年度
     */
    private String year;
    
    /**
     * 开始日期
     */
    private String beginDate;

    /**
     * 结束日期
     */
    private String endDate;

    /**
     * 生态店
     */
    private String ecologyShop;
    
    /**
     * 品类
     */
    private String category;
    
    /**
     * 产业
     */
    private String industry;
    
    /**
     * sku
     */
    private String sku;
    
    /**
     * 型号描述
     */
    private String modelDes;
    
    /**
     * 品牌
     */
    private String brand;
    
    /**
     * 销售数量
     */
    private Integer saleNumber;
    
    /**
     * 销售金额
     */
    private BigDecimal saleAmount;
    
    /**
     * 类型 d
     */
    private String type;
    

    
    /**
     * 日期兑现金额
     */
    private BigDecimal dateCashAmount;
    
    /**
     * 台阶点位
     */
    private BigDecimal stepPoint;
    
    /**
     * 达标点位
     */
    private BigDecimal markPoint;
    
    /**
     * 台阶返利金额
     */
    private BigDecimal stepRebatesAmount;
    

    
    /**
     * SKU销量台阶返利金额或固定金额返利
     */
    private BigDecimal fixedAmount;
    
    /**
     * SKU销额台阶点位
     */
    private BigDecimal skuStepPoint;
    
    /**
     * SKU销额台阶返利金额
     */
    private BigDecimal skuStepRebatesAmount;
    
    /**
     * SKU销量台阶点位或固定金额
     */
    private BigDecimal fixedPoint;
    
    /**
     * 销量台阶点位
     */
    private BigDecimal amountPoint;
    
    /**
     * 销量台阶返利金额
     */
    private BigDecimal amountStepRebates;



    private  BigDecimal  btStepPoint;//销额对赌台阶点位

    private  BigDecimal  btStepRebatesAmount;//销额对赌返利金额

    private  BigDecimal  btSkuStepPoint;//SKU销额对赌台阶点位

    private  BigDecimal  btSkuStepRebatesAmount;//SKU销额对赌返利金额

    private  BigDecimal  btFixedPoint;//SKU销量对赌点位或固定金额

    private  BigDecimal  btFixedAmount;//SKU销量对赌返利金额或固定金额返利金额

    private  BigDecimal  btAmountPoint;//销量对赌台阶点位

    private  BigDecimal  btAmountStepRebates;//销量对赌返利金额

    private  String  btTarget;//对赌目标

    private  BigDecimal  btCoefficient;//对赌系数

    private  String  btCalculationType;//对赌计算类型


    private  String  rewardType;//奖励类型

    private String  createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getModelDes() {
        return modelDes;
    }

    public void setModelDes(String modelDes) {
        this.modelDes = modelDes;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getSaleNumber() {
        return saleNumber;
    }

    public void setSaleNumber(Integer saleNumber) {
        this.saleNumber = saleNumber;
    }

    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getDateCashAmount() {
        return dateCashAmount;
    }

    public void setDateCashAmount(BigDecimal dateCashAmount) {
        this.dateCashAmount = dateCashAmount;
    }

    public BigDecimal getStepPoint() {
        return stepPoint;
    }

    public void setStepPoint(BigDecimal stepPoint) {
        this.stepPoint = stepPoint;
    }

    public BigDecimal getMarkPoint() {
        return markPoint;
    }

    public void setMarkPoint(BigDecimal markPoint) {
        this.markPoint = markPoint;
    }

    public BigDecimal getStepRebatesAmount() {
        return stepRebatesAmount;
    }

    public void setStepRebatesAmount(BigDecimal stepRebatesAmount) {
        this.stepRebatesAmount = stepRebatesAmount;
    }

    public BigDecimal getFixedAmount() {
        return fixedAmount;
    }

    public void setFixedAmount(BigDecimal fixedAmount) {
        this.fixedAmount = fixedAmount;
    }

    public BigDecimal getSkuStepPoint() {
        return skuStepPoint;
    }

    public void setSkuStepPoint(BigDecimal skuStepPoint) {
        this.skuStepPoint = skuStepPoint;
    }

    public BigDecimal getSkuStepRebatesAmount() {
        return skuStepRebatesAmount;
    }

    public void setSkuStepRebatesAmount(BigDecimal skuStepRebatesAmount) {
        this.skuStepRebatesAmount = skuStepRebatesAmount;
    }

    public BigDecimal getFixedPoint() {
        return fixedPoint;
    }

    public void setFixedPoint(BigDecimal fixedPoint) {
        this.fixedPoint = fixedPoint;
    }

    public BigDecimal getAmountPoint() {
        return amountPoint;
    }

    public void setAmountPoint(BigDecimal amountPoint) {
        this.amountPoint = amountPoint;
    }

    public BigDecimal getAmountStepRebates() {
        return amountStepRebates;
    }

    public void setAmountStepRebates(BigDecimal amountStepRebates) {
        this.amountStepRebates = amountStepRebates;
    }

    public BigDecimal getBtStepPoint() {
        return btStepPoint;
    }

    public void setBtStepPoint(BigDecimal btStepPoint) {
        this.btStepPoint = btStepPoint;
    }

    public BigDecimal getBtStepRebatesAmount() {
        return btStepRebatesAmount;
    }

    public void setBtStepRebatesAmount(BigDecimal btStepRebatesAmount) {
        this.btStepRebatesAmount = btStepRebatesAmount;
    }

    public BigDecimal getBtSkuStepPoint() {
        return btSkuStepPoint;
    }

    public void setBtSkuStepPoint(BigDecimal btSkuStepPoint) {
        this.btSkuStepPoint = btSkuStepPoint;
    }

    public BigDecimal getBtSkuStepRebatesAmount() {
        return btSkuStepRebatesAmount;
    }

    public void setBtSkuStepRebatesAmount(BigDecimal btSkuStepRebatesAmount) {
        this.btSkuStepRebatesAmount = btSkuStepRebatesAmount;
    }

    public BigDecimal getBtFixedPoint() {
        return btFixedPoint;
    }

    public void setBtFixedPoint(BigDecimal btFixedPoint) {
        this.btFixedPoint = btFixedPoint;
    }

    public BigDecimal getBtFixedAmount() {
        return btFixedAmount;
    }

    public void setBtFixedAmount(BigDecimal btFixedAmount) {
        this.btFixedAmount = btFixedAmount;
    }

    public BigDecimal getBtAmountPoint() {
        return btAmountPoint;
    }

    public void setBtAmountPoint(BigDecimal btAmountPoint) {
        this.btAmountPoint = btAmountPoint;
    }

    public BigDecimal getBtAmountStepRebates() {
        return btAmountStepRebates;
    }

    public void setBtAmountStepRebates(BigDecimal btAmountStepRebates) {
        this.btAmountStepRebates = btAmountStepRebates;
    }

    public String getBtTarget() {
        return btTarget;
    }

    public void setBtTarget(String btTarget) {
        this.btTarget = btTarget;
    }

    public BigDecimal getBtCoefficient() {
        return btCoefficient;
    }

    public void setBtCoefficient(BigDecimal btCoefficient) {
        this.btCoefficient = btCoefficient;
    }

    public String getBtCalculationType() {
        return btCalculationType;
    }

    public void setBtCalculationType(String btCalculationType) {
        this.btCalculationType = btCalculationType;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }
}
