package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 返利政策点位维护与审核
 * ODS_TMFX_POINTMAINTAIN
 * 
 * @version 1.0 2017-10-16
 */
public class OdsTMFXPointMaintain implements Serializable {
    private static final long serialVersionUID = 2150256592464930632L;
    /**
     * 主键
     * ODS_TMFX_POINTMAINTAIN.ID
     */
    private String id;

    /**
     * 生态店
     * ODS_TMFX_POINTMAINTAIN.ECOLOGY_SHOP
     */
    private String ecologyShop;

    /**
     * 年度
     * ODS_TMFX_POINTMAINTAIN.YEAR
     */
    private String year;

    /**
     * 期间起
     * ODS_TMFX_POINTMAINTAIN.BEGIN_MONTH
     */
    private String beginMonth;

    /**
     * 期间止
     * ODS_TMFX_POINTMAINTAIN.END_MONTH
     */
    private String endMonth;

    /**
     * 产业
     * ODS_TMFX_POINTMAINTAIN.INDUSTRY
     */
    private String industry;

    /**
     * SKU
     * ODS_TMFX_POINTMAINTAIN.SKU
     */
    private String sku;

    /**
     * 型号描述
     * ODS_TMFX_POINTMAINTAIN.MODEL_DES
     */
    private String modelDes;

    /**
     * 品牌
     * ODS_TMFX_POINTMAINTAIN.BRAND
     */
    private String brand;

    /**
     * 奖励类型
     *
     */
    private String rewardType;

    /**
     * 类型 ymq
     * ODS_TMFX_POINTMAINTAIN.TYPE
     */
    private String type;

    /**
     * 目标起
     * ODS_TMFX_POINTMAINTAIN.BEGIN_TARGET
     */
    private String beginTarget;

    /**
     * 目标止
     * ODS_TMFX_POINTMAINTAIN.END_TARGET
     */
    private String endTarget;

    /**
     * 基础点位
     * ODS_TMFX_POINTMAINTAIN.BASE_POINT
     */
    private BigDecimal basePoint;

    /**
     * 额外返点
     * ODS_TMFX_POINTMAINTAIN.EXTRA_REBATE
     */
    private BigDecimal extraRebate;

    private BigDecimal stepPoint;

    private BigDecimal markPoint;

    private BigDecimal fixedAmount;

    private BigDecimal skuStepPoint;

    private BigDecimal amountPoint;

    /**
     * 对赌计算规则
     */
    private String intervalRule;

    /**
     * 对赌目标
     */
    private String bettingTarget;

    /**
     * 计算类型  actual 按实际  target 按目标
     */
    private String calculationType;

    /**
     * 对赌系数
     */
    private BigDecimal bettingCoefficient;

    public String getIntervalRule() {
        return intervalRule;
    }

    public void setIntervalRule(String intervalRule) {
        this.intervalRule = intervalRule;
    }

    public String getBettingTarget() {
        return bettingTarget;
    }

    public void setBettingTarget(String bettingTarget) {
        this.bettingTarget = bettingTarget;
    }

    public String getCalculationType() {
        return calculationType;
    }

    public void setCalculationType(String calculationType) {
        this.calculationType = calculationType;
    }

    public BigDecimal getBettingCoefficient() {
        return bettingCoefficient;
    }

    public void setBettingCoefficient(BigDecimal bettingCoefficient) {
        this.bettingCoefficient = bettingCoefficient;
    }

    /**
     * 创建时间
     * ODS_TMFX_POINTMAINTAIN.CREATE_TIME
     */
    private Date createTime;

    /**
     * 创建人
     * ODS_TMFX_POINTMAINTAIN.CREATE_BY
     */
    private String createBy;

    /**
     * 审核状态(B 业务审核 F财务审核 A 审核通过)
     * ODS_TMFX_POINTMAINTAIN.AUDIT_STATE
     */
    private String auditState;

    /**
     * 删除标记
     * ODS_TMFX_POINTMAINTAIN.DELETE_TAB
     */
    private String deleteTab;

    /**
     * 审核人
     * ODS_TMFX_POINTMAINTAIN.AUDIT_BY
     */
    private String auditBy;

    /**
     * 审核时间
     * ODS_TMFX_POINTMAINTAIN.AUDIT_TIME
     */
    private Date auditTime;
    private Integer page;
    private Integer rows;
    private String month;


    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public BigDecimal getAmountPoint() {
        return amountPoint;
    }

    public void setAmountPoint(BigDecimal amountPoint) {
        this.amountPoint = amountPoint;
    }

    /**
     * 主键<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.ID<br>
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * 主键<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.ID<br>
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 生态店<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.ECOLOGY_SHOP<br>
     * @return ECOLOGY_SHOP
     */
    public String getEcologyShop() {
        return ecologyShop;
    }

    /**
     * 生态店<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.ECOLOGY_SHOP<br>
     * @param ecologyShop
     */
    public void setEcologyShop(String ecologyShop) {
        this.ecologyShop = ecologyShop;
    }

