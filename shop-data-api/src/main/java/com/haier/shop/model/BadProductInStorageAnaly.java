package com.haier.shop.model;

import java.io.Serializable;
import java.util.Date;


public class BadProductInStorageAnaly implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 7551408006124518409L;
	/**
	 * 
	 */
	private String industryCategory;//产业别
	private int inStoreNotExpire;//已入库未超期
	private String channelCategory;//销售渠道别
	private int inStoreHasExpire;//已入库已超期
	private int inStoreTotal;//已入库合计
    private int notInStoreNotExpire;//未入库未超期
	private int notInStoreHasExpire;//未入库已超期
	private int notInStoreTotal;//未入库合计
    private int badProductTotal;//不良品总计
    private double badProductInStoreRate;//不良品完成入库率
	public String getIndustryCategory() {
		return industryCategory;
	}
	public void setIndustryCategory(String industryCategory) {
		this.industryCategory = industryCategory;
	}
	public int getInStoreNotExpire() {
		return inStoreNotExpire;
	}
	public void setInStoreNotExpire(int inStoreNotExpire) {
		this.inStoreNotExpire = inStoreNotExpire;
	}
	public String getChannelCategory() {
		return channelCategory;
	}
	public void setChannelCategory(String channelCategory) {
		this.channelCategory = channelCategory;
	}
	public int getInStoreHasExpire() {
		return inStoreHasExpire;
	}
	public void setInStoreHasExpire(int inStoreHasExpire) {
		this.inStoreHasExpire = inStoreHasExpire;
	}
	public int getInStoreTotal() {
		return inStoreTotal;
	}
	public void setInStoreTotal(int inStoreTotal) {
		this.inStoreTotal = inStoreTotal;
	}
	public int getNotInStoreNotExpire() {
		return notInStoreNotExpire;
	}
	public void setNotInStoreNotExpire(int notInStoreNotExpire) {
		this.notInStoreNotExpire = notInStoreNotExpire;
	}
	public int getNotInStoreHasExpire() {
		return notInStoreHasExpire;
	}
	public void setNotInStoreHasExpire(int notInStoreHasExpire) {
		this.notInStoreHasExpire = notInStoreHasExpire;
	}
	public int getNotInStoreTotal() {
		return notInStoreTotal;
	}
	public void setNotInStoreTotal(int notInStoreTotal) {
		this.notInStoreTotal = notInStoreTotal;
	}
	public int getBadProductTotal() {
		return badProductTotal;
	}
	public void setBadProductTotal(int badProductTotal) {
		this.badProductTotal = badProductTotal;
	}
	public double getBadProductInStoreRate() {
		return badProductInStoreRate;
	}
	public void setBadProductInStoreRate(double badProductInStoreRate) {
		this.badProductInStoreRate = badProductInStoreRate;
	}
	
}
