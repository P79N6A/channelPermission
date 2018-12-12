
package com.haier.svc.purchase.querydninfofromlestoehaier;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 *
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SysName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZFLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="INPUT" type="{http://www.example.org/QueryDNinfoFromLEStoEhaier/}ZBSTKD" maxOccurs="unbounded" minOccurs="0"/>
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
    "sysName",
    "zflag",
    "input"
})
@XmlRootElement(name = "QueryDNinfoFromLEStoEhaier")
public class QueryDNinfoFromLEStoEhaier_Type {

    @XmlElement(name = "SysName", required = true)
    protected String sysName;
    @XmlElement(name = "ZFLAG", required = true)
    protected String zflag;
    @XmlElement(name = "INPUT")
    protected List<ZBSTKD> input;

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSysName() {
        return sysName;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSysName(String value) {
        this.sysName = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZFLAG() {
        return zflag;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZFLAG(String value) {
        this.zflag = value;
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
     * {@link ZBSTKD }
     * 
     * 
     */
    public List<ZBSTKD> getINPUT() {
        if (input == null) {
            input = new ArrayList<ZBSTKD>();
        }
        return this.input;
    }

}
