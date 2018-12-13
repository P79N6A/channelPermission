package com.haier.shop.model;

import com.haier.shop.util.excel.Excel;
import com.haier.shop.util.excel.ExcelTitle;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 天猫分销-营销费用维护 ODS_TMFX_PROMOTION_COST
 *
 * @version 1.0 2017-10-23
 */
@Excel(filename = "营销费用维护导出")
public class OdsTMFXPromotionCost implements Serializable {
  /**
   * 主键
   * ODS_TMFX_PROMOTION_COST.ID
   */
  private String id;

  /**
   * 年度
   * ODS_TMFX_PROMOTION_COST.YEAR
   */
  @ExcelTitle(titleName = "年度", importIndex = 0)
  private String year;

  /**
   * 期间
   * ODS_TMFX_PROMOTION_COST.MONTH
   */
  @ExcelTitle(titleName = "期间", importIndex = 1)
  private String month;

  /**
   * 生态店
   * ODS_TMFX_PROMOTION_COST.ECOLOGY_SHOP
   */
  @ExcelTitle(titleName = "生态店", importIndex = 2)
  private String ecologyShop;

  @ExcelTitle(titleName = "产业", importIndex = 3)
  private String industry;

  /**
   * 品牌
   * ODS_TMFX_PROMOTION_COST.BRAND
   */
  @ExcelTitle(titleName = "品牌", importIndex = 4)
  private String brand;

  /**
   * 费用描述
   * ODS_TMFX_PROMOTION_COST.COST_DES
   */
  @ExcelTitle(titleName = "费用描述", importIndex = 7)
  private String costDes;

  /**
   * 营销费用金额
   * ODS_TMFX_PROMOTION_COST.yx_cost_amount
   */
  @ExcelTitle(importIndex = 5)
  private BigDecimal yxCostAmount;

  @ExcelTitle(titleName = "其他费用金额", importIndex = 6)
  private BigDecimal qtCostAmount;

  private String costType; // 费用类别  YX   QT

  public String getCostType() {
    return costType;
  }

  public void setCostType(String costType) {
    this.costType = costType;
  }

  /**
   * 创建时间
   * ODS_TMFX_PROMOTION_COST.CREATE_TIME
   */
  @ExcelTitle(titleName = "创建时间")
  private Date createTime;

  /**
   * 创建人
   * ODS_TMFX_PROMOTION_COST.CREATE_BY
   */
  @ExcelTitle(titleName = "创建人")
  private String createBy;

  /**
   * 初审状态
   * ODS_TMFX_PROMOTION_COST.FIRST_AUDIT
   */
  @ExcelTitle(titleName = "初审状态")
  private String firstAudit;

  /**
   * 复审状态
   * ODS_TMFX_PROMOTION_COST.SECOND_AUDIT
   */
  @ExcelTitle(titleName = "复审状态")
  private String secondAudit;

  /**
   * 审核人
   * ODS_TMFX_PROMOTION_COST.AUDIT_BY
   */
  @ExcelTitle(titleName = "审核人")
  private String auditBy;

  /**
   * 审核时间
   * ODS_TMFX_PROMOTION_COST.AUDIT_TIME
   */
  @ExcelTitle(titleName = "审核时间")
  private Date auditTime;

  private Integer page;
  private Integer rows;

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

  public String getIndustry() {
    return industry;
  }

  public void setIndustry(String industry) {
    this.industry = industry;
  }

  /**
   * 主键<br>
   * 对应数据库表字段：ODS_TMFX_PROMOTION_COST.ID<br>
   * @return ID
   */
  public String getId() {
    return id;
  }

  /**
   * 主键<br>
   * 对应数据库表字段：ODS_TMFX_PROMOTION_COST.ID<br>
   * @param id
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * 年度<br>
   * 对应数据库表字段：ODS_TMFX_PROMOTION_COST.YEAR<br>
   * @return YEAR
   */
  public String getYear() {
    return year;
  }

  /**
   * 年度<br>
   * 对应数据库表字段：ODS_TMFX_PROMOTION_COST.YEAR<br>
   * @param year
   */
  public void setYear(String year) {
    this.year = year;
  }

  /**
   * 期间<br>
   * 对应数据库表字段：ODS_TMFX_PROMOTION_COST.MONTH<br>
   * @return MONTH
   */
  public String getMonth() {
    return month;
  }

  /**
   * 期间<br>
   * 对应数据库表字段：ODS_TMFX_PROMOTION_COST.MONTH<br>
   * @param month
   */
  public void setMonth(String month) {
    this.month = month;
  }

  /**
   * 生态店<br>
   * 对应数据库表字段：ODS_TMFX_PROMOTION_COST.ECOLOGY_SHOP<br>
   * @return ECOLOGY_SHOP
   */
  public String getEcologyShop() {
    return ecologyShop;
  }

