package com.haier.svc.api.controller.util.http.suning;

/**
 * 苏宁网单信息帮助类(payMentList) author:lujun
 */
public class SuningPayMentList {
	private String banktypecode;
	private String offLinePayFlag;
	private String payamount;
	private String paycode;

	public String getBanktypecode() {
		return banktypecode;
	}

	public void setBanktypecode(String banktypecode) {
		this.banktypecode = banktypecode;
	}

	public String getOffLinePayFlag() {
		return offLinePayFlag;
	}

	public void setOffLinePayFlag(String offLinePayFlag) {
		this.offLinePayFlag = offLinePayFlag;
	}

	public String getPayamount() {
		return payamount;
	}

	public void setPayamount(String payamount) {
		this.payamount = payamount;
	}

	public String getPaycode() {
		return paycode;
	}

	public void setPaycode(String paycode) {
		this.paycode = paycode;
	}

}
