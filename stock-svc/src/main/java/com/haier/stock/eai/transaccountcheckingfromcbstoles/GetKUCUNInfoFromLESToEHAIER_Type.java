package com.haier.stock.eai.transaccountcheckingfromcbstoles;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CRK" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DATE_BEGIN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DATE_END" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="KUWEI" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TIME_BEGIN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TIME_END" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "crk", "datebegin", "dateend", "kuwei", "timebegin", "timeend" })
@XmlRootElement(name = "GetKUCUNInfoFromLESToEHAIER")
public class GetKUCUNInfoFromLESToEHAIER_Type {

    @XmlElement(name = "CRK", required = true)
    protected String crk;
    @XmlElement(name = "DATE_BEGIN", required = true)
    protected String datebegin;
    @XmlElement(name = "DATE_END", required = true)
    protected String dateend;
    @XmlElement(name = "KUWEI", required = true)
    protected String kuwei;
    @XmlElement(name = "TIME_BEGIN", required = true)
    protected String timebegin;
    @XmlElement(name = "TIME_END", required = true)
    protected String timeend;

    /**
     * Gets the value of the crk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCRK() {
        return crk;
    }

    /**
     * Sets the value of the crk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCRK(String value) {
        this.crk = value;
    }

    /**
     * Gets the value of the datebegin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDATEBEGIN() {
        return datebegin;
    }

    /**
     * Sets the value of the datebegin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDATEBEGIN(String value) {
        this.datebegin = value;
    }

    /**
     * Gets the value of the dateend property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDATEEND() {
        return dateend;
    }

    /**
     * Sets the value of the dateend property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDATEEND(String value) {
        this.dateend = value;
    }

    /**
     * Gets the value of the kuwei property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKUWEI() {
        return kuwei;
    }

    /**
     * Sets the value of the kuwei property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKUWEI(String value) {
        this.kuwei = value;
    }

    /**
     * Gets the value of the timebegin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIMEBEGIN() {
        return timebegin;
    }

    /**
     * Sets the value of the timebegin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIMEBEGIN(String value) {
        this.timebegin = value;
    }

    /**
     * Gets the value of the timeend property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIMEEND() {
        return timeend;
    }

    /**
     * Sets the value of the timeend property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIMEEND(String value) {
        this.timeend = value;
    }

}
