
package com.haier.stock.eai.finance.plpurchase.push;

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
 *         &lt;element name="T_ZMMS0013" type="{http://www.example.org/Trans3PLReceiveInfoFromEhaierToGVS/}ZMMS0013" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Sysname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "tzmms0013",
    "sysname"
})
@XmlRootElement(name = "Trans3PLReceiveInfoFromEhaierToGVS")
public class Trans3PLReceiveInfoFromEhaierToGVS_Type {

    @XmlElement(name = "T_ZMMS0013")
    protected List<ZMMS0013> tzmms0013;
    @XmlElement(name = "Sysname", required = true)
    protected String sysname;

    /**
     * Gets the value of the tzmms0013 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tzmms0013 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTZMMS0013().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ZMMS0013 }
     * 
     * 
     */
    public List<ZMMS0013> getTZMMS0013() {
        if (tzmms0013 == null) {
            tzmms0013 = new ArrayList<ZMMS0013>();
        }
        return this.tzmms0013;
    }

    /**
     * Gets the value of the sysname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSysname() {
        return sysname;
    }

    /**
     * Sets the value of the sysname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSysname(String value) {
        this.sysname = value;
    }

}
