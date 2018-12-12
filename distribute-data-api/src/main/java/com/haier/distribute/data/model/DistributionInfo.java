package com.haier.distribute.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DistributionInfo implements Serializable {
	  /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3431631072818009195L;
   
    private Integer id;
    private String distributionCode;
    private String distributionName;
    private Integer sort;
    private String createBy;
    private Date createTime;
    private String updateBy;
    private Date updateTime;
    private String remark;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDistributionCode() {
		return distributionCode;
	}
	public void setDistributionCode(String distributionCode) {
		this.distributionCode = distributionCode;
	}
	public String getDistributionName() {
		return distributionName;
	}
	public void setDistributionName(String distributionName) {
		this.distributionName = distributionName;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
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
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
   
}