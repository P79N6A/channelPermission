package com.haier.svc.purchase.purchasefromgvs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for ZSDS0002 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZSDS0002">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MESSAGE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MESSAGE_V1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MESSAGE_V2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MESSAGE_V3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MESSAGE_V4" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZSDS0002", propOrder = { "type", "id", "number", "message", "messagev1",
                                         "messagev2", "messagev3", "messagev4" })
public class ZSDS0002 {

    @XmlElement(name = "TYPE", required = true)
    protected String type;
    @XmlElement(name = "ID", required = true)
    protected String id;
    @XmlElement(name = "NUMBER", required = true)
    protected String number;
    @XmlElement(name = "MESSAGE", required = true)
    protected String message;
    @XmlElement(name = "MESSAGE_V1", required = true)
    protected String messagev1;
    @XmlElement(name = "MESSAGE_V2", required = true)
    protected String messagev2;
    @XmlElement(name = "MESSAGE_V3", required = true)
    protected String messagev3;
    @XmlElement(name = "MESSAGE_V4", required = true)
    protected String messagev4;

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
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setID(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the number property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMBER() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMBER(String value) {
        this.number = value;
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
     * Gets the value of the messagev1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMESSAGEV1() {
        return messagev1;
    }

    /**
     * Sets the value of the messagev1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMESSAGEV1(String value) {
        this.messagev1 = value;
    }

    /**
     * Gets the value of the messagev2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMESSAGEV2() {
        return messagev2;
    }

    /**
     * Sets the value of the messagev2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMESSAGEV2(String value) {
        this.messagev2 = value;
    }

    /**
     * Gets the value of the messagev3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMESSAGEV3() {
        return messagev3;
    }

    /**
     * Sets the value of the messagev3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMESSAGEV3(String value) {
        this.messagev3 = value;
    }

    /**
     * Gets the value of the messagev4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMESSAGEV4() {
        return messagev4;
    }

    /**
     * Sets the value of the messagev4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMESSAGEV4(String value) {
        this.messagev4 = value;
    }

}
