
package com.haier.stock.createscordertoles;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>InputType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="InputType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SOURCE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SOURCE_EXT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SOURCE_SN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BSTKD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="POSEX" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AUDAT" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="AUTIM" type="{http://www.w3.org/2001/XMLSchema}time" minOccurs="0"/&gt;
 *         &lt;element name="AUART" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="KUNNR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="KUNWE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="MATNR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="COMTYP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="KWMENG" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="MEINS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LGORT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CHARG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SDABW" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AUGRU" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BSTKD_E" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="POSNR_E" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="KBETR" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="KWERT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="SHIPCO" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="KWERZ" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="DHRXM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DHRPH" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SHRXM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SHRMOB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SHRTEL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PROV" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CITY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="COUNTY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ADDRESS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PSTLZ" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PAYSTE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PAYTYP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="YDDAT" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="YDTIME" type="{http://www.w3.org/2001/XMLSchema}time" minOccurs="0"/&gt;
 *         &lt;element name="SDMEMO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="KUNEM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="KUNZ1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SDAEM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="URLAB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="URMEMO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="INVO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="INVTYP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="INVACC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="INVADD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="INVBANK" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="INVNUM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ERDAT" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="ERZET" type="{http://www.w3.org/2001/XMLSchema}time" minOccurs="0"/&gt;
 *         &lt;element name="ADD1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ADD2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ADD3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
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
     * 获取source属性的值。
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
     * 设置source属性的值。
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
     * 获取sourceext属性的值。
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
     * 设置sourceext属性的值。
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
     * 获取sourcesn属性的值。
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
     * 设置sourcesn属性的值。
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
     * 获取bstkd属性的值。
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
     * 设置bstkd属性的值。
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
     * 获取posex属性的值。
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
     * 设置posex属性的值。
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
     * 获取audat属性的值。
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
     * 设置audat属性的值。
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
     * 获取autim属性的值。
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
     * 设置autim属性的值。
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
     * 获取auart属性的值。
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
     * 设置auart属性的值。
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
     * 获取kunnr属性的值。
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
     * 设置kunnr属性的值。
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
     * 获取kunwe属性的值。
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
     * 设置kunwe属性的值。
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
     * 获取matnr属性的值。
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
     * 设置matnr属性的值。
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
     * 获取comtyp属性的值。
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
     * 设置comtyp属性的值。
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
     * 获取kwmeng属性的值。
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
     * 设置kwmeng属性的值。
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
     * 获取meins属性的值。
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
     * 设置meins属性的值。
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
     * 获取lgort属性的值。
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
     * 设置lgort属性的值。
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
     * 获取charg属性的值。
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
     * 设置charg属性的值。
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
     * 获取sdabw属性的值。
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
     * 设置sdabw属性的值。
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
     * 获取augru属性的值。
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
     * 设置augru属性的值。
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
     * 获取bstkde属性的值。
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
     * 设置bstkde属性的值。
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
     * 获取posnre属性的值。
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
     * 设置posnre属性的值。
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
     * 获取kbetr属性的值。
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
     * 设置kbetr属性的值。
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
     * 获取kwert属性的值。
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
     * 设置kwert属性的值。
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
     * 获取shipco属性的值。
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
     * 设置shipco属性的值。
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
     * 获取kwerz属性的值。
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
     * 设置kwerz属性的值。
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
     * 获取dhrxm属性的值。
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
     * 设置dhrxm属性的值。
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
     * 获取dhrph属性的值。
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
     * 设置dhrph属性的值。
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
     * 获取shrxm属性的值。
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
     * 设置shrxm属性的值。
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
     * 获取shrmob属性的值。
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
     * 设置shrmob属性的值。
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
     * 获取shrtel属性的值。
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
     * 设置shrtel属性的值。
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
     * 获取prov属性的值。
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
     * 设置prov属性的值。
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
     * 获取city属性的值。
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
     * 设置city属性的值。
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
     * 获取county属性的值。
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
     * 设置county属性的值。
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
     * 获取address属性的值。
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
     * 设置address属性的值。
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
     * 获取pstlz属性的值。
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
     * 设置pstlz属性的值。
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
     * 获取payste属性的值。
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
     * 设置payste属性的值。
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
     * 获取paytyp属性的值。
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
     * 设置paytyp属性的值。
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
     * 获取yddat属性的值。
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
     * 设置yddat属性的值。
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
     * 获取ydtime属性的值。
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
     * 设置ydtime属性的值。
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
     * 获取sdmemo属性的值。
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
     * 设置sdmemo属性的值。
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
     * 获取kunem属性的值。
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
     * 设置kunem属性的值。
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
     * 获取kunz1属性的值。
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
     * 设置kunz1属性的值。
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
     * 获取sdaem属性的值。
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
     * 设置sdaem属性的值。
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
     * 获取urlab属性的值。
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
     * 设置urlab属性的值。
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
     * 获取urmemo属性的值。
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
     * 设置urmemo属性的值。
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
     * 获取invo属性的值。
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
     * 设置invo属性的值。
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
     * 获取invtyp属性的值。
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
     * 设置invtyp属性的值。
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
     * 获取invacc属性的值。
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
     * 设置invacc属性的值。
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
     * 获取invadd属性的值。
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
     * 设置invadd属性的值。
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
     * 获取invbank属性的值。
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
     * 设置invbank属性的值。
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
     * 获取invnum属性的值。
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
     * 设置invnum属性的值。
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
     * 获取erdat属性的值。
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
     * 设置erdat属性的值。
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
     * 获取erzet属性的值。
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
     * 设置erzet属性的值。
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
     * 获取add1属性的值。
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
     * 设置add1属性的值。
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
     * 获取add2属性的值。
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
     * 设置add2属性的值。
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
     * 获取add3属性的值。
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
     * 设置add3属性的值。
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
