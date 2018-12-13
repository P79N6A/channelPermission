
package com.haier.stock.eai.getTidan;

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
 *         &lt;element name="ERDAT" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "erdat"
})
@XmlRootElement(name = "GetTidanZWDFromLESToEHAIER")
public class GetTidanZWDFromLESToEHAIER_Type {

    @XmlElement(name = "ERDAT", required = true)
    protected String erdat;

    /**
     * Gets the value of the erdat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getERDAT() {
        return erdat;
    }

    /**
     * Sets the value of the erdat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setERDAT(String value) {
        this.erdat = value;
    }

}
