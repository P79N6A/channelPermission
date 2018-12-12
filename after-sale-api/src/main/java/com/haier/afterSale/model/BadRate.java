package com.haier.afterSale.model;

import java.io.Serializable;


public class BadRate implements Serializable {
    private static final long serialVersionUID = -3117914630224183116L;
    private String month;
    private String badrate;
    public BadRate(String month, String badrate) {
        this.month = month;
        this.badrate = badrate;
    }
    public String getBadrate() {
		return badrate;
	}
    public void setBadrate(String badrate) {
		this.badrate = badrate;
	}
    public String getMonth() {
		return month;
	}
    public void setMonth(String month) {
		this.month = month;
	}
    
    
}
