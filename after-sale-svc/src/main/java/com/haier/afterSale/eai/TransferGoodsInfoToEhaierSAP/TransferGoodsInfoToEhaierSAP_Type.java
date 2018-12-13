
package com.haier.afterSale.eai.TransferGoodsInfoToEhaierSAP;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="T_ZMMS0010" type="{http://www.example.org/TransferGoodsInfoToEhaierSAP/}ZMMS0010" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "tzmms0010"
})
@XmlRootElement(name = "TransferGoodsInfoToEhaierSAP")
public class TransferGoodsInfoToEhaierSAP_Type {

    @XmlElement(name = "T_ZMMS0010")
    protected List<ZMMS0010> tzmms0010;

    /**
     * Gets the value of the tzmms0010 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tzmms0010 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTZMMS0010().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ZMMS0010 }
     * 
     * 
     */
    public List<ZMMS0010> getTZMMS0010() {
        if (tzmms0010 == null) {
            tzmms0010 = new ArrayList<ZMMS0010>();
        }
        return this.tzmms0010;
    }

}
