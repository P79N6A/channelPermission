package com.haier.stock.eai.getbominfofromlestoehaier;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for ZLES_DCMS_24 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZLES_DCMS_24">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MATNR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WERKS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="STLNR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="POSNR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IDNRK" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MENGE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="UPDATE_TIME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="STATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MAKTX1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MAKTX2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZLES_DCMS_24", propOrder = { "matnr", "werks", "stlnr", "posnr", "idnrk", "menge",
                                             "updatetime", "state", "maktx1", "maktx2" })
public class ZLESDCMS24 {

    @XmlElement(name = "MATNR")
    protected String     matnr;
    @XmlElement(name = "WERKS")
    protected String     werks;
    @XmlElement(name = "STLNR")
    protected String     stlnr;
    @XmlElement(name = "POSNR")
    protected String     posnr;
    @XmlElement(name = "IDNRK")
    protected String     idnrk;
    @XmlElement(name = "MENGE")
    protected BigDecimal menge;
    @XmlElement(name = "UPDATE_TIME")
    protected String     updatetime;
    @XmlElement(name = "STATE")
    protected String     state;
    @XmlElement(name = "MAKTX1")
    protected String     maktx1;
    @XmlElement(name = "MAKTX2")
    protected String     maktx2;

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
     * Gets the value of the werks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWERKS() {
        return werks;
    }

    /**
     * Sets the value of the werks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWERKS(String value) {
        this.werks = value;
    }

    /**
     * Gets the value of the stlnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTLNR() {
        return stlnr;
    }

    /**
     * Sets the value of the stlnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTLNR(String value) {
        this.stlnr = value;
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
     * Gets the value of the idnrk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDNRK() {
        return idnrk;
    }

    /**
     * Sets the value of the idnrk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDNRK(String value) {
        this.idnrk = value;
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
     * Gets the value of the updatetime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUPDATETIME() {
        return updatetime;
    }

    /**
     * Sets the value of the updatetime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUPDATETIME(String value) {
        this.updatetime = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTATE() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTATE(String value) {
        this.state = value;
    }

    /**
     * Gets the value of the maktx1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMAKTX1() {
        return maktx1;
    }

    /**
     * Sets the value of the maktx1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMAKTX1(String value) {
        this.maktx1 = value;
    }

    /**
     * Gets the value of the maktx2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMAKTX2() {
        return maktx2;
    }

    /**
     * Sets the value of the maktx2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMAKTX2(String value) {
        this.maktx2 = value;
    }

}
