package com.haier.shop.model;

import com.haier.shop.util.excel.Excel;
import com.haier.shop.util.excel.ExcelTitle;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.shop.model
 * @Date: Created in 2018/5/29 14:30
 * @Modified By:
 */
@Excel(filename = "苏宁分销-按产业汇总导出")
public class SNOdsTMFXRebatesSummary implements Serializable {

  /**
   * 主键 ODS_TMFX_REBATES_SUMMARY.ID
   */
  private String id;

  /**
   * 生态店 ODS_TMFX_REBATES_SUMMARY.ECOLOGY_SHOP
   */
  @ExcelTitle(titleName = "生态店")
  private String ecologyShop;

  /**
   * 年度 ODS_TMFX_REBATES_SUMMARY.YEAR
   */
  @ExcelTitle(titleName = "年度")
  private String year;

  /**
   * 期间 ODS_TMFX_REBATES_SUMMARY.MONTH
   */
  @ExcelTitle(titleName = "期间")
  private String month;

  /**
   * 类型 ODS_TMFX_REBATES_SUMMARY.TYPE
   */
  @ExcelTitle(titleName = "类型")
  private String type;

  /**
   * 产业 ODS_TMFX_REBATES_SUMMARY.INDUSTRY
   */
  @ExcelTitle(titleName = "产业")
  private String industry;

  /**
   * 品牌 ODS_TMFX_REBATES_SUMMARY.BRAND
   */
  @ExcelTitle(titleName = "品牌")
  private String brand;

  /**
   * 实际销售额 ODS_TMFX_REBATES_SUMMARY.SALE_AMOUNT
   */
  @ExcelTitle(titleName = "实际销售额")
  private BigDecimal saleAmount;

  /**
   * 兑现总额 ODS_TMFX_REBATES_SUMMARY.REBATES_AMOUNT
   */
  @ExcelTitle(titleName = "兑现总额")
  private BigDecimal rebatesAmount;

  /**
   * 目标 ODS_TMFX_REBATES_SUMMARY.TARGET
   */
  @ExcelTitle(titleName = "目标")
  private BigDecimal target;

  /**
   * 完成率 ODS_TMFX_REBATES_SUMMARY.COMPLETION
   */
  @ExcelTitle(titleName = "完成率")
  private BigDecimal completion;

  /**
   * 产业目标 ODS_TMFX_REBATES_SUMMARY.COMPLETION
   */
  @ExcelTitle(titleName = "产业目标")
  private BigDecimal industryTarget;

  /**
   * 产业完成率 ODS_TMFX_REBATES_SUMMARY.COMPLETION
   */
  @ExcelTitle(titleName = "产业完成率")
  private BigDecimal industryCompletion;

  /**
   * 基础点位 ODS_TMFX_REBATES_SUMMARY.BASE_POINT
   */
  @ExcelTitle(titleName = "基础点位")
  private BigDecimal basePoint;

  /**
   * 额外点位 ODS_TMFX_REBATES_SUMMARY.EXTRA_REBATE
   */
  @ExcelTitle(titleName = "额外点位")
  private BigDecimal extraRebate;

  /**
   * 台阶点位 ODS_TMFX_REBATES_SUMMARY.EXTRA_REBATE
   */
  @ExcelTitle(titleName = "台阶点位")
  private BigDecimal stepPoint;

  /**
   * 达标点位 ODS_TMFX_REBATES_SUMMARY.EXTRA_REBATE
   */
  @ExcelTitle(titleName = "达标点位")
  private BigDecimal markPoint;

  @ExcelTitle(titleName = "SKU销额台阶点位")
  private BigDecimal skuStepPoint;

  /**
   * 基础返点金额 ODS_TMFX_REBATES_SUMMARY.BASE_REBATES_AMOUNT
   */
  @ExcelTitle(titleName = "基础返点金额")
  private BigDecimal baseRebatesAmount;

