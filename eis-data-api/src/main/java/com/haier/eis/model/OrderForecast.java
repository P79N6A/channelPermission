package com.haier.eis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Table: <strong>order_forecast</strong> <p><table class="er-mapping" cellspacing=0
 * cellpadding=0 style="border:solid 1 #666;padding:3px;"> <tr style="background-color:#ddd;Text-align:Left;">
 * <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 * </tr> <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>主键</td></tr>
 * <tr><td>secCode</td><td>{@link String}</td><td>sec_code</td><td>varchar</td><td>库位编码</td></tr>
 * <tr><td>secName</td><td>{@link String}</td><td>sec_name</td><td>varchar</td><td>库位名称</td></tr>
 * <tr><td>productType</td><td>{@link String}</td><td>product_type</td><td>varchar</td><td>产品大类</td></tr>
 * <tr><td>productTypeName</td><td>{@link String}</td><td>product_type_name</td><td>varchar</td><td>&nbsp;</td></tr>
 * <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>varchar</td><td>型号</td></tr>
 * <tr><td>skuName</td><td>{@link String}</td><td>sku_name</td><td>varchar</td><td>&nbsp;</td></tr>
 * <tr><td>stockAMonthOverdue</td><td>{@link Integer}</td><td>stock_a_month_overdue</td><td>int</td><td>超期一个月库存</td></tr>
 * <tr><td>stock2MonthOverdue</td><td>{@link Integer}</td><td>stock_2_month_overdue</td><td>int</td><td>超期两个月库存</td></tr>
 * <tr><td>shopForecast</td><td>{@link Integer}</td><td>shop_forecast</td><td>int</td><td>商城预测数量</td></tr>
 * <tr><td>shopSurplusStock</td><td>{@link Integer}</td><td>shop_surplus_stock</td><td>int</td><td>商城剩余库存</td></tr>
 * <tr><td>shopTwoWeeksSales</td><td>{@link Integer}</td><td>shop_two_weeks_sales</td><td>int</td><td>商城两周均销量</td></tr>
 * <tr><td>shopInTransit</td><td>{@link Integer}</td><td>shop_in_transit</td><td>int</td><td>商城在途</td></tr>
 * <tr><td>shopReserved</td><td>{@link Integer}</td><td>shop_reserved</td><td>int</td><td>商城预留</td></tr>
 * <tr><td>tbForecast</td><td>{@link Integer}</td><td>tb_forecast</td><td>int</td><td>淘宝预测数量</td></tr>
 * <tr><td>tbSurplusStock</td><td>{@link Integer}</td><td>tb_surplus_stock</td><td>int</td><td>淘宝剩余库存</td></tr>
 * <tr><td>tbTwoWeeksSales</td><td>{@link Integer}</td><td>tb_two_weeks_sales</td><td>int</td><td>淘宝两周均销量</td></tr>
 * <tr><td>tbInTransit</td><td>{@link Integer}</td><td>tb_in_transit</td><td>int</td><td>淘宝在途</td></tr>
 * <tr><td>tbReserved</td><td>{@link Integer}</td><td>tb_reserved</td><td>int</td><td>淘宝预留</td></tr>
 * <tr><td>keyaccountForecast</td><td>{@link Integer}</td><td>keyaccount_forecast</td><td>int</td><td>大客户预测数量</td></tr>
 * <tr><td>keyaccountSurplusStock</td><td>{@link Integer}</td><td>keyaccount_surplus_stock</td><td>int</td><td>大客户剩余库存</td></tr>
 * <tr><td>keyaccountTwoWeeksSales</td><td>{@link Integer}</td><td>keyaccount_two_weeks_sales</td><td>int</td><td>大客户两周均销量</td></tr>
 * <tr><td>keyaccountInTransit</td><td>{@link Integer}</td><td>keyaccount_in_transit</td><td>int</td><td>大客户在途</td></tr>
 * <tr><td>keyaccountReserved</td><td>{@link Integer}</td><td>keyaccount_reserved</td><td>int</td><td>大客户预留</td></tr>
 * <tr><td>addtime</td><td>{@link Date}</td><td>addtime</td><td>datetime</td><td>统计时间</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-10-17
 * @email yudi@sina.com
 */
public class OrderForecast implements Serializable {

  /**
   * Comment for <code>serialVersionUID</code>
   */
  private static final long serialVersionUID = 1L;
  private Integer id;

  /**
   * 获取 主键。
   */
  public Integer getId() {
    return this.id;
  }

  /**
   * 设置 主键。
   *
   * @param value 属性值
   */
  public void setId(Integer value) {
    this.id = value;
  }

  private String secCode;

  /**
   * 获取 库位编码。
   */
  public String getSecCode() {
    return this.secCode;
  }

  /**
   * 设置 库位编码。
   *
   * @param value 属性值
   */
  public void setSecCode(String value) {
    this.secCode = value;
  }

