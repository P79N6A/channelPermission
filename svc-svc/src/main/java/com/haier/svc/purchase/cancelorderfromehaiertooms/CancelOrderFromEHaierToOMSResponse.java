
package com.haier.svc.purchase.cancelorderfromehaiertooms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 *
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestDataReturn" type="{http://www.example.org/CancelOrderFromEHaierToOMS/}ResponseData"/>
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
    "requestDataReturn"
})
@XmlRootElement(name = "CancelOrderFromEHaierToOMSResponse")
public class CancelOrderFromEHaierToOMSResponse {

    @XmlElement(required = true)
    protected ResponseData requestDataReturn;

    /**
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
