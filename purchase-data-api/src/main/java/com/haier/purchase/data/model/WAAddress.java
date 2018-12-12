package com.haier.purchase.data.model;

import java.io.Serializable;

/**
 * 
 *                       
 * @Filename: WAAddress.java
 * @Version: 1.0
 * @Author: lizhen
 * @Email: zhen1.li@dhc.com.cn
 *
 */
public class WAAddress implements Serializable {
    private String  sCode;          //'库位码',
    private String  centerName;     //'所属中心',
    private String  cCode;          //'中心代码',
    private String  province;       //'所属省级',
    private String  city;           //'市',
    private String  county;         //'县区',
    private String  lesCode;        //'LES省级代码',
    private String  address;        //'地址',
    private String  zipCode;        //'邮编',
    private String  contact_cgodbm; //'CGO/DBM正品退货联系人',
    private String  contact_crm;    //'CRM正品退货联系人',
    private String  mobilePhone;    //'联系电话(手机)',
    private String  phone;          //'固定电话',

    private Integer is_enabled;     //'启用和禁用状态，启用是0，禁用是1',

    //不是数据表内的数据，启用和禁用
    private String  is_enabled_name;

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

    public String getContact_cgodbm() {
        return contact_cgodbm;
    }

    public void setContact_cgodbm(String contact_cgodbm) {
        this.contact_cgodbm = contact_cgodbm;
    }

    public String getContact_crm() {
        return contact_crm;
    }

    public void setContact_crm(String contact_crm) {
        this.contact_crm = contact_crm;
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

    public Integer getIs_enabled() {
        return is_enabled;
    }

    public void setIs_enabled(Integer is_enabled) {
        this.is_enabled = is_enabled;
    }

    public String getIs_enabled_name() {
        return is_enabled_name;
    }

    public void setIs_enabled_name(String is_enabled_name) {
        this.is_enabled_name = is_enabled_name;
    }

    @Override
    public String toString() {
        return "[sCode=" + sCode + ", centerName=" + centerName + ", cCode=" + cCode
               + ", province=" + province + ", city=" + city + ", county=" + county + ", lesCode="
               + lesCode + ", address=" + address + ", zipCode=" + zipCode + ", contact_cgodbm="
               + contact_cgodbm + ", contact_crm=" + contact_crm + ", mobilePhone=" + mobilePhone
               + ", phone=" + phone +"]";
    }

}