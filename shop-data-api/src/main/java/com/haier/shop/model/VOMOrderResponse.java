package com.haier.shop.model;

import java.io.Serializable;

public class VOMOrderResponse implements Serializable {

	private String flag;
	
	private String msg;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
