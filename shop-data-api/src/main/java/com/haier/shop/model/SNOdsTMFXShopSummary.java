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
 * @Date: Created in 2018/5/29 14:13
 * @Modified By:
 */
@Excel(filename = "苏宁分销-按生态店汇总")
public class SNOdsTMFXShopSummary implements Serializable {

  /**
   * 主键 ODS_TMFX_SHOP_SUMMARY.ID
   */
  private String id;

  /**
   * 年度 ODS_TMFX_SHOP_SUMMARY.YEAR
   */
  @ExcelTitle(titleName = "年度")
  private String year;

  /**
   * 期间 ODS_TMFX_SHOP_SUMMARY.MONTH
   */
  @ExcelTitle(titleName = "期间")
  private String month;

  /**
   * 生态店 ODS_TMFX_SHOP_SUMMARY.ECOLOGY_SHOP
   */
  @ExcelTitle(titleName = "生态店")
  private String ecologyShop;

  /**
   * 类型 ODS_TMFX_SHOP_SUMMARY.TYPE
   */
  @ExcelTitle(titleName = "类型")
  private String type;

  /**
   * 年度签约目标 ODS_TMFX_SHOP_SUMMARY.YEAR_TARGET
   */
  @ExcelTitle(titleName = "年度签约目标")
  private BigDecimal yearTarget;

  /**
   * 目标 ODS_TMFX_SHOP_SUMMARY.TARGET
   */
  @ExcelTitle(titleName = "目标")
  private BigDecimal target;

  /**
   * 实际销售额 ODS_TMFX_SHOP_SUMMARY.SALE_AMOUNT
   */
  @ExcelTitle(titleName = "实际销售额")
  private BigDecimal saleAmount;

  /**
   * 完成率 ODS_TMFX_SHOP_SUMMARY.COMPLETION
   */
  @ExcelTitle(titleName = "完成率")
  private BigDecimal completion;

  @ExcelTitle(titleName = "月度兑现总额")
  private BigDecimal monthlyCashAmount;
  /**
   * 基础返点金额 ODS_TMFX_SHOP_SUMMARY.BASE_REBATES_AMOUNT
   */
  private BigDecimal baseRebatesAmount;

  /**
   * 额外返点金额 ODS_TMFX_SHOP_SUMMARY.EXTRA_REBATES_AMOUNT
   */
  private BigDecimal extraRebatesAmount;

  private BigDecimal stepRebatesAmount;

  private BigDecimal markRebatesAmount;

  private BigDecimal fixedAmount;

  private BigDecimal skuStepRebatesAmount;

  /**
   * 营销费用 ODS_TMFX_SHOP_SUMMARY.YX_COST
   */
  @ExcelTitle(titleName = "营销费用")
  private BigDecimal yxCost;

  /**
   * 其他费用 ODS_TMFX_SHOP_SUMMARY.QT_COST
   */
  @ExcelTitle(titleName = "其他费用")
  private BigDecimal qtCost;

  /**
   * 返利总额 ODS_TMFX_SHOP_SUMMARY.REBATES_AMOUNT
   */
  @ExcelTitle(titleName = "返利总额")
  private BigDecimal rebatesAmount;


  public BigDecimal getMonthlyCashAmount() {
    return monthlyCashAmount;
  }

