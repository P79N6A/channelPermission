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
 *         &lt;element name="T_SELCOND" type="{http://www.example.org/QueryProSaleHandleFromEhaierSAP/}ZSDS0006" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "tselcond" })
@XmlRootElement(name = "QueryProSaleHandleFromEhaierSAP")
public class QueryProSaleHandleFromEhaierSAP_Type {

    @XmlElement(name = "T_SELCOND")
    protected List<ZSDS0006> tselcond;

    /**
     * Gets the value of the tselcond property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tselcond property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTSELCOND().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ZSDS0006 }
     * 
     * 
     */
    public List<ZSDS0006> getTSELCOND() {
        if (tselcond == null) {
            tselcond = new ArrayList<ZSDS0006>();
        }
        return this.tselcond;
    }

}
