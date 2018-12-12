package com.haier.shop.model;

import java.io.Serializable;
import java.util.Date;


public class ProductToIndustry implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -7745155778094145840L;
	/**
	 * 
	 */
	private String parentPath; // 判断产业的
	private int reject; //拒收数量
	private int sign; // 揽收数量
	private byte typeFlag; //判断是拒收还是揽收

	public int getReject() {
		return reject;
	}
	public void setReject(int reject) {
		this.reject = reject;
	}
	public int getSign() {
		return sign;
	}
	public void setSign(int sign) {
		this.sign = sign;
	}
	public byte getTypeFlag() {
		return typeFlag;
	}
	public void setTypeFlag(byte typeFlag) {
		this.typeFlag = typeFlag;
	}
	public String getParentPath() {
		return parentPath;
	}
	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}

	
 
}
