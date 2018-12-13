package com.haier.svc.api.controller.excel;

import com.haier.svc.api.controller.util.excel.ExcelName;
import com.haier.svc.api.controller.util.excel.ExcelTitle;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: gaohaiming
 * @description:
 * @date:created in 2018/6/23 13:30
 * @modificed by:
 */
@ExcelName(fileName = "LES库存数据报表")
public class LesStockQueryExport implements Serializable {
  private static final long serialVersionUID = 4044242277530064966L;

  /**
   * 库存地点
   */
  @ExcelTitle(cellValue = "库存地点",columnStartIndex = 1)
  private String lgort1;

  /**
   * 库存地点
   */
  @ExcelTitle(cellValue = "库存地点",columnStartIndex = 2)
  private String lgort;

  /**
   * 物料号码
   */
  @ExcelTitle(cellValue = "物料号码",columnStartIndex = 3)
  private String matnr;

  /**
   * 物料号码
   */
  @ExcelTitle(cellValue = "物料号码(新)",columnStartIndex = 4)
  private String matnrnew;

  /**
   * 批次编号
   */
  @ExcelTitle(cellValue = "批次编号",columnStartIndex = 5)
  private String atwart;

  /**
   * 实物库存
   */
  @ExcelTitle(cellValue = "实物库存",columnStartIndex = 6)
  private BigDecimal clabs;

  /**
   * 在运库存
   */
  @ExcelTitle(cellValue = "在运库存",columnStartIndex = 7)
  private BigDecimal cumlm;

  /**
   * 基本计量单位
   */
  @ExcelTitle(cellValue = "基本计量单位",columnStartIndex = 8)
  private String meins;

  /**
   * 数量2
   */
  @ExcelTitle(cellValue = "数量",columnStartIndex = 9)
  private BigDecimal zmenge1;

  /**
   * 单位2
   */
  @ExcelTitle(cellValue = "单位",columnStartIndex = 10)
  private String vrkme;

  /**
   * 开票未提
   */
  @ExcelTitle(cellValue = "开票未提",columnStartIndex = 11)
  private BigDecimal menge1;

  /**
   * 可用库存数
   */
  @ExcelTitle(cellValue = "可用库存数",columnStartIndex = 12)
  private BigDecimal menge2;

  /**
   * 物料组
   */
  @ExcelTitle(cellValue = "物料组",columnStartIndex = 13)
  private String matkl;

  /**
   * 实物库存体积
   */
  @ExcelTitle(cellValue = "实物库存体积",columnStartIndex = 14)
  private BigDecimal volum;

  /**
   * 单位
   */
  @ExcelTitle(cellValue = "单位",columnStartIndex = 15)
  private String zvoleh;

  public String getLgort1() {
    return lgort1;
  }

  public void setLgort1(String lgort1) {
    this.lgort1 = lgort1;
  }

  public String getLgort() {
    return lgort;
  }

  public void setLgort(String lgort) {
    this.lgort = lgort;
  }

  public String getMatnr() {
    return matnr;
  }

  public void setMatnr(String matnr) {
    this.matnr = matnr;
  }

  public String getMatnrnew() {
    return matnrnew;
  }

  public void setMatnrnew(String matnrnew) {
    this.matnrnew = matnrnew;
  }

  public String getAtwart() {
    return atwart;
  }

  public void setAtwart(String atwart) {
    this.atwart = atwart;
  }

  public BigDecimal getClabs() {
    return clabs;
  }

  public void setClabs(BigDecimal clabs) {
    this.clabs = clabs;
  }

  public BigDecimal getCumlm() {
    return cumlm;
  }

  public void setCumlm(BigDecimal cumlm) {
    this.cumlm = cumlm;
  }

  public String getMeins() {
    return meins;
  }

  public void setMeins(String meins) {
    this.meins = meins;
  }

  public BigDecimal getZmenge1() {
    return zmenge1;
  }

  public void setZmenge1(BigDecimal zmenge1) {
    this.zmenge1 = zmenge1;
  }

  public String getVrkme() {
    return vrkme;
  }

  public void setVrkme(String vrkme) {
    this.vrkme = vrkme;
  }

  public BigDecimal getMenge1() {
    return menge1;
  }

  public void setMenge1(BigDecimal menge1) {
    this.menge1 = menge1;
  }

  public BigDecimal getMenge2() {
    return menge2;
  }

  public void setMenge2(BigDecimal menge2) {
    this.menge2 = menge2;
  }

  public String getMatkl() {
    return matkl;
  }

  public void setMatkl(String matkl) {
    this.matkl = matkl;
  }

  public BigDecimal getVolum() {
    return volum;
  }

  public void setVolum(BigDecimal volum) {
    this.volum = volum;
  }

  public String getZvoleh() {
    return zvoleh;
  }

  public void setZvoleh(String zvoleh) {
    this.zvoleh = zvoleh;
  }
}
