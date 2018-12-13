package com.haier.svc.purchase.selectmdminfofrommdmbyconditon;

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
 *         &lt;element name="Outputs" type="{http://www.example.org/SelectMDMInfoFromMDMByConditon/}OutputsType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "outputs" })
@XmlRootElement(name = "SelectMDMInfoFromMDMByConditon_OPResponse")
public class SelectMDMInfoFromMDMByConditonOPResponse {

    @XmlElement(name = "Outputs", required = true)
    protected OutputsType outputs;

    /**
     * Gets the value of the outputs property.
     * 
     * @return
     *     possible object is
     *     {@link OutputsType }
     *     
     */
    public OutputsType getOutputs() {
        return outputs;
    }

    /**
     * Sets the value of the outputs property.
     * 
     * @param value
     *     allowed object is
     *     {@link OutputsType }
     *     
     */
    public void setOutputs(OutputsType value) {
        this.outputs = value;
    }

}
