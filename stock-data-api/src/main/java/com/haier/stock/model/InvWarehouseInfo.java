package com.haier.stock.model;

import java.io.Serializable;

public class InvWarehouseInfo implements Serializable {

    private String secCode;

    public String getSecCode() {
        return secCode;
    }

    public void setSecCode(String secCode) {
        this.secCode = secCode;
    }

    private String centerName;

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    private String centerCode;

    public String getCenterCode() {
        return centerCode;
    }

    public void setCenterCode(String centerCode) {
        this.centerCode = centerCode;
    }

    private String province;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private String region;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    private String lesProvinceCode;

    public String getLesProvinceCode() {
        return lesProvinceCode;
    }

    public void setLesProvinceCode(String lesProvinceCode) {
        this.lesProvinceCode = lesProvinceCode;
    }

    private String secAddress;

    public String getSecAddress() {
        return secAddress;
    }

    public void setSecAddress(String secAddress) {
        this.secAddress = secAddress;
    }

    private String zipcode;

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    private String cgodbmZpthPerson;

    public String getCgodbmZpthPerson() {
        return cgodbmZpthPerson;
    }

    public void setCgodbmZpthPerson(String cgodbmZpthPerson) {
        this.cgodbmZpthPerson = cgodbmZpthPerson;
    }

    private String crmZpthPerson;

    public String getCrmZpthPerson() {
        return crmZpthPerson;
    }

    public void setCrmZpthPerson(String crmZpthPerson) {
        this.crmZpthPerson = crmZpthPerson;
    }

    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}