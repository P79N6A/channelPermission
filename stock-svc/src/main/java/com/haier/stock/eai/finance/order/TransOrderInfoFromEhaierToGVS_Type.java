package com.haier.stock.eai.finance.order;

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
 *         &lt;element name="T_ZSDS0001" type="{http://www.example.org/TransOrderInfoFromEhaierToGVS/}ZSDS0001" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "tzsds0001" })
@XmlRootElement(name = "TransOrderInfoFromEhaierToGVS")
public class TransOrderInfoFromEhaierToGVS_Type {

    @XmlElement(name = "T_ZSDS0001")
    protected List<ZSDS0001> tzsds0001;

    /**
     * Gets the value of the tzsds0001 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tzsds0001 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTZSDS0001().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ZSDS0001 }
     * 
     * 
     */
    public List<ZSDS0001> getTZSDS0001() {
        if (tzsds0001 == null) {
            tzsds0001 = new ArrayList<ZSDS0001>();
        }
        return this.tzsds0001;
    }

}
