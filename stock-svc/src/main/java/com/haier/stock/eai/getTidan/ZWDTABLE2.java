
package com.haier.stock.eai.getTidan;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ZWD_TABLE_2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZWD_TABLE_2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BSTKD" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="GVS_SO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="KUNNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="KUNWE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ADD4" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ERDAT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ERZET" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AD1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AD2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AD3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZWD_TABLE_2", propOrder = {
    "bstkd",
    "gvsso",
    "kunnr",
    "kunwe",
    "add4",
    "erdat",
    "erzet",
    "ad1",
    "ad2",
    "ad3"
})
public class ZWDTABLE2 {

    @XmlElement(name = "BSTKD", required = true)
    protected String bstkd;
    @XmlElement(name = "GVS_SO", required = true)
    protected String gvsso;
    @XmlElement(name = "KUNNR", required = true)
    protected String kunnr;
    @XmlElement(name = "KUNWE", required = true)
    protected String kunwe;
    @XmlElement(name = "ADD4", required = true)
    protected String add4;
    @XmlElement(name = "ERDAT", required = true)
    protected String erdat;
    @XmlElement(name = "ERZET", required = true)
    protected String erzet;
    @XmlElement(name = "AD1", required = true)
    protected String ad1;
    @XmlElement(name = "AD2", required = true)
    protected String ad2;
    @XmlElement(name = "AD3", required = true)
    protected String ad3;

    /**
     * Gets the value of the bstkd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBSTKD() {
        return bstkd;
    }

    /**
     * Sets the value of the bstkd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBSTKD(String value) {
        this.bstkd = value;
    }

    /**
     * Gets the value of the gvsso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGVSSO() {
        return gvsso;
    }

    /**
     * Sets the value of the gvsso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGVSSO(String value) {
        this.gvsso = value;
    }

    /**
     * Gets the value of the kunnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKUNNR() {
        return kunnr;
    }

    /**
     * Sets the value of the kunnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKUNNR(String value) {
        this.kunnr = value;
    }

    /**
     * Gets the value of the kunwe property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKUNWE() {
        return kunwe;
    }

    /**
     * Sets the value of the kunwe property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKUNWE(String value) {
        this.kunwe = value;
    }

    /**
     * Gets the value of the add4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD4() {
        return add4;
    }

    /**
     * Sets the value of the add4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD4(String value) {
        this.add4 = value;
    }

    /**
     * Gets the value of the erdat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getERDAT() {
        return erdat;
    }

    /**
     * Sets the value of the erdat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setERDAT(String value) {
        this.erdat = value;
    }

    /**
     * Gets the value of the erzet property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getERZET() {
        return erzet;
    }

    /**
     * Sets the value of the erzet property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setERZET(String value) {
        this.erzet = value;
    }

    /**
     * Gets the value of the ad1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAD1() {
        return ad1;
    }

    /**
     * Sets the value of the ad1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAD1(String value) {
        this.ad1 = value;
    }

    /**
     * Gets the value of the ad2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAD2() {
        return ad2;
    }

    /**
     * Sets the value of the ad2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAD2(String value) {
        this.ad2 = value;
    }

    /**
     * Gets the value of the ad3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAD3() {
        return ad3;
    }

    /**
     * Sets the value of the ad3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAD3(String value) {
        this.ad3 = value;
    }

}
