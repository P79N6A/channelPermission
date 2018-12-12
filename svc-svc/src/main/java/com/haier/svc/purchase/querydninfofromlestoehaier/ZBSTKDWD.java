
package com.haier.svc.purchase.querydninfofromlestoehaier;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 *
 *
 * <pre>
 * &lt;complexType name="ZBSTKD_WD">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BSTKD" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FLAG_RK" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ERDAT_RK" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ERZET_RK" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FLAG_CK" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ERDAT_CK" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ERZET_CK" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AD1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AD2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AD3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AD4" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZBSTKD_WD", propOrder = {
    "bstkd",
    "flagrk",
    "erdatrk",
    "erzetrk",
    "flagck",
    "erdatck",
    "erzetck",
    "ad1",
    "ad2",
    "ad3",
    "ad4"
})
public class ZBSTKDWD {

    @XmlElement(name = "BSTKD", required = true)
    protected String bstkd;
    @XmlElement(name = "FLAG_RK", required = true)
    protected String flagrk;
    @XmlElement(name = "ERDAT_RK", required = true)
    protected String erdatrk;
    @XmlElement(name = "ERZET_RK", required = true)
    protected String erzetrk;
    @XmlElement(name = "FLAG_CK", required = true)
    protected String flagck;
    @XmlElement(name = "ERDAT_CK", required = true)
    protected String erdatck;
    @XmlElement(name = "ERZET_CK", required = true)
    protected String erzetck;
    @XmlElement(name = "AD1", required = true)
    protected String ad1;
    @XmlElement(name = "AD2", required = true)
    protected String ad2;
    @XmlElement(name = "AD3", required = true)
    protected String ad3;
    @XmlElement(name = "AD4", required = true)
    protected String ad4;

    /**
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
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLAGRK() {
        return flagrk;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLAGRK(String value) {
        this.flagrk = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getERDATRK() {
        return erdatrk;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setERDATRK(String value) {
        this.erdatrk = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getERZETRK() {
        return erzetrk;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setERZETRK(String value) {
        this.erzetrk = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLAGCK() {
        return flagck;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLAGCK(String value) {
        this.flagck = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getERDATCK() {
        return erdatck;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setERDATCK(String value) {
        this.erdatck = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getERZETCK() {
        return erzetck;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setERZETCK(String value) {
        this.erzetck = value;
    }

    /**
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
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAD3(String value) {
        this.ad3 = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAD4() {
        return ad4;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAD4(String value) {
        this.ad4 = value;
    }

}
