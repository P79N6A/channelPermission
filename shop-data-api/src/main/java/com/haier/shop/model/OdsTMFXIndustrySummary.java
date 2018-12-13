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
 * @Date: Created in 2018/5/29 14:29
 * @Modified By:
 */
@Excel(filename = "天猫分销按产业汇总导出")
public class OdsTMFXIndustrySummary implements Serializable {

  /**
   * 主键 ODS_TMFX_INDUSTRY_SUMMARY.ID
   */
  private String id;

  /**
   * 年度 ODS_TMFX_INDUSTRY_SUMMARY.YEAR
   */
  @ExcelTitle(titleName = "年度")
  private String year;

  /**
   * 期间 ODS_TMFX_INDUSTRY_SUMMARY.MONTH
   */
  @ExcelTitle(titleName = "期间")
  private String month;

  /**
   * 类型 ODS_TMFX_INDUSTRY_SUMMARY.TYPE
   */
  @ExcelTitle(titleName = "类型")
  private String type;

  /**
   * 产业 ODS_TMFX_INDUSTRY_SUMMARY.INDUSTRY
   */
  @ExcelTitle(titleName = "产业")
  private String industry;

  /**
   * 品牌 ODS_TMFX_INDUSTRY_SUMMARY.BRAND
   */
  @ExcelTitle(titleName = "品牌")
  private String brand;


  /**
   * 营销费用 ODS_TMFX_INDUSTRY_SUMMARY.YX_COST
   */
  @ExcelTitle(titleName = "营销费用")
  private BigDecimal yxCost;

  @ExcelTitle(titleName = "其他费用")
  private BigDecimal qtCost;

  @ExcelTitle(titleName = "月度兑现总额")
  private BigDecimal monthlyCashAmount;

  /**
   * 返利总额 ODS_TMFX_INDUSTRY_SUMMARY.REBATES_AMOUNT
   */
  @ExcelTitle(titleName = "返利总额")
  private BigDecimal rebatesAmount;

  @ExcelTitle(titleName = "销售总额")
  private BigDecimal saleAmount;//销售总额

  public BigDecimal getSaleAmount() {
    return saleAmount;
  }

  public void setSaleAmount(BigDecimal saleAmount) {
    this.saleAmount = saleAmount;
  }

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

  public BigDecimal getQtCost() {
    return qtCost;
  }

  public void setQtCost(BigDecimal qtCost) {
    this.qtCost = qtCost;
  }

  /**
   * 主键<br> 对应数据库表字段：ODS_TMFX_INDUSTRY_SUMMARY.ID<br>
   *
   * @return ID
   */
  public String getId() {
    return id;
  }

  /**
   * 主键<br> 对应数据库表字段：ODS_TMFX_INDUSTRY_SUMMARY.ID<br>
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * 年度<br> 对应数据库表字段：ODS_TMFX_INDUSTRY_SUMMARY.YEAR<br>
   *
   * @return YEAR
   */
  public String getYear() {
    return year;
  }

  /**
   * 年度<br> 对应数据库表字段：ODS_TMFX_INDUSTRY_SUMMARY.YEAR<br>
   */
  public void setYear(String year) {
    this.year = year;
  }

  /**
   * 期间<br> 对应数据库表字段：ODS_TMFX_INDUSTRY_SUMMARY.MONTH<br>
   *
   * @return MONTH
   */
  public String getMonth() {
    return month;
  }

  /**
   * 期间<br> 对应数据库表字段：ODS_TMFX_INDUSTRY_SUMMARY.MONTH<br>
   */
  public void setMonth(String month) {
    this.month = month;
  }

  /**
   * 类型<br> 对应数据库表字段：ODS_TMFX_INDUSTRY_SUMMARY.TYPE<br>
   *
   * @return TYPE
   */
  public String getType() {
    return type;
  }

  /**
   * 类型<br> 对应数据库表字段：ODS_TMFX_INDUSTRY_SUMMARY.TYPE<br>
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * 产业<br> 对应数据库表字段：ODS_TMFX_INDUSTRY_SUMMARY.INDUSTRY<br>
   *
   * @return INDUSTRY
   */
  public String getIndustry() {
    return industry;
  }

  /**
   * 产业<br> 对应数据库表字段：ODS_TMFX_INDUSTRY_SUMMARY.INDUSTRY<br>
   */
  public void setIndustry(String industry) {
    this.industry = industry;
  }

  /**
   * 品牌<br> 对应数据库表字段：ODS_TMFX_INDUSTRY_SUMMARY.BRAND<br>
   *
   * @return BRAND
   */
  public String getBrand() {
    return brand;
  }

  /**
   * 品牌<br> 对应数据库表字段：ODS_TMFX_INDUSTRY_SUMMARY.BRAND<br>
   */
  public void setBrand(String brand) {
    this.brand = brand;
  }

  /**
   * 返利总额<br> 对应数据库表字段：ODS_TMFX_INDUSTRY_SUMMARY.REBATES_AMOUNT<br>
   *
   * @return REBATES_AMOUNT
   */
  public BigDecimal getRebatesAmount() {
    return rebatesAmount;
  }

  /**
   * 返利总额<br> 对应数据库表字段：ODS_TMFX_INDUSTRY_SUMMARY.REBATES_AMOUNT<br>
   */
  public void setRebatesAmount(BigDecimal rebatesAmount) {
    this.rebatesAmount = rebatesAmount;
  }

  /**
   * 营销费用<br> 对应数据库表字段：ODS_TMFX_INDUSTRY_SUMMARY.YX_COST<br>
   *
   * @return YX_COST
   */
  public BigDecimal getYxCost() {
    return yxCost;
  }

  /**
   * 营销费用<br> 对应数据库表字段：ODS_TMFX_INDUSTRY_SUMMARY.YX_COST<br>
   */
  public void setYxCost(BigDecimal yxCost) {
    this.yxCost = yxCost;
  }
}