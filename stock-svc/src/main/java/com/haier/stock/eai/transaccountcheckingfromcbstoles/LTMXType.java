
package com.haier.stock.eai.transaccountcheckingfromcbstoles;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;


/**
 * <p>Java class for LTMXType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LTMXType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MBLNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MJAHR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CPUDT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZEILE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MATNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MEINS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MENGE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="WERKS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LGORT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CHARG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BWART" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="VBELN_DN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="POSNR_DN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="VBELN_SO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="POSNR_SO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AUART" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BSTNK" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BSTNK100" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ERNAM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ERDAT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SHKZG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LTMXType", propOrder = {
    "mblnr",
    "mjahr",
    "cpudt",
    "zeile",
    "matnr",
    "meins",
    "menge",
    "werks",
    "lgort",
    "charg",
    "bwart",
    "vbelndn",
    "posnrdn",
    "vbelnso",
    "posnrso",
    "auart",
    "bstnk",
    "bstnk100",
    "ernam",
    "erdat",
    "shkzg"
})
public class LTMXType {

    @XmlElement(name = "MBLNR", required = true)
    protected String mblnr;
    @XmlElement(name = "MJAHR", required = true)
    protected String mjahr;
    @XmlElement(name = "CPUDT", required = true)
    protected String cpudt;
    @XmlElement(name = "ZEILE", required = true)
    protected String zeile;
    @XmlElement(name = "MATNR", required = true)
    protected String matnr;
    @XmlElement(name = "MEINS", required = true)
    protected String meins;
    @XmlElement(name = "MENGE", required = true)
    protected BigDecimal menge;
    @XmlElement(name = "WERKS", required = true)
    protected String werks;
    @XmlElement(name = "LGORT", required = true)
    protected String lgort;
    @XmlElement(name = "CHARG", required = true)
    protected String charg;
    @XmlElement(name = "BWART", required = true)
    protected String bwart;
    @XmlElement(name = "VBELN_DN", required = true)
    protected String vbelndn;
    @XmlElement(name = "POSNR_DN", required = true)
    protected String posnrdn;
    @XmlElement(name = "VBELN_SO", required = true)
    protected String vbelnso;
    @XmlElement(name = "POSNR_SO", required = true)
    protected String posnrso;
    @XmlElement(name = "AUART", required = true)
    protected String auart;
    @XmlElement(name = "BSTNK", required = true)
    protected String bstnk;
    @XmlElement(name = "BSTNK100", required = true)
    protected String bstnk100;
    @XmlElement(name = "ERNAM", required = true)
    protected String ernam;
    @XmlElement(name = "ERDAT", required = true)
    protected String erdat;
    @XmlElement(name = "SHKZG", required = true)
    protected String shkzg;

    /**
     * Gets the value of the mblnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMBLNR() {
        return mblnr;
    }

    /**
     * Sets the value of the mblnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMBLNR(String value) {
        this.mblnr = value;
    }

    /**
     * Gets the value of the mjahr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMJAHR() {
        return mjahr;
    }

    /**
     * Sets the value of the mjahr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMJAHR(String value) {
        this.mjahr = value;
    }

    /**
     * Gets the value of the cpudt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCPUDT() {
        return cpudt;
    }

    /**
     * Sets the value of the cpudt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCPUDT(String value) {
        this.cpudt = value;
    }

    /**
     * Gets the value of the zeile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZEILE() {
        return zeile;
    }

    /**
     * Sets the value of the zeile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZEILE(String value) {
        this.zeile = value;
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
     * Gets the value of the menge property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMENGE() {
        return menge;
    }

    /**
     * Sets the value of the menge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMENGE(BigDecimal value) {
        this.menge = value;
    }

    /**
     * Gets the value of the werks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWERKS() {
        return werks;
    }

    /**
     * Sets the value of the werks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWERKS(String value) {
        this.werks = value;
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
     * Gets the value of the charg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCHARG() {
        return charg;
    }

    /**
     * Sets the value of the charg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCHARG(String value) {
        this.charg = value;
    }

    /**
     * Gets the value of the bwart property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBWART() {
        return bwart;
    }

    /**
     * Sets the value of the bwart property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBWART(String value) {
        this.bwart = value;
    }

    /**
     * Gets the value of the vbelndn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVBELNDN() {
        return vbelndn;
    }

    /**
     * Sets the value of the vbelndn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVBELNDN(String value) {
        this.vbelndn = value;
    }

    /**
     * Gets the value of the posnrdn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPOSNRDN() {
        return posnrdn;
    }

    /**
     * Sets the value of the posnrdn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPOSNRDN(String value) {
        this.posnrdn = value;
    }

    /**
     * Gets the value of the vbelnso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVBELNSO() {
        return vbelnso;
    }

    /**
     * Sets the value of the vbelnso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVBELNSO(String value) {
        this.vbelnso = value;
    }

    /**
     * Gets the value of the posnrso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPOSNRSO() {
        return posnrso;
    }

    /**
     * Sets the value of the posnrso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPOSNRSO(String value) {
        this.posnrso = value;
    }

    /**
     * Gets the value of the auart property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAUART() {
        return auart;
    }

    /**
     * Sets the value of the auart property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAUART(String value) {
        this.auart = value;
    }

    /**
     * Gets the value of the bstnk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBSTNK() {
        return bstnk;
    }

    /**
     * Sets the value of the bstnk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBSTNK(String value) {
        this.bstnk = value;
    }

    /**
     * Gets the value of the bstnk100 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBSTNK100() {
        return bstnk100;
    }

    /**
     * Sets the value of the bstnk100 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBSTNK100(String value) {
        this.bstnk100 = value;
    }

    /**
     * Gets the value of the ernam property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getERNAM() {
        return ernam;
    }

    /**
     * Sets the value of the ernam property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setERNAM(String value) {
        this.ernam = value;
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
     * Gets the value of the shkzg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSHKZG() {
        return shkzg;
    }

    /**
     * Sets the value of the shkzg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSHKZG(String value) {
        this.shkzg = value;
    }

}
