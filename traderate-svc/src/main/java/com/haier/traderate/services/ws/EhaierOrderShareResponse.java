
package com.haier.traderate.services.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ehaierOrderShareResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ehaierOrderShareResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="return" type="{http://demo.server.webservice.cc.neusoft.com/}ehaierOrder" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ehaierOrderShareResponse", propOrder = {
    "_return"
})
public class EhaierOrderShareResponse {

    @XmlElement(name = "return")
    protected EhaierOrder _return;

    /**
     * 获取return属性的值。
     * 
     * @return
     *     possible object is
     *     {@link EhaierOrder }
     *     
     */
    public EhaierOrder getReturn() {
        return _return;
    }

    /**
     * 设置return属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link EhaierOrder }
     *     
     */
    public void setReturn(EhaierOrder value) {
        this._return = value;
    }

}
