package com.haier.stock.model.selectmdminfofrommdmbyconditon;

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
 *         &lt;element name="Inputs" type="{http://www.example.org/SelectMDMInfoFromMDMByConditon/}InputsType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "inputs" })
@XmlRootElement(name = "SelectMDMInfoFromMDMByConditon_OP")
public class SelectMDMInfoFromMDMByConditonOP {

    @XmlElement(name = "Inputs", required = true)
    protected InputsType inputs;

    /**
     * Gets the value of the inputs property.
     * 
     * @return
     *     possible object is
     *     {@link InputsType }
     *     
     */
    public InputsType getInputs() {
        return inputs;
    }

    /**
     * Sets the value of the inputs property.
     * 
     * @param value
     *     allowed object is
     *     {@link InputsType }
     *     
     */
    public void setInputs(InputsType value) {
        this.inputs = value;
    }

}