  /**
   * 额外返点金额 ODS_TMFX_REBATES_SUMMARY.EXTRA_REBATES_AMOUNT
   */
  @ExcelTitle(titleName = "额外返点金额")
  private BigDecimal extraRebatesAmount;

  @ExcelTitle(titleName = "台阶返利金额")
  private BigDecimal stepRebatesAmount;

  @ExcelTitle(titleName = "月度达标返利金额")
  private BigDecimal markRebatesAmount;

  @ExcelTitle(titleName = "固定返利金额")
  private BigDecimal fixedAmount;

  @ExcelTitle(titleName = "SKU销额台阶返利金额")
  private BigDecimal skuStepRebatesAmount;

  /**
   * 营销费用 ODS_TMFX_REBATES_SUMMARY.YX_COST
   */
  @ExcelTitle(titleName = "营销费用")
  private BigDecimal yxCost;

  /**
   * 其他费用 ODS_TMFX_REBATES_SUMMARY.QT_COST
   */
  @ExcelTitle(titleName = "其他费用")
  private BigDecimal qtCost;

  @ExcelTitle(titleName = "分摊比例")
  private BigDecimal shareProportion;

  @ExcelTitle(titleName = "返利总额")
  private BigDecimal cashAmount;//返利总额

  public BigDecimal getCashAmount() {
    return cashAmount;
  }

  public void setCashAmount(BigDecimal cashAmount) {
    this.cashAmount = cashAmount;
  }

  public BigDecimal getShareProportion() {
    return shareProportion;
  }

  public void setShareProportion(BigDecimal shareProportion) {
    this.shareProportion = shareProportion;
  }

  private Integer page;

  private Integer rows;

