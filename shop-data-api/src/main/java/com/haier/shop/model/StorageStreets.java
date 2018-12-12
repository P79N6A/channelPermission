package com.haier.shop.model;        

public class StorageStreets {

	private int id;       
	/**  省ID  */
	private int provinceId;
	/**  市ID  */
	private int cityId;
	/**  地区ID  */
	private int regionId;
	/**  街道ID  */
	private String streetId;
	/**  省名称  */
	private String provinceName;
	/**  市名称  */
	private String cityName;
	/**  区县名称  */
	private String regionName;
	/**  街道名称  */
	private String streetName;
	/**  大家电库房编码  */
	private String sCodeA;
	/**  小家电库房编码  */
	private String sCodeB;
	/**  24小时限时达  */
	private int onedayLimit;
	/**  TC覆盖能力  */
	private int tcCover;
	/**  TC速度  */
	private String tcLimit;
	/**  网点覆盖能力  */
	private int pointCover;
	/**  网点速度  */
	private String pointLimit;
	/**  是否超时免单  */
	private int timeOutFree;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public String getStreetId() {
		return streetId;
	}
	public void setStreetId(String streetId) {
		this.streetId = streetId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getsCodeA() {
		return sCodeA;
	}
	public void setsCodeA(String sCodeA) {
		this.sCodeA = sCodeA;
	}
	public String getsCodeB() {
		return sCodeB;
	}
	public void setsCodeB(String sCodeB) {
		this.sCodeB = sCodeB;
	}
	public int getOnedayLimit() {
		return onedayLimit;
	}
	public void setOnedayLimit(int onedayLimit) {
		this.onedayLimit = onedayLimit;
	}
	public int getTcCover() {
		return tcCover;
	}
	public void setTcCover(int tcCover) {
		this.tcCover = tcCover;
	}
	public String getTcLimit() {
		return tcLimit;
	}
	public void setTcLimit(String tcLimit) {
		this.tcLimit = tcLimit;
	}
	public int getPointCover() {
		return pointCover;
	}
	public void setPointCover(int pointCover) {
		this.pointCover = pointCover;
	}
	public String getPointLimit() {
		return pointLimit;
	}
	public void setPointLimit(String pointLimit) {
		this.pointLimit = pointLimit;
	}
	public int getTimeOutFree() {
		return timeOutFree;
	}
	public void setTimeOutFree(int timeOutFree) {
		this.timeOutFree = timeOutFree;
	}
}
