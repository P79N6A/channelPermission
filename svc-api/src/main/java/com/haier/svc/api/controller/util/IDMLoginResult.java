package com.haier.svc.api.controller.util;

import java.io.Serializable;


public class IDMLoginResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1614460365103124206L;
	private String result;
	private String msg;
	
	public static final String LOGIN_DISABLED = "LOGIN_DISABLED";
	public static final String LOGIN_INTRUDER_ATTEMPTS = "LOGIN_INTRUDERATTEMPTS";
	public static final String LOGIN_EXPIRATION_TIME = "LOGIN_EXPIRATION_TIME";
	public static final String PASSWORD_EXPIRATION_TIME = "PASSWORD_EXPIRATION_TIME";
	public static final String SUCCESS = "SUCCESS";
	public static final String PASSWORD_WRONG = "PASSWORD_WRONG";
	public static final String EXCEPTION = "EXCEPTION";

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
