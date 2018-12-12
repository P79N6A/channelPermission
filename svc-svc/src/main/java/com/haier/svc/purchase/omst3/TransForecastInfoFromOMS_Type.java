
package com.haier.svc.purchase.omst3;

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
 *         &lt;element name="requestData" type="{http://www.example.org/TransForecastInfoFromOMS/}RequestData"/>
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
    "requestData"
})
@XmlRootElement(name = "TransForecastInfoFromOMS")
public class TransForecastInfoFromOMS_Type {

    @XmlElement(required = true)
    protected RequestData requestData;

    /**
     * 获取requestData属性的值。
     * 
     * @return
     *     possible object is
     *     {@link RequestData }
     *     
     */
    public RequestData getRequestData() {
        return requestData;
    }

    /**
     * 设置requestData属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link RequestData }
     *     
     */
    public void setRequestData(RequestData value) {
        this.requestData = value;
    }

}
