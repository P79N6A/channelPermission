package com.haier.vehicle.wsdl.purchasefromgvs;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for ZMMS0003 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZMMS0003">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ZSPNB" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="VBELN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="POSNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZSPDT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LIFNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MATNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MENGE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="LGORT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZFGLG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZLSGI" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZMMS0003", propOrder = { "zspnb", "vbeln", "posnr", "zspdt", "lifnr", "matnr",
                                         "menge", "lgort", "zfglg", "zlsgi" })
public class ZMMS0003 {

    @XmlElement(name = "ZSPNB", required = true)
    protected String     zspnb;
    @XmlElement(name = "VBELN", required = true)
    protected String     vbeln;
    @XmlElement(name = "POSNR", required = true)
    protected String     posnr;
    @XmlElement(name = "ZSPDT", required = true)
    protected String     zspdt;
    @XmlElement(name = "LIFNR", required = true)
    protected String     lifnr;
    @XmlElement(name = "MATNR", required = true)
    protected String     matnr;
    @XmlElement(name = "MENGE", required = true)
    protected BigDecimal menge;
    @XmlElement(name = "LGORT", required = true)
    protected String     lgort;
    @XmlElement(name = "ZFGLG", required = true)
    protected String     zfglg;
    @XmlElement(name = "ZLSGI", required = true)
    protected String     zlsgi;

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
     * Gets the value of the vbeln property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVBELN() {
        return vbeln;
    }

    /**
     * Sets the value of the vbeln property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVBELN(String value) {
        this.vbeln = value;
    }

    /**
     * Gets the value of the posnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPOSNR() {
        return posnr;
    }

    /**
     * Sets the value of the posnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPOSNR(String value) {
        this.posnr = value;
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

}