  private String secName;

  /**
   * 获取 库位名称。
   */
  public String getSecName() {
    return this.secName;
  }

  /**
   * 设置 库位名称。
   *
   * @param value 属性值
   */
  public void setSecName(String value) {
    this.secName = value;
  }

  private String productType;

  /**
   * 获取 产品大类。
   */
  public String getProductType() {
    return this.productType;
  }

  /**
   * 设置 产品大类。
   *
   * @param value 属性值
   */
  public void setProductType(String value) {
    this.productType = value;
  }

  private String productTypeName;

  public String getProductTypeName() {
    return this.productTypeName;
  }

  public void setProductTypeName(String value) {
    this.productTypeName = value;
  }

  private String sku;

  /**
   * 获取 型号。
   */
  public String getSku() {
    return this.sku;
  }

  /**
   * 设置 型号。
   *
   * @param value 属性值
   */
  public void setSku(String value) {
    this.sku = value;
  }

  private String skuName;

  public String getSkuName() {
    return this.skuName;
  }

  public void setSkuName(String value) {
    this.skuName = value;
  }

  private Integer stockAMonthOverdue;

  /**
   * 获取 超期一个月库存。
   */
  public Integer getStockAMonthOverdue() {
    return this.stockAMonthOverdue;
  }

  /**
   * 设置 超期一个月库存。
   *
   * @param value 属性值
   */
  public void setStockAMonthOverdue(Integer value) {
    this.stockAMonthOverdue = value;
  }

  private Integer stock2MonthOverdue;

  /**
   * 获取 超期两个月库存。
   */
  public Integer getStock2MonthOverdue() {
    return this.stock2MonthOverdue;
  }

  /**
   * 设置 超期两个月库存。
   *
   * @param value 属性值
   */
  public void setStock2MonthOverdue(Integer value) {
    this.stock2MonthOverdue = value;
  }

  private Integer shopForecast;

  /**
   * 获取 商城预测数量。
   */
  public Integer getShopForecast() {
    return this.shopForecast;
  }

  /**
   * 设置 商城预测数量。
   *
   * @param value 属性值
   */
  public void setShopForecast(Integer value) {
    this.shopForecast = value;
  }

  private Integer shopSurplusStock;

  /**
   * 获取 商城剩余库存。
   */
  public Integer getShopSurplusStock() {
    return this.shopSurplusStock;
  }

  /**
   * 设置 商城剩余库存。
   *
   * @param value 属性值
   */
  public void setShopSurplusStock(Integer value) {
    this.shopSurplusStock = value;
  }

  private Integer shopTwoWeeksSales;

  /**
   * 获取 商城两周均销量。
   */
  public Integer getShopTwoWeeksSales() {
    return this.shopTwoWeeksSales;
  }

  /**
   * 设置 商城两周均销量。
   *
   * @param value 属性值
   */
  public void setShopTwoWeeksSales(Integer value) {
    this.shopTwoWeeksSales = value;
  }

  private Integer shopInTransit;

  /**
   * 获取 商城在途。
   */
  public Integer getShopInTransit() {
    return this.shopInTransit;
  }

  /**
   * 设置 商城在途。
   *
   * @param value 属性值
   */
  public void setShopInTransit(Integer value) {
    this.shopInTransit = value;
  }

  private Integer shopReserved;

  /**
   * 获取 商城预留。
   */
  public Integer getShopReserved() {
    return this.shopReserved;
  }

  /**
   * 设置 商城预留。
   *
   * @param value 属性值
   */
  public void setShopReserved(Integer value) {
    this.shopReserved = value;
  }

  private Integer tbForecast;

  /**
   * 获取 淘宝预测数量。
   */
  public Integer getTbForecast() {
    return this.tbForecast;
  }

  /**
   * 设置 淘宝预测数量。
   *
   * @param value 属性值
   */
  public void setTbForecast(Integer value) {
    this.tbForecast = value;
  }

  private Integer tbSurplusStock;

  /**
   * 获取 淘宝剩余库存。
   */
  public Integer getTbSurplusStock() {
    return this.tbSurplusStock;
  }

  /**
   * 设置 淘宝剩余库存。
   *
   * @param value 属性值
   */
  public void setTbSurplusStock(Integer value) {
    this.tbSurplusStock = value;
  }

  private Integer tbTwoWeeksSales;

  /**
   * 获取 淘宝两周均销量。
   */
  public Integer getTbTwoWeeksSales() {
    return this.tbTwoWeeksSales;
  }

  /**
   * 设置 淘宝两周均销量。
   *
   * @param value 属性值
   */
  public void setTbTwoWeeksSales(Integer value) {
    this.tbTwoWeeksSales = value;
  }

  private Integer tbInTransit;

  /**
   * 获取 淘宝在途。
   */
  public Integer getTbInTransit() {
    return this.tbInTransit;
  }

