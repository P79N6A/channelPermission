package com.haier.purchase.data.model;

import java.io.Serializable;

public class LesFiveYardInfo implements Serializable {
	private int id;
	private int siteId;
	private String fiveYard;
	private String desc;
	private String jdeCode;
	private String address;
	private String centerName;
	private String centerCode;
	private String sCode;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSiteId() {
		return this.siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public String getFiveYard() {
		return this.fiveYard;
	}

	public void setFiveYard(String fiveYard) {
		this.fiveYard = fiveYard;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getJdeCode() {
		return this.jdeCode;
	}

	public void setJdeCode(String jdeCode) {
		this.jdeCode = jdeCode;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCenterName() {
		return this.centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public String getCenterCode() {
		return this.centerCode;
	}

	public void setCenterCode(String centerCode) {
		this.centerCode = centerCode;
	}

	public String getsCode() {
		return this.sCode;
	}

	public void setsCode(String sCode) {
		this.sCode = sCode;
	}
}