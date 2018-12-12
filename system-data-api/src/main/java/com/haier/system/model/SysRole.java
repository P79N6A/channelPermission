package com.haier.system.model;

import java.io.Serializable;
import java.util.Date;

public class SysRole implements Serializable {
	private static final long serialVersionUID = -7771206507293783692L;
	private Integer roleId;
	private String roleName;
	private Integer status;
	private String remark;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;

	public SysRole() {
		this.roleId = Integer.valueOf(0);

		this.roleName = "";

		this.status = Integer.valueOf(0);

		this.remark = "";

		this.createUser = "";

		this.createTime = null;

		this.updateUser = "";
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer value) {
		this.roleId = value;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String value) {
		this.roleName = value;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer value) {
		this.status = value;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String value) {
		this.remark = value;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String value) {
		this.createUser = value;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date value) {
		this.createTime = value;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String value) {
		this.updateUser = value;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}
}