
package com.haier.stock.eai.finance.pushReturnInfoToGVS;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OutType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OutType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EX_SUBRC" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="T_MSG" type="{http://www.example.org/PushReturnInfoToGVS/}T_MSGType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OutType", propOrder = {
    "exsubrc",
    "tmsg"
})
public class OutType {

    @XmlElement(name = "EX_SUBRC")
    protected int exsubrc;
    @XmlElement(name = "T_MSG")
    protected List<TMSGType> tmsg;

    /**
     * Gets the value of the exsubrc property.
     * 
     */
    public int getEXSUBRC() {
        return exsubrc;
    }

    /**
     * Sets the value of the exsubrc property.
     * 
     */
    public void setEXSUBRC(int value) {
        this.exsubrc = value;
    }

    /**
     * Gets the value of the tmsg property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tmsg property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTMSG().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TMSGType }
     * 
     * 
     */
    public List<TMSGType> getTMSG() {
        if (tmsg == null) {
            tmsg = new ArrayList<TMSGType>();
        }
        return this.tmsg;
    }

}
