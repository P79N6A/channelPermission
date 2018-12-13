package com.haier.stock.eai.finance.pushreturn;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for ZSDS0004 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZSDS0004">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ZSYST" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZWBDR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZWBDT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZORDR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZCHNL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="KUNNR_AG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="KUNNR_RG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AUGRU" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MATNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="KWMENG" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="KBETR" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="LGORT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZFGBL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZSDS0004", propOrder = { "zsyst", "zwbdr", "zwbdt", "zordr", "zchnl", "kunnrag",
                                         "kunnrrg", "augru", "matnr", "kwmeng", "kbetr", "lgort",
                                         "zfgbl" })
public class ZSDS0004 {

    @XmlElement(name = "ZSYST", required = true)
    protected String     zsyst;
    @XmlElement(name = "ZWBDR", required = true)
    protected String     zwbdr;
    @XmlElement(name = "ZWBDT", required = true)
    protected String     zwbdt;
    @XmlElement(name = "ZORDR", required = true)
    protected String     zordr;
    @XmlElement(name = "ZCHNL", required = true)
    protected String     zchnl;
    @XmlElement(name = "KUNNR_AG", required = true)
    protected String     kunnrag;
    @XmlElement(name = "KUNNR_RG", required = true)
    protected String     kunnrrg;
    @XmlElement(name = "AUGRU", required = true)
    protected String     augru;
    @XmlElement(name = "MATNR", required = true)
    protected String     matnr;
    @XmlElement(name = "KWMENG", required = true)
    protected BigDecimal kwmeng;
    @XmlElement(name = "KBETR", required = true)
    protected BigDecimal kbetr;
    @XmlElement(name = "LGORT", required = true)
    protected String     lgort;
    @XmlElement(name = "ZFGBL", required = true)
    protected String     zfgbl;

    /**
     * Gets the value of the zsyst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZSYST() {
        return zsyst;
    }

    /**
     * Sets the value of the zsyst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZSYST(String value) {
        this.zsyst = value;
    }

    /**
     * Gets the value of the zwbdr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZWBDR() {
        return zwbdr;
    }

    /**
     * Sets the value of the zwbdr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZWBDR(String value) {
        this.zwbdr = value;
    }

    /**
     * Gets the value of the zwbdt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZWBDT() {
        return zwbdt;
    }

    /**
     * Sets the value of the zwbdt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZWBDT(String value) {
        this.zwbdt = value;
    }

    /**
     * Gets the value of the zordr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZORDR() {
        return zordr;
    }

    /**
     * Sets the value of the zordr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZORDR(String value) {
        this.zordr = value;
    }

    /**
     * Gets the value of the zchnl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZCHNL() {
        return zchnl;
    }

    /**
     * Sets the value of the zchnl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZCHNL(String value) {
        this.zchnl = value;
    }

    /**
     * Gets the value of the kunnrag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKUNNRAG() {
        return kunnrag;
    }

    /**
     * Sets the value of the kunnrag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKUNNRAG(String value) {
        this.kunnrag = value;
    }

    /**
     * Gets the value of the kunnrrg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKUNNRRG() {
        return kunnrrg;
    }

    /**
     * Sets the value of the kunnrrg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKUNNRRG(String value) {
        this.kunnrrg = value;
    }

    /**
     * Gets the value of the augru property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAUGRU() {
        return augru;
    }

    /**
     * Sets the value of the augru property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAUGRU(String value) {
        this.augru = value;
    }

    /**
     * Gets the value of the matnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMATNR() {
        return matnr;
    }

    /**
     * Sets the value of the matnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMATNR(String value) {
        this.matnr = value;
    }

    /**
     * Gets the value of the kwmeng property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getKWMENG() {
        return kwmeng;
    }

    /**
     * Sets the value of the kwmeng property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setKWMENG(BigDecimal value) {
        this.kwmeng = value;
    }

    /**
     * Gets the value of the kbetr property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getKBETR() {
        return kbetr;
    }

    /**
     * Sets the value of the kbetr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setKBETR(BigDecimal value) {
        this.kbetr = value;
    }

    /**
     * Gets the value of the lgort property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLGORT() {
        return lgort;
    }

    /**
     * Sets the value of the lgort property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLGORT(String value) {
        this.lgort = value;
    }

    /**
     * Gets the value of the zfgbl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZFGBL() {
        return zfgbl;
    }

    /**
     * Sets the value of the zfgbl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZFGBL(String value) {
        this.zfgbl = value;
    }

}
