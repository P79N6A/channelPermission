
package com.haier.svc.purchase.createscordertoles;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 *
 *
 * <pre>
 * &lt;complexType name="InputTypes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SOURCE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SOURCE_EXT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SOURCE_SN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BSTKD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="POSEX" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AUDAT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AUTIM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AUART" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="KUNNR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="KUNWE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MATNR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COMTYP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="KWMENG" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="MEINS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LGORT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CHARG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SDABW" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AUGRU" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BSTKD_E" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="POSNR_E" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="KBETR" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="KWERT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="SHIPCO" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="KWERZ" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="DHRXM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DHRPH" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SHRXM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SHRMOB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SHRTEL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PROV" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CITY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COUNTY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ADDRESS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PSTLZ" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PAYSTE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PAYTYP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="YDDAT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="YDTIME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SDMEMO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="KUNEM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="KUNZ1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SDAEM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="URLAB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="URMEMO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="INVO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="INVTYP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="INVACC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="INVADD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="INVBANK" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="INVNUM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ERDAT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ERZET" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ADD1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ADD2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ADD3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InputType", propOrder = {
    "source",
    "sourceext",
    "sourcesn",
    "bstkd",
    "posex",
    "audat",
    "autim",
    "auart",
    "kunnr",
    "kunwe",
    "matnr",
    "comtyp",
    "kwmeng",
    "meins",
    "lgort",
    "charg",
    "sdabw",
    "augru",
    "bstkde",
    "posnre",
    "kbetr",
    "kwert",
    "shipco",
    "kwerz",
    "dhrxm",
    "dhrph",
    "shrxm",
    "shrmob",
    "shrtel",
    "prov",
    "city",
    "county",
    "address",
    "pstlz",
    "payste",
    "paytyp",
    "yddat",
    "ydtime",
    "sdmemo",
    "kunem",
    "kunz1",
    "sdaem",
    "urlab",
    "urmemo",
    "invo",
    "invtyp",
    "invacc",
    "invadd",
    "invbank",
    "invnum",
    "erdat",
    "erzet",
    "add1",
    "add2",
    "add3"
})
public class InputType {

    @XmlElement(name = "SOURCE")
    protected String source;
    @XmlElement(name = "SOURCE_EXT")
    protected String sourceext;
    @XmlElement(name = "SOURCE_SN")
    protected String sourcesn;
    @XmlElement(name = "BSTKD")
    protected String bstkd;
    @XmlElement(name = "POSEX")
    protected String posex;
    @XmlElement(name = "AUDAT")
    protected String audat;
    @XmlElement(name = "AUTIM")
    protected String autim;
    @XmlElement(name = "AUART")
    protected String auart;
    @XmlElement(name = "KUNNR")
    protected String kunnr;
    @XmlElement(name = "KUNWE")
    protected String kunwe;
    @XmlElement(name = "MATNR")
    protected String matnr;
    @XmlElement(name = "COMTYP")
    protected String comtyp;
    @XmlElement(name = "KWMENG")
    protected BigDecimal kwmeng;
    @XmlElement(name = "MEINS")
    protected String meins;
    @XmlElement(name = "LGORT")
    protected String lgort;
    @XmlElement(name = "CHARG")
    protected String charg;
    @XmlElement(name = "SDABW")
    protected String sdabw;
    @XmlElement(name = "AUGRU")
    protected String augru;
    @XmlElement(name = "BSTKD_E")
    protected String bstkde;
    @XmlElement(name = "POSNR_E")
    protected String posnre;
    @XmlElement(name = "KBETR")
    protected BigDecimal kbetr;
    @XmlElement(name = "KWERT")
    protected BigDecimal kwert;
    @XmlElement(name = "SHIPCO")
    protected BigDecimal shipco;
    @XmlElement(name = "KWERZ")
    protected BigDecimal kwerz;
    @XmlElement(name = "DHRXM")
    protected String dhrxm;
    @XmlElement(name = "DHRPH")
    protected String dhrph;
    @XmlElement(name = "SHRXM")
    protected String shrxm;
    @XmlElement(name = "SHRMOB")
    protected String shrmob;
    @XmlElement(name = "SHRTEL")
    protected String shrtel;
    @XmlElement(name = "PROV")
    protected String prov;
    @XmlElement(name = "CITY")
    protected String city;
    @XmlElement(name = "COUNTY")
    protected String county;
    @XmlElement(name = "ADDRESS")
    protected String address;
    @XmlElement(name = "PSTLZ")
    protected String pstlz;
    @XmlElement(name = "PAYSTE")
    protected String payste;
    @XmlElement(name = "PAYTYP")
    protected String paytyp;
    @XmlElement(name = "YDDAT")
    protected String yddat;
    @XmlElement(name = "YDTIME")
    protected String ydtime;
    @XmlElement(name = "SDMEMO")
    protected String sdmemo;
    @XmlElement(name = "KUNEM")
    protected String kunem;
    @XmlElement(name = "KUNZ1")
    protected String kunz1;
    @XmlElement(name = "SDAEM")
    protected String sdaem;
    @XmlElement(name = "URLAB")
    protected String urlab;
    @XmlElement(name = "URMEMO")
    protected String urmemo;
    @XmlElement(name = "INVO")
    protected String invo;
    @XmlElement(name = "INVTYP")
    protected String invtyp;
    @XmlElement(name = "INVACC")
    protected String invacc;
    @XmlElement(name = "INVADD")
    protected String invadd;
    @XmlElement(name = "INVBANK")
    protected String invbank;
    @XmlElement(name = "INVNUM")
    protected String invnum;
    @XmlElement(name = "ERDAT")
    protected String erdat;
    @XmlElement(name = "ERZET")
    protected String erzet;
    @XmlElement(name = "ADD1")
    protected String add1;
    @XmlElement(name = "ADD2")
    protected String add2;
    @XmlElement(name = "ADD3")
    protected String add3;

