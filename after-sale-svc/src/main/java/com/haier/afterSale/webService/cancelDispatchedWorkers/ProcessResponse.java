
package com.haier.afterSale.webService.cancelDispatchedWorkers;

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
 *         &lt;element name="result" type="{http://xmlns.oracle.com/MyFirstSOA/TransOrderCancelFromEHAIERToHP/TransOrderCancelFromEHAIERToHP}outputs"/&gt;
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
    "result"
})
@XmlRootElement(name = "processResponse")
public class ProcessResponse {

    @XmlElement(required = true)
    protected Outputs result;

    /**
     * 获取result属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Outputs }
     *     
     */
    public Outputs getResult() {
        return result;
    }

    /**
     * 设置result属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Outputs }
     *     
     */
    public void setResult(Outputs value) {
        this.result = value;
    }

}
