
package com.haier.svc.purchase.omst3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>RequestData complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="RequestData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sysName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="customerCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="customerName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fromSystem" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="omsRole" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="productCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="roleChannel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="t10_num" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="t11_num" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="t12_num" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="t13_num" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="t3_num" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="t4_num" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="t5_num" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="t6_num" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="t7_num" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="t8_num" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="t9_num" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="tradeCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestData", propOrder = {
    "sysName",
    "customerCode",
    "customerName",
    "fromSystem",
    "omsRole",
    "productCode",
    "roleChannel",
    "t10Num",
    "t11Num",
    "t12Num",
    "t13Num",
    "t3Num",
    "t4Num",
    "t5Num",
    "t6Num",
    "t7Num",
    "t8Num",
    "t9Num",
    "tradeCode"
})
public class RequestData {

    @XmlElement(required = true)
    protected String sysName;
    @XmlElement(required = true)
    protected String customerCode;
    @XmlElement(required = true)
    protected String customerName;
    @XmlElement(required = true)
    protected String fromSystem;
    @XmlElement(required = true)
    protected String omsRole;
    @XmlElement(required = true)
    protected String productCode;
    @XmlElement(required = true)
    protected String roleChannel;
    @XmlElement(name = "t10_num")
    protected double t10Num;
    @XmlElement(name = "t11_num")
    protected double t11Num;
    @XmlElement(name = "t12_num")
    protected double t12Num;
    @XmlElement(name = "t13_num")
    protected double t13Num;
    @XmlElement(name = "t3_num")
    protected double t3Num;
    @XmlElement(name = "t4_num")
    protected double t4Num;
    @XmlElement(name = "t5_num")
    protected double t5Num;
    @XmlElement(name = "t6_num")
    protected double t6Num;
    @XmlElement(name = "t7_num")
    protected double t7Num;
    @XmlElement(name = "t8_num")
    protected double t8Num;
    @XmlElement(name = "t9_num")
    protected double t9Num;
    @XmlElement(required = true)
    protected String tradeCode;

    /**
     * 获取sysName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSysName() {
        return sysName;
    }

    /**
     * 设置sysName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSysName(String value) {
        this.sysName = value;
    }

    /**
     * 获取customerCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerCode() {
        return customerCode;
    }

    /**
     * 设置customerCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerCode(String value) {
        this.customerCode = value;
    }

    /**
     * 获取customerName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * 设置customerName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerName(String value) {
        this.customerName = value;
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
     * 获取omsRole属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOmsRole() {
        return omsRole;
    }

    /**
     * 设置omsRole属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOmsRole(String value) {
        this.omsRole = value;
    }

    /**
     * 获取productCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * 设置productCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductCode(String value) {
        this.productCode = value;
    }

    /**
     * 获取roleChannel属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoleChannel() {
        return roleChannel;
    }

    /**
     * 设置roleChannel属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoleChannel(String value) {
        this.roleChannel = value;
    }

    /**
     * 获取t10Num属性的值。
     * 
     */
    public double getT10Num() {
        return t10Num;
    }

    /**
     * 设置t10Num属性的值。
     * 
     */
    public void setT10Num(double value) {
        this.t10Num = value;
    }

    /**
     * 获取t11Num属性的值。
     * 
     */
    public double getT11Num() {
        return t11Num;
    }

    /**
     * 设置t11Num属性的值。
     * 
     */
    public void setT11Num(double value) {
        this.t11Num = value;
    }

    /**
     * 获取t12Num属性的值。
     * 
     */
    public double getT12Num() {
        return t12Num;
    }

    /**
     * 设置t12Num属性的值。
     * 
     */
    public void setT12Num(double value) {
        this.t12Num = value;
    }

    /**
     * 获取t13Num属性的值。
     * 
     */
    public double getT13Num() {
        return t13Num;
    }

    /**
     * 设置t13Num属性的值。
     * 
     */
    public void setT13Num(double value) {
        this.t13Num = value;
    }

    /**
     * 获取t3Num属性的值。
     * 
     */
    public double getT3Num() {
        return t3Num;
    }

    /**
     * 设置t3Num属性的值。
     * 
     */
    public void setT3Num(double value) {
        this.t3Num = value;
    }

    /**
     * 获取t4Num属性的值。
     * 
     */
    public double getT4Num() {
        return t4Num;
    }

    /**
     * 设置t4Num属性的值。
     * 
     */
    public void setT4Num(double value) {
        this.t4Num = value;
    }

    /**
     * 获取t5Num属性的值。
     * 
     */
    public double getT5Num() {
        return t5Num;
    }

    /**
     * 设置t5Num属性的值。
     * 
     */
    public void setT5Num(double value) {
        this.t5Num = value;
    }

    /**
     * 获取t6Num属性的值。
     * 
     */
    public double getT6Num() {
        return t6Num;
    }

    /**
     * 设置t6Num属性的值。
     * 
     */
    public void setT6Num(double value) {
        this.t6Num = value;
    }

    /**
     * 获取t7Num属性的值。
     * 
     */
    public double getT7Num() {
        return t7Num;
    }

    /**
     * 设置t7Num属性的值。
     * 
     */
    public void setT7Num(double value) {
        this.t7Num = value;
    }

    /**
     * 获取t8Num属性的值。
     * 
     */
    public double getT8Num() {
        return t8Num;
    }

    /**
     * 设置t8Num属性的值。
     * 
     */
    public void setT8Num(double value) {
        this.t8Num = value;
    }

    /**
     * 获取t9Num属性的值。
     * 
     */
    public double getT9Num() {
        return t9Num;
    }

    /**
     * 设置t9Num属性的值。
     * 
     */
    public void setT9Num(double value) {
        this.t9Num = value;
    }

    /**
     * 获取tradeCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTradeCode() {
        return tradeCode;
    }

    /**
     * 设置tradeCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTradeCode(String value) {
        this.tradeCode = value;
    }

}
