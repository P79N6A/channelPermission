package com.haier.stock.eai.getbominfofromlestoehaier;

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
 *         &lt;element name="Z_INPUT_PARA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "zinputpara" })
@XmlRootElement(name = "GetBomInfoFromLESToEHAIER")
public class GetBomInfoFromLESToEHAIER_Type {

    @XmlElement(name = "Z_INPUT_PARA")
    protected String zinputpara;

    /**
     * Gets the value of the zinputpara property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZINPUTPARA() {
        return zinputpara;
    }

    /**
     * Sets the value of the zinputpara property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZINPUTPARA(String value) {
        this.zinputpara = value;
    }

}
