//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.08.07 at 09:05:54 AM CST 
//

package com.haier.stock.model.itembase;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for output complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="output">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rowset" type="{}rowset" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "output", propOrder = { "rowset" })
public class Output {

    @XmlElement(name = "rowset")
    protected Rowset rowset;

    /**
     * Gets the value of the rowset property.
     * 
     * @return
     *     possible object is
     *     {@link Rowset }
     *     
     */
    public Rowset getRowset() {
        return rowset;
    }

    /**
     * Sets the value of the rowset property.
     * 
     * @param value
     *     allowed object is
     *     {@link Rowset }
     *     
     */
    public void setRowset(Rowset value) {
        this.rowset = value;
    }

}
