
package com.haier.traderate.services.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>rrsResultResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="rrsResultResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="return" type="{http://demo.server.webservice.cc.neusoft.com/}rrsOrder" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rrsResultResponse", propOrder = {
    "_return"
})
public class RrsResultResponse {

    @XmlElement(name = "return")
    protected RrsOrder _return;

    /**
     * 获取return属性的值。
     * 
     * @return
     *     possible object is
     *     {@link RrsOrder }
     *     
     */
    public RrsOrder getReturn() {
        return _return;
    }

    /**
     * 设置return属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link RrsOrder }
     *     
     */
    public void setReturn(RrsOrder value) {
        this._return = value;
    }

}
