
package com.haier.svc.purchase.cancelorderfromehaiertooms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 *
 *
 * <pre>
 * &lt;complexType name="ResponseData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="field1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="field2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="field3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="field4" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="field5" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="titleSoCodeId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fault" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseData", propOrder = {
    "field1",
    "field2",
    "field3",
    "field4",
    "field5",
    "message",
    "status",
    "titleSoCodeId",
    "flag",
    "fault"
})
public class ResponseData {

    @XmlElement(required = true)
    protected String field1;
    @XmlElement(required = true)
    protected String field2;
    @XmlElement(required = true)
    protected String field3;
    @XmlElement(required = true)
    protected String field4;
    @XmlElement(required = true)
    protected String field5;
    @XmlElement(required = true)
    protected String message;
    @XmlElement(required = true)
    protected String status;
    @XmlElement(required = true)
    protected String titleSoCodeId;
    @XmlElement(required = true)
    protected String flag;
    @XmlElement(required = true)
    protected String fault;

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getField1() {
        return field1;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setField1(String value) {
        this.field1 = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getField2() {
        return field2;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setField2(String value) {
        this.field2 = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getField3() {
        return field3;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setField3(String value) {
        this.field3 = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getField4() {
        return field4;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setField4(String value) {
        this.field4 = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getField5() {
        return field5;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setField5(String value) {
        this.field5 = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitleSoCodeId() {
        return titleSoCodeId;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitleSoCodeId(String value) {
        this.titleSoCodeId = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlag() {
        return flag;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlag(String value) {
        this.flag = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFault() {
        return fault;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFault(String value) {
        this.fault = value;
    }

}
