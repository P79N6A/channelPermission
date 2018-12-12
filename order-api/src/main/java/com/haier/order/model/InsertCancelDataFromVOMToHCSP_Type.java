package com.haier.order.model;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

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
 *         &lt;element name="Inputs" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="orderNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="cancelDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                   &lt;element name="createdDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                   &lt;element name="procFlag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="procRemark" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "", propOrder = { "inputs" })
@XmlRootElement(name = "InsertCancelDataFromVOMToHCSP")
public class InsertCancelDataFromVOMToHCSP_Type {

    @XmlElement(name = "Inputs")
    protected List<InsertCancelDataFromVOMToHCSP_Type.Inputs> inputs;

    /**
     * Gets the value of the inputs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inputs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInputs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InsertCancelDataFromVOMToHCSP_Type.Inputs }
     * 
     * 
     */
    public List<InsertCancelDataFromVOMToHCSP_Type.Inputs> getInputs() {
        if (inputs == null) {
            inputs = new ArrayList<InsertCancelDataFromVOMToHCSP_Type.Inputs>();
        }
        return this.inputs;
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
     *         &lt;element name="orderNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="cancelDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *         &lt;element name="createdDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *         &lt;element name="procFlag" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="procRemark" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "orderNo", "cancelDate", "createdDate", "procFlag",
            "procRemark" })
    public static class Inputs {

        @XmlElement(required = true)
        protected String               orderNo;
        @XmlElement(required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar cancelDate;
        @XmlElement(required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar createdDate;
        @XmlElement(required = true)
        protected String               procFlag;
        @XmlElement(required = true)
        protected String               procRemark;

        /**
         * 获取orderNo属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOrderNo() {
            return orderNo;
        }

        /**
         * 设置orderNo属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOrderNo(String value) {
            this.orderNo = value;
        }

        /**
         * 获取cancelDate属性的值。
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getCancelDate() {
            return cancelDate;
        }

        /**
         * 设置cancelDate属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setCancelDate(XMLGregorianCalendar value) {
            this.cancelDate = value;
        }

        /**
         * 获取createdDate属性的值。
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getCreatedDate() {
            return createdDate;
        }

        /**
         * 设置createdDate属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setCreatedDate(XMLGregorianCalendar value) {
            this.createdDate = value;
        }

        /**
         * 获取procFlag属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProcFlag() {
            return procFlag;
        }

        /**
         * 设置procFlag属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProcFlag(String value) {
            this.procFlag = value;
        }

        /**
         * 获取procRemark属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProcRemark() {
            return procRemark;
        }

        /**
         * 设置procRemark属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProcRemark(String value) {
            this.procRemark = value;
        }

    }

}
