
package com.haier.stock.canceltidan;

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
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="in">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ABGRU" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="VBELN" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "", propOrder = {
    "in"
})
@XmlRootElement(name = "JdeAndLesLoseValidity")
public class JdeAndLesLoseValidity_Type {

    @XmlElement(namespace = "", required = true)
    protected JdeAndLesLoseValidity_Type.In in;

    /**
     * 获取in属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JdeAndLesLoseValidity_Type.In }
     *     
     */
    public JdeAndLesLoseValidity_Type.In getIn() {
        return in;
    }

    /**
     * 设置in属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JdeAndLesLoseValidity_Type.In }
     *     
     */
    public void setIn(JdeAndLesLoseValidity_Type.In value) {
        this.in = value;
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
     *         &lt;element name="ABGRU" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="VBELN" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "abgru",
        "vbeln"
    })
    public static class In {

        @XmlElement(name = "ABGRU", namespace = "", required = true)
        protected String abgru;
        @XmlElement(name = "VBELN", namespace = "", required = true)
        protected String vbeln;

        /**
         * 获取abgru属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getABGRU() {
            return abgru;
        }

        /**
         * 设置abgru属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setABGRU(String value) {
            this.abgru = value;
        }

        /**
         * 获取vbeln属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getVBELN() {
            return vbeln;
        }

        /**
         * 设置vbeln属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setVBELN(String value) {
            this.vbeln = value;
        }

    }

}
