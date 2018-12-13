
package com.haier.stock.eai.createpurchaseordertoles;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for InputType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InputType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SOURCE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SOURCE_EXT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SOURCE_SN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BSTKD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="POSEX" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AUDAT" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="AUTIM" type="{http://www.w3.org/2001/XMLSchema}time" minOccurs="0"/>
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
 *         &lt;element name="YDDAT" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="YDTIME" type="{http://www.w3.org/2001/XMLSchema}time" minOccurs="0"/>
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
 *         &lt;element name="ERDAT" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="ERZET" type="{http://www.w3.org/2001/XMLSchema}time" minOccurs="0"/>
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
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar audat;
    @XmlElement(name = "AUTIM")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar autim;
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
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar yddat;
    @XmlElement(name = "YDTIME")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar ydtime;
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
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar erdat;
    @XmlElement(name = "ERZET")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar erzet;
    @XmlElement(name = "ADD1")
    protected String add1;
    @XmlElement(name = "ADD2")
    protected String add2;
    @XmlElement(name = "ADD3")
    protected String add3;

    /**
     * Gets the value of the source property.
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
     * Sets the value of the source property.
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
     * Gets the value of the sourceext property.
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
     * Sets the value of the sourceext property.
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
     * Gets the value of the sourcesn property.
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
     * Sets the value of the sourcesn property.
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
     * Gets the value of the posex property.
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
     * Sets the value of the posex property.
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
     * Gets the value of the audat property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAUDAT() {
        return audat;
    }

    /**
     * Sets the value of the audat property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAUDAT(XMLGregorianCalendar value) {
        this.audat = value;
    }

    /**
     * Gets the value of the autim property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAUTIM() {
        return autim;
    }

    /**
     * Sets the value of the autim property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAUTIM(XMLGregorianCalendar value) {
        this.autim = value;
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
     * Gets the value of the comtyp property.
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
     * Sets the value of the comtyp property.
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
     * Gets the value of the sdabw property.
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
     * Sets the value of the sdabw property.
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
     * Gets the value of the bstkde property.
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
     * Sets the value of the bstkde property.
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
     * Gets the value of the posnre property.
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
     * Sets the value of the posnre property.
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
     * Gets the value of the kwert property.
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
     * Sets the value of the kwert property.
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
     * Gets the value of the shipco property.
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
     * Sets the value of the shipco property.
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
     * Gets the value of the kwerz property.
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
     * Sets the value of the kwerz property.
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
     * Gets the value of the dhrxm property.
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
     * Sets the value of the dhrxm property.
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
     * Gets the value of the dhrph property.
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
     * Sets the value of the dhrph property.
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
     * Gets the value of the shrxm property.
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
     * Sets the value of the shrxm property.
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
     * Gets the value of the shrmob property.
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
     * Sets the value of the shrmob property.
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
     * Gets the value of the shrtel property.
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
     * Sets the value of the shrtel property.
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
     * Gets the value of the prov property.
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
     * Sets the value of the prov property.
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
     * Gets the value of the city property.
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
     * Sets the value of the city property.
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
     * Gets the value of the county property.
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
     * Sets the value of the county property.
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
     * Gets the value of the address property.
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
     * Sets the value of the address property.
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
     * Gets the value of the pstlz property.
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
     * Sets the value of the pstlz property.
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
     * Gets the value of the payste property.
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
     * Sets the value of the payste property.
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
     * Gets the value of the paytyp property.
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
     * Sets the value of the paytyp property.
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
     * Gets the value of the yddat property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getYDDAT() {
        return yddat;
    }

    /**
     * Sets the value of the yddat property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setYDDAT(XMLGregorianCalendar value) {
        this.yddat = value;
    }

    /**
     * Gets the value of the ydtime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getYDTIME() {
        return ydtime;
    }

    /**
     * Sets the value of the ydtime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setYDTIME(XMLGregorianCalendar value) {
        this.ydtime = value;
    }

    /**
     * Gets the value of the sdmemo property.
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
     * Sets the value of the sdmemo property.
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
     * Gets the value of the kunem property.
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
     * Sets the value of the kunem property.
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
     * Gets the value of the kunz1 property.
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
     * Sets the value of the kunz1 property.
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
     * Gets the value of the sdaem property.
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
     * Sets the value of the sdaem property.
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
     * Gets the value of the urlab property.
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
     * Sets the value of the urlab property.
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
     * Gets the value of the urmemo property.
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
     * Sets the value of the urmemo property.
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
     * Gets the value of the invo property.
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
     * Sets the value of the invo property.
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
     * Gets the value of the invtyp property.
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
     * Sets the value of the invtyp property.
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
     * Gets the value of the invacc property.
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
     * Sets the value of the invacc property.
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
     * Gets the value of the invadd property.
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
     * Sets the value of the invadd property.
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
     * Gets the value of the invbank property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINVBANK() {
        return invbank;
    }

    /**
     * Sets the value of the invbank property.
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
     * Gets the value of the invnum property.
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
     * Sets the value of the invnum property.
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
     * Gets the value of the erdat property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getERDAT() {
        return erdat;
    }

    /**
     * Sets the value of the erdat property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setERDAT(XMLGregorianCalendar value) {
        this.erdat = value;
    }

    /**
     * Gets the value of the erzet property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getERZET() {
        return erzet;
    }

    /**
     * Sets the value of the erzet property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setERZET(XMLGregorianCalendar value) {
        this.erzet = value;
    }

    /**
     * Gets the value of the add1 property.
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
     * Sets the value of the add1 property.
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
     * Gets the value of the add2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD2() {
        return add2;
    }

    /**
     * Sets the value of the add2 property.
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
     * Gets the value of the add3 property.
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
     * Sets the value of the add3 property.
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
