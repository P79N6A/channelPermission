
package com.haier.stock.eai.QueryTransferFeeFromHBDMToLES;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ZSTR_DIAOHUO complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ZSTR_DIAOHUO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BSTNK"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="20"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="AUART"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="4"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="KWMENG1"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal"&gt;
 *               &lt;totalDigits value="15"/&gt;
 *               &lt;fractionDigits value="3"/&gt;
 *               &lt;maxInclusive value="999999999999.999"/&gt;
 *               &lt;minInclusive value="-999999999999.999"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="CHARG"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="10"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="PRICE"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal"&gt;
 *               &lt;totalDigits value="15"/&gt;
 *               &lt;fractionDigits value="2"/&gt;
 *               &lt;maxInclusive value="9999999999999.99"/&gt;
 *               &lt;minInclusive value="-9999999999999.99"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="YSZQ"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;pattern value="\d+"/&gt;
 *               &lt;maxLength value="8"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="LR_NAME"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="12"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ERDAT_LR"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;pattern value="\d{4}-\d{2}-\d{2}"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ERZET_LR"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;pattern value="\d{2}:\d{2}:\d{2}"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ADD1"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="30"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ADD2"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="40"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZSTR_DIAOHUO", propOrder = {
    "bstnk",
    "auart",
    "kwmeng1",
    "charg",
    "price",
    "yszq",
    "lrname",
    "erdatlr",
    "erzetlr",
    "add1",
    "add2"
})
public class ZSTRDIAOHUO {

    @XmlElement(name = "BSTNK", required = true)
    protected String bstnk;
    @XmlElement(name = "AUART", required = true)
    protected String auart;
    @XmlElement(name = "KWMENG1", required = true)
    protected BigDecimal kwmeng1;
    @XmlElement(name = "CHARG", required = true)
    protected String charg;
    @XmlElement(name = "PRICE", required = true)
    protected BigDecimal price;
    @XmlElement(name = "YSZQ", required = true)
    protected String yszq;
    @XmlElement(name = "LR_NAME", required = true)
    protected String lrname;
    @XmlElement(name = "ERDAT_LR", required = true)
    protected String erdatlr;
    @XmlElement(name = "ERZET_LR", required = true)
    protected String erzetlr;
    @XmlElement(name = "ADD1", required = true)
    protected String add1;
    @XmlElement(name = "ADD2", required = true)
    protected String add2;

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
     * 获取kwmeng1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getKWMENG1() {
        return kwmeng1;
    }

    /**
     * 设置kwmeng1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setKWMENG1(BigDecimal value) {
        this.kwmeng1 = value;
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
     * 获取price属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPRICE() {
        return price;
    }

    /**
     * 设置price属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPRICE(BigDecimal value) {
        this.price = value;
    }

    /**
     * 获取yszq属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYSZQ() {
        return yszq;
    }

    /**
     * 设置yszq属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYSZQ(String value) {
        this.yszq = value;
    }

    /**
     * 获取lrname属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLRNAME() {
        return lrname;
    }

    /**
     * 设置lrname属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLRNAME(String value) {
        this.lrname = value;
    }

    /**
     * 获取erdatlr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getERDATLR() {
        return erdatlr;
    }

    /**
     * 设置erdatlr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setERDATLR(String value) {
        this.erdatlr = value;
    }

    /**
     * 获取erzetlr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getERZETLR() {
        return erzetlr;
    }

    /**
     * 设置erzetlr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setERZETLR(String value) {
        this.erzetlr = value;
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

}
