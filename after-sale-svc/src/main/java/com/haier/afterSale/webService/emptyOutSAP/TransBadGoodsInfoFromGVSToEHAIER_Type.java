
package com.haier.afterSale.webService.emptyOutSAP;

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
 *         &lt;element name="SYSNAME" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="T_ZMMS0008" type="{http://www.example.org/TransBadGoodsInfoFromGVSToEHAIER/}ZMM_INBOUND_010_IN" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "sysname",
    "tzmms0008"
})
@XmlRootElement(name = "TransBadGoodsInfoFromGVSToEHAIER")
public class TransBadGoodsInfoFromGVSToEHAIER_Type {

    @XmlElement(name = "SYSNAME", required = true)
    protected String sysname;
    @XmlElement(name = "T_ZMMS0008")
    protected List<ZMMINBOUND010IN> tzmms0008;

    /**
     * 获取sysname属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSYSNAME() {
        return sysname;
    }

    /**
     * 设置sysname属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSYSNAME(String value) {
        this.sysname = value;
    }

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
     * {@link ZMMINBOUND010IN }
     * 
     * 
     */
    public List<ZMMINBOUND010IN> getTZMMS0008() {
        if (tzmms0008 == null) {
            tzmms0008 = new ArrayList<ZMMINBOUND010IN>();
        }
        return this.tzmms0008;
    }

}
