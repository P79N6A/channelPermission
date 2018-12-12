
package com.haier.svc.bean.transforecastpracticalfromb2ctooms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="customerCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="customerName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fromSystem" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="omsRole" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="roleChannel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tradeCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="itemcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="itemname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "customerCode",
    "customerName",
    "fromSystem",
    "omsRole",
    "roleChannel",
    "tradeCode",
    "itemcode",
    "itemname"
})
@XmlRootElement(name = "TransForecastPracticalFromB2CToOMS")
public class TransForecastPracticalFromB2CToOMS_Type {

    @XmlElement(required = true)
    protected String customerCode;
    @XmlElement(required = true)
    protected String customerName;
    @XmlElement(required = true)
    protected String fromSystem;
    @XmlElement(required = true)
    protected String omsRole;
    @XmlElement(required = true)
    protected String roleChannel;
    @XmlElement(required = true)
    protected String tradeCode;
    @XmlElement(required = true)
    protected String itemcode;
    @XmlElement(required = true)
    protected String itemname;

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

    /**
     * 获取itemcode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemcode() {
        return itemcode;
    }

    /**
     * 设置itemcode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemcode(String value) {
        this.itemcode = value;
    }

    /**
     * 获取itemname属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemname() {
        return itemname;
    }

    /**
     * 设置itemname属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemname(String value) {
        this.itemname = value;
    }

}
