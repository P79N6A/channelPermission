
package com.haier.order.wsdl.transordernoticefromehaiertohp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>inputType complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="inputType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ORDER_NO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TAIL_SECTION_NO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TAIL_SECTION_DATE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CREATED_DATE" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="USER_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ReserverDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inputType", propOrder = {
    "orderno",
    "tailsectionno",
    "tailsectiondate",
    "createddate",
    "usercode",
    "reserverDate"
})
public class InputType {

    @XmlElement(name = "ORDER_NO", required = true)
    protected String orderno;
    @XmlElement(name = "TAIL_SECTION_NO", required = true)
    protected String tailsectionno;
    @XmlElement(name = "TAIL_SECTION_DATE", required = true)
    protected String tailsectiondate;
    @XmlElement(name = "CREATED_DATE", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar createddate;
    @XmlElement(name = "USER_CODE", required = true)
    protected String usercode;
    @XmlElement(name = "ReserverDate", required = true)
    protected String reserverDate;

    /**
     * 获取orderno属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getORDERNO() {
        return orderno;
    }

    /**
     * 设置orderno属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setORDERNO(String value) {
        this.orderno = value;
    }

    /**
     * 获取tailsectionno属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTAILSECTIONNO() {
        return tailsectionno;
    }

    /**
     * 设置tailsectionno属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTAILSECTIONNO(String value) {
        this.tailsectionno = value;
    }

    /**
     * 获取tailsectiondate属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTAILSECTIONDATE() {
        return tailsectiondate;
    }

    /**
     * 设置tailsectiondate属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTAILSECTIONDATE(String value) {
        this.tailsectiondate = value;
    }

    /**
     * 获取createddate属性的值。
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getCREATEDDATE() {
        return createddate;
    }

    /**
     * 设置createddate属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setCREATEDDATE(XMLGregorianCalendar value) {
        this.createddate = value;
    }

    /**
     * 获取usercode属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getUSERCODE() {
        return usercode;
    }

    /**
     * 设置usercode属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setUSERCODE(String value) {
        this.usercode = value;
    }

    /**
     * 获取reserverDate属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getReserverDate() {
        return reserverDate;
    }

    /**
     * 设置reserverDate属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setReserverDate(String value) {
        this.reserverDate = value;
    }

}
