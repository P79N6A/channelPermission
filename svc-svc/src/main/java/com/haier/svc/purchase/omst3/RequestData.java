
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
 * &lt;complexType name="RequestData"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="customerCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="customerName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fromSystem" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="omsRole" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="productCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="roleChannel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="t10_num" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t10_num_tm" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t10_num_tm1" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t10_num_tm2" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t10_num_tm3" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t11_num" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t11_num_tm" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t11_num_tm1" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t11_num_tm2" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t11_num_tm3" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t12_num" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t12_num_tm" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t12_num_tm1" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t12_num_tm2" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t12_num_tm3" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t13_num" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t13_num_tm" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t13_num_tm1" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t13_num_tm2" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t13_num_tm3" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t3_num" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t3_num_tm" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t3_num_tm1" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t3_num_tm2" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t3_num_tm3" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t4_num" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t4_num_tm" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t4_num_tm1" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t4_num_tm2" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t4_num_tm3" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t5_num" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t5_num_tm" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t5_num_tm1" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t5_num_tm2" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t5_num_tm3" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t6_num" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t6_num_tm" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t6_num_tm1" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t6_num_tm2" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t6_num_tm3" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t7_num" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t7_num_tm" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t7_num_tm1" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t7_num_tm2" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t7_num_tm3" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t8_num" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t8_num_tm" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t8_num_tm1" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t8_num_tm2" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t8_num_tm3" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t9_num" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t9_num_tm" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t9_num_tm1" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t9_num_tm2" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="t9_num_tm3" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="tradeCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestData", propOrder = {
    "customerCode",
    "customerName",
    "fromSystem",
    "omsRole",
    "productCode",
    "roleChannel",
    "t10Num",
    "t10NumTm",
    "t10NumTm1",
    "t10NumTm2",
    "t10NumTm3",
    "t11Num",
    "t11NumTm",
    "t11NumTm1",
    "t11NumTm2",
    "t11NumTm3",
    "t12Num",
    "t12NumTm",
    "t12NumTm1",
    "t12NumTm2",
    "t12NumTm3",
    "t13Num",
    "t13NumTm",
    "t13NumTm1",
    "t13NumTm2",
    "t13NumTm3",
    "t3Num",
    "t3NumTm",
    "t3NumTm1",
    "t3NumTm2",
    "t3NumTm3",
    "t4Num",
    "t4NumTm",
    "t4NumTm1",
    "t4NumTm2",
    "t4NumTm3",
    "t5Num",
    "t5NumTm",
    "t5NumTm1",
    "t5NumTm2",
    "t5NumTm3",
    "t6Num",
    "t6NumTm",
    "t6NumTm1",
    "t6NumTm2",
    "t6NumTm3",
    "t7Num",
    "t7NumTm",
    "t7NumTm1",
    "t7NumTm2",
    "t7NumTm3",
    "t8Num",
    "t8NumTm",
    "t8NumTm1",
    "t8NumTm2",
    "t8NumTm3",
    "t9Num",
    "t9NumTm",
    "t9NumTm1",
    "t9NumTm2",
    "t9NumTm3",
    "tradeCode"
})
public class RequestData {

