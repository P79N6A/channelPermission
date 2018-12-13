
package com.haier.stock.eai.createpurchaseordertoles;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OutputType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OutputType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BSTKD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="POSEX" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VBELN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="POSNR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FLAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MESSAGE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OutputType", propOrder = {
    "bstkd",
    "posex",
    "vbeln",
    "posnr",
    "flag",
    "message"
})
public class OutputType {

    @XmlElement(name = "BSTKD")
    protected String bstkd;
    @XmlElement(name = "POSEX")
    protected String posex;
    @XmlElement(name = "VBELN")
    protected String vbeln;
    @XmlElement(name = "POSNR")
    protected String posnr;
    @XmlElement(name = "FLAG")
    protected String flag;
    @XmlElement(name = "MESSAGE")
    protected String message;

    /**
     * Gets the value of the bstkd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBSTKD() {
        return bstkd;
    }

    /**
     * Sets the value of the bstkd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBSTKD(String value) {
        this.bstkd = value;
    }

    /**
     * Gets the value of the posex property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPOSEX() {
        return posex;
    }

    /**
     * Sets the value of the posex property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPOSEX(String value) {
        this.posex = value;
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
     * Gets the value of the flag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLAG() {
        return flag;
    }

    /**
     * Sets the value of the flag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLAG(String value) {
        this.flag = value;
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

}
