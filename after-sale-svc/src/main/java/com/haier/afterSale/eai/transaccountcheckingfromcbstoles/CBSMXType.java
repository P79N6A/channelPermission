
package com.haier.afterSale.eai.transaccountcheckingfromcbstoles;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;


/**
 * <p>Java class for CBS_MXType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CBS_MXType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BKTXT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BLDAT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BUDAT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BWART" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CHARG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CPUTM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ERFME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ERFMG" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="LGORT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LIFNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MATNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MBLNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MEINS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MENGE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="MJAHR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SHKZG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="USNAM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="WERKS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="XBLNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LGPLA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LGOBE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="VOLUM" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ZVOLUM" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="VBELV" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="POSNV" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="VBELN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PEICHE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TPLST" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="VBELV_O" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="POSNV_O" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BSTKD" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NAME1_SDF" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MATKL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EXTWG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CBS_MXType", propOrder = {
    "bktxt",
    "bldat",
    "budat",
    "bwart",
    "charg",
    "cputm",
    "erfme",
    "erfmg",
    "lgort",
    "lifnr",
    "matnr",
    "mblnr",
    "meins",
    "menge",
    "mjahr",
    "shkzg",
    "usnam",
    "werks",
    "xblnr",
    "lgpla",
    "lgobe",
    "volum",
    "zvolum",
    "vbelv",
    "posnv",
    "vbeln",
    "peiche",
    "tplst",
    "vbelvo",
    "posnvo",
    "bstkd",
    "name1SDF",
    "matkl",
    "extwg"
})
public class CBSMXType {

    @XmlElement(name = "BKTXT", required = true)
    protected String bktxt;
    @XmlElement(name = "BLDAT", required = true)
    protected String bldat;
    @XmlElement(name = "BUDAT", required = true)
    protected String budat;
    @XmlElement(name = "BWART", required = true)
    protected String bwart;
    @XmlElement(name = "CHARG", required = true)
    protected String charg;
    @XmlElement(name = "CPUTM", required = true)
    protected String cputm;
    @XmlElement(name = "ERFME", required = true)
    protected String erfme;
    @XmlElement(name = "ERFMG", required = true)
    protected BigDecimal erfmg;
    @XmlElement(name = "LGORT", required = true)
    protected String lgort;
    @XmlElement(name = "LIFNR", required = true)
    protected String lifnr;
    @XmlElement(name = "MATNR", required = true)
    protected String matnr;
    @XmlElement(name = "MBLNR", required = true)
    protected String mblnr;
    @XmlElement(name = "MEINS", required = true)
    protected String meins;
    @XmlElement(name = "MENGE", required = true)
    protected BigDecimal menge;
    @XmlElement(name = "MJAHR", required = true)
    protected String mjahr;
    @XmlElement(name = "SHKZG", required = true)
    protected String shkzg;
    @XmlElement(name = "USNAM", required = true)
    protected String usnam;
    @XmlElement(name = "WERKS", required = true)
    protected String werks;
    @XmlElement(name = "XBLNR", required = true)
    protected String xblnr;
    @XmlElement(name = "LGPLA", required = true)
    protected String lgpla;
    @XmlElement(name = "LGOBE", required = true)
    protected String lgobe;
    @XmlElement(name = "VOLUM", required = true)
    protected BigDecimal volum;
    @XmlElement(name = "ZVOLUM", required = true)
    protected BigDecimal zvolum;
    @XmlElement(name = "VBELV", required = true)
    protected String vbelv;
    @XmlElement(name = "POSNV", required = true)
    protected String posnv;
    @XmlElement(name = "VBELN", required = true)
    protected String vbeln;
    @XmlElement(name = "PEICHE", required = true)
    protected String peiche;
    @XmlElement(name = "TPLST", required = true)
    protected String tplst;
    @XmlElement(name = "VBELV_O", required = true)
    protected String vbelvo;
    @XmlElement(name = "POSNV_O", required = true)
    protected String posnvo;
    @XmlElement(name = "BSTKD", required = true)
    protected String bstkd;
    @XmlElement(name = "NAME1_SDF", required = true)
    protected String name1SDF;
    @XmlElement(name = "MATKL", required = true)
    protected String matkl;
    @XmlElement(name = "EXTWG", required = true)
    protected String extwg;

    /**
     * Gets the value of the bktxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBKTXT() {
        return bktxt;
    }

    /**
     * Sets the value of the bktxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBKTXT(String value) {
        this.bktxt = value;
    }

    /**
     * Gets the value of the bldat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBLDAT() {
        return bldat;
    }

    /**
     * Sets the value of the bldat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBLDAT(String value) {
        this.bldat = value;
    }

    /**
     * Gets the value of the budat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBUDAT() {
        return budat;
    }

    /**
     * Sets the value of the budat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBUDAT(String value) {
        this.budat = value;
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
     * Gets the value of the cputm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCPUTM() {
        return cputm;
    }

    /**
     * Sets the value of the cputm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCPUTM(String value) {
        this.cputm = value;
    }

    /**
     * Gets the value of the erfme property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getERFME() {
        return erfme;
    }

    /**
     * Sets the value of the erfme property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setERFME(String value) {
        this.erfme = value;
    }

    /**
     * Gets the value of the erfmg property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getERFMG() {
        return erfmg;
    }

    /**
     * Sets the value of the erfmg property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setERFMG(BigDecimal value) {
        this.erfmg = value;
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
     * Gets the value of the lifnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLIFNR() {
        return lifnr;
    }

    /**
     * Sets the value of the lifnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLIFNR(String value) {
        this.lifnr = value;
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

    /**
     * Gets the value of the usnam property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSNAM() {
        return usnam;
    }

    /**
     * Sets the value of the usnam property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSNAM(String value) {
        this.usnam = value;
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
     * Gets the value of the xblnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXBLNR() {
        return xblnr;
    }

    /**
     * Sets the value of the xblnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXBLNR(String value) {
        this.xblnr = value;
    }

    /**
     * Gets the value of the lgpla property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLGPLA() {
        return lgpla;
    }

    /**
     * Sets the value of the lgpla property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLGPLA(String value) {
        this.lgpla = value;
    }

    /**
     * Gets the value of the lgobe property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLGOBE() {
        return lgobe;
    }

    /**
     * Sets the value of the lgobe property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLGOBE(String value) {
        this.lgobe = value;
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
     * Gets the value of the zvolum property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getZVOLUM() {
        return zvolum;
    }

    /**
     * Sets the value of the zvolum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setZVOLUM(BigDecimal value) {
        this.zvolum = value;
    }

    /**
     * Gets the value of the vbelv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVBELV() {
        return vbelv;
    }

    /**
     * Sets the value of the vbelv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVBELV(String value) {
        this.vbelv = value;
    }

    /**
     * Gets the value of the posnv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPOSNV() {
        return posnv;
    }

    /**
     * Sets the value of the posnv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPOSNV(String value) {
        this.posnv = value;
    }

    /**
     * Gets the value of the vbeln property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVBELN() {
        return vbeln;
    }

    /**
     * Sets the value of the vbeln property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVBELN(String value) {
        this.vbeln = value;
    }

    /**
     * Gets the value of the peiche property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPEICHE() {
        return peiche;
    }

    /**
     * Sets the value of the peiche property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPEICHE(String value) {
        this.peiche = value;
    }

    /**
     * Gets the value of the tplst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTPLST() {
        return tplst;
    }

    /**
     * Sets the value of the tplst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTPLST(String value) {
        this.tplst = value;
    }

    /**
     * Gets the value of the vbelvo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVBELVO() {
        return vbelvo;
    }

    /**
     * Sets the value of the vbelvo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVBELVO(String value) {
        this.vbelvo = value;
    }

    /**
     * Gets the value of the posnvo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPOSNVO() {
        return posnvo;
    }

    /**
     * Sets the value of the posnvo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPOSNVO(String value) {
        this.posnvo = value;
    }

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
     * Gets the value of the name1SDF property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNAME1SDF() {
        return name1SDF;
    }

    /**
     * Sets the value of the name1SDF property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNAME1SDF(String value) {
        this.name1SDF = value;
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
     * Gets the value of the extwg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEXTWG() {
        return extwg;
    }

    /**
     * Sets the value of the extwg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEXTWG(String value) {
        this.extwg = value;
    }

}
