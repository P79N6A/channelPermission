package com.haier.shop.model;

import com.haier.shop.util.excel.Excel;
import com.haier.shop.util.excel.ExcelTitle;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.shop.model
 * @Date: Created in 2018/5/28 14:10
 * @Modified By:
 */
@Excel(filename = "苏宁分销返利政策目标维护")
public class SNOdsTMFXTargetMaintain implements Serializable {

  /**
   * 主键 ODS_TMFX_TARGETMAINTAIN.ID
   */
  private int id;

  /**
   * 生态店 ODS_TMFX_TARGETMAINTAIN.ECOLOGY_SHOP
   */
  @ExcelTitle(titleName = "生态店", importIndex = 0)
  private String ecologyShop;

  /**
   * 年度 ODS_TMFX_TARGETMAINTAIN.YEAR
   */
  @ExcelTitle(titleName = "年度", importIndex = 1)
  private String year;

  /**
   * 期间 ODS_TMFX_TARGETMAINTAIN.MONTH
   */
  @ExcelTitle(titleName = "期间", importIndex = 2)
  private String month;

  /**
   * 产业 ODS_TMFX_TARGETMAINTAIN.INDUSTRY
   */
  @ExcelTitle(titleName = "产业", importIndex = 3)
  private String industry;

  /**
   * 品牌 ODS_TMFX_TARGETMAINTAIN.BRAND
   */
  @ExcelTitle(titleName = "品牌", importIndex = 4)
  private String brand;

  /**
   * 类型 年度y 月度m 季度q ODS_TMFX_TARGETMAINTAIN.TYPE
   */
  @ExcelTitle(titleName = "类型", importIndex = 5)
  private String type;

  /**
   * 目标(万元) ODS_TMFX_TARGETMAINTAIN.TARGET
   */
  @ExcelTitle(titleName = "目标", importIndex = 6)
  private BigDecimal target;

  /**
   * 标记删除 ODS_TMFX_TARGETMAINTAIN.DELETE_TAB
   */
  @ExcelTitle(titleName = "标记删除")
  private String deleteTab;

  /**
   * 创建时间 ODS_TMFX_TARGETMAINTAIN.CREATE_TIME
   */
  @ExcelTitle(titleName = "创建时间")
  private Date createTime;

  /**
   * 创建人 ODS_TMFX_TARGETMAINTAIN.CREATE_BY
   */
  @ExcelTitle(titleName = "创建人")
  private String createBy;

  /**
   * 审核状态 ODS_TMFX_TARGETMAINTAIN.AUDIT_STATE
   */
  @ExcelTitle(titleName = "审核状态")
  private String auditState;

  /**
   * 审核人 ODS_TMFX_TARGETMAINTAIN.AUDIT_BY
   */
  @ExcelTitle(titleName = "审核人")
  private String auditBy;

  /**
   * 审核时间 ODS_TMFX_TARGETMAINTAIN.AUDIT_TIME
   */
  @ExcelTitle(titleName = "审核时间")
  private Date auditTime;
  //是否已生成数据的标志
  private String createFlag;

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

  public int getStart() {
    if (this.page == null || rows == null) {
      return 0;
    }
    return (this.page - 1) * this.rows;
  }

  public String getCreateFlag() {
    return createFlag;
  }

  public void setCreateFlag(String createFlag) {
    this.createFlag = createFlag;
  }

  /**
   * 主键<br> 对应数据库表字段：ODS_TMFX_TARGETMAINTAIN.ID<br>
   *
   * @return ID
   */
  public int getId() {
    return id;
  }

  /**
   * 主键<br> 对应数据库表字段：ODS_TMFX_TARGETMAINTAIN.ID<br>
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * 生态店<br> 对应数据库表字段：ODS_TMFX_TARGETMAINTAIN.ECOLOGY_SHOP<br>
   *
   * @return ECOLOGY_SHOP
   */
  public String getEcologyShop() {
    return ecologyShop;
  }

  /**
   * 生态店<br> 对应数据库表字段：ODS_TMFX_TARGETMAINTAIN.ECOLOGY_SHOP<br>
   */
  public void setEcologyShop(String ecologyShop) {
    this.ecologyShop = ecologyShop;
  }

  /**
   * 年度<br> 对应数据库表字段：ODS_TMFX_TARGETMAINTAIN.YEAR<br>
   *
   * @return YEAR
   */
  public String getYear() {
    return year;
  }

  /**
   * 年度<br> 对应数据库表字段：ODS_TMFX_TARGETMAINTAIN.YEAR<br>
   */
  public void setYear(String year) {
    this.year = year;
  }

  /**
   * 期间<br> 对应数据库表字段：ODS_TMFX_TARGETMAINTAIN.MONTH<br>
   *
   * @return MONTH
   */
  public String getMonth() {
    return month;
  }

