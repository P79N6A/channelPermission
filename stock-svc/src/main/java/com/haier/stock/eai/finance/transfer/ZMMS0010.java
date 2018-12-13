package com.haier.stock.eai.finance.transfer;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for ZMMS0010 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZMMS0010">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ZLSOT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZLSOI" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZLSIN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZLSII" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BLDAT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BUDAT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZCBSN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MATNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MENGE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ZLGORT_O" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZLGORT_I" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZMMS0010", propOrder = { "zlsot", "zlsoi", "zlsin", "zlsii", "bldat", "budat",
                                         "zcbsn", "matnr", "menge", "zlgorto", "zlgorti" })
public class ZMMS0010 {

    @XmlElement(name = "ZLSOT", required = true)
    protected String     zlsot;
    @XmlElement(name = "ZLSOI", required = true)
    protected String     zlsoi;
    @XmlElement(name = "ZLSIN", required = true)
    protected String     zlsin;
    @XmlElement(name = "ZLSII", required = true)
    protected String     zlsii;
    @XmlElement(name = "BLDAT", required = true)
    protected String     bldat;
    @XmlElement(name = "BUDAT", required = true)
    protected String     budat;
    @XmlElement(name = "ZCBSN", required = true)
    protected String     zcbsn;
    @XmlElement(name = "MATNR", required = true)
    protected String     matnr;
    @XmlElement(name = "MENGE", required = true)
    protected BigDecimal menge;
    @XmlElement(name = "ZLGORT_O", required = true)
    protected String     zlgorto;
    @XmlElement(name = "ZLGORT_I", required = true)
    protected String     zlgorti;

    /**
     * Gets the value of the zlsot property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZLSOT() {
        return zlsot;
    }

    /**
     * Sets the value of the zlsot property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZLSOT(String value) {
        this.zlsot = value;
    }

    /**
     * Gets the value of the zlsoi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZLSOI() {
        return zlsoi;
    }

    /**
     * Sets the value of the zlsoi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZLSOI(String value) {
        this.zlsoi = value;
    }

    /**
     * Gets the value of the zlsin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZLSIN() {
        return zlsin;
    }

    /**
     * Sets the value of the zlsin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZLSIN(String value) {
        this.zlsin = value;
    }

    /**
     * Gets the value of the zlsii property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZLSII() {
        return zlsii;
    }

    /**
     * Sets the value of the zlsii property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZLSII(String value) {
        this.zlsii = value;
    }

    /**
     * Gets the value of the bldat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBLDAT() {
        return bldat;
    }

    /**
     * Sets the value of the bldat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBLDAT(String value) {
        this.bldat = value;
    }

    /**
     * Gets the value of the budat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBUDAT() {
        return budat;
    }

    /**
     * Sets the value of the budat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBUDAT(String value) {
        this.budat = value;
    }

    /**
     * Gets the value of the zcbsn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZCBSN() {
        return zcbsn;
    }

    /**
     * Sets the value of the zcbsn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZCBSN(String value) {
        this.zcbsn = value;
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
     * Gets the value of the zlgorto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZLGORTO() {
        return zlgorto;
    }

    /**
     * Sets the value of the zlgorto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZLGORTO(String value) {
        this.zlgorto = value;
    }

    /**
     * Gets the value of the zlgorti property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZLGORTI() {
        return zlgorti;
    }

    /**
     * Sets the value of the zlgorti property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZLGORTI(String value) {
        this.zlgorti = value;
    }

}
