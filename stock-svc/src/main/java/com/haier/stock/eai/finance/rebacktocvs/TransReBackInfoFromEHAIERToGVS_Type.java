package com.haier.stock.eai.finance.rebacktocvs;

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
 *         &lt;element name="T_ZMMS0008" type="{http://www.example.org/TransReBackInfoFromEHAIERToGVS/}ZMMS0008" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "tzmms0008" })
@XmlRootElement(name = "TransReBackInfoFromEHAIERToGVS")
public class TransReBackInfoFromEHAIERToGVS_Type {

    @XmlElement(name = "T_ZMMS0008")
    protected List<ZMMS0008> tzmms0008;

    /**
     * Gets the value of the tzmms0008 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tzmms0008 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTZMMS0008().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ZMMS0008 }
     * 
     * 
     */
    public List<ZMMS0008> getTZMMS0008() {
        if (tzmms0008 == null) {
            tzmms0008 = new ArrayList<ZMMS0008>();
        }
        return this.tzmms0008;
    }

}
