package com.haier.system.model;

import java.io.Serializable;

public class PermissionOwner implements Serializable {
	private static final long serialVersionUID = -1046953621061626287L;
	private int type;
	private int id;
	public String name;
	public int status;

	public int getType() {
		return this.type;
	}

	public void setType(int value) {
		this.type = value;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int value) {
		this.id = value;
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
}