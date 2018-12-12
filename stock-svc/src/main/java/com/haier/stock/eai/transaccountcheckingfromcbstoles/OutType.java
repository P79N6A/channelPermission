
package com.haier.stock.eai.transaccountcheckingfromcbstoles;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for outType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="outType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CBS_KC" type="{http://www.example.org/TransAccountCheckingFromCBSToLES/}CBS_KCType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="CBS_MX" type="{http://www.example.org/TransAccountCheckingFromCBSToLES/}CBS_MXType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="COLLECTOR" type="{http://www.example.org/TransAccountCheckingFromCBSToLES/}COLLECTORType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="LTMX" type="{http://www.example.org/TransAccountCheckingFromCBSToLES/}LTMXType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "outType", propOrder = {
    "cbskc",
    "cbsmx",
    "collector",
    "ltmx"
})
public class OutType {

    @XmlElement(name = "CBS_KC")
    protected List<CBSKCType> cbskc;
    @XmlElement(name = "CBS_MX")
    protected List<CBSMXType> cbsmx;
    @XmlElement(name = "COLLECTOR")
    protected List<COLLECTORType> collector;
    @XmlElement(name = "LTMX")
    protected List<LTMXType> ltmx;

    /**
     * Gets the value of the cbskc property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cbskc property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCBSKC().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CBSKCType }
     * 
     * 
     */
    public List<CBSKCType> getCBSKC() {
        if (cbskc == null) {
            cbskc = new ArrayList<CBSKCType>();
        }
        return this.cbskc;
    }

    /**
     * Gets the value of the cbsmx property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cbsmx property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCBSMX().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CBSMXType }
     * 
     * 
     */
    public List<CBSMXType> getCBSMX() {
        if (cbsmx == null) {
            cbsmx = new ArrayList<CBSMXType>();
        }
        return this.cbsmx;
    }

    /**
     * Gets the value of the collector property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the collector property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCOLLECTOR().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COLLECTORType }
     * 
     * 
     */
    public List<COLLECTORType> getCOLLECTOR() {
        if (collector == null) {
            collector = new ArrayList<COLLECTORType>();
        }
        return this.collector;
    }

    /**
     * Gets the value of the ltmx property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ltmx property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLTMX().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LTMXType }
     * 
     * 
     */
    public List<LTMXType> getLTMX() {
        if (ltmx == null) {
            ltmx = new ArrayList<LTMXType>();
        }
        return this.ltmx;
    }

}
