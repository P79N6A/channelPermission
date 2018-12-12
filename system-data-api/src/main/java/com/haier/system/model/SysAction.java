package com.haier.system.model;

import java.io.Serializable;

public class SysAction implements Serializable {
	private static final long serialVersionUID = -6167952093955233428L;
	private Integer actId;
	private String actKey;
	private String actName;
	private Integer fnId;
	private String fnName;
	private Integer status;
	private String url;
	private String remark;

	public SysAction() {
		this.fnId = Integer.valueOf(0);

		this.status = Integer.valueOf(1);

		this.remark = "";
	}

	public Integer getActId() {
		return this.actId;
	}

	public void setActId(Integer value) {
		this.actId = value;
	}

	public String getActKey() {
		return this.actKey;
	}

	public void setActKey(String value) {
		this.actKey = value;
	}

	public String getActName() {
		return this.actName;
	}

	public void setActName(String value) {
		this.actName = value;
	}

	public Integer getFnId() {
		return this.fnId;
	}

	public void setFnId(Integer value) {
		this.fnId = value;
	}

	public String getFnName() {
		return this.fnName;
	}

	public void setFnName(String value) {
		this.fnName = value;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer value) {
		this.status = value;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String value) {
		this.url = value;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String value) {
		this.remark = value;
	}
}