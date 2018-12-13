package com.haier.shop.model;

import java.io.Serializable;
import java.util.Date;

public class ReviewImage implements Serializable {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -1455256137297503280L;

	private Integer id;

	private String workorderid;

	private Integer isdel;

	private String createuser;

	private Date createtime;

	private String information;
	
	private String name;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWorkorderid() {
		return workorderid;
	}

	public void setWorkorderid(String workorderid) {
		this.workorderid = workorderid == null ? null : workorderid.trim();
	}

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser == null ? null : createuser.trim();
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information == null ? null : information.trim();
	}
}