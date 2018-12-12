package com.haier.shop.model;

import java.io.Serializable;
/*
*
* 作者:张波
* 2017/12/19
* */
public class NetPoints implements Serializable {

    private static final long serialVersionUID = -3908081670037735426L;

    private Integer id;

    private Integer siteId;

    private String netPointCode;

    private String netPointN8;

    private String password;

    private String salt;

    private String netPointName;

    private String contactName;

    private String contactMobile;

    private Integer areaCode;

    private String phoneNumber;

    private String contactEmail;

    private Integer contactProvinceId;

    private Integer contactCityId;

    private Integer shippingTimeLimit;

    private String contactAddress;

    private String coordinate;

    private String TCCode;

    private Integer shippingDistance;

    private Integer logisticsTimeLimit;

    private Integer addTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getNetPointCode() {
        return netPointCode;
    }

    public void setNetPointCode(String netPointCode) {
        this.netPointCode = netPointCode;
    }

    public String getNetPointN8() {
        return netPointN8;
    }

    public void setNetPointN8(String netPointN8) {
        this.netPointN8 = netPointN8;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getNetPointName() {
        return netPointName;
    }

    public void setNetPointName(String netPointName) {
        this.netPointName = netPointName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public Integer getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Integer areaCode) {
        this.areaCode = areaCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public Integer getContactProvinceId() {
        return contactProvinceId;
    }

    public void setContactProvinceId(Integer contactProvinceId) {
        this.contactProvinceId = contactProvinceId;
    }

    public Integer getContactCityId() {
        return contactCityId;
    }

    public void setContactCityId(Integer contactCityId) {
        this.contactCityId = contactCityId;
    }

    public Integer getShippingTimeLimit() {
        return shippingTimeLimit;
    }

    public void setShippingTimeLimit(Integer shippingTimeLimit) {
        this.shippingTimeLimit = shippingTimeLimit;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getTCCode() {
        return TCCode;
    }

    public void setTCCode(String TCCode) {
        this.TCCode = TCCode;
    }

    public Integer getShippingDistance() {
        return shippingDistance;
    }

    public void setShippingDistance(Integer shippingDistance) {
        this.shippingDistance = shippingDistance;
    }

    public Integer getLogisticsTimeLimit() {
        return logisticsTimeLimit;
    }

    public void setLogisticsTimeLimit(Integer logisticsTimeLimit) {
        this.logisticsTimeLimit = logisticsTimeLimit;
    }

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }
}