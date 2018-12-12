
package com.haier.svc.purchase.queryfrostorderfromehaiertooms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.haier.svc.bean.queryfrostorderfromehaiertooms.InType;


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
 *         &lt;element name="in" type="{http://www.example.org/QueryFrostOrderFromEHAIERToOMS/}inType"/>
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
    "in"
})
@XmlRootElement(name = "QueryFrostOrderFromEHAIERToOMS")
public class QueryFrostOrderFromEHAIERToOMS_Type {

    @XmlElement(required = true)
    protected InType in;

    /**
     * 获取in属性的值。
     * 
     * @return
     *     possible object is
     *     {@link InType }
     *     
     */
    public InType getIn() {
        return in;
    }

    /**
     * 设置in属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link InType }
     *     
     */
    public void setIn(InType value) {
        this.in = value;
    }

}
