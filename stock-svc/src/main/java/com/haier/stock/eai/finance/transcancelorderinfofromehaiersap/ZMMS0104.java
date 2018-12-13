package com.haier.stock.eai.finance.transcancelorderinfofromehaiersap;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for ZMMS0104 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZMMS0104">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ZOUNB" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZOUIT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZSPDT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BUKRS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MATNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MENGE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="LGORT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZFGLG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZMMS0104", propOrder = { "zounb", "zouit", "zspdt", "bukrs", "matnr", "menge",
                                         "lgort", "zfglg" })
public class ZMMS0104 {

    @XmlElement(name = "ZOUNB", required = true)
    protected String     zounb;
    @XmlElement(name = "ZOUIT", required = true)
    protected String     zouit;
    @XmlElement(name = "ZSPDT", required = true)
    protected String     zspdt;
    @XmlElement(name = "BUKRS", required = true)
    protected String     bukrs;
    @XmlElement(name = "MATNR", required = true)
    protected String     matnr;
    @XmlElement(name = "MENGE", required = true)
    protected BigDecimal menge;
    @XmlElement(name = "LGORT", required = true)
    protected String     lgort;
    @XmlElement(name = "ZFGLG", required = true)
    protected String     zfglg;

    /**
     * Gets the value of the zounb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZOUNB() {
        return zounb;
    }

    /**
     * Sets the value of the zounb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZOUNB(String value) {
        this.zounb = value;
    }

    /**
     * Gets the value of the zouit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZOUIT() {
        return zouit;
    }

    /**
     * Sets the value of the zouit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZOUIT(String value) {
        this.zouit = value;
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
     * Gets the value of the bukrs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBUKRS() {
        return bukrs;
    }

    /**
     * Sets the value of the bukrs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBUKRS(String value) {
        this.bukrs = value;
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

    /**
     * Gets the value of the zfglg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZFGLG() {
        return zfglg;
    }

    /**
     * Sets the value of the zfglg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZFGLG(String value) {
        this.zfglg = value;
    }

}
