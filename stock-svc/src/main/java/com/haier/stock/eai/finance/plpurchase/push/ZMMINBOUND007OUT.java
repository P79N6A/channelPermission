
package com.haier.stock.eai.finance.plpurchase.push;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ZMM_INBOUND_007_OUT complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZMM_INBOUND_007_OUT">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="POSNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZLSGI" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MESSAGE" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "ZMM_INBOUND_007_OUT", propOrder = {
    "posnr",
    "zlsgi",
    "type",
    "message",
    "faultDetail"
})
public class ZMMINBOUND007OUT {

    @XmlElement(name = "POSNR", required = true)
    protected String posnr;
    @XmlElement(name = "ZLSGI", required = true)
    protected String zlsgi;
    @XmlElement(name = "TYPE", required = true)
    protected String type;
    @XmlElement(name = "MESSAGE", required = true)
    protected String message;
    @XmlElement(name = "FaultDetail", required = true)
    protected String faultDetail;

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
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTYPE() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTYPE(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMESSAGE() {
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
    public void setMESSAGE(String value) {
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
