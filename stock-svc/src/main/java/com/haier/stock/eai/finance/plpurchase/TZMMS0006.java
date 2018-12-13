package com.haier.stock.eai.finance.plpurchase;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for T_ZMMS0006 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T_ZMMS0006">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ZSPNB" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZLSGI" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZSPDT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LIFNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MATNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MENGE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="MENGE_L" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="NETPR" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="LGORT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZSPIT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T_ZMMS0006", propOrder = { "zspnb", "zlsgi", "zspdt", "lifnr", "matnr", "menge",
                                           "mengel", "netpr", "lgort" })
public class TZMMS0006 {

    @XmlElement(name = "ZSPNB", required = true)
    protected String     zspnb;
    @XmlElement(name = "ZLSGI", required = true)
    protected String     zlsgi;
    @XmlElement(name = "ZSPDT", required = true)
    protected String     zspdt;
    @XmlElement(name = "LIFNR", required = true)
    protected String     lifnr;
    @XmlElement(name = "MATNR", required = true)
    protected String     matnr;
    @XmlElement(name = "MENGE", required = true)
    protected BigDecimal menge;
    @XmlElement(name = "MENGE_L", required = true)
    protected BigDecimal mengel;
    @XmlElement(name = "NETPR", required = true)
    protected BigDecimal netpr;
    @XmlElement(name = "LGORT", required = true)
    protected String     lgort;
    @XmlElement(name = "ZSPIT", required = true)
    protected String     zspit;

    /**
     * Gets the value of the zspnb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZSPNB() {
        return zspnb;
    }

    /**
     * Sets the value of the zspnb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZSPNB(String value) {
        this.zspnb = value;
    }

    /**
     * Gets the value of the zlsgi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZLSGI() {
        return zlsgi;
    }

    /**
     * Sets the value of the zlsgi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZLSGI(String value) {
        this.zlsgi = value;
    }

    /**
     * Gets the value of the zspdt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZSPDT() {
        return zspdt;
    }

    /**
     * Sets the value of the zspdt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZSPDT(String value) {
        this.zspdt = value;
    }

    /**
     * Gets the value of the lifnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLIFNR() {
        return lifnr;
    }

    /**
     * Sets the value of the lifnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLIFNR(String value) {
        this.lifnr = value;
    }

    /**
     * Gets the value of the matnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMATNR() {
        return matnr;
    }

    /**
     * Sets the value of the matnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMATNR(String value) {
        this.matnr = value;
    }

    /**
     * Gets the value of the menge property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMENGE() {
        return menge;
    }

    /**
     * Sets the value of the menge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMENGE(BigDecimal value) {
        this.menge = value;
    }

    /**
     * Gets the value of the mengel property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMENGEL() {
        return mengel;
    }

    /**
     * Sets the value of the mengel property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMENGEL(BigDecimal value) {
        this.mengel = value;
    }

    /**
     * Gets the value of the netpr property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNETPR() {
        return netpr;
    }

    /**
     * Sets the value of the netpr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNETPR(BigDecimal value) {
        this.netpr = value;
    }

    /**
     * Gets the value of the lgort property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLGORT() {
        return lgort;
    }

    /**
     * Sets the value of the lgort property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLGORT(String value) {
        this.lgort = value;
    }

    public String getZSPIT() {
        return zspit;
    }

    public void setZSPIT(String value) {
        this.zspit = value;
    }
}
