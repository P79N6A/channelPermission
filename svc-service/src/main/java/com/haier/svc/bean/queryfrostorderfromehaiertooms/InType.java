
package com.haier.svc.bean.queryfrostorderfromehaiertooms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>inType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="inType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="orderCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="detailCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fromSystem" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="operationFlage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cancelReason" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fild1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fild2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fild3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fild4" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fild5" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inType", propOrder = {
    "orderCode",
    "detailCode",
    "fromSystem",
    "operationFlage",
    "cancelReason",
    "fild1",
    "fild2",
    "fild3",
    "fild4",
    "fild5"
})
public class InType {

    @XmlElement(required = true)
    protected String orderCode;
    @XmlElement(required = true)
    protected String detailCode;
    @XmlElement(required = true)
    protected String fromSystem;
    @XmlElement(required = true)
    protected String operationFlage;
    @XmlElement(required = true)
    protected String cancelReason;
    @XmlElement(required = true)
    protected String fild1;
    @XmlElement(required = true)
    protected String fild2;
    @XmlElement(required = true)
    protected String fild3;
    @XmlElement(required = true)
    protected String fild4;
    @XmlElement(required = true)
    protected String fild5;

    /**
     * 获取orderCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderCode() {
        return orderCode;
    }

    /**
     * 设置orderCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderCode(String value) {
        this.orderCode = value;
    }

    /**
     * 获取detailCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetailCode() {
        return detailCode;
    }

    /**
     * 设置detailCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetailCode(String value) {
        this.detailCode = value;
    }

    /**
     * 获取fromSystem属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromSystem() {
        return fromSystem;
    }

    /**
     * 设置fromSystem属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromSystem(String value) {
        this.fromSystem = value;
    }

    /**
     * 获取operationFlage属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationFlage() {
        return operationFlage;
    }

    /**
     * 设置operationFlage属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationFlage(String value) {
        this.operationFlage = value;
    }

    /**
     * 获取cancelReason属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCancelReason() {
        return cancelReason;
    }

    /**
     * 设置cancelReason属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCancelReason(String value) {
        this.cancelReason = value;
    }

    /**
     * 获取fild1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFild1() {
        return fild1;
    }

    /**
     * 设置fild1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFild1(String value) {
        this.fild1 = value;
    }

    /**
     * 获取fild2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFild2() {
        return fild2;
    }

    /**
     * 设置fild2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFild2(String value) {
        this.fild2 = value;
    }

    /**
     * 获取fild3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFild3() {
        return fild3;
    }

    /**
     * 设置fild3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFild3(String value) {
        this.fild3 = value;
    }

    /**
     * 获取fild4属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFild4() {
        return fild4;
    }

    /**
     * 设置fild4属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFild4(String value) {
        this.fild4 = value;
    }

    /**
     * 获取fild5属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFild5() {
        return fild5;
    }

    /**
     * 设置fild5属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFild5(String value) {
        this.fild5 = value;
    }

}
