
package com.haier.order.wsdl.transordernoticefromehaiertohp;

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
 *         &lt;element name="MSCODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MESSAGE" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "mscode",
    "message"
})
@XmlRootElement(name = "processResponse")
public class ProcessResponse {

    @XmlElement(name = "MSCODE", required = true)
    protected String mscode;
    @XmlElement(name = "MESSAGE", required = true)
    protected String message;

    /**
     * 获取mscode属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMSCODE() {
        return mscode;
    }

    /**
     * 设置mscode属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMSCODE(String value) {
        this.mscode = value;
    }

    /**
     * 获取message属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMESSAGE() {
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
    public void setMESSAGE(String value) {
        this.message = value;
    }

}
