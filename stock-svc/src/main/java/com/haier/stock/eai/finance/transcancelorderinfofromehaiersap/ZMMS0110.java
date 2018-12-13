package com.haier.stock.eai.finance.transcancelorderinfofromehaiersap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for ZMMS0110 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZMMS0110">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ZOUNB" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZOUIT" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "ZMMS0110", propOrder = { "zounb", "zouit", "zlsgi", "ztype", "zmsg" })
public class ZMMS0110 {

    @XmlElement(name = "ZOUNB", required = true)
    protected String zounb;
    @XmlElement(name = "ZOUIT", required = true)
    protected String zouit;
    @XmlElement(name = "ZLSGI", required = true)
    protected String zlsgi;
    @XmlElement(name = "ZTYPE", required = true)
    protected String ztype;
    @XmlElement(name = "ZMSG", required = true)
    protected String zmsg;

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
