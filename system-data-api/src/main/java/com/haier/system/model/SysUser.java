package com.haier.system.model;

import java.io.Serializable;
import java.util.Date;

public class SysUser implements Serializable {
	private static final long serialVersionUID = -7987745453662526261L;
	private Integer userId;
	private String userName;
	private Integer status;
	private String loginId;
	private String password;
	private String email;
	private String phone;
	private String mobile;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;

	public SysUser() {
		this.status = Integer.valueOf(1);

		this.createUser = "";

		this.createTime = null;

		this.updateUser = "";
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer value) {
		this.userId = value;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String value) {
		this.userName = value;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer value) {
		this.status = value;
	}

	public String getLoginId() {
		return this.loginId;
	}

	public void setLoginId(String value) {
		this.loginId = value;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String value) {
		this.password = value;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String value) {
		this.email = value;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String value) {
		this.phone = value;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String value) {
		this.mobile = value;
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