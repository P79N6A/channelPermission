package com.haier.stock.eai.finance.pushprosale.query;

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
 *         &lt;element name="T_ZSDS0003" type="{http://www.example.org/QueryProSaleHandleFromEhaierSAP/}ZSDS0003" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "tzsds0003" })
@XmlRootElement(name = "QueryProSaleHandleFromEhaierSAPResponse")
public class QueryProSaleHandleFromEhaierSAPResponse {

    @XmlElement(name = "T_ZSDS0003")
    protected List<ZSDS0003> tzsds0003;

    /**
     * Gets the value of the tzsds0003 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tzsds0003 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTZSDS0003().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ZSDS0003 }
     * 
     * 
     */
    public List<ZSDS0003> getTZSDS0003() {
        if (tzsds0003 == null) {
            tzsds0003 = new ArrayList<ZSDS0003>();
        }
        return this.tzsds0003;
    }

}