    @XmlElement(required = true, nillable = true)
    protected String customerCode;
    @XmlElement(required = true, nillable = true)
    protected String customerName;
    @XmlElement(required = true, nillable = true)
    protected String fromSystem;
    @XmlElement(required = true, nillable = true)
    protected String omsRole;
    @XmlElement(required = true, nillable = true)
    protected String productCode;
    @XmlElement(required = true, nillable = true)
    protected String roleChannel;
    @XmlElement(name = "t10_num", required = true, type = Double.class, nillable = true)
    protected Double t10Num;
    @XmlElement(name = "t10_num_tm", required = true, type = Double.class, nillable = true)
    protected Double t10NumTm;
    @XmlElement(name = "t10_num_tm1", required = true, type = Double.class, nillable = true)
    protected Double t10NumTm1;
    @XmlElement(name = "t10_num_tm2", required = true, type = Double.class, nillable = true)
    protected Double t10NumTm2;
    @XmlElement(name = "t10_num_tm3", required = true, type = Double.class, nillable = true)
    protected Double t10NumTm3;
    @XmlElement(name = "t11_num", required = true, type = Double.class, nillable = true)
    protected Double t11Num;
    @XmlElement(name = "t11_num_tm", required = true, type = Double.class, nillable = true)
    protected Double t11NumTm;
    @XmlElement(name = "t11_num_tm1", required = true, type = Double.class, nillable = true)
    protected Double t11NumTm1;
    @XmlElement(name = "t11_num_tm2", required = true, type = Double.class, nillable = true)
    protected Double t11NumTm2;
    @XmlElement(name = "t11_num_tm3", required = true, type = Double.class, nillable = true)
    protected Double t11NumTm3;
    @XmlElement(name = "t12_num", required = true, type = Double.class, nillable = true)
    protected Double t12Num;
    @XmlElement(name = "t12_num_tm", required = true, type = Double.class, nillable = true)
    protected Double t12NumTm;
    @XmlElement(name = "t12_num_tm1", required = true, type = Double.class, nillable = true)
    protected Double t12NumTm1;
    @XmlElement(name = "t12_num_tm2", required = true, type = Double.class, nillable = true)
    protected Double t12NumTm2;
    @XmlElement(name = "t12_num_tm3", required = true, type = Double.class, nillable = true)
    protected Double t12NumTm3;
    @XmlElement(name = "t13_num", required = true, type = Double.class, nillable = true)
    protected Double t13Num;
    @XmlElement(name = "t13_num_tm", required = true, type = Double.class, nillable = true)
    protected Double t13NumTm;
    @XmlElement(name = "t13_num_tm1", required = true, type = Double.class, nillable = true)
    protected Double t13NumTm1;
    @XmlElement(name = "t13_num_tm2", required = true, type = Double.class, nillable = true)
    protected Double t13NumTm2;
    @XmlElement(name = "t13_num_tm3", required = true, type = Double.class, nillable = true)
    protected Double t13NumTm3;
    @XmlElement(name = "t3_num", required = true, type = Double.class, nillable = true)
    protected Double t3Num;
    @XmlElement(name = "t3_num_tm", required = true, type = Double.class, nillable = true)
    protected Double t3NumTm;
    @XmlElement(name = "t3_num_tm1", required = true, type = Double.class, nillable = true)
    protected Double t3NumTm1;
    @XmlElement(name = "t3_num_tm2", required = true, type = Double.class, nillable = true)
    protected Double t3NumTm2;
    @XmlElement(name = "t3_num_tm3", required = true, type = Double.class, nillable = true)
    protected Double t3NumTm3;
    @XmlElement(name = "t4_num", required = true, type = Double.class, nillable = true)
    protected Double t4Num;
    @XmlElement(name = "t4_num_tm", required = true, type = Double.class, nillable = true)
    protected Double t4NumTm;
    @XmlElement(name = "t4_num_tm1", required = true, type = Double.class, nillable = true)
    protected Double t4NumTm1;
    @XmlElement(name = "t4_num_tm2", required = true, type = Double.class, nillable = true)
    protected Double t4NumTm2;
    @XmlElement(name = "t4_num_tm3", required = true, type = Double.class, nillable = true)
    protected Double t4NumTm3;
    @XmlElement(name = "t5_num", required = true, type = Double.class, nillable = true)
    protected Double t5Num;
    @XmlElement(name = "t5_num_tm", required = true, type = Double.class, nillable = true)
    protected Double t5NumTm;
    @XmlElement(name = "t5_num_tm1", required = true, type = Double.class, nillable = true)
    protected Double t5NumTm1;
    @XmlElement(name = "t5_num_tm2", required = true, type = Double.class, nillable = true)
    protected Double t5NumTm2;
    @XmlElement(name = "t5_num_tm3", required = true, type = Double.class, nillable = true)
    protected Double t5NumTm3;
    @XmlElement(name = "t6_num", required = true, type = Double.class, nillable = true)
    protected Double t6Num;
    @XmlElement(name = "t6_num_tm", required = true, type = Double.class, nillable = true)
    protected Double t6NumTm;
    @XmlElement(name = "t6_num_tm1", required = true, type = Double.class, nillable = true)
    protected Double t6NumTm1;
    @XmlElement(name = "t6_num_tm2", required = true, type = Double.class, nillable = true)
    protected Double t6NumTm2;
    @XmlElement(name = "t6_num_tm3", required = true, type = Double.class, nillable = true)
    protected Double t6NumTm3;
    @XmlElement(name = "t7_num", required = true, type = Double.class, nillable = true)
    protected Double t7Num;
    @XmlElement(name = "t7_num_tm", required = true, type = Double.class, nillable = true)
    protected Double t7NumTm;
    @XmlElement(name = "t7_num_tm1", required = true, type = Double.class, nillable = true)
    protected Double t7NumTm1;
    @XmlElement(name = "t7_num_tm2", required = true, type = Double.class, nillable = true)
    protected Double t7NumTm2;
    @XmlElement(name = "t7_num_tm3", required = true, type = Double.class, nillable = true)
    protected Double t7NumTm3;
    @XmlElement(name = "t8_num", required = true, type = Double.class, nillable = true)
    protected Double t8Num;
    @XmlElement(name = "t8_num_tm", required = true, type = Double.class, nillable = true)
    protected Double t8NumTm;
    @XmlElement(name = "t8_num_tm1", required = true, type = Double.class, nillable = true)
    protected Double t8NumTm1;
    @XmlElement(name = "t8_num_tm2", required = true, type = Double.class, nillable = true)
    protected Double t8NumTm2;
    @XmlElement(name = "t8_num_tm3", required = true, type = Double.class, nillable = true)
    protected Double t8NumTm3;
    @XmlElement(name = "t9_num", required = true, type = Double.class, nillable = true)
    protected Double t9Num;
    @XmlElement(name = "t9_num_tm", required = true, type = Double.class, nillable = true)
    protected Double t9NumTm;
    @XmlElement(name = "t9_num_tm1", required = true, type = Double.class, nillable = true)
    protected Double t9NumTm1;
    @XmlElement(name = "t9_num_tm2", required = true, type = Double.class, nillable = true)
    protected Double t9NumTm2;
    @XmlElement(name = "t9_num_tm3", required = true, type = Double.class, nillable = true)
    protected Double t9NumTm3;
    @XmlElement(required = true, nillable = true)
    protected String tradeCode;

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
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT10Num() {
        return t10Num;
    }

