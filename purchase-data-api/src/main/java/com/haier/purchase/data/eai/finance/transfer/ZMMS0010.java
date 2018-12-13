
package com.haier.purchase.data.eai.finance.transfer;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ZMMS0010 complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ZMMS0010"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ZLSOT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ZLSOI" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ZLSIN" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ZLSII" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BLDAT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BUDAT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ZCBSN" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MATNR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MENGE" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="ZLGORT_O" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ZLGORT_I" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZMMS0010", propOrder = {
    "zlsot",
    "zlsoi",
    "zlsin",
    "zlsii",
    "bldat",
    "budat",
    "zcbsn",
    "matnr",
    "menge",
    "zlgorto",
    "zlgorti"
})
public class ZMMS0010 {

    @XmlElement(name = "ZLSOT", required = true)
    protected String zlsot;
    @XmlElement(name = "ZLSOI", required = true)
    protected String zlsoi;
    @XmlElement(name = "ZLSIN", required = true)
    protected String zlsin;
    @XmlElement(name = "ZLSII", required = true)
    protected String zlsii;
    @XmlElement(name = "BLDAT", required = true)
    protected String bldat;
    @XmlElement(name = "BUDAT", required = true)
    protected String budat;
    @XmlElement(name = "ZCBSN", required = true)
    protected String zcbsn;
    @XmlElement(name = "MATNR", required = true)
    protected String matnr;
    @XmlElement(name = "MENGE", required = true)
    protected BigDecimal menge;
    @XmlElement(name = "ZLGORT_O", required = true)
    protected String zlgorto;
    @XmlElement(name = "ZLGORT_I", required = true)
    protected String zlgorti;

    /**
     * 获取zlsot属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZLSOT() {
        return zlsot;
    }

    /**
     * 设置zlsot属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZLSOT(String value) {
        this.zlsot = value;
    }

    /**
     * 获取zlsoi属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZLSOI() {
        return zlsoi;
    }

    /**
     * 设置zlsoi属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZLSOI(String value) {
        this.zlsoi = value;
    }

    /**
     * 获取zlsin属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZLSIN() {
        return zlsin;
    }

    /**
     * 设置zlsin属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZLSIN(String value) {
        this.zlsin = value;
    }

    /**
     * 获取zlsii属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZLSII() {
        return zlsii;
    }

    /**
     * 设置zlsii属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZLSII(String value) {
        this.zlsii = value;
    }

    /**
     * 获取bldat属性的值。
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
     * 设置bldat属性的值。
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
     * 获取budat属性的值。
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
     * 设置budat属性的值。
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
     * 获取zcbsn属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZCBSN() {
        return zcbsn;
    }

    /**
     * 设置zcbsn属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZCBSN(String value) {
        this.zcbsn = value;
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
     * 获取menge属性的值。
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
     * 设置menge属性的值。
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
     * 获取zlgorto属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZLGORTO() {
        return zlgorto;
    }

    /**
     * 设置zlgorto属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZLGORTO(String value) {
        this.zlgorto = value;
    }

    /**
     * 获取zlgorti属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZLGORTI() {
        return zlgorti;
    }

    /**
     * 设置zlgorti属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZLGORTI(String value) {
        this.zlgorti = value;
    }

}