  /**
   * 设置 淘宝在途。
   *
   * @param value 属性值
   */
  public void setTbInTransit(Integer value) {
    this.tbInTransit = value;
  }

  private Integer tbReserved;

  /**
   * 获取 淘宝预留。
   */
  public Integer getTbReserved() {
    return this.tbReserved;
  }

  /**
   * 设置 淘宝预留。
   *
   * @param value 属性值
   */
  public void setTbReserved(Integer value) {
    this.tbReserved = value;
  }

  private Integer keyaccountForecast;

  /**
   * 获取 大客户预测数量。
   */
  public Integer getKeyaccountForecast() {
    return this.keyaccountForecast;
  }

  /**
   * 设置 大客户预测数量。
   *
   * @param value 属性值
   */
  public void setKeyaccountForecast(Integer value) {
    this.keyaccountForecast = value;
  }

  private Integer keyaccountSurplusStock;

  /**
   * 获取 大客户剩余库存。
   */
  public Integer getKeyaccountSurplusStock() {
    return this.keyaccountSurplusStock;
  }

  /**
   * 设置 大客户剩余库存。
   *
   * @param value 属性值
   */
  public void setKeyaccountSurplusStock(Integer value) {
    this.keyaccountSurplusStock = value;
  }

  private Integer keyaccountTwoWeeksSales;

  /**
   * 获取 大客户两周均销量。
   */
  public Integer getKeyaccountTwoWeeksSales() {
    return this.keyaccountTwoWeeksSales;
  }

  /**
   * 设置 大客户两周均销量。
   *
   * @param value 属性值
   */
  public void setKeyaccountTwoWeeksSales(Integer value) {
    this.keyaccountTwoWeeksSales = value;
  }

  private Integer keyaccountInTransit;

  /**
   * 获取 大客户在途。
   */
  public Integer getKeyaccountInTransit() {
    return this.keyaccountInTransit;
  }

  /**
   * 设置 大客户在途。
   *
   * @param value 属性值
   */
  public void setKeyaccountInTransit(Integer value) {
    this.keyaccountInTransit = value;
  }

  private Integer keyaccountReserved;

  /**
   * 获取 大客户预留。
   */
  public Integer getKeyaccountReserved() {
    return this.keyaccountReserved;
  }

  /**
   * 设置 大客户预留。
   *
   * @param value 属性值
   */
  public void setKeyaccountReserved(Integer value) {
    this.keyaccountReserved = value;
  }

  private Date addtime;

  /**
   * 获取 统计时间。
   */
  public Date getAddtime() {
    return this.addtime;
  }

  /**
   * 设置 统计时间。
   *
   * @param value 属性值
   */
  public void setAddtime(Date value) {
    this.addtime = value;
  }

  private Double seasonPer;

  /**
   * 获取季节性系数
   */
  public Double getSeasonPer() {
    return seasonPer;
  }

  /**
   * 设置季节性系数
   */
  public void setSeasonPer(Double seasonPer) {
    this.seasonPer = seasonPer;
  }

  private Integer orderWeek;

  public Integer getOrderWeek() {
    return orderWeek;
  }

  public void setOrderWeek(Integer orderWeek) {
    this.orderWeek = orderWeek;
  }

  private Integer orderYear;

  public Integer getOrderYear() {
    return orderYear;
  }

  public void setOrderYear(Integer orderYear) {
    this.orderYear = orderYear;
  }

  private Integer shopWeekSale;

  public Integer getShopWeekSale() {
    return shopWeekSale;
  }

  public void setShopWeekSale(Integer shopWeekSale) {
    this.shopWeekSale = shopWeekSale;
  }

  private Integer tbWeekSale;

  public Integer getTbWeekSale() {
    return tbWeekSale;
  }

  public void setTbWeekSale(Integer tbWeekSale) {
    this.tbWeekSale = tbWeekSale;
  }

  private Integer keyaccountWeekSale;

  public Integer getKeyaccountWeekSale() {
    return keyaccountWeekSale;
  }

  public void setKeyaccountWeekSale(Integer keyaccountWeekSale) {
    this.keyaccountWeekSale = keyaccountWeekSale;
  }

  private Integer scGoalQty;

  public Integer getScGoalQty() {
    return scGoalQty;
  }

  public void setScGoalQty(Integer scGoalQty) {
    this.scGoalQty = scGoalQty;
  }

  private Integer tbGoalQty;

  public Integer getTbGoalQty() {
    return tbGoalQty;
  }

  public void setTbGoalQty(Integer tbGoalQty) {
    this.tbGoalQty = tbGoalQty;
  }

  private Integer dkhGoalQty;

  public Integer getDkhGoalQty() {
    return dkhGoalQty;
  }

  public void setDkhGoalQty(Integer dkhGoalQty) {
    this.dkhGoalQty = dkhGoalQty;
  }

}