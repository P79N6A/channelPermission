package com.haier.invoice.model.queryinvoiceinfofromtaxtoehaier;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;

/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="out">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="WDH" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="KHBM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="KHMC" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="KHSH" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="KHDZ" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="KHKHYHZH" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="BZ" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="WDRQ" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                   &lt;element name="CPDM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="CPMC" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="CPXH" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="CPDW" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="CPSL" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                   &lt;element name="HSDJ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                   &lt;element name="HSJE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                   &lt;element name="BHSDJ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                   &lt;element name="BHSJE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                   &lt;element name="JSJE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                   &lt;element name="JSSL" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                   &lt;element name="JFJE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                   &lt;element name="JLJE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                   &lt;element name="JLBZ" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="FPLX" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="FPZT" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="SKFS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="LBJSDH" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="KWBM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="HPTX" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="DDLX" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="FGSNO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="DDHNO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="WLNO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="ADD1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="ADD2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="RRRQ" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                   &lt;element name="GXRQ" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                   &lt;element name="FPHM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="KPRQ" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                   &lt;element name="SKRQ" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                   &lt;element name="KPMAN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="KPZT" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="ZFRQ" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "out" })
@XmlRootElement(name = "QueryInvoiceInfoFromTAXtoEHAIERResponse")
public class QueryInvoiceInfoFromTAXtoEHAIERResponse {

    @XmlElement(required = true)
    protected QueryInvoiceInfoFromTAXtoEHAIERResponse.Out out;

    /**
     * 获取out属性的值。
     *
     * @return
     *     possible object is
     *     {@link QueryInvoiceInfoFromTAXtoEHAIERResponse.Out }
     *
     */
    public QueryInvoiceInfoFromTAXtoEHAIERResponse.Out getOut() {
        return out;
    }

    /**
     * 设置out属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link QueryInvoiceInfoFromTAXtoEHAIERResponse.Out }
     *
     */
    public void setOut(QueryInvoiceInfoFromTAXtoEHAIERResponse.Out value) {
        this.out = value;
    }

