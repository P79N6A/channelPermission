package com.haier.svc.purchase.querywaorderbillfromihs;

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
 *         &lt;element name="VW_WAOrderBillYTJOutput" type="{http://www.example.org/QueryWAOrderBillFromIHS/}VW_WAOrderBillYTJOutput" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "vwwaOrderBillYTJOutput" })
@XmlRootElement(name = "QueryWAOrderBillFromIHSResponse")
public class QueryWAOrderBillFromIHSResponse {

    @XmlElement(name = "VW_WAOrderBillYTJOutput")
    protected List<VWWAOrderBillYTJOutput> vwwaOrderBillYTJOutput;

    /**
     * Gets the value of the vwwaOrderBillYTJOutput property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vwwaOrderBillYTJOutput property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVWWAOrderBillYTJOutput().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VWWAOrderBillYTJOutput }
     * 
     * 
     */
    public List<VWWAOrderBillYTJOutput> getVWWAOrderBillYTJOutput() {
        if (vwwaOrderBillYTJOutput == null) {
            vwwaOrderBillYTJOutput = new ArrayList<VWWAOrderBillYTJOutput>();
        }
        return this.vwwaOrderBillYTJOutput;
    }

}
