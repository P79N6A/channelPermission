
package com.haier.traderate.services.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>rrsOrder complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="rrsOrder"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://demo.server.webservice.cc.neusoft.com/}baseVo"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="centerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="handlePerson" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="handlePersonArea" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="handleResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="handleTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="orderId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="success" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rrsOrder", propOrder = {
    "centerName",
    "handlePerson",
    "handlePersonArea",
    "handleResult",
    "handleTime",
    "message",
    "orderId",
    "success"
})
public class RrsOrder
    extends BaseVo
{

    protected String centerName;
    protected String handlePerson;
    protected String handlePersonArea;
    protected String handleResult;
    protected String handleTime;
    protected String message;
    protected String orderId;
    protected boolean success;

    /**
     * 获取centerName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCenterName() {
        return centerName;
    }

    /**
     * 设置centerName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCenterName(String value) {
        this.centerName = value;
    }

    /**
     * 获取handlePerson属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandlePerson() {
        return handlePerson;
    }

    /**
     * 设置handlePerson属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandlePerson(String value) {
        this.handlePerson = value;
    }

    /**
     * 获取handlePersonArea属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandlePersonArea() {
        return handlePersonArea;
    }

    /**
     * 设置handlePersonArea属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandlePersonArea(String value) {
        this.handlePersonArea = value;
    }

    /**
     * 获取handleResult属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandleResult() {
        return handleResult;
    }

    /**
     * 设置handleResult属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandleResult(String value) {
        this.handleResult = value;
    }

    /**
     * 获取handleTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandleTime() {
        return handleTime;
    }

    /**
     * 设置handleTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandleTime(String value) {
        this.handleTime = value;
    }

    /**
     * 获取message属性的值。
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
     * 设置message属性的值。
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
     * 获取orderId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置orderId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderId(String value) {
        this.orderId = value;
    }

    /**
     * 获取success属性的值。
     * 
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * 设置success属性的值。
     * 
     */
    public void setSuccess(boolean value) {
        this.success = value;
    }

}
