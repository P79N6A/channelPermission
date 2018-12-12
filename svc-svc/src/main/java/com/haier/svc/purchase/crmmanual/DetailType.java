
package com.haier.svc.purchase.crmmanual;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>DetailType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="DetailType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BillCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="InvCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="InvSort" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="InvBrand" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="InvState" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Qty" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="UnitPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="SumMoney" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="StockPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="RetailPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ActPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="BateRate" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="BateMoney" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="VerCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="VerMoney" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ProLossMoney" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="LossRate" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="IsFL" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IsKPO" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="InvMemo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ADD1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ADD2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ADD3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetailType", propOrder = {
    "billCode",
    "invCode",
    "invSort",
    "invBrand",
    "invState",
    "qty",
    "unitPrice",
    "sumMoney",
    "stockPrice",
    "retailPrice",
    "actPrice",
    "bateRate",
    "bateMoney",
    "verCode",
    "verMoney",
    "proLossMoney",
    "lossRate",
    "isFL",
    "isKPO",
    "invMemo",
    "add1",
    "add2",
    "add3"
})
public class DetailType {

    @XmlElement(name = "BillCode", required = true)
    protected String billCode;
    @XmlElement(name = "InvCode", required = true)
    protected String invCode;
    @XmlElement(name = "InvSort", required = true)
    protected String invSort;
    @XmlElement(name = "InvBrand", required = true)
    protected String invBrand;
    @XmlElement(name = "InvState", required = true)
    protected String invState;
    @XmlElement(name = "Qty")
    protected int qty;
    @XmlElement(name = "UnitPrice", required = true)
    protected BigDecimal unitPrice;
    @XmlElement(name = "SumMoney", required = true)
    protected BigDecimal sumMoney;
    @XmlElement(name = "StockPrice", required = true)
    protected BigDecimal stockPrice;
    @XmlElement(name = "RetailPrice", required = true)
    protected BigDecimal retailPrice;
    @XmlElement(name = "ActPrice", required = true)
    protected BigDecimal actPrice;
    @XmlElement(name = "BateRate", required = true)
    protected BigDecimal bateRate;
    @XmlElement(name = "BateMoney", required = true)
    protected BigDecimal bateMoney;
    @XmlElement(name = "VerCode", required = true)
    protected String verCode;
    @XmlElement(name = "VerMoney", required = true)
    protected BigDecimal verMoney;
    @XmlElement(name = "ProLossMoney", required = true)
    protected BigDecimal proLossMoney;
    @XmlElement(name = "LossRate", required = true)
    protected BigDecimal lossRate;
    @XmlElement(name = "IsFL")
    protected int isFL;
    @XmlElement(name = "IsKPO")
    protected int isKPO;
    @XmlElement(name = "InvMemo", required = true)
    protected String invMemo;
    @XmlElement(name = "ADD1", required = true)
    protected String add1;
    @XmlElement(name = "ADD2", required = true)
    protected String add2;
    @XmlElement(name = "ADD3", required = true)
    protected String add3;

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
     * 获取invBrand属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvBrand() {
        return invBrand;
    }

    /**
     * 设置invBrand属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvBrand(String value) {
        this.invBrand = value;
    }

    /**
     * 获取invState属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvState() {
        return invState;
    }

    /**
     * 设置invState属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvState(String value) {
        this.invState = value;
    }

    /**
     * 获取qty属性的值。
     * 
     */
    public int getQty() {
        return qty;
    }

    /**
     * 设置qty属性的值。
     * 
     */
    public void setQty(int value) {
        this.qty = value;
    }

    /**
     * 获取unitPrice属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * 设置unitPrice属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUnitPrice(BigDecimal value) {
        this.unitPrice = value;
    }

    /**
     * 获取sumMoney属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSumMoney() {
        return sumMoney;
    }

    /**
     * 设置sumMoney属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSumMoney(BigDecimal value) {
        this.sumMoney = value;
    }

    /**
     * 获取stockPrice属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getStockPrice() {
        return stockPrice;
    }

    /**
     * 设置stockPrice属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setStockPrice(BigDecimal value) {
        this.stockPrice = value;
    }

    /**
     * 获取retailPrice属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    /**
     * 设置retailPrice属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRetailPrice(BigDecimal value) {
        this.retailPrice = value;
    }

    /**
     * 获取actPrice属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getActPrice() {
        return actPrice;
    }

    /**
     * 设置actPrice属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setActPrice(BigDecimal value) {
        this.actPrice = value;
    }

    /**
     * 获取bateRate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBateRate() {
        return bateRate;
    }

    /**
     * 设置bateRate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBateRate(BigDecimal value) {
        this.bateRate = value;
    }

    /**
     * 获取bateMoney属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBateMoney() {
        return bateMoney;
    }

    /**
     * 设置bateMoney属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBateMoney(BigDecimal value) {
        this.bateMoney = value;
    }

    /**
     * 获取verCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVerCode() {
        return verCode;
    }

    /**
     * 设置verCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVerCode(String value) {
        this.verCode = value;
    }

    /**
     * 获取verMoney属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVerMoney() {
        return verMoney;
    }

    /**
     * 设置verMoney属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVerMoney(BigDecimal value) {
        this.verMoney = value;
    }

    /**
     * 获取proLossMoney属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getProLossMoney() {
        return proLossMoney;
    }

    /**
     * 设置proLossMoney属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setProLossMoney(BigDecimal value) {
        this.proLossMoney = value;
    }

    /**
     * 获取lossRate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLossRate() {
        return lossRate;
    }

    /**
     * 设置lossRate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLossRate(BigDecimal value) {
        this.lossRate = value;
    }

    /**
     * 获取isFL属性的值。
     * 
     */
    public int getIsFL() {
        return isFL;
    }

    /**
     * 设置isFL属性的值。
     * 
     */
    public void setIsFL(int value) {
        this.isFL = value;
    }

    /**
     * 获取isKPO属性的值。
     * 
     */
    public int getIsKPO() {
        return isKPO;
    }

    /**
     * 设置isKPO属性的值。
     * 
     */
    public void setIsKPO(int value) {
        this.isKPO = value;
    }

    /**
     * 获取invMemo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvMemo() {
        return invMemo;
    }

    /**
     * 设置invMemo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvMemo(String value) {
        this.invMemo = value;
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

}
