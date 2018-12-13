package com.haier.stock.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 旧cbs系统推入新cbs 用来执行定时任务
 * @Author: liwei
 * @Description:
 * @Date: Create in 10:34 2018/5/21
 * @Modified By:
 */
public class SystemTo3wCbs implements Serializable {

  private static final long serialVersionUID = -7820477880878510358L;

  /**
   * 主键id
   */
  private int id;

  /**
   * 数据来源
   */
  private String dataSource;

  /**
   * 传入时间
   */
  private Timestamp notifyTime;

  /**
   * 调用接口标识
   */
  private Integer interfaceFlag;

  /**
   * 报文格式
   */
  private String dataType;

  /**
   * 报文内容
   */
  private String content;

  /**
   * 状态
   */
  private Integer status;

  /**
   * 失败信息
   */
  private String errorMessage;

  /**
   * 创建时间
   */
  private Timestamp createTime;

  private Integer counts;

  /**
   * 修改日期
   */
  private Timestamp updateTime;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDataSource() {
    return dataSource;
  }

  public void setDataSource(String dataSource) {
    this.dataSource = dataSource;
  }

  public Timestamp getNotifyTime() {
    return notifyTime;
  }

  public void setNotifyTime(Timestamp notifyTime) {
    this.notifyTime = notifyTime;
  }

  public Integer getInterfaceFlag() {
    return interfaceFlag;
  }

  public void setInterfaceFlag(Integer interfaceFlag) {
    this.interfaceFlag = interfaceFlag;
  }

  public String getDataType() {
    return dataType;
  }

  public void setDataType(String dataType) {
    this.dataType = dataType;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public Timestamp getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Timestamp updateTime) {
    this.updateTime = updateTime;
  }

  public Integer getCounts() {
    return counts;
  }

  public void setCounts(Integer counts) {
    this.counts = counts;
  }
}