  /**
   * 期间<br> 对应数据库表字段：ODS_TMFX_TARGETMAINTAIN.MONTH<br>
   */
  public void setMonth(String month) {
    this.month = month;
  }

  /**
   * 产业<br> 对应数据库表字段：ODS_TMFX_TARGETMAINTAIN.INDUSTRY<br>
   *
   * @return INDUSTRY
   */
  public String getIndustry() {
    return industry;
  }

  /**
   * 产业<br> 对应数据库表字段：ODS_TMFX_TARGETMAINTAIN.INDUSTRY<br>
   */
  public void setIndustry(String industry) {
    this.industry = industry;
  }

  /**
   * 品牌<br> 对应数据库表字段：ODS_TMFX_TARGETMAINTAIN.BRAND<br>
   *
   * @return BRAND
   */
  public String getBrand() {
    return brand;
  }

  /**
   * 品牌<br> 对应数据库表字段：ODS_TMFX_TARGETMAINTAIN.BRAND<br>
   */
  public void setBrand(String brand) {
    this.brand = brand;
  }

  /**
   * 类型 年度y 月度m 季度q<br> 对应数据库表字段：ODS_TMFX_TARGETMAINTAIN.TYPE<br>
   *
   * @return TYPE
   */
  public String getType() {
    return type;
  }

  /**
   * 类型 年度y 月度m 季度q<br> 对应数据库表字段：ODS_TMFX_TARGETMAINTAIN.TYPE<br>
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * 目标(万元)<br> 对应数据库表字段：ODS_TMFX_TARGETMAINTAIN.TARGET<br>
   *
   * @return TARGET
   */
  public BigDecimal getTarget() {
    return target;
  }

  /**
   * 目标(万元)<br> 对应数据库表字段：ODS_TMFX_TARGETMAINTAIN.TARGET<br>
   */
  public void setTarget(BigDecimal target) {
    this.target = target;
  }

  /**
   * 标记删除<br> 对应数据库表字段：ODS_TMFX_TARGETMAINTAIN.DELETE_TAB<br>
   *
   * @return DELETE_TAB
   */
  public String getDeleteTab() {
    return deleteTab;
  }

  /**
   * 标记删除<br> 对应数据库表字段：ODS_TMFX_TARGETMAINTAIN.DELETE_TAB<br>
   */
  public void setDeleteTab(String deleteTab) {
    this.deleteTab = deleteTab;
  }

  /**
   * 创建时间<br> 对应数据库表字段：ODS_TMFX_TARGETMAINTAIN.CREATE_TIME<br>
   *
   * @return CREATE_TIME
   */
  public Date getCreateTime() {
    return createTime;
  }

  /**
   * 创建时间<br> 对应数据库表字段：ODS_TMFX_TARGETMAINTAIN.CREATE_TIME<br>
   */
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  /**
   * 创建人<br> 对应数据库表字段：ODS_TMFX_TARGETMAINTAIN.CREATE_BY<br>
   *
   * @return CREATE_BY
   */
  public String getCreateBy() {
    return createBy;
  }

  /**
   * 创建人<br> 对应数据库表字段：ODS_TMFX_TARGETMAINTAIN.CREATE_BY<br>
   */
  public void setCreateBy(String createBy) {
    this.createBy = createBy;
  }

  /**
   * 审核状态<br> 对应数据库表字段：ODS_TMFX_TARGETMAINTAIN.AUDIT_STATE<br>
   *
   * @return AUDIT_STATE
   */
  public String getAuditState() {
    return auditState;
  }

  /**
   * 审核状态<br> 对应数据库表字段：ODS_TMFX_TARGETMAINTAIN.AUDIT_STATE<br>
   */
  public void setAuditState(String auditState) {
    this.auditState = auditState;
  }

  /**
   * 审核人<br> 对应数据库表字段：ODS_TMFX_TARGETMAINTAIN.AUDIT_BY<br>
   *
   * @return AUDIT_BY
   */
  public String getAuditBy() {
    return auditBy;
  }

  /**
   * 审核人<br> 对应数据库表字段：ODS_TMFX_TARGETMAINTAIN.AUDIT_BY<br>
   */
  public void setAuditBy(String auditBy) {
    this.auditBy = auditBy;
  }

  /**
   * 审核时间<br> 对应数据库表字段：ODS_TMFX_TARGETMAINTAIN.AUDIT_TIME<br>
   *
   * @return AUDIT_TIME
   */
  public Date getAuditTime() {
    return auditTime;
  }

  /**
   * 审核时间<br> 对应数据库表字段：ODS_TMFX_TARGETMAINTAIN.AUDIT_TIME<br>
   */
  public void setAuditTime(Date auditTime) {
    this.auditTime = auditTime;
  }

}