    /**
     * 年度<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.YEAR<br>
     * @return YEAR
     */
    public String getYear() {
        return year;
    }

    /**
     * 年度<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.YEAR<br>
     * @param year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * 期间起<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.BEGIN_MONTH<br>
     * @return BEGIN_MONTH
     */
    public String getBeginMonth() {
        return beginMonth;
    }

    /**
     * 期间起<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.BEGIN_MONTH<br>
     * @param beginMonth
     */
    public void setBeginMonth(String beginMonth) {
        this.beginMonth = beginMonth;
    }

    /**
     * 期间止<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.END_MONTH<br>
     * @return END_MONTH
     */
    public String getEndMonth() {
        return endMonth;
    }

    /**
     * 期间止<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.END_MONTH<br>
     * @param endMonth
     */
    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    /**
     * 产业<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.INDUSTRY<br>
     * @return INDUSTRY
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * 产业<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.INDUSTRY<br>
     * @param industry
     */
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    /**
     * SKU<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.SKU<br>
     * @return SKU
     */
    public String getSku() {
        return sku;
    }

    /**
     * SKU<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.SKU<br>
     * @param sku
     */
    public void setSku(String sku) {
        this.sku = sku;
    }

    /**
     * 型号描述<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.MODEL_DES<br>
     * @return MODEL_DES
     */
    public String getModelDes() {
        return modelDes;
    }

    /**
     * 型号描述<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.MODEL_DES<br>
     * @param modelDes
     */
    public void setModelDes(String modelDes) {
        this.modelDes = modelDes;
    }

    /**
     * 品牌<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.BRAND<br>
     * @return BRAND
     */
    public String getBrand() {
        return brand;
    }

    /**
     * 品牌<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.BRAND<br>
     * @param brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }



    /**
     * 类型 ymq<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.TYPE<br>
     * @return TYPE
     */
    public String getType() {
        return type;
    }

    /**
     * 类型 ymq<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.TYPE<br>
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 目标起<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.BEGIN_TARGET<br>
     * @return BEGIN_TARGET
     */
    public String getBeginTarget() {
        return beginTarget;
    }

    /**
     * 目标起<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.BEGIN_TARGET<br>
     * @param beginTarget
     */
    public void setBeginTarget(String beginTarget) {
        this.beginTarget = beginTarget;
    }

    /**
     * 目标止<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.END_TARGET<br>
     * @return END_TARGET
     */
    public String getEndTarget() {
        return endTarget;
    }

    /**
     * 目标止<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.END_TARGET<br>
     * @param endTarget
     */
    public void setEndTarget(String endTarget) {
        this.endTarget = endTarget;
    }

    /**
     * 基础点位<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.BASE_POINT<br>
     * @return BASE_POINT
     */
    public BigDecimal getBasePoint() {
        return basePoint;
    }

    /**
     * 基础点位<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.BASE_POINT<br>
     * @param basePoint
     */
    public void setBasePoint(BigDecimal basePoint) {
        this.basePoint = basePoint;
    }

    /**
     * 额外返点<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.EXTRA_REBATE<br>
     * @return EXTRA_REBATE
     */
    public BigDecimal getExtraRebate() {
        return extraRebate;
    }

    /**
     * 额外返点<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.EXTRA_REBATE<br>
     * @param extraRebate
     */
    public void setExtraRebate(BigDecimal extraRebate) {
        this.extraRebate = extraRebate;
    }

    /**
     * 创建时间<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.CREATE_TIME<br>
     * @return CREATE_TIME
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.CREATE_TIME<br>
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 创建人<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.CREATE_BY<br>
     * @return CREATE_BY
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 创建人<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.CREATE_BY<br>
     * @param createBy
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 审核状态(B 业务审核 F财务审核 A 审核通过)<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.AUDIT_STATE<br>
     * @return AUDIT_STATE
     */
    public String getAuditState() {
        return auditState;
    }

    /**
     * 审核状态(B 业务审核 F财务审核 A 审核通过)<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.AUDIT_STATE<br>
     * @param auditState
     */
    public void setAuditState(String auditState) {
        this.auditState = auditState;
    }

    /**
     * 删除标记<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.DELETE_TAB<br>
     * @return DELETE_TAB
     */
    public String getDeleteTab() {
        return deleteTab;
    }

    /**
     * 删除标记<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.DELETE_TAB<br>
     * @param deleteTab
     */
    public void setDeleteTab(String deleteTab) {
        this.deleteTab = deleteTab;
    }

    /**
     * 审核人<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.AUDIT_BY<br>
     * @return AUDIT_BY
     */
    public String getAuditBy() {
        return auditBy;
    }

    /**
     * 审核人<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.AUDIT_BY<br>
     * @param auditBy
     */
    public void setAuditBy(String auditBy) {
        this.auditBy = auditBy;
    }

    /**
     * 审核时间<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.AUDIT_TIME<br>
     * @return AUDIT_TIME
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * 审核时间<br>
     * 对应数据库表字段：ODS_TMFX_POINTMAINTAIN.AUDIT_TIME<br>
     * @param auditTime
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
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
}