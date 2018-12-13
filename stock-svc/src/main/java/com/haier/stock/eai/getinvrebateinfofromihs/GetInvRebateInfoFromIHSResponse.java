package com.haier.stock.eai.getinvrebateinfofromihs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="out" type="{http://www.example.org/GetInvRebateInfoFromIHS/}OutputType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "out" })
@XmlRootElement(name = "GetInvRebateInfoFromIHSResponse")
public class GetInvRebateInfoFromIHSResponse {

    @XmlElement(required = true)
    protected OutputType out;

    /**
     * Gets the value of the out property.
     * 
     * @return
     *     possible object is
     *     {@link OutputType }
     *     
     */
    public OutputType getOut() {
        return out;
    }

    /**
     * Sets the value of the out property.
     * 
     * @param value
     *     allowed object is
     *     {@link OutputType }
     *     
     */
    public void setOut(OutputType value) {
        this.out = value;
    }

}
