
package com.haier.svc.purchase.cancelorderfromehaiertooms;

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
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="requestDataReturn" type="{http://www.example.org/CancelOrderFromEHaierToOMS/}ResponseData"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "requestDataReturn"
})
@XmlRootElement(name = "CancelOrderFromEHaierToOMSResponse")
public class CancelOrderFromEHaierToOMSResponse {

    @XmlElement(required = true)
    protected ResponseData requestDataReturn;

    /**
     * 获取requestDataReturn属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ResponseData }
     *     
     */
    public ResponseData getRequestDataReturn() {
        return requestDataReturn;
    }

    /**
     * 设置requestDataReturn属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseData }
     *     
     */
    public void setRequestDataReturn(ResponseData value) {
        this.requestDataReturn = value;
    }

}
