
package com.haier.traderate.services.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>qygOrder complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="qygOrder"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://demo.server.webservice.cc.neusoft.com/}baseVo"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="detailTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pre1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pre2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pre3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pre4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pre5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="success" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="totalMoney" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="woId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="woStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "qygOrder", propOrder = {
    "detailTime",
    "message",
    "pre1",
    "pre2",
    "pre3",
    "pre4",
    "pre5",
    "success",
    "totalMoney",
    "woId",
    "woStatus"
})
public class QygOrder
    extends BaseVo
{

    protected String detailTime;
    protected String message;
    protected String pre1;
    protected String pre2;
    protected String pre3;
    protected String pre4;
    protected String pre5;
    protected boolean success;
    protected String totalMoney;
    protected String woId;
    protected String woStatus;

    /**
     * 获取detailTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetailTime() {
        return detailTime;
    }

    /**
     * 设置detailTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetailTime(String value) {
        this.detailTime = value;
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
     * 获取pre1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPre1() {
        return pre1;
    }

    /**
     * 设置pre1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPre1(String value) {
        this.pre1 = value;
    }

    /**
     * 获取pre2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPre2() {
        return pre2;
    }

    /**
     * 设置pre2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPre2(String value) {
        this.pre2 = value;
    }

    /**
     * 获取pre3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPre3() {
        return pre3;
    }

    /**
     * 设置pre3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPre3(String value) {
        this.pre3 = value;
    }

    /**
     * 获取pre4属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPre4() {
        return pre4;
    }

    /**
     * 设置pre4属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPre4(String value) {
        this.pre4 = value;
    }

    /**
     * 获取pre5属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPre5() {
        return pre5;
    }

    /**
     * 设置pre5属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPre5(String value) {
        this.pre5 = value;
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

    /**
     * 获取totalMoney属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalMoney() {
        return totalMoney;
    }

    /**
     * 设置totalMoney属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalMoney(String value) {
        this.totalMoney = value;
    }

    /**
     * 获取woId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWoId() {
        return woId;
    }

    /**
     * 设置woId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWoId(String value) {
        this.woId = value;
    }

    /**
     * 获取woStatus属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWoStatus() {
        return woStatus;
    }

    /**
     * 设置woStatus属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWoStatus(String value) {
        this.woStatus = value;
    }

}
