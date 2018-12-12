
package com.haier.vehicle.wsdl.queryDNFrom;

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
 *         &lt;element name="CALLCODE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SOURCE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="INPUT" type="{http://www.example.org/QueryDNFromLEStoAPP/}ZINT_WX_WT_LOG" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "callcode",
    "source",
    "input"
})
@XmlRootElement(name = "QueryDNFromLEStoAPP")
public class QueryDNFromLEStoAPP_Type {

    @XmlElement(name = "CALLCODE", required = true)
    protected String callcode;
    @XmlElement(name = "SOURCE", required = true)
    protected String source;
    @XmlElement(name = "INPUT")
    protected List<ZINTWXWTLOG> input;

    /**
     * 获取callcode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCALLCODE() {
        return callcode;
    }

    /**
     * 设置callcode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCALLCODE(String value) {
        this.callcode = value;
    }

    /**
     * 获取source属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSOURCE() {
        return source;
    }

    /**
     * 设置source属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSOURCE(String value) {
        this.source = value;
    }

    /**
     * Gets the value of the input property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the input property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getINPUT().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ZINTWXWTLOG }
     * 
     * 
     */
    public List<ZINTWXWTLOG> getINPUT() {
        if (input == null) {
            input = new ArrayList<ZINTWXWTLOG>();
        }
        return this.input;
    }

}
