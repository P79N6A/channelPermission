
package com.haier.stock.canceltidan;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>EAIInputMessage complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="EAIInputMessage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="target" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mainTableRecordAccount" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="childTableRecordAccount" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
@XmlType(name = "EAIInputMessage", namespace = "http://www.example.org/CommonType", propOrder = {
    "target",
    "mainTableRecordAccount",
    "childTableRecordAccount",
    "field1",
    "field2",
    "field3",
    "field4",
    "field5"
})
public class EAIInputMessage {

    @XmlElement(required = true)
    protected String target;
    protected long mainTableRecordAccount;
    protected long childTableRecordAccount;
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
     * 获取target属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTarget() {
        return target;
    }

    /**
     * 设置target属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTarget(String value) {
        this.target = value;
    }

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
