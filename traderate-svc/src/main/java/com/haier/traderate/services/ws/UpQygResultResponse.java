
package com.haier.traderate.services.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>upQygResultResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="upQygResultResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="return" type="{http://demo.server.webservice.cc.neusoft.com/}qygOrder" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "upQygResultResponse", propOrder = {
    "_return"
})
public class UpQygResultResponse {

    @XmlElement(name = "return")
    protected QygOrder _return;

    /**
     * 获取return属性的值。
     * 
     * @return
     *     possible object is
     *     {@link QygOrder }
     *     
     */
    public QygOrder getReturn() {
        return _return;
    }

    /**
     * 设置return属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link QygOrder }
     *     
     */
    public void setReturn(QygOrder value) {
        this._return = value;
    }

}