    /**
     * 设置t10Num属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT10Num(Double value) {
        this.t10Num = value;
    }

    /**
     * 获取t10NumTm属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT10NumTm() {
        return t10NumTm;
    }

    /**
     * 设置t10NumTm属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT10NumTm(Double value) {
        this.t10NumTm = value;
    }

    /**
     * 获取t10NumTm1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT10NumTm1() {
        return t10NumTm1;
    }

    /**
     * 设置t10NumTm1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT10NumTm1(Double value) {
        this.t10NumTm1 = value;
    }

    /**
     * 获取t10NumTm2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT10NumTm2() {
        return t10NumTm2;
    }

    /**
     * 设置t10NumTm2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT10NumTm2(Double value) {
        this.t10NumTm2 = value;
    }

    /**
     * 获取t10NumTm3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT10NumTm3() {
        return t10NumTm3;
    }

    /**
     * 设置t10NumTm3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT10NumTm3(Double value) {
        this.t10NumTm3 = value;
    }

    /**
     * 获取t11Num属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT11Num() {
        return t11Num;
    }

    /**
     * 设置t11Num属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT11Num(Double value) {
        this.t11Num = value;
    }

    /**
     * 获取t11NumTm属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT11NumTm() {
        return t11NumTm;
    }

    /**
     * 设置t11NumTm属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT11NumTm(Double value) {
        this.t11NumTm = value;
    }

    /**
     * 获取t11NumTm1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT11NumTm1() {
        return t11NumTm1;
    }

    /**
     * 设置t11NumTm1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT11NumTm1(Double value) {
        this.t11NumTm1 = value;
    }

    /**
     * 获取t11NumTm2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT11NumTm2() {
        return t11NumTm2;
    }

    /**
     * 设置t11NumTm2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT11NumTm2(Double value) {
        this.t11NumTm2 = value;
    }

    /**
     * 获取t11NumTm3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT11NumTm3() {
        return t11NumTm3;
    }

    /**
     * 设置t11NumTm3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT11NumTm3(Double value) {
        this.t11NumTm3 = value;
    }

    /**
     * 获取t12Num属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT12Num() {
        return t12Num;
    }

    /**
     * 设置t12Num属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT12Num(Double value) {
        this.t12Num = value;
    }

    /**
     * 获取t12NumTm属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT12NumTm() {
        return t12NumTm;
    }

    /**
     * 设置t12NumTm属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT12NumTm(Double value) {
        this.t12NumTm = value;
    }

    /**
     * 获取t12NumTm1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT12NumTm1() {
        return t12NumTm1;
    }

    /**
     * 设置t12NumTm1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT12NumTm1(Double value) {
        this.t12NumTm1 = value;
    }

    /**
     * 获取t12NumTm2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT12NumTm2() {
        return t12NumTm2;
    }

    /**
     * 设置t12NumTm2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT12NumTm2(Double value) {
        this.t12NumTm2 = value;
    }

    /**
     * 获取t12NumTm3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT12NumTm3() {
        return t12NumTm3;
    }

    /**
     * 设置t12NumTm3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT12NumTm3(Double value) {
        this.t12NumTm3 = value;
    }

    /**
     * 获取t13Num属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT13Num() {
        return t13Num;
    }

    /**
     * 设置t13Num属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT13Num(Double value) {
        this.t13Num = value;
    }

    /**
     * 获取t13NumTm属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT13NumTm() {
        return t13NumTm;
    }

    /**
     * 设置t13NumTm属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT13NumTm(Double value) {
        this.t13NumTm = value;
    }

    /**
     * 获取t13NumTm1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT13NumTm1() {
        return t13NumTm1;
    }

    /**
     * 设置t13NumTm1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT13NumTm1(Double value) {
        this.t13NumTm1 = value;
    }

    /**
     * 获取t13NumTm2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT13NumTm2() {
        return t13NumTm2;
    }

    /**
     * 设置t13NumTm2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT13NumTm2(Double value) {
        this.t13NumTm2 = value;
    }

    /**
     * 获取t13NumTm3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT13NumTm3() {
        return t13NumTm3;
    }

    /**
     * 设置t13NumTm3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT13NumTm3(Double value) {
        this.t13NumTm3 = value;
    }

    /**
     * 获取t3Num属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT3Num() {
        return t3Num;
    }

    /**
     * 设置t3Num属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT3Num(Double value) {
        this.t3Num = value;
    }

    /**
     * 获取t3NumTm属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT3NumTm() {
        return t3NumTm;
    }

    /**
     * 设置t3NumTm属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT3NumTm(Double value) {
        this.t3NumTm = value;
    }

    /**
     * 获取t3NumTm1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT3NumTm1() {
        return t3NumTm1;
    }

    /**
     * 设置t3NumTm1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT3NumTm1(Double value) {
        this.t3NumTm1 = value;
    }

    /**
     * 获取t3NumTm2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT3NumTm2() {
        return t3NumTm2;
    }

    /**
     * 设置t3NumTm2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT3NumTm2(Double value) {
        this.t3NumTm2 = value;
    }

    /**
     * 获取t3NumTm3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT3NumTm3() {
        return t3NumTm3;
    }

    /**
     * 设置t3NumTm3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT3NumTm3(Double value) {
        this.t3NumTm3 = value;
    }

    /**
     * 获取t4Num属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT4Num() {
        return t4Num;
    }

    /**
     * 设置t4Num属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT4Num(Double value) {
        this.t4Num = value;
    }

    /**
     * 获取t4NumTm属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT4NumTm() {
        return t4NumTm;
    }

    /**
     * 设置t4NumTm属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT4NumTm(Double value) {
        this.t4NumTm = value;
    }

    /**
     * 获取t4NumTm1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT4NumTm1() {
        return t4NumTm1;
    }

    /**
     * 设置t4NumTm1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT4NumTm1(Double value) {
        this.t4NumTm1 = value;
    }

    /**
     * 获取t4NumTm2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT4NumTm2() {
        return t4NumTm2;
    }

    /**
     * 设置t4NumTm2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT4NumTm2(Double value) {
        this.t4NumTm2 = value;
    }

    /**
     * 获取t4NumTm3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT4NumTm3() {
        return t4NumTm3;
    }

    /**
     * 设置t4NumTm3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT4NumTm3(Double value) {
        this.t4NumTm3 = value;
    }

    /**
     * 获取t5Num属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT5Num() {
        return t5Num;
    }

    /**
     * 设置t5Num属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT5Num(Double value) {
        this.t5Num = value;
    }

    /**
     * 获取t5NumTm属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT5NumTm() {
        return t5NumTm;
    }

    /**
     * 设置t5NumTm属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT5NumTm(Double value) {
        this.t5NumTm = value;
    }

    /**
     * 获取t5NumTm1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT5NumTm1() {
        return t5NumTm1;
    }

    /**
     * 设置t5NumTm1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT5NumTm1(Double value) {
        this.t5NumTm1 = value;
    }

    /**
     * 获取t5NumTm2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT5NumTm2() {
        return t5NumTm2;
    }

    /**
     * 设置t5NumTm2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT5NumTm2(Double value) {
        this.t5NumTm2 = value;
    }

    /**
     * 获取t5NumTm3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT5NumTm3() {
        return t5NumTm3;
    }

    /**
     * 设置t5NumTm3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT5NumTm3(Double value) {
        this.t5NumTm3 = value;
    }

    /**
     * 获取t6Num属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT6Num() {
        return t6Num;
    }

    /**
     * 设置t6Num属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT6Num(Double value) {
        this.t6Num = value;
    }

    /**
     * 获取t6NumTm属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT6NumTm() {
        return t6NumTm;
    }

    /**
     * 设置t6NumTm属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT6NumTm(Double value) {
        this.t6NumTm = value;
    }

    /**
     * 获取t6NumTm1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT6NumTm1() {
        return t6NumTm1;
    }

    /**
     * 设置t6NumTm1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT6NumTm1(Double value) {
        this.t6NumTm1 = value;
    }

    /**
     * 获取t6NumTm2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT6NumTm2() {
        return t6NumTm2;
    }

    /**
     * 设置t6NumTm2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT6NumTm2(Double value) {
        this.t6NumTm2 = value;
    }

    /**
     * 获取t6NumTm3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT6NumTm3() {
        return t6NumTm3;
    }

    /**
     * 设置t6NumTm3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT6NumTm3(Double value) {
        this.t6NumTm3 = value;
    }

    /**
     * 获取t7Num属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT7Num() {
        return t7Num;
    }

    /**
     * 设置t7Num属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT7Num(Double value) {
        this.t7Num = value;
    }

    /**
     * 获取t7NumTm属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT7NumTm() {
        return t7NumTm;
    }

    /**
     * 设置t7NumTm属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT7NumTm(Double value) {
        this.t7NumTm = value;
    }

    /**
     * 获取t7NumTm1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT7NumTm1() {
        return t7NumTm1;
    }

    /**
     * 设置t7NumTm1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT7NumTm1(Double value) {
        this.t7NumTm1 = value;
    }

    /**
     * 获取t7NumTm2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT7NumTm2() {
        return t7NumTm2;
    }

    /**
     * 设置t7NumTm2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT7NumTm2(Double value) {
        this.t7NumTm2 = value;
    }

    /**
     * 获取t7NumTm3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT7NumTm3() {
        return t7NumTm3;
    }

    /**
     * 设置t7NumTm3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT7NumTm3(Double value) {
        this.t7NumTm3 = value;
    }

    /**
     * 获取t8Num属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT8Num() {
        return t8Num;
    }

    /**
     * 设置t8Num属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT8Num(Double value) {
        this.t8Num = value;
    }

    /**
     * 获取t8NumTm属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT8NumTm() {
        return t8NumTm;
    }

    /**
     * 设置t8NumTm属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT8NumTm(Double value) {
        this.t8NumTm = value;
    }

    /**
     * 获取t8NumTm1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT8NumTm1() {
        return t8NumTm1;
    }

    /**
     * 设置t8NumTm1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT8NumTm1(Double value) {
        this.t8NumTm1 = value;
    }

    /**
     * 获取t8NumTm2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT8NumTm2() {
        return t8NumTm2;
    }

    /**
     * 设置t8NumTm2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT8NumTm2(Double value) {
        this.t8NumTm2 = value;
    }

    /**
     * 获取t8NumTm3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT8NumTm3() {
        return t8NumTm3;
    }

    /**
     * 设置t8NumTm3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT8NumTm3(Double value) {
        this.t8NumTm3 = value;
    }

    /**
     * 获取t9Num属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT9Num() {
        return t9Num;
    }

    /**
     * 设置t9Num属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT9Num(Double value) {
        this.t9Num = value;
    }

    /**
     * 获取t9NumTm属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT9NumTm() {
        return t9NumTm;
    }

    /**
     * 设置t9NumTm属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT9NumTm(Double value) {
        this.t9NumTm = value;
    }

    /**
     * 获取t9NumTm1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT9NumTm1() {
        return t9NumTm1;
    }

    /**
     * 设置t9NumTm1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT9NumTm1(Double value) {
        this.t9NumTm1 = value;
    }

    /**
     * 获取t9NumTm2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT9NumTm2() {
        return t9NumTm2;
    }

    /**
     * 设置t9NumTm2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT9NumTm2(Double value) {
        this.t9NumTm2 = value;
    }

    /**
     * 获取t9NumTm3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getT9NumTm3() {
        return t9NumTm3;
    }

    /**
     * 设置t9NumTm3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setT9NumTm3(Double value) {
        this.t9NumTm3 = value;
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
