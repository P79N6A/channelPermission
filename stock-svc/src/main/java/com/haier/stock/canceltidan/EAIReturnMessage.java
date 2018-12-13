
package com.haier.stock.canceltidan;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>EAIReturnMessage complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="EAIReturnMessage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mainTableRecordAccount" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="childTableRecordAccount" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="messageCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="messageDetail" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FIELD1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FIELD2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FIELD3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FIELD4" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FIELD5" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EAIReturnMessage", namespace = "http://www.example.org/CommonType", propOrder = {
    "mainTableRecordAccount",
    "childTableRecordAccount",
    "messageCode",
    "messageDetail",
    "field1",
    "field2",
    "field3",
    "field4",
    "field5"
})
public class EAIReturnMessage {

    protected long mainTableRecordAccount;
    protected long childTableRecordAccount;
    @XmlElement(required = true)
    protected String messageCode;
    @XmlElement(required = true)
    protected String messageDetail;
    @XmlElement(name = "FIELD1", required = true)
    protected String field1;
    @XmlElement(name = "FIELD2", required = true)
    protected String field2;
    @XmlElement(name = "FIELD3", required = true)
    protected String field3;
    @XmlElement(name = "FIELD4", required = true)
    protected String field4;
    @XmlElement(name = "FIELD5", required = true)
    protected String field5;

    /**
     * 获取mainTableRecordAccount属性的值。
     * 
     */
    public long getMainTableRecordAccount() {
        return mainTableRecordAccount;
    }

    /**
     * 设置mainTableRecordAccount属性的值。
     * 
     */
    public void setMainTableRecordAccount(long value) {
        this.mainTableRecordAccount = value;
    }

    /**
     * 获取childTableRecordAccount属性的值。
     * 
     */
    public long getChildTableRecordAccount() {
        return childTableRecordAccount;
    }

    /**
     * 设置childTableRecordAccount属性的值。
     * 
     */
    public void setChildTableRecordAccount(long value) {
        this.childTableRecordAccount = value;
    }

    /**
     * 获取messageCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageCode() {
        return messageCode;
    }

    /**
     * 设置messageCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageCode(String value) {
        this.messageCode = value;
    }

    /**
     * 获取messageDetail属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageDetail() {
        return messageDetail;
    }

    /**
     * 设置messageDetail属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageDetail(String value) {
        this.messageDetail = value;
    }

    /**
     * 获取field1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFIELD1() {
        return field1;
    }

    /**
     * 设置field1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFIELD1(String value) {
        this.field1 = value;
    }

    /**
     * 获取field2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFIELD2() {
        return field2;
    }

    /**
     * 设置field2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFIELD2(String value) {
        this.field2 = value;
    }

    /**
     * 获取field3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFIELD3() {
        return field3;
    }

    /**
     * 设置field3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFIELD3(String value) {
        this.field3 = value;
    }

    /**
     * 获取field4属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFIELD4() {
        return field4;
    }

    /**
     * 设置field4属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFIELD4(String value) {
        this.field4 = value;
    }

    /**
     * 获取field5属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFIELD5() {
        return field5;
    }

    /**
     * 设置field5属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFIELD5(String value) {
        this.field5 = value;
    }

}
