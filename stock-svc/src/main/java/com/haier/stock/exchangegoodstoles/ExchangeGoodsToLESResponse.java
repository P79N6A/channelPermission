
package com.haier.stock.exchangegoodstoles;

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
 *         &lt;element name="OUTPUT"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="MESSAGE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "output"
})
@XmlRootElement(name = "ExchangeGoodsToLESResponse")
public class ExchangeGoodsToLESResponse {

    @XmlElement(name = "OUTPUT", required = true)
    protected ExchangeGoodsToLESResponse.OUTPUT output;

    /**
     * 获取output属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ExchangeGoodsToLESResponse.OUTPUT }
     *     
     */
    public ExchangeGoodsToLESResponse.OUTPUT getOUTPUT() {
        return output;
    }

    /**
     * 设置output属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ExchangeGoodsToLESResponse.OUTPUT }
     *     
     */
    public void setOUTPUT(ExchangeGoodsToLESResponse.OUTPUT value) {
        this.output = value;
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
     *         &lt;element name="FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="MESSAGE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "flag",
        "message"
    })
    public static class OUTPUT {

        @XmlElement(name = "FLAG", required = true)
        protected String flag;
        @XmlElement(name = "MESSAGE", required = true)
        protected String message;

        /**
         * 获取flag属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFLAG() {
            return flag;
        }

        /**
         * 设置flag属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFLAG(String value) {
            this.flag = value;
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

    }

}