    /**
     * <p>anonymous complex type的 Java 类。
     * 
     * <p>以下模式片段指定包含在此类中的预期内容。
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="WDH" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="KHBM" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="KHMC" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="KHSH" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="KHDZ" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="KHKHYHZH" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="BZ" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="WDRQ" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *         &lt;element name="CPDM" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="CPMC" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="CPXH" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="CPDW" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="CPSL" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *         &lt;element name="HSDJ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *         &lt;element name="HSJE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *         &lt;element name="BHSDJ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *         &lt;element name="BHSJE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *         &lt;element name="JSJE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *         &lt;element name="JSSL" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *         &lt;element name="JFJE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *         &lt;element name="JLJE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *         &lt;element name="JLBZ" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="FPLX" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="FPZT" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="SKFS" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="LBJSDH" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="KWBM" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="HPTX" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="DDLX" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="FGSNO" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="DDHNO" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="WLNO" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ADD1" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ADD2" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="RRRQ" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *         &lt;element name="GXRQ" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *         &lt;element name="FPHM" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="KPRQ" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *         &lt;element name="SKRQ" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *         &lt;element name="KPMAN" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="KPZT" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="ZFRQ" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "wdh", "khbm", "khmc", "khsh", "khdz", "khkhyhzh", "bz",
            "wdrq", "cpdm", "cpmc", "cpxh", "cpdw", "cpsl", "hsdj", "hsje", "bhsdj", "bhsje",
            "jsje", "jssl", "jfje", "jlje", "jlbz", "fplx", "fpzt", "skfs", "lbjsdh", "kwbm",
            "hptx", "ddlx", "fgsno", "ddhno", "wlno", "add1", "add2", "rrrq", "gxrq", "fphm",
            "kprq", "skrq", "kpman", "kpzt", "zfrq" })
    public static class Out {

        @XmlElement(name = "WDH", required = true)
        protected String wdh;
        @XmlElement(name = "KHBM", required = true)
        protected String khbm;
        @XmlElement(name = "KHMC", required = true)
        protected String khmc;
        @XmlElement(name = "KHSH", required = true)
        protected String khsh;
        @XmlElement(name = "KHDZ", required = true)
        protected String khdz;
        @XmlElement(name = "KHKHYHZH", required = true)
        protected String khkhyhzh;
        @XmlElement(name = "BZ", required = true)
        protected String bz;
        @XmlElement(name = "WDRQ", required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar wdrq;
        @XmlElement(name = "CPDM", required = true)
        protected String cpdm;
        @XmlElement(name = "CPMC", required = true)
        protected String cpmc;
        @XmlElement(name = "CPXH", required = true)
        protected String cpxh;
        @XmlElement(name = "CPDW", required = true)
        protected String cpdw;
        @XmlElement(name = "CPSL", required = true)
        protected BigDecimal cpsl;
        @XmlElement(name = "HSDJ", required = true)
        protected BigDecimal hsdj;
        @XmlElement(name = "HSJE", required = true)
        protected BigDecimal hsje;
        @XmlElement(name = "BHSDJ", required = true)
        protected BigDecimal bhsdj;
        @XmlElement(name = "BHSJE", required = true)
        protected BigDecimal bhsje;
        @XmlElement(name = "JSJE", required = true)
        protected BigDecimal jsje;
        @XmlElement(name = "JSSL", required = true)
        protected BigDecimal jssl;
        @XmlElement(name = "JFJE", required = true)
        protected BigDecimal jfje;
        @XmlElement(name = "JLJE", required = true)
        protected BigDecimal jlje;
        @XmlElement(name = "JLBZ", required = true)
        protected String jlbz;
        @XmlElement(name = "FPLX")
        protected int                  fplx;
        @XmlElement(name = "FPZT")
        protected int                  fpzt;
        @XmlElement(name = "SKFS", required = true)
        protected String skfs;
        @XmlElement(name = "LBJSDH", required = true)
        protected String lbjsdh;
        @XmlElement(name = "KWBM", required = true)
        protected String kwbm;
        @XmlElement(name = "HPTX")
        protected int                  hptx;
        @XmlElement(name = "DDLX", required = true)
        protected String ddlx;
        @XmlElement(name = "FGSNO", required = true)
        protected String fgsno;
        @XmlElement(name = "DDHNO", required = true)
        protected String ddhno;
        @XmlElement(name = "WLNO", required = true)
        protected String wlno;
        @XmlElement(name = "ADD1", required = true)
        protected String add1;
        @XmlElement(name = "ADD2", required = true)
        protected String add2;
        @XmlElement(name = "RRRQ", required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar rrrq;
        @XmlElement(name = "GXRQ", required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar gxrq;
        @XmlElement(name = "FPHM", required = true)
        protected String fphm;
        @XmlElement(name = "KPRQ", required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar kprq;
        @XmlElement(name = "SKRQ", required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar skrq;
        @XmlElement(name = "KPMAN", required = true)
        protected String kpman;
        @XmlElement(name = "KPZT")
        protected int                  kpzt;
        @XmlElement(name = "ZFRQ", required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar zfrq;

        /**
         * 获取wdh属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getWDH() {
            return wdh;
        }

        /**
         * 设置wdh属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setWDH(String value) {
            this.wdh = value;
        }

        /**
         * 获取khbm属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getKHBM() {
            return khbm;
        }

        /**
         * 设置khbm属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setKHBM(String value) {
            this.khbm = value;
        }

        /**
         * 获取khmc属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getKHMC() {
            return khmc;
        }

        /**
         * 设置khmc属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setKHMC(String value) {
            this.khmc = value;
        }

        /**
         * 获取khsh属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getKHSH() {
            return khsh;
        }

        /**
         * 设置khsh属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setKHSH(String value) {
            this.khsh = value;
        }

        /**
         * 获取khdz属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getKHDZ() {
            return khdz;
        }

        /**
         * 设置khdz属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setKHDZ(String value) {
            this.khdz = value;
        }

        /**
         * 获取khkhyhzh属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getKHKHYHZH() {
            return khkhyhzh;
        }

        /**
         * 设置khkhyhzh属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setKHKHYHZH(String value) {
            this.khkhyhzh = value;
        }

        /**
         * 获取bz属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBZ() {
            return bz;
        }

        /**
         * 设置bz属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBZ(String value) {
            this.bz = value;
        }

        /**
         * 获取wdrq属性的值。
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getWDRQ() {
            return wdrq;
        }

        /**
         * 设置wdrq属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setWDRQ(XMLGregorianCalendar value) {
            this.wdrq = value;
        }

        /**
         * 获取cpdm属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCPDM() {
            return cpdm;
        }

        /**
         * 设置cpdm属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCPDM(String value) {
            this.cpdm = value;
        }

        /**
         * 获取cpmc属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCPMC() {
            return cpmc;
        }

        /**
         * 设置cpmc属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCPMC(String value) {
            this.cpmc = value;
        }

        /**
         * 获取cpxh属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCPXH() {
            return cpxh;
        }

        /**
         * 设置cpxh属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCPXH(String value) {
            this.cpxh = value;
        }

        /**
         * 获取cpdw属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCPDW() {
            return cpdw;
        }

        /**
         * 设置cpdw属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCPDW(String value) {
            this.cpdw = value;
        }

        /**
         * 获取cpsl属性的值。
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getCPSL() {
            return cpsl;
        }

        /**
         * 设置cpsl属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setCPSL(BigDecimal value) {
            this.cpsl = value;
        }

        /**
         * 获取hsdj属性的值。
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getHSDJ() {
            return hsdj;
        }

        /**
         * 设置hsdj属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setHSDJ(BigDecimal value) {
            this.hsdj = value;
        }

        /**
         * 获取hsje属性的值。
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getHSJE() {
            return hsje;
        }

        /**
         * 设置hsje属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setHSJE(BigDecimal value) {
            this.hsje = value;
        }

        /**
         * 获取bhsdj属性的值。
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getBHSDJ() {
            return bhsdj;
        }

        /**
         * 设置bhsdj属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setBHSDJ(BigDecimal value) {
            this.bhsdj = value;
        }

        /**
         * 获取bhsje属性的值。
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getBHSJE() {
            return bhsje;
        }

        /**
         * 设置bhsje属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setBHSJE(BigDecimal value) {
            this.bhsje = value;
        }

        /**
         * 获取jsje属性的值。
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getJSJE() {
            return jsje;
        }

        /**
         * 设置jsje属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setJSJE(BigDecimal value) {
            this.jsje = value;
        }

        /**
         * 获取jssl属性的值。
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getJSSL() {
            return jssl;
        }

        /**
         * 设置jssl属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setJSSL(BigDecimal value) {
            this.jssl = value;
        }

        /**
         * 获取jfje属性的值。
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getJFJE() {
            return jfje;
        }

        /**
         * 设置jfje属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setJFJE(BigDecimal value) {
            this.jfje = value;
        }

        /**
         * 获取jlje属性的值。
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getJLJE() {
            return jlje;
        }

        /**
         * 设置jlje属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setJLJE(BigDecimal value) {
            this.jlje = value;
        }

        /**
         * 获取jlbz属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getJLBZ() {
            return jlbz;
        }

        /**
         * 设置jlbz属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setJLBZ(String value) {
            this.jlbz = value;
        }

        /**
         * 获取fplx属性的值。
         * 
         */
        public int getFPLX() {
            return fplx;
        }

