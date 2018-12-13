package com.haier.system.model;

import java.io.Serializable;
import java.util.Date;

public class SysPermission implements Serializable {
	private static final long serialVersionUID = 8740373662191230996L;
	public static final int OWNER_ROLE = 2;
	public static final int OWNER_USER = 1;
	public static final int RESOURCE_ACTION = 1;
	public static final int RESOURCE_MENU = 2;
	private Integer permId;
	private Integer ownerId;
	private Integer ownerType;
	private Integer resId;
	private Integer resType;
	private Date startTime;
	private Date endTime;
	private String updateUser;
	private Date updateTime;

	public SysPermission() {
		this.ownerId = Integer.valueOf(0);

		this.ownerType = Integer.valueOf(0);

		this.resId = Integer.valueOf(0);

		this.startTime = null;

		this.endTime = null;

		this.updateUser = "";
	}

	public Integer getPermId() {
		return this.permId;
	}

	public void setPermId(Integer value) {
		this.permId = value;
	}

	public Integer getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerId(Integer value) {
		this.ownerId = value;
	}

	public Integer getOwnerType() {
		return this.ownerType;
	}

	public void setOwnerType(Integer value) {
		this.ownerType = value;
	}

	public Integer getResId() {
		return this.resId;
	}

	public void setResId(Integer value) {
		this.resId = value;
	}

	public Integer getResType() {
		return this.resType;
	}

	public void setResType(Integer value) {
		this.resType = value;
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