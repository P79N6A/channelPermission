
package com.haier.vehicle.wsdl.queryDNFrom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ZINT_WX_WT_LOG complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ZINT_WX_WT_LOG"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="MANDT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BSTNK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BSTKD" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="POSNR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="TKNUM" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SOURCE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SOURCE_SN" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CRDAT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CRZET" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SDABW" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="KUNNR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="KUNWE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="NAME1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MESSAGE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD2" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD3" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD4" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD5" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD6" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD7" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD8" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD9" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD10" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZINT_WX_WT_LOG", propOrder = {
    "mandt",
    "bstnk",
    "bstkd",
    "posnr",
    "tknum",
    "source",
    "sourcesn",
    "crdat",
    "crzet",
    "sdabw",
    "kunnr",
    "kunwe",
    "name1",
    "message",
    "add1",
    "add2",
    "add3",
    "add4",
    "add5",
    "add6",
    "add7",
    "add8",
    "add9",
    "add10"
})
public class ZINTWXWTLOG {

    @XmlElement(name = "MANDT", required = true)
    protected String mandt;
    @XmlElement(name = "BSTNK", required = true)
    protected String bstnk;
    @XmlElement(name = "BSTKD", required = true)
    protected String bstkd;
    @XmlElement(name = "POSNR", required = true)
    protected String posnr;
    @XmlElement(name = "TKNUM", required = true)
    protected String tknum;
    @XmlElement(name = "SOURCE", required = true)
    protected String source;
    @XmlElement(name = "SOURCE_SN", required = true)
    protected String sourcesn;
    @XmlElement(name = "CRDAT", required = true)
    protected String crdat;
    @XmlElement(name = "CRZET", required = true)
    protected String crzet;
    @XmlElement(name = "SDABW", required = true)
    protected String sdabw;
    @XmlElement(name = "KUNNR", required = true)
    protected String kunnr;
    @XmlElement(name = "KUNWE", required = true)
    protected String kunwe;
    @XmlElement(name = "NAME1", required = true)
    protected String name1;
    @XmlElement(name = "MESSAGE", required = true)
    protected String message;
    @XmlElement(name = "ADD1", required = true)
    protected String add1;
    @XmlElement(name = "ADD2", required = true)
    protected String add2;
    @XmlElement(name = "ADD3", required = true)
    protected String add3;
    @XmlElement(name = "ADD4", required = true)
    protected String add4;
    @XmlElement(name = "ADD5", required = true)
    protected String add5;
    @XmlElement(name = "ADD6", required = true)
    protected String add6;
    @XmlElement(name = "ADD7", required = true)
    protected String add7;
    @XmlElement(name = "ADD8", required = true)
    protected String add8;
    @XmlElement(name = "ADD9", required = true)
    protected String add9;
    @XmlElement(name = "ADD10", required = true)
    protected String add10;

    /**
     * 获取mandt属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMANDT() {
        return mandt;
    }

    /**
     * 设置mandt属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMANDT(String value) {
        this.mandt = value;
    }

    /**
     * 获取bstnk属性的值。
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
     * 设置bstnk属性的值。
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
     * 获取posnr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPOSNR() {
        return posnr;
    }

    /**
     * 设置posnr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPOSNR(String value) {
        this.posnr = value;
    }

    /**
     * 获取tknum属性的值。
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
     * 设置tknum属性的值。
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
     * 获取crdat属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCRDAT() {
        return crdat;
    }

    /**
     * 设置crdat属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCRDAT(String value) {
        this.crdat = value;
    }

    /**
     * 获取crzet属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCRZET() {
        return crzet;
    }

    /**
     * 设置crzet属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCRZET(String value) {
        this.crzet = value;
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
     * 获取name1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNAME1() {
        return name1;
    }

    /**
     * 设置name1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNAME1(String value) {
        this.name1 = value;
    }

    /**
     * 获取message属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMESSAGE() {
        return message;
    }

    /**
     * 设置message属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMESSAGE(String value) {
        this.message = value;
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

    /**
     * 获取add4属性的值。
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
     * 设置add4属性的值。
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
     * 获取add5属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD5() {
        return add5;
    }

    /**
     * 设置add5属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD5(String value) {
        this.add5 = value;
    }

    /**
     * 获取add6属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD6() {
        return add6;
    }

    /**
     * 设置add6属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD6(String value) {
        this.add6 = value;
    }

    /**
     * 获取add7属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD7() {
        return add7;
    }

    /**
     * 设置add7属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD7(String value) {
        this.add7 = value;
    }

    /**
     * 获取add8属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD8() {
        return add8;
    }

    /**
     * 设置add8属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD8(String value) {
        this.add8 = value;
    }

    /**
     * 获取add9属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD9() {
        return add9;
    }

    /**
     * 设置add9属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD9(String value) {
        this.add9 = value;
    }

    /**
     * 获取add10属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD10() {
        return add10;
    }

    /**
     * 设置add10属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD10(String value) {
        this.add10 = value;
    }

}
