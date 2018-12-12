package com.haier.purchase.data.model.vehcile;

import java.util.Date;


public class DepartmentInfoDTO extends BaseDTO {

  private static final long serialVersionUID = -8065098041158298300L;
  private String deliveryToCode; //送达方编码
  private String departmentCode; //工贸编码
  private String departmentName; //工贸名称
  private String organizationCode; //组织编码
  private String organizationName; //组织名称
  private String createBy; //
  private Date createTime; //
  private String lastUpdateBy; //
  private Date lastUpdateTime; //

  public String getDeliveryToCode() {
    return deliveryToCode;
  }

  public void setDeliveryToCode(String deliveryToCode) {
    this.deliveryToCode = deliveryToCode;
  }

  public String getDepartmentCode() {
    return departmentCode;
  }

  public void setDepartmentCode(String departmentCode) {
    this.departmentCode = departmentCode;
  }

  public String getDepartmentName() {
    return departmentName;
  }

  public void setDepartmentName(String departmentName) {
    this.departmentName = departmentName;
  }

  public String getOrganizationCode() {
    return organizationCode;
  }

  public void setOrganizationCode(String organizationCode) {
    this.organizationCode = organizationCode;
  }

  public String getOrganizationName() {
    return organizationName;
  }

  public void setOrganizationName(String organizationName) {
    this.organizationName = organizationName;
  }

  public String getCreateBy() {
    return createBy;
  }

  public void setCreateBy(String createBy) {
    this.createBy = createBy;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getLastUpdateBy() {
    return lastUpdateBy;
  }

  public void setLastUpdateBy(String lastUpdateBy) {
    this.lastUpdateBy = lastUpdateBy;
  }

  public Date getLastUpdateTime() {
    return lastUpdateTime;
  }

  public void setLastUpdateTime(Date lastUpdateTime) {
    this.lastUpdateTime = lastUpdateTime;
  }
}


