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
 *         &lt;element name="T_ZMMS0110" type="{http://www.example.org/TransCancelOrderInfoFromEhaierSAP/}ZMMS0110" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "tzmms0110" })
@XmlRootElement(name = "TransCancelOrderInfoFromEhaierSAPResponse")
public class TransCancelOrderInfoFromEhaierSAPResponse {

    @XmlElement(name = "T_ZMMS0110")
    protected List<ZMMS0110> tzmms0110;

    /**
     * Gets the value of the tzmms0110 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tzmms0110 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTZMMS0110().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ZMMS0110 }
     * 
     * 
     */
    public List<ZMMS0110> getTZMMS0110() {
        if (tzmms0110 == null) {
            tzmms0110 = new ArrayList<ZMMS0110>();
        }
        return this.tzmms0110;
    }

}
