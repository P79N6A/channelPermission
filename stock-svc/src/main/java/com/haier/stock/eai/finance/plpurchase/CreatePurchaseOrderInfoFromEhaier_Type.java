package com.haier.stock.eai.finance.plpurchase;

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
 *         &lt;element name="T_ZMMS0006" type="{http://www.example.org/CreatePurchaseOrderInfoFromEhaier/}ZMMS0006" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "tzmms0006" })
@XmlRootElement(name = "CreatePurchaseOrderInfoFromEhaier")
public class CreatePurchaseOrderInfoFromEhaier_Type {

    @XmlElement(name = "T_ZMMS0006")
    protected List<ZMMS0006> tzmms0006;

    /**
     * Gets the value of the tzmms0006 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tzmms0006 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTZMMS0006().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ZMMS0006 }
     * 
     * 
     */
    public List<ZMMS0006> getTZMMS0006() {
        if (tzmms0006 == null) {
            tzmms0006 = new ArrayList<ZMMS0006>();
        }
        return this.tzmms0006;
    }

}