        /**
         * 设置fplx属性的值。
         * 
         */
        public void setFPLX(int value) {
            this.fplx = value;
        }

        /**
         * 获取fpzt属性的值。
         * 
         */
        public int getFPZT() {
            return fpzt;
        }

        /**
         * 设置fpzt属性的值。
         * 
         */
        public void setFPZT(int value) {
            this.fpzt = value;
        }

        /**
         * 获取skfs属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSKFS() {
            return skfs;
        }

        /**
         * 设置skfs属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSKFS(String value) {
            this.skfs = value;
        }

        /**
         * 获取lbjsdh属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLBJSDH() {
            return lbjsdh;
        }

        /**
         * 设置lbjsdh属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLBJSDH(String value) {
            this.lbjsdh = value;
        }

        /**
         * 获取kwbm属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getKWBM() {
            return kwbm;
        }

        /**
         * 设置kwbm属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setKWBM(String value) {
            this.kwbm = value;
        }

        /**
         * 获取hptx属性的值。
         * 
         */
        public int getHPTX() {
            return hptx;
        }

        /**
         * 设置hptx属性的值。
         * 
         */
        public void setHPTX(int value) {
            this.hptx = value;
        }

        /**
         * 获取ddlx属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDDLX() {
            return ddlx;
        }

        /**
         * 设置ddlx属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDDLX(String value) {
            this.ddlx = value;
        }

        /**
         * 获取fgsno属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFGSNO() {
            return fgsno;
        }

        /**
         * 设置fgsno属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFGSNO(String value) {
            this.fgsno = value;
        }

        /**
         * 获取ddhno属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDDHNO() {
            return ddhno;
        }

        /**
         * 设置ddhno属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDDHNO(String value) {
            this.ddhno = value;
        }

        /**
         * 获取wlno属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getWLNO() {
            return wlno;
        }

        /**
         * 设置wlno属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setWLNO(String value) {
            this.wlno = value;
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
         * 获取rrrq属性的值。
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getRRRQ() {
            return rrrq;
        }

        /**
         * 设置rrrq属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setRRRQ(XMLGregorianCalendar value) {
            this.rrrq = value;
        }

        /**
         * 获取gxrq属性的值。
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getGXRQ() {
            return gxrq;
        }

        /**
         * 设置gxrq属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setGXRQ(XMLGregorianCalendar value) {
            this.gxrq = value;
        }

        /**
         * 获取fphm属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFPHM() {
            return fphm;
        }

        /**
         * 设置fphm属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFPHM(String value) {
            this.fphm = value;
        }

        /**
         * 获取kprq属性的值。
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getKPRQ() {
            return kprq;
        }

        /**
         * 设置kprq属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setKPRQ(XMLGregorianCalendar value) {
            this.kprq = value;
        }

        /**
         * 获取skrq属性的值。
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getSKRQ() {
            return skrq;
        }

        /**
         * 设置skrq属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setSKRQ(XMLGregorianCalendar value) {
            this.skrq = value;
        }

        /**
         * 获取kpman属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getKPMAN() {
            return kpman;
        }

        /**
         * 设置kpman属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setKPMAN(String value) {
            this.kpman = value;
        }

        /**
         * 获取kpzt属性的值。
         * 
         */
        public int getKPZT() {
            return kpzt;
        }

        /**
         * 设置kpzt属性的值。
         * 
         */
        public void setKPZT(int value) {
            this.kpzt = value;
        }

        /**
         * 获取zfrq属性的值。
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getZFRQ() {
            return zfrq;
        }

        /**
         * 设置zfrq属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setZFRQ(XMLGregorianCalendar value) {
            this.zfrq = value;
        }

    }

}