  /**
   * 生态店<br>
   * 对应数据库表字段：ODS_TMFX_PROMOTION_COST.ECOLOGY_SHOP<br>
   * @param ecologyShop
   */
  public void setEcologyShop(String ecologyShop) {
    this.ecologyShop = ecologyShop;
  }

  /**
   * 品牌<br>
   * 对应数据库表字段：ODS_TMFX_PROMOTION_COST.BRAND<br>
   * @return BRAND
   */
  public String getBrand() {
    return brand;
  }

  /**
   * 品牌<br>
   * 对应数据库表字段：ODS_TMFX_PROMOTION_COST.BRAND<br>
   * @param brand
   */
  public void setBrand(String brand) {
    this.brand = brand;
  }

  /**
   * 费用描述<br>
   * 对应数据库表字段：ODS_TMFX_PROMOTION_COST.COST_DES<br>
   * @return COST_DES
   */
  public String getCostDes() {
    return costDes;
  }

  /**
   * 费用描述<br>
   * 对应数据库表字段：ODS_TMFX_PROMOTION_COST.COST_DES<br>
   * @param costDes
   */
  public void setCostDes(String costDes) {
    this.costDes = costDes;
  }

  /**
   * 创建时间<br>
   * 对应数据库表字段：ODS_TMFX_PROMOTION_COST.CREATE_TIME<br>
   * @return CREATE_TIME
   */
  public Date getCreateTime() {
    return createTime;
  }

  /**
   * 创建时间<br>
   * 对应数据库表字段：ODS_TMFX_PROMOTION_COST.CREATE_TIME<br>
   * @param createTime
   */
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  /**
   * 创建人<br>
   * 对应数据库表字段：ODS_TMFX_PROMOTION_COST.CREATE_BY<br>
   * @return CREATE_BY
   */
  public String getCreateBy() {
    return createBy;
  }

  /**
   * 创建人<br>
   * 对应数据库表字段：ODS_TMFX_PROMOTION_COST.CREATE_BY<br>
   * @param createBy
   */
  public void setCreateBy(String createBy) {
    this.createBy = createBy;
  }

  /**
   * 初审状态<br>
   * 对应数据库表字段：ODS_TMFX_PROMOTION_COST.FIRST_AUDIT<br>
   * @return FIRST_AUDIT
   */
  public String getFirstAudit() {
    return firstAudit;
  }

  /**
   * 初审状态<br>
   * 对应数据库表字段：ODS_TMFX_PROMOTION_COST.FIRST_AUDIT<br>
   * @param firstAudit
   */
  public void setFirstAudit(String firstAudit) {
    this.firstAudit = firstAudit;
  }

  /**
   * 复审状态<br>
   * 对应数据库表字段：ODS_TMFX_PROMOTION_COST.SECOND_AUDIT<br>
   * @return SECOND_AUDIT
   */
  public String getSecondAudit() {
    return secondAudit;
  }

  /**
   * 复审状态<br>
   * 对应数据库表字段：ODS_TMFX_PROMOTION_COST.SECOND_AUDIT<br>
   * @param secondAudit
   */
  public void setSecondAudit(String secondAudit) {
    this.secondAudit = secondAudit;
  }

  /**
   * 审核人<br>
   * 对应数据库表字段：ODS_TMFX_PROMOTION_COST.AUDIT_BY<br>
   * @return AUDIT_BY
   */
  public String getAuditBy() {
    return auditBy;
  }

  /**
   * 审核人<br>
   * 对应数据库表字段：ODS_TMFX_PROMOTION_COST.AUDIT_BY<br>
   * @param auditBy
   */
  public void setAuditBy(String auditBy) {
    this.auditBy = auditBy;
  }

  /**
   * 审核时间<br>
   * 对应数据库表字段：ODS_TMFX_PROMOTION_COST.AUDIT_TIME<br>
   * @return AUDIT_TIME
   */
  public Date getAuditTime() {
    return auditTime;
  }

  /**
   * 审核时间<br>
   * 对应数据库表字段：ODS_TMFX_PROMOTION_COST.AUDIT_TIME<br>
   * @param auditTime
   */
  public void setAuditTime(Date auditTime) {
    this.auditTime = auditTime;
  }

  public BigDecimal getYxCostAmount() {
    return yxCostAmount;
  }

  public void setYxCostAmount(BigDecimal yxCostAmount) {
    this.yxCostAmount = yxCostAmount;
  }

  public BigDecimal getQtCostAmount() {
    return qtCostAmount;
  }

  public void setQtCostAmount(BigDecimal qtCostAmount) {
    this.qtCostAmount = qtCostAmount;
  }

  public int getStart() {
    if (this.page == null || rows == null) {
      return 0;
    }
    return (this.page - 1) * this.rows;
  }
}