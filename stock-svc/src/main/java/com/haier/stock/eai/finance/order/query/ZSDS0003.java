package com.haier.stock.eai.finance.order.query;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for ZSDS0003 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZSDS0003">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ZSYST" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZWBDR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZSTTS" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "ZSDS0003", propOrder = { "zsyst", "zwbdr", "zstts", "ztype", "zmsg" })
public class ZSDS0003 {

    @XmlElement(name = "ZSYST", required = true)
    protected String zsyst;
    @XmlElement(name = "ZWBDR", required = true)
    protected String zwbdr;
    @XmlElement(name = "ZSTTS", required = true)
    protected String zstts;
    @XmlElement(name = "ZTYPE", required = true)
    protected String ztype;
    @XmlElement(name = "ZMSG", required = true)
    protected String zmsg;

    /**
     * Gets the value of the zsyst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZSYST() {
        return zsyst;
    }

    /**
     * Sets the value of the zsyst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZSYST(String value) {
        this.zsyst = value;
    }

    /**
     * Gets the value of the zwbdr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZWBDR() {
        return zwbdr;
    }

    /**
     * Sets the value of the zwbdr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZWBDR(String value) {
        this.zwbdr = value;
    }

    /**
     * Gets the value of the zstts property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZSTTS() {
        return zstts;
    }

    /**
     * Sets the value of the zstts property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZSTTS(String value) {
        this.zstts = value;
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
