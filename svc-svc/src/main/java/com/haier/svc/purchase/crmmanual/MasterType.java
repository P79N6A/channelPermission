
package com.haier.svc.purchase.crmmanual;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>MasterType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="MasterType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BillCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CorpCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RegionID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BillType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PickType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CustCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DestCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BaseCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="WhCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CustMgr" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ProMgr" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ProDeputy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PlanInDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="OrderCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ProjectCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BudgetSort" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BudgetOrg" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="InvSort" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BrandCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SaleOrgCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="UserMemo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Maker" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AudiMan" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AudiFlag" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="BName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Address" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Tel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZipCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ReGetmoney" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Flag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="UpAccount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ADD1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ADD2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ADD3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ADD4" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ADD5" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MasterType", propOrder = {
    "billCode",
    "corpCode",
    "regionID",
    "billType",
    "pickType",
    "custCode",
    "destCode",
    "baseCode",
    "whCode",
    "custMgr",
    "proMgr",
    "proDeputy",
    "planInDate",
    "orderCode",
    "projectCode",
    "budgetSort",
    "budgetOrg",
    "invSort",
    "brandCode",
    "saleOrgCode",
    "userMemo",
    "maker",
    "audiMan",
    "audiFlag",
    "bName",
    "address",
    "tel",
    "zipCode",
    "reGetmoney",
    "flag",
    "upAccount",
    "add1",
    "add2",
    "add3",
    "add4",
    "add5"
})
public class MasterType {

