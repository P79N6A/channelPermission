package com.haier.invoice.model.queryinvoiceinfofromtaxtoehaier;

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
 *         &lt;element name="in">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="WDH" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "", propOrder = { "in" })
@XmlRootElement(name = "QueryInvoiceInfoFromTAXtoEHAIER")
public class QueryInvoiceInfoFromTAXtoEHAIER_Type {

    @XmlElement(required = true)
    protected QueryInvoiceInfoFromTAXtoEHAIER_Type.In in;

    /**
     * 获取in属性的值。
     *
     * @return
     *     possible object is
     *     {@link QueryInvoiceInfoFromTAXtoEHAIER_Type.In }
     *
     */
    public QueryInvoiceInfoFromTAXtoEHAIER_Type.In getIn() {
        return in;
    }

    /**
     * 设置in属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link QueryInvoiceInfoFromTAXtoEHAIER_Type.In }
     *
     */
    public void setIn(QueryInvoiceInfoFromTAXtoEHAIER_Type.In value) {
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
     *         &lt;element name="WDH" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "wdh" })
    public static class In {

        @XmlElement(name = "WDH", required = true)
        protected String wdh;

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

    }

}
