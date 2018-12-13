
package com.haier.stock.eai.QueryTransferFeeFromHBDMToLES;

import java.util.ArrayList;
import java.util.List;
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
 *       &lt;all&gt;
 *         &lt;element name="INPUT" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="item" type="{urn:sap-com:document:sap:rfc:functions:ZWD_CBSINT01_NEW}ZCBS_DIAOBO_IN" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="OUTPUT" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="item" type="{urn:sap-com:document:sap:rfc:functions:ZWD_CBSINT01_NEW}ZSTR_DIAOHUO" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/all&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "ZWD_CBSINT01_NEW")
public class ZWDCBSINT01NEW {

    @XmlElement(name = "INPUT")
    protected ZWDCBSINT01NEW.INPUT input;
    @XmlElement(name = "OUTPUT")
    protected ZWDCBSINT01NEW.OUTPUT output;

    /**
     * 获取input属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ZWDCBSINT01NEW.INPUT }
     *     
     */
    public ZWDCBSINT01NEW.INPUT getINPUT() {
        return input;
    }

    /**
     * 设置input属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ZWDCBSINT01NEW.INPUT }
     *     
     */
    public void setINPUT(ZWDCBSINT01NEW.INPUT value) {
        this.input = value;
    }

    /**
     * 获取output属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ZWDCBSINT01NEW.OUTPUT }
     *     
     */
    public ZWDCBSINT01NEW.OUTPUT getOUTPUT() {
        return output;
    }

    /**
     * 设置output属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ZWDCBSINT01NEW.OUTPUT }
     *     
     */
    public void setOUTPUT(ZWDCBSINT01NEW.OUTPUT value) {
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
     *         &lt;element name="item" type="{urn:sap-com:document:sap:rfc:functions:ZWD_CBSINT01_NEW}ZCBS_DIAOBO_IN" maxOccurs="unbounded" minOccurs="0"/&gt;
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
        "item"
    })
    public static class INPUT {

        protected List<ZCBSDIAOBOIN> item;

        /**
         * Gets the value of the item property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the item property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ZCBSDIAOBOIN }
         * 
         * 
         */
        public List<ZCBSDIAOBOIN> getItem() {
            if (item == null) {
                item = new ArrayList<ZCBSDIAOBOIN>();
            }
            return this.item;
        }

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
     *         &lt;element name="item" type="{urn:sap-com:document:sap:rfc:functions:ZWD_CBSINT01_NEW}ZSTR_DIAOHUO" maxOccurs="unbounded" minOccurs="0"/&gt;
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
        "item"
    })
    public static class OUTPUT {

        protected List<ZSTRDIAOHUO> item;

        /**
         * Gets the value of the item property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the item property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ZSTRDIAOHUO }
         * 
         * 
         */
        public List<ZSTRDIAOHUO> getItem() {
            if (item == null) {
                item = new ArrayList<ZSTRDIAOHUO>();
            }
            return this.item;
        }

    }

}
