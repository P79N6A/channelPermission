
package com.haier.stock.exchangegoodstoles;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="INPUT"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="ADD1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="ADD2" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="ARKTX" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="AUDAT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="AUTIM" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="BSTNK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="KUNAG" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="KUNNR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="KWMENG" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *                   &lt;element name="LGORT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="MATNR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="MEINS" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="POSNR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
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
@XmlType(name = "", propOrder = {
    "input"
})
@XmlRootElement(name = "ExchangeGoodsToLES")
public class ExchangeGoodsToLES_Type {

    @XmlElement(name = "INPUT", required = true)
    protected ExchangeGoodsToLES_Type.INPUT input;

    /**
     * 获取input属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ExchangeGoodsToLES_Type.INPUT }
     *     
     */
    public ExchangeGoodsToLES_Type.INPUT getINPUT() {
        return input;
    }

    /**
     * 设置input属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ExchangeGoodsToLES_Type.INPUT }
     *     
     */
    public void setINPUT(ExchangeGoodsToLES_Type.INPUT value) {
        this.input = value;
    }


    /**
     * <p>anonymous complex type的 Java 类。
     * 
     * <p>以下模式片段指定包含在此类中的预期内容。
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="ADD1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="ADD2" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="ARKTX" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="AUDAT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="AUTIM" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="BSTNK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="KUNAG" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="KUNNR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="KWMENG" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
     *         &lt;element name="LGORT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="MATNR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="MEINS" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="POSNR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "add1",
        "add2",
        "arktx",
        "audat",
        "autim",
        "bstnk",
        "kunag",
        "kunnr",
        "kwmeng",
        "lgort",
        "matnr",
        "meins",
        "posnr"
    })
    public static class INPUT {

        @XmlElement(name = "ADD1", required = true)
        protected String add1;
        @XmlElement(name = "ADD2", required = true)
        protected String add2;
        @XmlElement(name = "ARKTX", required = true)
        protected String arktx;
        @XmlElement(name = "AUDAT", required = true)
        protected String audat;
        @XmlElement(name = "AUTIM", required = true)
        protected String autim;
        @XmlElement(name = "BSTNK", required = true)
        protected String bstnk;
        @XmlElement(name = "KUNAG", required = true)
        protected String kunag;
        @XmlElement(name = "KUNNR", required = true)
        protected String kunnr;
        @XmlElement(name = "KWMENG", required = true)
        protected BigDecimal kwmeng;
        @XmlElement(name = "LGORT", required = true)
        protected String lgort;
        @XmlElement(name = "MATNR", required = true)
        protected String matnr;
        @XmlElement(name = "MEINS", required = true)
        protected String meins;
        @XmlElement(name = "POSNR", required = true)
        protected String posnr;

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
         * 获取arktx属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getARKTX() {
            return arktx;
        }

        /**
         * 设置arktx属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setARKTX(String value) {
            this.arktx = value;
        }

        /**
         * 获取audat属性的值。
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
         * 设置audat属性的值。
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
         * 获取autim属性的值。
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
         * 设置autim属性的值。
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
         * 获取kunag属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getKUNAG() {
            return kunag;
        }

        /**
         * 设置kunag属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setKUNAG(String value) {
            this.kunag = value;
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

    }

}
