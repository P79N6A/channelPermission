package com.haier.purchase.data.model;

import java.io.Serializable;

public class WAAddressInfo implements Serializable {
    private String sCode;      //'库位码',
    private String centerName; //'所属中心',
    private String cCode;      //'中心代码',
    private String province;   //'所属省级',
    private String city;       //'市',
    private String county;     //'县区',
    private String lesCode;    //'LES省级代码',
    private String address;    //'地址',
    private String zipCode;    //'邮编',
    private String contact;    //'分中心联系人',
    private String mobilePhone; //'联系电话(手机)',
    private String phone;      //'固定电话',
    private String createTime; //订单生成时间

    public String getsCode() {
        return sCode;
    }

    public void setsCode(String sCode) {
        this.sCode = sCode;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getcCode() {
        return cCode;
    }

    public void setcCode(String cCode) {
        this.cCode = cCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getLesCode() {
        return lesCode;
    }

    public void setLesCode(String lesCode) {
        this.lesCode = lesCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
    
}