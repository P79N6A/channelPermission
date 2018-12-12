
package com.haier.svc.purchase.omsquery;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>RequestData complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="RequestData"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SysName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="field1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="field2" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="field3" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="field4" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="field5" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="orderSoCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "sysName",
    "field1",
    "field2",
    "field3",
    "field4",
    "field5",
    "orderSoCode"
})
public class RequestData {

    @XmlElement(name = "SysName", required = true)
    protected String sysName;
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
    protected String orderSoCode;

    /**
     * ��ȡsysName���Ե�ֵ��
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
     * ����sysName���Ե�ֵ��
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
     * ��ȡfield1���Ե�ֵ��
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
     * ����field1���Ե�ֵ��
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
     * ��ȡfield2���Ե�ֵ��
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
     * ����field2���Ե�ֵ��
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
     * ��ȡfield3���Ե�ֵ��
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
     * ����field3���Ե�ֵ��
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
     * ��ȡfield4���Ե�ֵ��
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
     * ����field4���Ե�ֵ��
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
     * ��ȡfield5���Ե�ֵ��
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
     * ����field5���Ե�ֵ��
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
     * ��ȡorderSoCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderSoCode() {
        return orderSoCode;
    }

    /**
     * ����orderSoCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderSoCode(String value) {
        this.orderSoCode = value;
    }

}
