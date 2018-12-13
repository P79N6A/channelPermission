package com.haier.stock.eai.finance.postaccounttoehaiersap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for ZMMS0108 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZMMS0108">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ZSPNB" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZSPIT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZLSGI" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZTYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZMSG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZMMS0108", propOrder = { "zspnb", "zspit", "zlsgi", "ztype", "zmsg" })
public class ZMMS0108 {

    @XmlElement(name = "ZSPNB", required = true)
    protected String zspnb;
    @XmlElement(name = "ZSPIT", required = true)
    protected String zspit;
    @XmlElement(name = "ZLSGI", required = true)
    protected String zlsgi;
    @XmlElement(name = "ZTYPE", required = true)
    protected String ztype;
    @XmlElement(name = "ZMSG", required = true)
    protected String zmsg;

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
     * Gets the value of the zspit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZSPIT() {
        return zspit;
    }

    /**
     * Sets the value of the zspit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZSPIT(String value) {
        this.zspit = value;
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
     * Gets the value of the ztype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZTYPE() {
        return ztype;
    }

    /**
     * Sets the value of the ztype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZTYPE(String value) {
        this.ztype = value;
    }

    /**
     * Gets the value of the zmsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZMSG() {
        return zmsg;
    }

    /**
     * Sets the value of the zmsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZMSG(String value) {
        this.zmsg = value;
    }

}
