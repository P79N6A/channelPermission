package com.haier.system.model;

import java.io.Serializable;
import java.util.Date;

public class SysUserRole implements Serializable {
	private static final long serialVersionUID = -3270490301182331694L;
	private Integer userRoleId;
	private Integer userId;
	private Integer roleId;
	private Date startTime;
	private Date endTime;
	private String updateUser;
	private Date updateTime;

	public SysUserRole() {
		this.userRoleId = Integer.valueOf(0);

		this.roleId = Integer.valueOf(0);

		this.startTime = null;

		this.endTime = null;

		this.updateUser = "";
	}

	public Integer getUserRoleId() {
		return this.userRoleId;
	}

	public void setUserRoleId(Integer value) {
		this.userRoleId = value;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer value) {
		this.userId = value;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer value) {
		this.roleId = value;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date value) {
		this.startTime = value;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date value) {
		this.endTime = value;
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