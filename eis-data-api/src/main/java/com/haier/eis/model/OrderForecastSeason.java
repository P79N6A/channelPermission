package com.haier.eis.model;

import java.io.Serializable;

public class OrderForecastSeason implements Serializable {

  /**
   * Comment for <code>serialVersionUID</code>
   */
  private static final long serialVersionUID = 1L;
  private Integer id;
  private String trade;
  private String sCode;
  private String productType;
  private Integer seasonWeek;
  private double seasonPer;
  private String SArea;
  private String productGroups;

  public String getProductGroups() {
    return productGroups;
  }

  public void setProductGroups(String productGroups) {
    this.productGroups = productGroups;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTrade() {
    return trade;
  }

  public void setTrade(String trade) {
    this.trade = trade;
  }

  public String getsCode() {
    return sCode;
  }

  public void setsCode(String sCode) {
    this.sCode = sCode;
  }

  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public Integer getSeasonWeek() {
    return seasonWeek;
  }

  public void setSeasonWeek(Integer seasonWeek) {
    this.seasonWeek = seasonWeek;
  }

  public double getSeasonPer() {
    return seasonPer;
  }

  public void setSeasonPer(double seasonPer) {
    this.seasonPer = seasonPer;
  }

  public String getSArea() {
    return SArea;
  }

  public void setSArea(String sArea) {
    SArea = sArea;
  }

}
