
package com.haier.svc.purchase.queryfrostorderfromehaiertooms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.haier.svc.bean.queryfrostorderfromehaiertooms.OutType;


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
 *         &lt;element name="out" type="{http://www.example.org/QueryFrostOrderFromEHAIERToOMS/}outType"/>
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
    "out"
})
@XmlRootElement(name = "QueryFrostOrderFromEHAIERToOMSResponse")
public class QueryFrostOrderFromEHAIERToOMSResponse {

    @XmlElement(required = true)
    protected OutType out;

    /**
     * 获取out属性的值。
     * 
     * @return
     *     possible object is
     *     {@link OutType }
     *     
     */
    public OutType getOut() {
        return out;
    }

    /**
     * 设置out属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link OutType }
     *     
     */
    public void setOut(OutType value) {
        this.out = value;
    }

}
