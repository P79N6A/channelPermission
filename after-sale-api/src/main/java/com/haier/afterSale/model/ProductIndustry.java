package com.haier.afterSale.model;

import java.io.Serializable;


public class ProductIndustry implements Serializable {
    private static final long serialVersionUID = -3117914630224183116L;
    private String fridge;
    private String washingMach;
    private String freezer;
    private String airCondition;
    private String waterHeater;
    private String kichenMach;
    private String colorTV;
    private double rate;
    private String centerName;
     
  

  public void setRate(double rate) {
	this.rate = rate;
  }
  public double getRate() {
	return rate;
	}
  public String getCenterName() {
	return centerName;
}
  public void setCenterName(String centerName) {
	this.centerName = centerName;
}
public String getFridge() {
	return fridge;
}
public void setFridge(String fridge) {
	this.fridge = fridge;
}
public String getWashingMach() {
	return washingMach;
}
public void setWashingMach(String washingMach) {
	this.washingMach = washingMach;
}
public String getFreezer() {
	return freezer;
}
public void setFreezer(String freezer) {
	this.freezer = freezer;
}
public String getAirCondition() {
	return airCondition;
}
public void setAirCondition(String airCondition) {
	this.airCondition = airCondition;
}
public String getWaterHeater() {
	return waterHeater;
}
public void setWaterHeater(String waterHeater) {
	this.waterHeater = waterHeater;
}
public String getKichenMach() {
	return kichenMach;
}
public void setKichenMach(String kichenMach) {
	this.kichenMach = kichenMach;
}
public String getColorTV() {
	return colorTV;
}
public void setColorTV(String colorTV) {
	this.colorTV = colorTV;
}
  
    
}
