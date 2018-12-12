package com.haier.system.model;

public class CBSContext {
	private SysUser currentUser;
	private long loginTime;
	private String sessionId;

	public SysUser getCurrentUser() {
		return this.currentUser;
	}

	public void setCurrentUser(SysUser value) {
		this.currentUser = value;
	}

	public long getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(long value) {
		this.loginTime = value;
	}

	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(String value) {
		this.sessionId = value;
	}
}