package com.haier.stock.eai.finance.pushreturn;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="T_ZSDS0004" type="{http://www.example.org/PushReturnInfoToGVS/}ZSDS0004" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "tzsds0004" })
@XmlRootElement(name = "PushReturnInfoToGVS")
public class PushReturnInfoToGVS_Type {

    @XmlElement(name = "T_ZSDS0004")
    protected List<ZSDS0004> tzsds0004;

    /**
     * Gets the value of the tzsds0004 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tzsds0004 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTZSDS0004().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ZSDS0004 }
     * 
     * 
     */
    public List<ZSDS0004> getTZSDS0004() {
        if (tzsds0004 == null) {
            tzsds0004 = new ArrayList<ZSDS0004>();
        }
        return this.tzsds0004;
    }

}
