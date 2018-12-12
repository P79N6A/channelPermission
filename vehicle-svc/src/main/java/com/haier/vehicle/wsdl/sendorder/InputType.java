
package com.haier.vehicle.wsdl.sendorder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>InputType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="InputType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BillCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CorpCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RegionID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BillType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PickType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CorpDest" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="WhCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CustMgr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ProMgr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ProDeputy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PlanInDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="OrderCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CustCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CustDest" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MGCustCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Maker" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="LockDay" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="InvCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="InvSort" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Qty" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ReleBillCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="UserMemo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BaseCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Flag" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD2" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD3" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD4" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD5" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD6" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD7" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD8" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD9" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD10" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD11" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD12" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD13" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD14" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD15" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD16" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD17" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD18" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ProjectCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InputType", propOrder = {
    "billCode",
    "corpCode",
    "regionID",
    "billType",
    "pickType",
    "corpDest",
    "whCode",
    "custMgr",
    "proMgr",
    "proDeputy",
    "planInDate",
    "orderCode",
    "custCode",
    "custDest",
    "mgCustCode",
    "maker",
    "lockDay",
    "invCode",
    "invSort",
    "qty",
    "releBillCode",
    "userMemo",
    "baseCode",
    "flag",
    "add1",
    "add2",
    "add3",
    "add4",
    "add5",
    "add6",
    "add7",
    "add8",
    "add9",
    "add10",
    "add11",
    "add12",
    "add13",
    "add14",
    "add15",
    "add16",
    "add17",
    "add18",
    "projectCode"
})
public class InputType {

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
    @XmlElement(name = "CorpDest", required = true)
    protected String corpDest;
    @XmlElement(name = "WhCode", required = true)
    protected String whCode;
    @XmlElement(name = "CustMgr", required = true)
    protected String custMgr;
    @XmlElement(name = "ProMgr", required = true)
    protected String proMgr;
    @XmlElement(name = "ProDeputy", required = true)
    protected String proDeputy;
    @XmlElement(name = "PlanInDate", required = true)
    protected String planInDate;
    @XmlElement(name = "OrderCode", required = true)
    protected String orderCode;
    @XmlElement(name = "CustCode", required = true)
    protected String custCode;
    @XmlElement(name = "CustDest", required = true)
    protected String custDest;
    @XmlElement(name = "MGCustCode", required = true)
    protected String mgCustCode;
    @XmlElement(name = "Maker", required = true)
    protected String maker;
    @XmlElement(name = "LockDay", required = true)
    protected String lockDay;
    @XmlElement(name = "InvCode", required = true)
    protected String invCode;
    @XmlElement(name = "InvSort", required = true)
    protected String invSort;
    @XmlElement(name = "Qty", required = true)
    protected String qty;
    @XmlElement(name = "ReleBillCode", required = true)
    protected String releBillCode;
    @XmlElement(name = "UserMemo", required = true)
    protected String userMemo;
    @XmlElement(name = "BaseCode", required = true)
    protected String baseCode;
    @XmlElement(name = "Flag", required = true)
    protected String flag;
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
    @XmlElement(name = "ADD6", required = true)
    protected String add6;
    @XmlElement(name = "ADD7", required = true)
    protected String add7;
    @XmlElement(name = "ADD8", required = true)
    protected String add8;
    @XmlElement(name = "ADD9", required = true)
    protected String add9;
    @XmlElement(name = "ADD10", required = true)
    protected String add10;
    @XmlElement(name = "ADD11", required = true)
    protected String add11;
    @XmlElement(name = "ADD12", required = true)
    protected String add12;
    @XmlElement(name = "ADD13", required = true)
    protected String add13;
    @XmlElement(name = "ADD14", required = true)
    protected String add14;
    @XmlElement(name = "ADD15", required = true)
    protected String add15;
    @XmlElement(name = "ADD16", required = true)
    protected String add16;
    @XmlElement(name = "ADD17", required = true)
    protected String add17;
    @XmlElement(name = "ADD18", required = true)
    protected String add18;
    @XmlElement(name = "ProjectCode", required = true)
    protected String projectCode;

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
     * 获取corpDest属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorpDest() {
        return corpDest;
    }

    /**
     * 设置corpDest属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorpDest(String value) {
        this.corpDest = value;
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
     *     {@link String }
     *     
     */
    public String getPlanInDate() {
        return planInDate;
    }

    /**
     * 设置planInDate属性的值。
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
     * 获取custDest属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustDest() {
        return custDest;
    }

    /**
     * 设置custDest属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustDest(String value) {
        this.custDest = value;
    }

    /**
     * 获取mgCustCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMGCustCode() {
        return mgCustCode;
    }

    /**
     * 设置mgCustCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMGCustCode(String value) {
        this.mgCustCode = value;
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
     * 获取lockDay属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLockDay() {
        return lockDay;
    }

    /**
     * 设置lockDay属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLockDay(String value) {
        this.lockDay = value;
    }

    /**
     * 获取invCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvCode() {
        return invCode;
    }

    /**
     * 设置invCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvCode(String value) {
        this.invCode = value;
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
     * 获取qty属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQty() {
        return qty;
    }

    /**
     * 设置qty属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQty(String value) {
        this.qty = value;
    }

    /**
     * 获取releBillCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReleBillCode() {
        return releBillCode;
    }

    /**
     * 设置releBillCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReleBillCode(String value) {
        this.releBillCode = value;
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

    /**
     * 获取add6属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD6() {
        return add6;
    }

    /**
     * 设置add6属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD6(String value) {
        this.add6 = value;
    }

    /**
     * 获取add7属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD7() {
        return add7;
    }

    /**
     * 设置add7属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD7(String value) {
        this.add7 = value;
    }

    /**
     * 获取add8属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD8() {
        return add8;
    }

    /**
     * 设置add8属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD8(String value) {
        this.add8 = value;
    }

    /**
     * 获取add9属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD9() {
        return add9;
    }

    /**
     * 设置add9属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD9(String value) {
        this.add9 = value;
    }

    /**
     * 获取add10属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD10() {
        return add10;
    }

    /**
     * 设置add10属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD10(String value) {
        this.add10 = value;
    }

    /**
     * 获取add11属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD11() {
        return add11;
    }

    /**
     * 设置add11属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD11(String value) {
        this.add11 = value;
    }

    /**
     * 获取add12属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD12() {
        return add12;
    }

    /**
     * 设置add12属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD12(String value) {
        this.add12 = value;
    }

    /**
     * 获取add13属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD13() {
        return add13;
    }

    /**
     * 设置add13属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD13(String value) {
        this.add13 = value;
    }

    /**
     * 获取add14属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD14() {
        return add14;
    }

    /**
     * 设置add14属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD14(String value) {
        this.add14 = value;
    }

    /**
     * 获取add15属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD15() {
        return add15;
    }

    /**
     * 设置add15属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD15(String value) {
        this.add15 = value;
    }

    /**
     * 获取add16属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD16() {
        return add16;
    }

    /**
     * 设置add16属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD16(String value) {
        this.add16 = value;
    }

    /**
     * 获取add17属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD17() {
        return add17;
    }

    /**
     * 设置add17属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD17(String value) {
        this.add17 = value;
    }

    /**
     * 获取add18属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD18() {
        return add18;
    }

    /**
     * 设置add18属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD18(String value) {
        this.add18 = value;
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

}