    @XmlElement(name = "BillCode", required = true)
    protected String billCode;
    @XmlElement(name = "CorpCode", required = true)
    protected String corpCode;
    @XmlElement(name = "RegionID", required = true)
    protected String regionID;
    @XmlElement(name = "BillType", required = true)
    protected String billType;
    @XmlElement(name = "PickType", required = true)
    protected String pickType;
    @XmlElement(name = "CustCode", required = true)
    protected String custCode;
    @XmlElement(name = "DestCode", required = true)
    protected String destCode;
    @XmlElement(name = "BaseCode", required = true)
    protected String baseCode;
    @XmlElement(name = "WhCode", required = true)
    protected String whCode;
    @XmlElement(name = "CustMgr", required = true)
    protected String custMgr;
    @XmlElement(name = "ProMgr", required = true)
    protected String proMgr;
    @XmlElement(name = "ProDeputy", required = true)
    protected String proDeputy;
    @XmlElement(name = "PlanInDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar planInDate;
    @XmlElement(name = "OrderCode", required = true)
    protected String orderCode;
    @XmlElement(name = "ProjectCode", required = true)
    protected String projectCode;
    @XmlElement(name = "BudgetSort", required = true)
    protected String budgetSort;
    @XmlElement(name = "BudgetOrg", required = true)
    protected String budgetOrg;
    @XmlElement(name = "InvSort", required = true)
    protected String invSort;
    @XmlElement(name = "BrandCode", required = true)
    protected String brandCode;
    @XmlElement(name = "SaleOrgCode", required = true)
    protected String saleOrgCode;
    @XmlElement(name = "UserMemo", required = true)
    protected String userMemo;
    @XmlElement(name = "Maker", required = true)
    protected String maker;
    @XmlElement(name = "AudiMan", required = true)
    protected String audiMan;
    @XmlElement(name = "AudiFlag")
    protected int audiFlag;
    @XmlElement(name = "BName", required = true)
    protected String bName;
    @XmlElement(name = "Address", required = true)
    protected String address;
    @XmlElement(name = "Tel", required = true)
    protected String tel;
    @XmlElement(name = "ZipCode", required = true)
    protected String zipCode;
    @XmlElement(name = "ReGetmoney", required = true)
    protected String reGetmoney;
    @XmlElement(name = "Flag", required = true)
    protected String flag;
    @XmlElement(name = "UpAccount", required = true)
    protected String upAccount;
    @XmlElement(name = "ADD1", required = true)
    protected String add1;
    @XmlElement(name = "ADD2", required = true)
    protected String add2;
    @XmlElement(name = "ADD3", required = true)
    protected String add3;
    @XmlElement(name = "ADD4", required = true)
    protected String add4;
    @XmlElement(name = "ADD5", required = true)
    protected String add5;

    /**
     * 获取billCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillCode() {
        return billCode;
    }

    /**
     * 设置billCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillCode(String value) {
        this.billCode = value;
    }

    /**
     * 获取corpCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorpCode() {
        return corpCode;
    }

    /**
     * 设置corpCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorpCode(String value) {
        this.corpCode = value;
    }

    /**
     * 获取regionID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegionID() {
        return regionID;
    }

    /**
     * 设置regionID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegionID(String value) {
        this.regionID = value;
    }

    /**
     * 获取billType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillType() {
        return billType;
    }

    /**
     * 设置billType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillType(String value) {
        this.billType = value;
    }

    /**
     * 获取pickType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPickType() {
        return pickType;
    }

    /**
     * 设置pickType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPickType(String value) {
        this.pickType = value;
    }

    /**
     * 获取custCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustCode() {
        return custCode;
    }

    /**
     * 设置custCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustCode(String value) {
        this.custCode = value;
    }

    /**
     * 获取destCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestCode() {
        return destCode;
    }

    /**
     * 设置destCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestCode(String value) {
        this.destCode = value;
    }

    /**
     * 获取baseCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBaseCode() {
        return baseCode;
    }

    /**
     * 设置baseCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBaseCode(String value) {
        this.baseCode = value;
    }

    /**
     * 获取whCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWhCode() {
        return whCode;
    }

    /**
     * 设置whCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWhCode(String value) {
        this.whCode = value;
    }

    /**
     * 获取custMgr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustMgr() {
        return custMgr;
    }

    /**
     * 设置custMgr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustMgr(String value) {
        this.custMgr = value;
    }

    /**
     * 获取proMgr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProMgr() {
        return proMgr;
    }

    /**
     * 设置proMgr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProMgr(String value) {
        this.proMgr = value;
    }

    /**
     * 获取proDeputy属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProDeputy() {
        return proDeputy;
    }

    /**
     * 设置proDeputy属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProDeputy(String value) {
        this.proDeputy = value;
    }

    /**
     * 获取planInDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPlanInDate() {
        return planInDate;
    }

    /**
     * 设置planInDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPlanInDate(XMLGregorianCalendar value) {
        this.planInDate = value;
    }

    /**
     * 获取orderCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderCode() {
        return orderCode;
    }

    /**
     * 设置orderCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderCode(String value) {
        this.orderCode = value;
    }

    /**
     * 获取projectCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectCode() {
        return projectCode;
    }

    /**
     * 设置projectCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectCode(String value) {
        this.projectCode = value;
    }

    /**
     * 获取budgetSort属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBudgetSort() {
        return budgetSort;
    }

    /**
     * 设置budgetSort属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBudgetSort(String value) {
        this.budgetSort = value;
    }

    /**
     * 获取budgetOrg属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBudgetOrg() {
        return budgetOrg;
    }

    /**
     * 设置budgetOrg属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBudgetOrg(String value) {
        this.budgetOrg = value;
    }

    /**
     * 获取invSort属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvSort() {
        return invSort;
    }

    /**
     * 设置invSort属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvSort(String value) {
        this.invSort = value;
    }

    /**
     * 获取brandCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrandCode() {
        return brandCode;
    }

    /**
     * 设置brandCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrandCode(String value) {
        this.brandCode = value;
    }

    /**
     * 获取saleOrgCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSaleOrgCode() {
        return saleOrgCode;
    }

    /**
     * 设置saleOrgCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSaleOrgCode(String value) {
        this.saleOrgCode = value;
    }

    /**
     * 获取userMemo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserMemo() {
        return userMemo;
    }

    /**
     * 设置userMemo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserMemo(String value) {
        this.userMemo = value;
    }

    /**
     * 获取maker属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaker() {
        return maker;
    }

    /**
     * 设置maker属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaker(String value) {
        this.maker = value;
    }

    /**
     * 获取audiMan属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAudiMan() {
        return audiMan;
    }

    /**
     * 设置audiMan属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAudiMan(String value) {
        this.audiMan = value;
    }

    /**
     * 获取audiFlag属性的值。
     * 
     */
    public int getAudiFlag() {
        return audiFlag;
    }

    /**
     * 设置audiFlag属性的值。
     * 
     */
    public void setAudiFlag(int value) {
        this.audiFlag = value;
    }

    /**
     * 获取bName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBName() {
        return bName;
    }

    /**
     * 设置bName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBName(String value) {
        this.bName = value;
    }

    /**
     * 获取address属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置address属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress(String value) {
        this.address = value;
    }

    /**
     * 获取tel属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTel() {
        return tel;
    }

    /**
     * 设置tel属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTel(String value) {
        this.tel = value;
    }

    /**
     * 获取zipCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * 设置zipCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZipCode(String value) {
        this.zipCode = value;
    }

    /**
     * 获取reGetmoney属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReGetmoney() {
        return reGetmoney;
    }

    /**
     * 设置reGetmoney属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReGetmoney(String value) {
        this.reGetmoney = value;
    }

    /**
     * 获取flag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlag() {
        return flag;
    }

    /**
     * 设置flag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlag(String value) {
        this.flag = value;
    }

    /**
     * 获取upAccount属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpAccount() {
        return upAccount;
    }

    /**
     * 设置upAccount属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpAccount(String value) {
        this.upAccount = value;
    }

    /**
     * 获取add1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD1() {
        return add1;
    }

    /**
     * 设置add1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD1(String value) {
        this.add1 = value;
    }

    /**
     * 获取add2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD2() {
        return add2;
    }

    /**
     * 设置add2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD2(String value) {
        this.add2 = value;
    }

    /**
     * 获取add3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD3() {
        return add3;
    }

    /**
     * 设置add3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD3(String value) {
        this.add3 = value;
    }

    /**
     * 获取add4属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD4() {
        return add4;
    }

    /**
     * 设置add4属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD4(String value) {
        this.add4 = value;
    }

    /**
     * 获取add5属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD5() {
        return add5;
    }

    /**
     * 设置add5属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD5(String value) {
        this.add5 = value;
    }

}
