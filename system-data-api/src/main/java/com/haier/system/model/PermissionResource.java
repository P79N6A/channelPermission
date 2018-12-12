package com.haier.system.model;

import java.io.Serializable;
import java.util.List;

public class PermissionResource implements Serializable {
	private static final long serialVersionUID = -8005853049514900345L;
	private int rtype;
	private String rid;
	public String name;
	public int status;
	private Boolean checked;
	private List<PermissionResource> children;
	
	private Integer pId;

	public PermissionResource() {
		this.checked = Boolean.valueOf(false);
	}

	public int getRtype() {
		return this.rtype;
	}

	public void setRtype(int value) {
		this.rtype = value;
	}

	public String getRid() {
		return this.rid;
	}

	public void setRid(String value) {
		this.rid = value;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int value) {
		this.status = value;
	}

	public Boolean getChecked() {
		return this.checked;
	}

	public void setChecked(Boolean value) {
		this.checked = value;
	}

	public List<PermissionResource> getChildren() {
		return this.children;
	}

	public void setChildren(List<PermissionResource> value) {
		this.children = value;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}
	
}