package com.haier.svc.api.controller.excel;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.svc.api.controller.util.excel.ExcelName;
import com.haier.svc.api.controller.util.excel.ExcelTitle;
import java.util.Date;

/**
 * @author: gaohaiming
 * @description:
 * @date:created in 2018/6/23 10:54
 * @modificed by:
 */
@ExcelName(fileName = "库存交易列表")
public class InvStockTransExport {

  private String channel_code;
  /**
   * 物料
   */
  @ExcelTitle(cellValue = "物料", columnStartIndex = 1)
  private String sku;
  /**
   * 库位
   */
  @ExcelTitle(cellValue = "库位", columnStartIndex = 2)
  private String sec_code;
  /**
   * 单据号
   */
  @ExcelTitle(cellValue = "单据号", columnStartIndex = 3)
  private String corder_sn;
  /**
   * 渠道
   */
  @ExcelTitle(cellValue = "渠道", columnStartIndex = 4)
  private String channel_name;
  /**
   * 交易类型
   */
  @ExcelTitle(cellValue = "交易类型", columnStartIndex = 5)
  private String bill_type;
  /**
   * 借贷标志
   */
  @ExcelTitle(cellValue = "借贷标志", columnStartIndex = 6)
  private String mark;
  /**
   * 批次
   */
  @ExcelTitle(cellValue = "批次", columnStartIndex = 7)
  private String item_property;
  /**
   * 数量
   */
  @ExcelTitle(cellValue = "数量", columnStartIndex = 8)
  private Integer quantity;
  /**
   * 交易时间
   */
  @ExcelTitle(cellValue = "交易时间", columnStartIndex = 9)
  private Date bill_time;
  /**
   * 是否占用库存
   */
  @ExcelTitle(cellValue = "是否占用库存", columnStartIndex = 10)
  private String is_froze;
  /**
   * 处理状态
   */
  @ExcelTitle(cellValue = "处理状态", columnStartIndex = 11)
  private String process_status;
  /**
   * 处理时间
   */
  @ExcelTitle(cellValue = "处理时间", columnStartIndex = 12)
  private Date last_process_time;
  /**
   * 添加时间
   */
  @ExcelTitle(cellValue = "添加时间", columnStartIndex = 13)
  private Date add_time;

  /**
   * 处理结果
   */
  @ExcelTitle(cellValue = "处理结果", columnStartIndex = 14)
  private String message;

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getSec_code() {
    return sec_code;
  }

  public void setSec_code(String sec_code) {
    this.sec_code = sec_code;
  }

  public String getCorder_sn() {
    return corder_sn;
  }

  public void setCorder_sn(String corder_sn) {
    this.corder_sn = corder_sn;
  }

  public String getChannel_name() {
    return channel_name;
  }

  public void setChannel_name(String channel_name) {
    if (StringUtils.isBlank(channel_name)) {
      this.channel_name = channel_code;
    } else {
      this.channel_name = channel_name;
    }
  }

  public String getBill_type() {
    return bill_type;
  }

  public void setBill_type(String bill_type) {
    this.bill_type = bill_type;
  }

  public String getMark() {
    return mark;
  }

  public void setMark(String mark) {
    if ("S".equals(mark)) {
      this.mark = "入库";
    } else {
      this.mark = "出库";
    }
  }

  public String getItem_property() {
    return item_property;
  }

  public void setItem_property(String item_property) {

    if ("10".equals(item_property)) {
      this.item_property = "正品";
    } else if ("21".equals(item_property)) {
      this.item_property = "不良品";
    } else if ("22".equals(item_property)) {
      this.item_property = "开箱正品";
    } else if ("40".equals(item_property)) {
      this.item_property = "样品机";
    } else if ("41".equals(item_property)) {
      this.item_property = "夺宝机";
    } else {
      this.item_property = item_property;
    }
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Date getBill_time() {
    return bill_time;
  }

  public void setBill_time(Date bill_time) {
    this.bill_time = bill_time;
  }

  public String getIs_froze() {
    return is_froze;
  }

  public void setIs_froze(String is_froze) {
    if ("0".equals(is_froze)) {
      this.is_froze = "否";
    } else {
      this.is_froze = "是";
    }

  }

  public String getProcess_status() {
    return process_status;
  }

  public void setProcess_status(String process_status) {
    if ("0".equals(process_status)) {
      this.process_status = "未处理";
    } else if ("1".equals(process_status)) {
      this.process_status = "已更新库存";
    } else {
      this.process_status = "完成";
    }
  }

  public Date getLast_process_time() {
    return last_process_time;
  }

  public void setLast_process_time(Date last_process_time) {
    this.last_process_time = last_process_time;
  }

  public Date getAdd_time() {
    return add_time;
  }

  public void setAdd_time(Date add_time) {
    this.add_time = add_time;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getChannel_code() {
    return channel_code;
  }

  public void setChannel_code(String channel_code) {
    this.channel_code = channel_code;
  }
}