    /**
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
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSOURCEEXT() {
        return sourceext;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSOURCEEXT(String value) {
        this.sourceext = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSOURCESN() {
        return sourcesn;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSOURCESN(String value) {
        this.sourcesn = value;
    }

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
    public String getPOSEX() {
        return posex;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPOSEX(String value) {
        this.posex = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAUDAT() {
        return audat;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAUDAT(String value) {
        this.audat = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAUTIM() {
        return autim;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAUTIM(String value) {
        this.autim = value;
    }

    /**
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
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCOMTYP() {
        return comtyp;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCOMTYP(String value) {
        this.comtyp = value;
    }

    /**
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
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSDABW() {
        return sdabw;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSDABW(String value) {
        this.sdabw = value;
    }

    /**
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
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBSTKDE() {
        return bstkde;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBSTKDE(String value) {
        this.bstkde = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPOSNRE() {
        return posnre;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPOSNRE(String value) {
        this.posnre = value;
    }

    /**
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
     *
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *
     */
    public BigDecimal getKWERT() {
        return kwert;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *
     */
    public void setKWERT(BigDecimal value) {
        this.kwert = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *
     */
    public BigDecimal getSHIPCO() {
        return shipco;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *
     */
    public void setSHIPCO(BigDecimal value) {
        this.shipco = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *
     */
    public BigDecimal getKWERZ() {
        return kwerz;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *
     */
    public void setKWERZ(BigDecimal value) {
        this.kwerz = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDHRXM() {
        return dhrxm;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDHRXM(String value) {
        this.dhrxm = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDHRPH() {
        return dhrph;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDHRPH(String value) {
        this.dhrph = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSHRXM() {
        return shrxm;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSHRXM(String value) {
        this.shrxm = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSHRMOB() {
        return shrmob;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSHRMOB(String value) {
        this.shrmob = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSHRTEL() {
        return shrtel;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSHRTEL(String value) {
        this.shrtel = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPROV() {
        return prov;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPROV(String value) {
        this.prov = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCITY() {
        return city;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCITY(String value) {
        this.city = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCOUNTY() {
        return county;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCOUNTY(String value) {
        this.county = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getADDRESS() {
        return address;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setADDRESS(String value) {
        this.address = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPSTLZ() {
        return pstlz;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPSTLZ(String value) {
        this.pstlz = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPAYSTE() {
        return payste;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPAYSTE(String value) {
        this.payste = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPAYTYP() {
        return paytyp;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPAYTYP(String value) {
        this.paytyp = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getYDDAT() {
        return yddat;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setYDDAT(String value) {
        this.yddat = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getYDTIME() {
        return ydtime;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setYDTIME(String value) {
        this.ydtime = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSDMEMO() {
        return sdmemo;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSDMEMO(String value) {
        this.sdmemo = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getKUNEM() {
        return kunem;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setKUNEM(String value) {
        this.kunem = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getKUNZ1() {
        return kunz1;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setKUNZ1(String value) {
        this.kunz1 = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSDAEM() {
        return sdaem;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSDAEM(String value) {
        this.sdaem = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getURLAB() {
        return urlab;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setURLAB(String value) {
        this.urlab = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getURMEMO() {
        return urmemo;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setURMEMO(String value) {
        this.urmemo = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getINVO() {
        return invo;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setINVO(String value) {
        this.invo = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getINVTYP() {
        return invtyp;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setINVTYP(String value) {
        this.invtyp = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getINVACC() {
        return invacc;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setINVACC(String value) {
        this.invacc = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getINVADD() {
        return invadd;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setINVADD(String value) {
        this.invadd = value;
    }

    /**
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getINVBANK() {
        return invbank;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setINVBANK(String value) {
        this.invbank = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getINVNUM() {
        return invnum;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setINVNUM(String value) {
        this.invnum = value;
    }

    /**
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
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getADD1() {
        return add1;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setADD1(String value) {
        this.add1 = value;
    }

    /**
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getADD2() {
        return add2;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setADD2(String value) {
        this.add2 = value;
    }

    /**
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getADD3() {
        return add3;
    }

    /**
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setADD3(String value) {
        this.add3 = value;
    }

}
