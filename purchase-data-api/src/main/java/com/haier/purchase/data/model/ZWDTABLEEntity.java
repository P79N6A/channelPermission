
package com.haier.purchase.data.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ZWD_TABLE_2 complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ZWD_TABLE_2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BSTKD" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="GVS_SO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="KUNNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="KUNWE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ADD4" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ERDAT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ERZET" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AD1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AD2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AD3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZWD_TABLE_2", propOrder = {
    "bstkd",
    "gvsso",
    "kunnr",
    "kunwe",
    "add4",
    "erdat",
    "erzet",
    "ad1",
    "ad2",
    "ad3"
})
public class ZWDTABLEEntity implements Serializable{

    @XmlElement(name = "BSTKD", required = true)
    protected String bstkd;
    @XmlElement(name = "GVS_SO", required = true)
    protected String gvsso;
    @XmlElement(name = "KUNNR", required = true)
    protected String kunnr;
    @XmlElement(name = "KUNWE", required = true)
    protected String kunwe;
    @XmlElement(name = "ADD4", required = true)
    protected String add4;
    @XmlElement(name = "ERDAT", required = true)
    protected String erdat;
    @XmlElement(name = "ERZET", required = true)
    protected String erzet;
    @XmlElement(name = "AD1", required = true)
    protected String ad1;
    @XmlElement(name = "AD2", required = true)
    protected String ad2;
    @XmlElement(name = "AD3", required = true)
    protected String ad3;

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
     * 获取gvsso属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGVSSO() {
        return gvsso;
    }

    /**
     * 设置gvsso属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGVSSO(String value) {
        this.gvsso = value;
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
     * 获取erdat属性的值。
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
     * 设置erdat属性的值。
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
     * 获取erzet属性的值。
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
     * 设置erzet属性的值。
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
     * 获取ad1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAD1() {
        return ad1;
    }

    /**
     * 设置ad1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAD1(String value) {
        this.ad1 = value;
    }

    /**
     * 获取ad2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAD2() {
        return ad2;
    }

    /**
     * 设置ad2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAD2(String value) {
        this.ad2 = value;
    }

    /**
     * 获取ad3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAD3() {
        return ad3;
    }

    /**
     * 设置ad3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAD3(String value) {
        this.ad3 = value;
    }

}
