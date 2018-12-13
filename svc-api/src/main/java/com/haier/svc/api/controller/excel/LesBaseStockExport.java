package com.haier.svc.api.controller.excel;

import com.haier.svc.api.controller.util.excel.ExcelName;
import com.haier.svc.api.controller.util.excel.ExcelTitle;
import java.util.Date;

/**
 * @author: gaohaiming
 * @description:
 * @date:created in 2018/6/23 12:51
 * @modificed by:
 */
@ExcelName(fileName = "CBS库存数据报表")
public class LesBaseStockExport {

  @ExcelTitle(cellValue = "物料编号", columnStartIndex = 1)
  private String sku;
  @ExcelTitle(cellValue = "品类", columnStartIndex = 2)
  private String cbsCategory;
  @ExcelTitle(cellValue = "产品型号", columnStartIndex = 3)
  private String productName;
  @ExcelTitle(cellValue = "LES库位编码", columnStartIndex = 4)
  private String lesSecCode;
  @ExcelTitle(cellValue = "库位编码", columnStartIndex = 5)
  private String secCode;
  @ExcelTitle(cellValue = "库位名称", columnStartIndex = 6)
  private String secName;
  @ExcelTitle(cellValue = "实际库存", columnStartIndex = 7)
  private Integer stockQty;
  @ExcelTitle(cellValue = "占用库存", columnStartIndex = 8)
  private Integer frozenQty;
  @ExcelTitle(cellValue = "批次", columnStartIndex = 9)
  private Integer itemProperty;
  @ExcelTitle(cellValue = "创建时间", columnStartIndex = 10)
  private Date createTime;
  @ExcelTitle(cellValue = "更新时间", columnStartIndex = 11)
  private Date updateTime;

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getCbsCategory() {
    return cbsCategory;
  }

  public void setCbsCategory(String cbsCategory) {
    this.cbsCategory = cbsCategory;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getLesSecCode() {
    return lesSecCode;
  }

  public void setLesSecCode(String lesSecCode) {
    this.lesSecCode = lesSecCode;
  }

  public String getSecCode() {
    return secCode;
  }

  public void setSecCode(String secCode) {
    this.secCode = secCode;
  }

  public String getSecName() {
    return secName;
  }

  public void setSecName(String secName) {
    this.secName = secName;
  }

  public Integer getStockQty() {
    return stockQty;
  }

  public void setStockQty(Integer stockQty) {
    this.stockQty = stockQty;
  }

  public Integer getFrozenQty() {
    return frozenQty;
  }

  public void setFrozenQty(Integer frozenQty) {
    this.frozenQty = frozenQty;
  }

  public Integer getItemProperty() {
    return itemProperty;
  }

  public void setItemProperty(Integer itemProperty) {
    this.itemProperty = itemProperty;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }
}
