package com.haier.svc.purchase.querywaorderbillfromihs;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>Java class for VW_WAOrderBillYTJOutput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VW_WAOrderBillYTJOutput">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BillCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BillDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="SO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="WDCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="InvCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="UnitPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="SumMoney" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="Qty" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="BateRate" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="Flag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Message" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FaultDetail" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VW_WAOrderBillYTJOutput", propOrder = { "billCode", "billDate", "so", "dn",
                                                        "wdCode", "invCode", "unitPrice",
                                                        "sumMoney", "qty", "bateRate", "flag",
                                                        "message", "faultDetail" })
public class VWWAOrderBillYTJOutput {

    @XmlElement(name = "BillCode", required = true)
    protected String               billCode;
    @XmlElement(name = "BillDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar billDate;
    @XmlElement(name = "SO", required = true)
    protected String               so;
    @XmlElement(name = "DN", required = true)
    protected String               dn;
    @XmlElement(name = "WDCode", required = true)
    protected String               wdCode;
    @XmlElement(name = "InvCode", required = true)
    protected String               invCode;
    @XmlElement(name = "UnitPrice", required = true)
    protected BigDecimal           unitPrice;
    @XmlElement(name = "SumMoney", required = true)
    protected BigDecimal           sumMoney;
    @XmlElement(name = "Qty")
    protected int                  qty;
    @XmlElement(name = "BateRate", required = true)
    protected BigDecimal           bateRate;
    @XmlElement(name = "Flag", required = true)
    protected String               flag;
    @XmlElement(name = "Message", required = true)
    protected String               message;
    @XmlElement(name = "FaultDetail", required = true)
    protected String               faultDetail;

    /**
     * Gets the value of the billCode property.
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
     * Sets the value of the billCode property.
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
     * Gets the value of the billDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBillDate() {
        return billDate;
    }

    /**
     * Sets the value of the billDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBillDate(XMLGregorianCalendar value) {
        this.billDate = value;
    }

    /**
     * Gets the value of the so property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSO() {
        return so;
    }

    /**
     * Sets the value of the so property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSO(String value) {
        this.so = value;
    }

    /**
     * Gets the value of the dn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDN() {
        return dn;
    }

    /**
     * Sets the value of the dn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDN(String value) {
        this.dn = value;
    }

    /**
     * Gets the value of the wdCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWDCode() {
        return wdCode;
    }

    /**
     * Sets the value of the wdCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWDCode(String value) {
        this.wdCode = value;
    }

    /**
     * Gets the value of the invCode property.
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
     * Sets the value of the invCode property.
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
     * Gets the value of the unitPrice property.
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
     * Sets the value of the unitPrice property.
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
     * Gets the value of the sumMoney property.
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
     * Sets the value of the sumMoney property.
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
     * Gets the value of the qty property.
     * 
     */
    public int getQty() {
        return qty;
    }

    /**
     * Sets the value of the qty property.
     * 
     */
    public void setQty(int value) {
        this.qty = value;
    }

    /**
     * Gets the value of the bateRate property.
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
     * Sets the value of the bateRate property.
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
     * Gets the value of the flag property.
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
     * Sets the value of the flag property.
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
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the faultDetail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaultDetail() {
        return faultDetail;
    }

    /**
     * Sets the value of the faultDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFaultDetail(String value) {
        this.faultDetail = value;
    }

}
