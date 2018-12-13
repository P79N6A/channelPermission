package com.haier.svc.api.controller.excel;


import com.haier.svc.api.controller.util.excel.ExcelName;
import com.haier.svc.api.controller.util.excel.ExcelTitle;

/**
 * @author: gaohaiming
 * @description:
 * @date:created in 2018/6/15 11:28
 * @modificed by:
 */
@ExcelName(fileName = "库存统计")
public class InvStockAgeExport {

  @ExcelTitle(cellValue = "物料", rowStartIndex = 1, columnStartIndex = 1, rowNum = 2)
  private String sku;
  @ExcelTitle(cellValue = "库位", rowStartIndex = 1, columnStartIndex = 2, rowNum = 2)
  private String secCode;
  @ExcelTitle(cellValue = "渠道", rowStartIndex = 1, columnStartIndex = 3, rowNum = 2)
  private String channelName;
  @ExcelTitle(cellValue = "品类", rowStartIndex = 1, columnStartIndex = 4, rowNum = 2)
  private String productTypeName;
  @ExcelTitle(cellValue = "型号", rowStartIndex = 1, columnStartIndex = 5, rowNum = 2)
  private String productName;
  @ExcelTitle(cellValue = "渠道锁定", rowStartIndex = 1, columnStartIndex = 6, columnNum = 4, realTitle = false)
  private String channelLock;
  @ExcelTitle(cellValue = "锁定占用", rowStartIndex = 1, columnStartIndex = 10, rowNum = 2)
  private Integer lockOccupy;
  @ExcelTitle(cellValue = "渠道共享", rowStartIndex = 1, columnStartIndex = 11, columnNum = 4, realTitle = false)
  private String channelShare;

  @ExcelTitle(cellValue = "1-7天", rowStartIndex = 2, columnStartIndex = 6)
  private Integer dayLock1;
  @ExcelTitle(cellValue = "7-15天", rowStartIndex = 2, columnStartIndex = 7)
  private Integer dayLock2;
  @ExcelTitle(cellValue = "15-30天", rowStartIndex = 2, columnStartIndex = 8)
  private Integer dayLock3;
  @ExcelTitle(cellValue = "30天以上", rowStartIndex = 2, columnStartIndex = 9)
  private Integer dayLock4;
  @ExcelTitle(cellValue = "1-7天", rowStartIndex = 2, columnStartIndex = 11)
  private Integer dayShare1;
  @ExcelTitle(cellValue = "7-15天", rowStartIndex = 2, columnStartIndex = 12)
  private Integer dayShare2;
  @ExcelTitle(cellValue = "15-30天", rowStartIndex = 2, columnStartIndex = 13)
  private Integer dayShare3;
  @ExcelTitle(cellValue = "30天以上", rowStartIndex = 2, columnStartIndex = 14)
  private Integer dayShare4;

  @ExcelTitle(cellValue = "渠道共享占用", rowStartIndex = 1, columnStartIndex = 15, rowNum = 2)
  private Integer channelShareOccupy;

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getSecCode() {
    return secCode;
  }

  public void setSecCode(String secCode) {
    this.secCode = secCode;
  }

  public String getChannelName() {
    return channelName;
  }

  public void setChannelName(String channelName) {
    this.channelName = channelName;
  }

  public String getProductTypeName() {
    return productTypeName;
  }

  public void setProductTypeName(String productTypeName) {
    this.productTypeName = productTypeName;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getChannelLock() {
    return channelLock;
  }

  public void setChannelLock(String channelLock) {
    this.channelLock = channelLock;
  }

  public Integer getLockOccupy() {
    return lockOccupy;
  }

  public void setLockOccupy(Integer lockOccupy) {
    this.lockOccupy = lockOccupy;
  }

  public String getChannelShare() {
    return channelShare;
  }

  public void setChannelShare(String channelShare) {
    this.channelShare = channelShare;
  }

  public Integer getDayLock1() {
    return dayLock1;
  }

  public void setDayLock1(Integer dayLock1) {
    this.dayLock1 = dayLock1;
  }

  public Integer getDayLock2() {
    return dayLock2;
  }

  public void setDayLock2(Integer dayLock2) {
    this.dayLock2 = dayLock2;
  }

  public Integer getDayLock3() {
    return dayLock3;
  }

  public void setDayLock3(Integer dayLock3) {
    this.dayLock3 = dayLock3;
  }

  public Integer getDayLock4() {
    return dayLock4;
  }

  public void setDayLock4(Integer dayLock4) {
    this.dayLock4 = dayLock4;
  }

  public Integer getDayShare1() {
    return dayShare1;
  }

  public void setDayShare1(Integer dayShare1) {
    this.dayShare1 = dayShare1;
  }

  public Integer getDayShare2() {
    return dayShare2;
  }

  public void setDayShare2(Integer dayShare2) {
    this.dayShare2 = dayShare2;
  }

  public Integer getDayShare3() {
    return dayShare3;
  }

  public void setDayShare3(Integer dayShare3) {
    this.dayShare3 = dayShare3;
  }

  public Integer getDayShare4() {
    return dayShare4;
  }

  public void setDayShare4(Integer dayShare4) {
    this.dayShare4 = dayShare4;
  }

  public Integer getChannelShareOccupy() {
    return channelShareOccupy;
  }

  public void setChannelShareOccupy(Integer channelShareOccupy) {
    this.channelShareOccupy = channelShareOccupy;
  }
}
