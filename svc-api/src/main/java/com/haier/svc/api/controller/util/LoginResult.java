package com.haier.svc.api.controller.util;

import java.io.Serializable;


public class LoginResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7604550205364537125L;
	private String msg;
	private String result;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
