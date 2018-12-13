package com.haier.purchase.data.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MBLNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MJAHR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZEILE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CPUDT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CPUTM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BWART" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SHKZG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MATNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MATKL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CHARG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LFIMG" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="LGORT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="KUNNR_SALETO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="KUNNR_SENDTO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="VBELN_SO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="VBELN_DN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TKNUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BSTNK" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AUART" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RESERVE1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RESERVE2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BSTKD" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="KBETR" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "mblnr", "mjahr", "zeile", "cpudt", "cputm", "bwart", "shkzg",
                                 "matnr", "matkl", "charg", "lfimg", "lgort", "kunnrsaleto",
                                 "kunnrsendto", "vbelnso", "vbelndn", "tknum", "bstnk", "auart",
                                 "reserve1", "reserve2", "bstkd", "kbetr" })
public class GetKUCUNInfoFromLESToEHAIERResponseStockTransEntity implements Serializable{
    @XmlElement(name = "MBLNR", required = true)
    protected String     mblnr;
    @XmlElement(name = "MJAHR", required = true)
    protected String     mjahr;
    @XmlElement(name = "ZEILE", required = true)
    protected String     zeile;
    @XmlElement(name = "CPUDT", required = true)
    protected String     cpudt;
    @XmlElement(name = "CPUTM", required = true)
    protected String     cputm;
    @XmlElement(name = "BWART", required = true)
    protected String     bwart;
    @XmlElement(name = "SHKZG", required = true)
    protected String     shkzg;
    @XmlElement(name = "MATNR", required = true)
    protected String     matnr;
    @XmlElement(name = "MATKL", required = true)
    protected String     matkl;
    @XmlElement(name = "CHARG", required = true)
    protected String     charg;
    @XmlElement(name = "LFIMG", required = true)
    protected BigDecimal lfimg;
    @XmlElement(name = "LGORT", required = true)
    protected String     lgort;
    @XmlElement(name = "KUNNR_SALETO", required = true)
    protected String     kunnrsaleto;
    @XmlElement(name = "KUNNR_SENDTO", required = true)
    protected String     kunnrsendto;
    @XmlElement(name = "VBELN_SO", required = true)
    protected String     vbelnso;
    @XmlElement(name = "VBELN_DN", required = true)
    protected String     vbelndn;
    @XmlElement(name = "TKNUM", required = true)
    protected String     tknum;
    @XmlElement(name = "BSTNK", required = true)
    protected String     bstnk;
    @XmlElement(name = "AUART", required = true)
    protected String     auart;
    @XmlElement(name = "RESERVE1", required = true)
    protected String     reserve1;
    @XmlElement(name = "RESERVE2", required = true)
    protected String     reserve2;
    @XmlElement(name = "BSTKD", required = true)
    protected String     bstkd;
    @XmlElement(name = "KBETR", required = true)
    protected BigDecimal kbetr;

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
     * Gets the value of the lfimg property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLFIMG() {
        return lfimg;
    }

    /**
     * Sets the value of the lfimg property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLFIMG(BigDecimal value) {
        this.lfimg = value;
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
     * Gets the value of the kunnrsaleto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKUNNRSALETO() {
        return kunnrsaleto;
    }

    /**
     * Sets the value of the kunnrsaleto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKUNNRSALETO(String value) {
        this.kunnrsaleto = value;
    }

    /**
     * Gets the value of the kunnrsendto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKUNNRSENDTO() {
        return kunnrsendto;
    }

    /**
     * Sets the value of the kunnrsendto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKUNNRSENDTO(String value) {
        this.kunnrsendto = value;
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
     * Gets the value of the tknum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTKNUM() {
        return tknum;
    }

    /**
     * Sets the value of the tknum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTKNUM(String value) {
        this.tknum = value;
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
     * Gets the value of the reserve1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRESERVE1() {
        return reserve1;
    }

    /**
     * Sets the value of the reserve1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRESERVE1(String value) {
        this.reserve1 = value;
    }

    /**
     * Gets the value of the reserve2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRESERVE2() {
        return reserve2;
    }

    /**
     * Sets the value of the reserve2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRESERVE2(String value) {
        this.reserve2 = value;
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
}
