package com.haier.afterSale.model;

import java.io.Serializable;


public class ProductBadCount implements Serializable {
    private static final long serialVersionUID = -3117914630224183116L;
    private int reject;
    private int sign;
    private int total;
    private String industry;
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
    public int getTotal() {
		return total;
	}
    public void setTotal(int total) {
		this.total = total;
	}
    public String getIndustry() {
		return industry;
	}
    public void setIndustry(String industry) {
		this.industry = industry;
	}
    
}
