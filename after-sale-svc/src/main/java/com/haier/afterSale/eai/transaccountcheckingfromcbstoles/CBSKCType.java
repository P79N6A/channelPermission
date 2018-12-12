
package com.haier.afterSale.eai.transaccountcheckingfromcbstoles;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;


/**
 * <p>Java class for CBS_KCType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CBS_KCType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LGORT1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LGORT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MATNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MATNR_NEW" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATWART" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CLABS" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="CUMLM" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="MEINS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZMENGE1" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="VRKME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MENGE1" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="MENGE2" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="MATKL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="VOLUM" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ZVOLEH" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CBS_KCType", propOrder = {
    "lgort1",
    "lgort",
    "matnr",
    "matnrnew",
    "atwart",
    "clabs",
    "cumlm",
    "meins",
    "zmenge1",
    "vrkme",
    "menge1",
    "menge2",
    "matkl",
    "volum",
    "zvoleh"
})
public class CBSKCType {

    @XmlElement(name = "LGORT1", required = true)
    protected String lgort1;
    @XmlElement(name = "LGORT", required = true)
    protected String lgort;
    @XmlElement(name = "MATNR", required = true)
    protected String matnr;
    @XmlElement(name = "MATNR_NEW", required = true)
    protected String matnrnew;
    @XmlElement(name = "ATWART", required = true)
    protected String atwart;
    @XmlElement(name = "CLABS", required = true)
    protected BigDecimal clabs;
    @XmlElement(name = "CUMLM", required = true)
    protected BigDecimal cumlm;
    @XmlElement(name = "MEINS", required = true)
    protected String meins;
    @XmlElement(name = "ZMENGE1", required = true)
    protected BigDecimal zmenge1;
    @XmlElement(name = "VRKME", required = true)
    protected String vrkme;
    @XmlElement(name = "MENGE1", required = true)
    protected BigDecimal menge1;
    @XmlElement(name = "MENGE2", required = true)
    protected BigDecimal menge2;
    @XmlElement(name = "MATKL", required = true)
    protected String matkl;
    @XmlElement(name = "VOLUM", required = true)
    protected BigDecimal volum;
    @XmlElement(name = "ZVOLEH", required = true)
    protected String zvoleh;

    /**
     * Gets the value of the lgort1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLGORT1() {
        return lgort1;
    }

    /**
     * Sets the value of the lgort1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLGORT1(String value) {
        this.lgort1 = value;
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
     * Gets the value of the matnrnew property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMATNRNEW() {
        return matnrnew;
    }

    /**
     * Sets the value of the matnrnew property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMATNRNEW(String value) {
        this.matnrnew = value;
    }

    /**
     * Gets the value of the atwart property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATWART() {
        return atwart;
    }

    /**
     * Sets the value of the atwart property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATWART(String value) {
        this.atwart = value;
    }

    /**
     * Gets the value of the clabs property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCLABS() {
        return clabs;
    }

    /**
     * Sets the value of the clabs property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCLABS(BigDecimal value) {
        this.clabs = value;
    }

    /**
     * Gets the value of the cumlm property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCUMLM() {
        return cumlm;
    }

    /**
     * Sets the value of the cumlm property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCUMLM(BigDecimal value) {
        this.cumlm = value;
    }

    /**
     * Gets the value of the meins property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMEINS() {
        return meins;
    }

    /**
     * Sets the value of the meins property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMEINS(String value) {
        this.meins = value;
    }

    /**
     * Gets the value of the zmenge1 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getZMENGE1() {
        return zmenge1;
    }

    /**
     * Sets the value of the zmenge1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setZMENGE1(BigDecimal value) {
        this.zmenge1 = value;
    }

    /**
     * Gets the value of the vrkme property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVRKME() {
        return vrkme;
    }

    /**
     * Sets the value of the vrkme property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVRKME(String value) {
        this.vrkme = value;
    }

    /**
     * Gets the value of the menge1 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMENGE1() {
        return menge1;
    }

    /**
     * Sets the value of the menge1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMENGE1(BigDecimal value) {
        this.menge1 = value;
    }

    /**
     * Gets the value of the menge2 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMENGE2() {
        return menge2;
    }

    /**
     * Sets the value of the menge2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMENGE2(BigDecimal value) {
        this.menge2 = value;
    }

    /**
     * Gets the value of the matkl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMATKL() {
        return matkl;
    }

    /**
     * Sets the value of the matkl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMATKL(String value) {
        this.matkl = value;
    }

    /**
     * Gets the value of the volum property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVOLUM() {
        return volum;
    }

    /**
     * Sets the value of the volum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVOLUM(BigDecimal value) {
        this.volum = value;
    }

    /**
     * Gets the value of the zvoleh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZVOLEH() {
        return zvoleh;
    }

    /**
     * Sets the value of the zvoleh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZVOLEH(String value) {
        this.zvoleh = value;
    }

}