  public int getStart() {
    if (this.page == null || rows == null) {
      return 0;
    }
    return (this.page - 1) * this.rows;
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

  /**
   * 主键<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.ID<br>
   *
   * @return ID
   */
  public String getId() {
    return id;
  }

  /**
   * 主键<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.ID<br>
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * 生态店<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.ECOLOGY_SHOP<br>
   *
   * @return ECOLOGY_SHOP
   */
  public String getEcologyShop() {
    return ecologyShop;
  }

  /**
   * 生态店<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.ECOLOGY_SHOP<br>
   */
  public void setEcologyShop(String ecologyShop) {
    this.ecologyShop = ecologyShop;
  }

  /**
   * 年度<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.YEAR<br>
   *
   * @return YEAR
   */
  public String getYear() {
    return year;
  }

  /**
   * 年度<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.YEAR<br>
   */
  public void setYear(String year) {
    this.year = year;
  }

  /**
   * 期间<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.MONTH<br>
   *
   * @return MONTH
   */
  public String getMonth() {
    return month;
  }

  /**
   * 期间<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.MONTH<br>
   */
  public void setMonth(String month) {
    this.month = month;
  }

  /**
   * 类型<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.TYPE<br>
   *
   * @return TYPE
   */
  public String getType() {
    return type;
  }

  /**
   * 类型<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.TYPE<br>
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * 产业<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.INDUSTRY<br>
   *
   * @return INDUSTRY
   */
  public String getIndustry() {
    return industry;
  }

  /**
   * 产业<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.INDUSTRY<br>
   */
  public void setIndustry(String industry) {
    this.industry = industry;
  }

  /**
   * 品牌<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.BRAND<br>
   *
   * @return BRAND
   */
  public String getBrand() {
    return brand;
  }

  /**
   * 品牌<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.BRAND<br>
   */
  public void setBrand(String brand) {
    this.brand = brand;
  }

  /**
   * 实际销售额<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.SALE_AMOUNT<br>
   *
   * @return SALE_AMOUNT
   */
  public BigDecimal getSaleAmount() {
    return saleAmount;
  }

  /**
   * 实际销售额<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.SALE_AMOUNT<br>
   */
  public void setSaleAmount(BigDecimal saleAmount) {
    this.saleAmount = saleAmount;
  }

  /**
   * 返利总额<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.REBATES_AMOUNT<br>
   *
   * @return REBATES_AMOUNT
   */
  public BigDecimal getRebatesAmount() {
    return rebatesAmount;
  }

  /**
   * 返利总额<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.REBATES_AMOUNT<br>
   */
  public void setRebatesAmount(BigDecimal rebatesAmount) {
    this.rebatesAmount = rebatesAmount;
  }

  /**
   * 目标<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.TARGET<br>
   *
   * @return TARGET
   */
  public BigDecimal getTarget() {
    return target;
  }

  /**
   * 目标<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.TARGET<br>
   */
  public void setTarget(BigDecimal target) {
    this.target = target;
  }

  /**
   * 完成率<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.COMPLETION<br>
   *
   * @return COMPLETION
   */
  public BigDecimal getCompletion() {
    return completion;
  }

  /**
   * 完成率<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.COMPLETION<br>
   */
  public void setCompletion(BigDecimal completion) {
    this.completion = completion;
  }

  /**
   * 基础点位<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.BASE_POINT<br>
   *
   * @return BASE_POINT
   */
  public BigDecimal getBasePoint() {
    return basePoint;
  }

  /**
   * 基础点位<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.BASE_POINT<br>
   */
  public void setBasePoint(BigDecimal basePoint) {
    this.basePoint = basePoint;
  }

  /**
   * 额外点位<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.EXTRA_REBATE<br>
   *
   * @return EXTRA_REBATE
   */
  public BigDecimal getExtraRebate() {
    return extraRebate;
  }

  /**
   * 额外点位<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.EXTRA_REBATE<br>
   */
  public void setExtraRebate(BigDecimal extraRebate) {
    this.extraRebate = extraRebate;
  }

  /**
   * 基础返点金额<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.BASE_REBATES_AMOUNT<br>
   *
   * @return BASE_REBATES_AMOUNT
   */
  public BigDecimal getBaseRebatesAmount() {
    return baseRebatesAmount;
  }

  /**
   * 基础返点金额<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.BASE_REBATES_AMOUNT<br>
   */
  public void setBaseRebatesAmount(BigDecimal baseRebatesAmount) {
    this.baseRebatesAmount = baseRebatesAmount;
  }

  /**
   * 额外返点金额<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.EXTRA_REBATES_AMOUNT<br>
   *
   * @return EXTRA_REBATES_AMOUNT
   */
  public BigDecimal getExtraRebatesAmount() {
    return extraRebatesAmount;
  }

  /**
   * 额外返点金额<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.EXTRA_REBATES_AMOUNT<br>
   */
  public void setExtraRebatesAmount(BigDecimal extraRebatesAmount) {
    this.extraRebatesAmount = extraRebatesAmount;
  }

  /**
   * 营销费用<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.YX_COST<br>
   *
   * @return YX_COST
   */
  public BigDecimal getYxCost() {
    return yxCost;
  }

  /**
   * 营销费用<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.YX_COST<br>
   */
  public void setYxCost(BigDecimal yxCost) {
    this.yxCost = yxCost;
  }

  /**
   * 其他费用<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.QT_COST<br>
   *
   * @return QT_COST
   */
  public BigDecimal getQtCost() {
    return qtCost;
  }

  /**
   * 其他费用<br> 对应数据库表字段：ODS_TMFX_REBATES_SUMMARY.QT_COST<br>
   */
  public void setQtCost(BigDecimal qtCost) {
    this.qtCost = qtCost;
  }

  public BigDecimal getIndustryTarget() {
    return industryTarget;
  }

  public void setIndustryTarget(BigDecimal industryTarget) {
    this.industryTarget = industryTarget;
  }

  public BigDecimal getIndustryCompletion() {
    return industryCompletion;
  }

  public void setIndustryCompletion(BigDecimal industryCompletion) {
    this.industryCompletion = industryCompletion;
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

  public BigDecimal getMarkRebatesAmount() {
    return markRebatesAmount;
  }

  public void setMarkRebatesAmount(BigDecimal markRebatesAmount) {
    this.markRebatesAmount = markRebatesAmount;
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
}
