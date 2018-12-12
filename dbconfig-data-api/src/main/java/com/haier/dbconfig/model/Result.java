package com.haier.dbconfig.model;

// 菜鸟接口返回数据对象
public class Result {

	// 返回数据的状态
	private boolean status = false;
	// 返回的数据，如果返回失败，则为失败的原因
	private String message = "";
	
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
