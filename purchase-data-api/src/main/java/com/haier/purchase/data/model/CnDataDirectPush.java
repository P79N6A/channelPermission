package com.haier.purchase.data.model;

import java.io.Serializable;
import java.util.Date;



public class CnDataDirectPush implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -2511223994957446916L;

	private Integer id;
	
	private String data;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setData(String data) {
		this.data = data;
	}
	public String getData() {
		return data;
	}
}
