package com.haier.system.model;

import java.io.Serializable;
import java.util.Date;

public class SysAccessLog implements Serializable {
	private static final long serialVersionUID = 897926294884805444L;
	public static final int LOG_TYPE_LOGIN = 2;
	public static final int LOG_TYPE_ACCESS = 1;
	public static final int LOG_TYPE_LOGOUT = 3;
	private Integer logId;
	private Integer userId;
	private String userName;
	private Integer logType;
	private Date logTime;
	private String clientIp;
	private String serverIp;
	private Integer success;
	private String visitUrl;
	private String refererUrl;
	private String clientToken;
	private String sessionId;
	private String paramValue;
	private String cookieValue;
	private String agent;
	private String errorMsg;

	public SysAccessLog() {
		this.logId = Integer.valueOf(0);

		this.userId = Integer.valueOf(0);

		this.userName = "";

		this.logType = Integer.valueOf(1);

		this.logTime = null;

		this.clientIp = "";

		this.serverIp = "";

		this.success = Integer.valueOf(1);

		this.visitUrl = "";

		this.refererUrl = "";

		this.clientToken = "";

		this.sessionId = "";

		this.paramValue = "";

		this.cookieValue = "";

		this.agent = "";
	}

	public Integer getLogId() {
		return this.logId;
	}

	public void setLogId(Integer value) {
		this.logId = value;
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

	public Integer getLogType() {
		return this.logType;
	}

	public void setLogType(Integer value) {
		this.logType = value;
	}

	public Date getLogTime() {
		return this.logTime;
	}

	public void setLogTime(Date value) {
		this.logTime = value;
	}

	public String getClientIp() {
		return this.clientIp;
	}

	public void setClientIp(String value) {
		this.clientIp = value;
	}

	public String getServerIp() {
		return this.serverIp;
	}

	public void setServerIp(String value) {
		this.serverIp = value;
	}

	public Integer getSuccess() {
		return this.success;
	}

	public void setSuccess(Integer value) {
		this.success = value;
	}

	public String getVisitUrl() {
		return this.visitUrl;
	}

	public void setVisitUrl(String value) {
		this.visitUrl = value;
	}

	public String getRefererUrl() {
		return this.refererUrl;
	}

	public void setRefererUrl(String value) {
		this.refererUrl = value;
	}

	public String getClientToken() {
		return this.clientToken;
	}

	public void setClientToken(String value) {
		this.clientToken = value;
	}

	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(String value) {
		this.sessionId = value;
	}

	public String getParamValue() {
		return this.paramValue;
	}

	public void setParamValue(String value) {
		this.paramValue = value;
	}

	public String getCookieValue() {
		return this.cookieValue;
	}

	public void setCookieValue(String value) {
		this.cookieValue = value;
	}

	public String getAgent() {
		return this.agent;
	}

	public void setAgent(String value) {
		this.agent = value;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}

	public void setErrorMsg(String value) {
		this.errorMsg = value;
	}
}