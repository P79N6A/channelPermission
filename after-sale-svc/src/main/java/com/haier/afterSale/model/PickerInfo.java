package com.haier.afterSale.model;

public class PickerInfo {
    private String company="";
    private String name="";

    public void setCompany(String company) {
        this.company = company;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    private String tel="";
    private String mobile="";
    private String id="";

    public String getCompany() {
        return company;
    }

    public String getName() {
        return name;
    }

    public String getTel() {
        return tel;
    }

    public String getMobile() {
        return mobile;
    }

    public String getId() {
        return id;
    }

    public String getCarNo() {
        return carNo;
    }

    private String carNo;
}
