package com.haier.stock.eai.finance.transcancelorderinfofromehaiersap;

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
 *         &lt;element name="T_ZMMS0104" type="{http://www.example.org/TransCancelOrderInfoFromEhaierSAP/}ZMMS0104" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "tzmms0104" })
@XmlRootElement(name = "TransCancelOrderInfoFromEhaierSAP")
public class TransCancelOrderInfoFromEhaierSAP_Type {

    @XmlElement(name = "T_ZMMS0104")
    protected List<ZMMS0104> tzmms0104;

    /**
     * Gets the value of the tzmms0104 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tzmms0104 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTZMMS0104().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ZMMS0104 }
     * 
     * 
     */
    public List<ZMMS0104> getTZMMS0104() {
        if (tzmms0104 == null) {
            tzmms0104 = new ArrayList<ZMMS0104>();
        }
        return this.tzmms0104;
    }

}