  public void setMonthlyCashAmount(BigDecimal monthlyCashAmount) {
    this.monthlyCashAmount = monthlyCashAmount;
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
   * 主键<br> 对应数据库表字段：ODS_TMFX_SHOP_SUMMARY.ID<br>
   *
   * @return ID
   */
  public String getId() {
    return id;
  }

  /**
   * 主键<br> 对应数据库表字段：ODS_TMFX_SHOP_SUMMARY.ID<br>
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * 年度<br> 对应数据库表字段：ODS_TMFX_SHOP_SUMMARY.YEAR<br>
   *
   * @return YEAR
   */
  public String getYear() {
    return year;
  }

  /**
   * 年度<br> 对应数据库表字段：ODS_TMFX_SHOP_SUMMARY.YEAR<br>
   */
  public void setYear(String year) {
    this.year = year;
  }

  /**
   * 期间<br> 对应数据库表字段：ODS_TMFX_SHOP_SUMMARY.MONTH<br>
   *
   * @return MONTH
   */
  public String getMonth() {
    return month;
  }

  /**
   * 期间<br> 对应数据库表字段：ODS_TMFX_SHOP_SUMMARY.MONTH<br>
   */
  public void setMonth(String month) {
    this.month = month;
  }

  /**
   * 生态店<br> 对应数据库表字段：ODS_TMFX_SHOP_SUMMARY.ECOLOGY_SHOP<br>
   *
   * @return ECOLOGY_SHOP
   */
  public String getEcologyShop() {
    return ecologyShop;
  }

  /**
   * 生态店<br> 对应数据库表字段：ODS_TMFX_SHOP_SUMMARY.ECOLOGY_SHOP<br>
   */
  public void setEcologyShop(String ecologyShop) {
    this.ecologyShop = ecologyShop;
  }

  /**
   * 类型<br> 对应数据库表字段：ODS_TMFX_SHOP_SUMMARY.TYPE<br>
   *
   * @return TYPE
   */
  public String getType() {
    return type;
  }

  /**
   * 类型<br> 对应数据库表字段：ODS_TMFX_SHOP_SUMMARY.TYPE<br>
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * 年度签约目标<br> 对应数据库表字段：ODS_TMFX_SHOP_SUMMARY.YEAR_TARGET<br>
   *
   * @return YEAR_TARGET
   */
  public BigDecimal getYearTarget() {
    return yearTarget;
  }

  /**
   * 年度签约目标<br> 对应数据库表字段：ODS_TMFX_SHOP_SUMMARY.YEAR_TARGET<br>
   */
  public void setYearTarget(BigDecimal yearTarget) {
    this.yearTarget = yearTarget;
  }

  /**
   * 目标<br> 对应数据库表字段：ODS_TMFX_SHOP_SUMMARY.TARGET<br>
   *
   * @return TARGET
   */
  public BigDecimal getTarget() {
    return target;
  }

  /**
   * 目标<br> 对应数据库表字段：ODS_TMFX_SHOP_SUMMARY.TARGET<br>
   */
  public void setTarget(BigDecimal target) {
    this.target = target;
  }

  /**
   * 实际销售额<br> 对应数据库表字段：ODS_TMFX_SHOP_SUMMARY.SALE_AMOUNT<br>
   *
   * @return SALE_AMOUNT
   */
  public BigDecimal getSaleAmount() {
    return saleAmount;
  }

  /**
   * 实际销售额<br> 对应数据库表字段：ODS_TMFX_SHOP_SUMMARY.SALE_AMOUNT<br>
   */
  public void setSaleAmount(BigDecimal saleAmount) {
    this.saleAmount = saleAmount;
  }

  /**
   * 完成率<br> 对应数据库表字段：ODS_TMFX_SHOP_SUMMARY.COMPLETION<br>
   *
   * @return COMPLETION
   */
  public BigDecimal getCompletion() {
    return completion;
  }

  /**
   * 完成率<br> 对应数据库表字段：ODS_TMFX_SHOP_SUMMARY.COMPLETION<br>
   */
  public void setCompletion(BigDecimal completion) {
    this.completion = completion;
  }

  /**
   * 基础返点金额<br> 对应数据库表字段：ODS_TMFX_SHOP_SUMMARY.BASE_REBATES_AMOUNT<br>
   *
   * @return BASE_REBATES_AMOUNT
   */
  public BigDecimal getBaseRebatesAmount() {
    return baseRebatesAmount;
  }

  /**
   * 基础返点金额<br> 对应数据库表字段：ODS_TMFX_SHOP_SUMMARY.BASE_REBATES_AMOUNT<br>
   */
  public void setBaseRebatesAmount(BigDecimal baseRebatesAmount) {
    this.baseRebatesAmount = baseRebatesAmount;
  }

  /**
   * 额外返点金额<br> 对应数据库表字段：ODS_TMFX_SHOP_SUMMARY.EXTRA_REBATES_AMOUNT<br>
   *
   * @return EXTRA_REBATES_AMOUNT
   */
  public BigDecimal getExtraRebatesAmount() {
    return extraRebatesAmount;
  }

  /**
   * 额外返点金额<br> 对应数据库表字段：ODS_TMFX_SHOP_SUMMARY.EXTRA_REBATES_AMOUNT<br>
   */
  public void setExtraRebatesAmount(BigDecimal extraRebatesAmount) {
    this.extraRebatesAmount = extraRebatesAmount;
  }

  /**
   * 营销费用<br> 对应数据库表字段：ODS_TMFX_SHOP_SUMMARY.YX_COST<br>
   *
   * @return YX_COST
   */
  public BigDecimal getYxCost() {
    return yxCost;
  }

  /**
   * 营销费用<br> 对应数据库表字段：ODS_TMFX_SHOP_SUMMARY.YX_COST<br>
   */
  public void setYxCost(BigDecimal yxCost) {
    this.yxCost = yxCost;
  }

  /**
   * 其他费用<br> 对应数据库表字段：ODS_TMFX_SHOP_SUMMARY.QT_COST<br>
   *
   * @return QT_COST
   */
  public BigDecimal getQtCost() {
    return qtCost;
  }

  /**
   * 其他费用<br> 对应数据库表字段：ODS_TMFX_SHOP_SUMMARY.QT_COST<br>
   */
  public void setQtCost(BigDecimal qtCost) {
    this.qtCost = qtCost;
  }

  /**
   * 返利总额<br> 对应数据库表字段：ODS_TMFX_SHOP_SUMMARY.REBATES_AMOUNT<br>
   *
   * @return REBATES_AMOUNT
   */
  public BigDecimal getRebatesAmount() {
    return rebatesAmount;
  }

  /**
   * 返利总额<br> 对应数据库表字段：ODS_TMFX_SHOP_SUMMARY.REBATES_AMOUNT<br>
   */
  public void setRebatesAmount(BigDecimal rebatesAmount) {
    this.rebatesAmount = rebatesAmount;
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

  public BigDecimal getSkuStepRebatesAmount() {
    return skuStepRebatesAmount;
  }

  public void setSkuStepRebatesAmount(BigDecimal skuStepRebatesAmount) {
    this.skuStepRebatesAmount = skuStepRebatesAmount;
  }
}
