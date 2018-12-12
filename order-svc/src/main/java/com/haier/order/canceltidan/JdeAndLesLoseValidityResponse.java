
package com.haier.order.canceltidan;

import javax.xml.bind.annotation.*;

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
 *                   &lt;element name="FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="MESSAGE" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "out"
})
@XmlRootElement(name = "JdeAndLesLoseValidityResponse")
public class JdeAndLesLoseValidityResponse {

    @XmlElement(namespace = "", required = true)
    protected Out out;

    /**
     * 获取out属性的值。
     *
     * @return
     *     possible object is
     *     {@link Out }
     *
     */
    public Out getOut() {
        return out;
    }

    /**
     * 设置out属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link Out }
     *
     */
    public void setOut(Out value) {
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
     *         &lt;element name="FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="MESSAGE" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "flag",
        "message"
    })
    public static class Out {

        @XmlElement(name = "FLAG", namespace = "", required = true)
        protected String flag;
        @XmlElement(name = "MESSAGE", namespace = "", required = true)
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
