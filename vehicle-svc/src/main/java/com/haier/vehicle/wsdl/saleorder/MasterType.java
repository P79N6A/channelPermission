
package com.haier.vehicle.wsdl.saleorder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>MasterType complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="MasterType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BillCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CorpCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RegionID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BillType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PickType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CustCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DestCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BaseCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="WhCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CustMgr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ProMgr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ProDeputy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PlanInDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="OrderCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ProjectCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BudgetSort" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BudgetOrg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="InvSort" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BrandCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SaleOrgCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="UserMemo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Maker" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AudiMan" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AudiFlag" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Address" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Tel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ZipCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ReGetmoney" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Flag" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD2" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD3" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD4" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD5" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ATPPlanInDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
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
    "add1",
    "add2",
    "add3",
    "add4",
    "add5",
    "atpPlanInDate"
})
public class MasterType {

    @XmlElement(name = "BillCode", required = true, nillable = true)
    protected String billCode;
    @XmlElement(name = "CorpCode", required = true, nillable = true)
    protected String corpCode;
    @XmlElement(name = "RegionID", required = true, nillable = true)
    protected String regionID;
    @XmlElement(name = "BillType", required = true, nillable = true)
    protected String billType;
    @XmlElement(name = "PickType", required = true, nillable = true)
    protected String pickType;
    @XmlElement(name = "CustCode", required = true, nillable = true)
    protected String custCode;
    @XmlElement(name = "DestCode", required = true, nillable = true)
    protected String destCode;
    @XmlElement(name = "BaseCode", required = true, nillable = true)
    protected String baseCode;
    @XmlElement(name = "WhCode", required = true, nillable = true)
    protected String whCode;
    @XmlElement(name = "CustMgr", required = true, nillable = true)
    protected String custMgr;
    @XmlElement(name = "ProMgr", required = true, nillable = true)
    protected String proMgr;
    @XmlElement(name = "ProDeputy", required = true, nillable = true)
    protected String proDeputy;
    @XmlElement(name = "PlanInDate", required = true, nillable = true)
    protected String planInDate;
    @XmlElement(name = "OrderCode", required = true, nillable = true)
    protected String orderCode;
    @XmlElement(name = "ProjectCode", required = true, nillable = true)
    protected String projectCode;
    @XmlElement(name = "BudgetSort", required = true, nillable = true)
    protected String budgetSort;
    @XmlElement(name = "BudgetOrg", required = true, nillable = true)
    protected String budgetOrg;
    @XmlElement(name = "InvSort", required = true, nillable = true)
    protected String invSort;
    @XmlElement(name = "BrandCode", required = true, nillable = true)
    protected String brandCode;
    @XmlElement(name = "SaleOrgCode", required = true, nillable = true)
    protected String saleOrgCode;
    @XmlElement(name = "UserMemo", required = true, nillable = true)
    protected String userMemo;
    @XmlElement(name = "Maker", required = true, nillable = true)
    protected String maker;
    @XmlElement(name = "AudiMan", required = true, nillable = true)
    protected String audiMan;
    @XmlElement(name = "AudiFlag", required = true, nillable = true)
    protected String audiFlag;
    @XmlElement(name = "BName", required = true, nillable = true)
    protected String bName;
    @XmlElement(name = "Address", required = true, nillable = true)
    protected String address;
    @XmlElement(name = "Tel", required = true, nillable = true)
    protected String tel;
    @XmlElement(name = "ZipCode", required = true, nillable = true)
    protected String zipCode;
    @XmlElement(name = "ReGetmoney", required = true, nillable = true)
    protected String reGetmoney;
    @XmlElement(name = "Flag", required = true, nillable = true)
    protected String flag;
    @XmlElement(name = "ADD1", required = true, nillable = true)
    protected String add1;
    @XmlElement(name = "ADD2", required = true, nillable = true)
    protected String add2;
    @XmlElement(name = "ADD3", required = true, nillable = true)
    protected String add3;
    @XmlElement(name = "ADD4", required = true, nillable = true)
    protected String add4;
    @XmlElement(name = "ADD5", required = true, nillable = true)
    protected String add5;
    @XmlElement(name = "ATPPlanInDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar atpPlanInDate;

    /**
     * ��ȡbillCode���Ե�ֵ��
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
     * ����billCode���Ե�ֵ��
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
     * ��ȡcorpCode���Ե�ֵ��
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
     * ����corpCode���Ե�ֵ��
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
     * ��ȡregionID���Ե�ֵ��
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
     * ����regionID���Ե�ֵ��
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
     * ��ȡbillType���Ե�ֵ��
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
     * ����billType���Ե�ֵ��
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
     * ��ȡpickType���Ե�ֵ��
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
     * ����pickType���Ե�ֵ��
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
     * ��ȡcustCode���Ե�ֵ��
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
     * ����custCode���Ե�ֵ��
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
     * ��ȡdestCode���Ե�ֵ��
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
     * ����destCode���Ե�ֵ��
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
     * ��ȡbaseCode���Ե�ֵ��
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
     * ����baseCode���Ե�ֵ��
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
     * ��ȡwhCode���Ե�ֵ��
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
     * ����whCode���Ե�ֵ��
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
     * ��ȡcustMgr���Ե�ֵ��
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
     * ����custMgr���Ե�ֵ��
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
     * ��ȡproMgr���Ե�ֵ��
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
     * ����proMgr���Ե�ֵ��
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
     * ��ȡproDeputy���Ե�ֵ��
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
     * ����proDeputy���Ե�ֵ��
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
     * ��ȡplanInDate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanInDate() {
        return planInDate;
    }

    /**
     * ����planInDate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanInDate(String value) {
        this.planInDate = value;
    }

    /**
     * ��ȡorderCode���Ե�ֵ��
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
     * ����orderCode���Ե�ֵ��
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
     * ��ȡprojectCode���Ե�ֵ��
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
     * ����projectCode���Ե�ֵ��
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
     * ��ȡbudgetSort���Ե�ֵ��
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
     * ����budgetSort���Ե�ֵ��
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
     * ��ȡbudgetOrg���Ե�ֵ��
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
     * ����budgetOrg���Ե�ֵ��
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
     * ��ȡinvSort���Ե�ֵ��
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
     * ����invSort���Ե�ֵ��
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
     * ��ȡbrandCode���Ե�ֵ��
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
     * ����brandCode���Ե�ֵ��
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
     * ��ȡsaleOrgCode���Ե�ֵ��
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
     * ����saleOrgCode���Ե�ֵ��
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
     * ��ȡuserMemo���Ե�ֵ��
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
     * ����userMemo���Ե�ֵ��
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
     * ��ȡmaker���Ե�ֵ��
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
     * ����maker���Ե�ֵ��
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
     * ��ȡaudiMan���Ե�ֵ��
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
     * ����audiMan���Ե�ֵ��
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
     * ��ȡaudiFlag���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAudiFlag() {
        return audiFlag;
    }

    /**
     * ����audiFlag���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAudiFlag(String value) {
        this.audiFlag = value;
    }

    /**
     * ��ȡbName���Ե�ֵ��
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
     * ����bName���Ե�ֵ��
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
     * ��ȡaddress���Ե�ֵ��
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
     * ����address���Ե�ֵ��
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
     * ��ȡtel���Ե�ֵ��
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
     * ����tel���Ե�ֵ��
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
     * ��ȡzipCode���Ե�ֵ��
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
     * ����zipCode���Ե�ֵ��
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
     * ��ȡreGetmoney���Ե�ֵ��
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
     * ����reGetmoney���Ե�ֵ��
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
     * ��ȡflag���Ե�ֵ��
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
     * ����flag���Ե�ֵ��
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
     * ��ȡadd1���Ե�ֵ��
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
     * ����add1���Ե�ֵ��
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
     * ��ȡadd2���Ե�ֵ��
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
     * ����add2���Ե�ֵ��
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
     * ��ȡadd3���Ե�ֵ��
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
     * ����add3���Ե�ֵ��
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
     * ��ȡadd4���Ե�ֵ��
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
     * ����add4���Ե�ֵ��
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
     * ��ȡadd5���Ե�ֵ��
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
     * ����add5���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD5(String value) {
        this.add5 = value;
    }

    /**
     * ��ȡatpPlanInDate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getATPPlanInDate() {
        return atpPlanInDate;
    }

    /**
     * ����atpPlanInDate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setATPPlanInDate(XMLGregorianCalendar value) {
        this.atpPlanInDate = value;
    }

}
