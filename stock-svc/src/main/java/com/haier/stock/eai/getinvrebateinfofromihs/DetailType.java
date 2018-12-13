package com.haier.stock.eai.getinvrebateinfofromihs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for DetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DetailType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ReInvCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ReUnitPrice" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ReStockPrice" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ReRetailPrice" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ReActPrice" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ReBateRate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ReBateMoney" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ReLossRate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ReIsFL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ReIsKPO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetailType", propOrder = { "reInvCode", "reUnitPrice", "reStockPrice",
                                           "reRetailPrice", "reActPrice", "reBateRate",
                                           "reBateMoney", "reLossRate", "reIsFL", "reIsKPO" })
public class DetailType {

    @XmlElement(name = "ReInvCode", required = true)
    protected String reInvCode;
    @XmlElement(name = "ReUnitPrice", required = true)
    protected String reUnitPrice;
    @XmlElement(name = "ReStockPrice", required = true)
    protected String reStockPrice;
    @XmlElement(name = "ReRetailPrice", required = true)
    protected String reRetailPrice;
    @XmlElement(name = "ReActPrice", required = true)
    protected String reActPrice;
    @XmlElement(name = "ReBateRate", required = true)
    protected String reBateRate;
    @XmlElement(name = "ReBateMoney", required = true)
    protected String reBateMoney;
    @XmlElement(name = "ReLossRate", required = true)
    protected String reLossRate;
    @XmlElement(name = "ReIsFL", required = true)
    protected String reIsFL;
    @XmlElement(name = "ReIsKPO", required = true)
    protected String reIsKPO;

    /**
     * Gets the value of the reInvCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReInvCode() {
        return reInvCode;
    }

    /**
     * Sets the value of the reInvCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReInvCode(String value) {
        this.reInvCode = value;
    }

    /**
     * Gets the value of the reUnitPrice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReUnitPrice() {
        return reUnitPrice;
    }

    /**
     * Sets the value of the reUnitPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReUnitPrice(String value) {
        this.reUnitPrice = value;
    }

    /**
     * Gets the value of the reStockPrice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReStockPrice() {
        return reStockPrice;
    }

    /**
     * Sets the value of the reStockPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReStockPrice(String value) {
        this.reStockPrice = value;
    }

    /**
     * Gets the value of the reRetailPrice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReRetailPrice() {
        return reRetailPrice;
    }

    /**
     * Sets the value of the reRetailPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReRetailPrice(String value) {
        this.reRetailPrice = value;
    }

    /**
     * Gets the value of the reActPrice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReActPrice() {
        return reActPrice;
    }

    /**
     * Sets the value of the reActPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReActPrice(String value) {
        this.reActPrice = value;
    }

    /**
     * Gets the value of the reBateRate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReBateRate() {
        return reBateRate;
    }

    /**
     * Sets the value of the reBateRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReBateRate(String value) {
        this.reBateRate = value;
    }

    /**
     * Gets the value of the reBateMoney property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReBateMoney() {
        return reBateMoney;
    }

    /**
     * Sets the value of the reBateMoney property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReBateMoney(String value) {
        this.reBateMoney = value;
    }

    /**
     * Gets the value of the reLossRate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReLossRate() {
        return reLossRate;
    }

    /**
     * Sets the value of the reLossRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReLossRate(String value) {
        this.reLossRate = value;
    }

    /**
     * Gets the value of the reIsFL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReIsFL() {
        return reIsFL;
    }

    /**
     * Sets the value of the reIsFL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReIsFL(String value) {
        this.reIsFL = value;
    }

    /**
     * Gets the value of the reIsKPO property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReIsKPO() {
        return reIsKPO;
    }

    /**
     * Sets the value of the reIsKPO property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReIsKPO(String value) {
        this.reIsKPO = value;
    }

}
