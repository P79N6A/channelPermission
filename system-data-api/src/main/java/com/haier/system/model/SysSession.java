package com.haier.system.model;

import java.io.Serializable;
import java.util.Date;

public class SysSession implements Serializable {
	private static final long serialVersionUID = 914818167378928476L;
	private String sessionId;
	private Integer userId;
	private String userName;
	private Date loginTime;
	private Date lastAccessTime;
	private String loginIp;

	public SysSession() {
		this.sessionId = "";

		this.userId = Integer.valueOf(0);

		this.userName = "";

		this.loginTime = null;

		this.lastAccessTime = null;
	}

	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(String value) {
		this.sessionId = value;
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

	public Date getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Date value) {
		this.loginTime = value;
	}

	public Date getLastAccessTime() {
		return this.lastAccessTime;
	}

	public void setLastAccessTime(Date value) {
		this.lastAccessTime = value;
	}

	public String getLoginIp() {
		return this.loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
}