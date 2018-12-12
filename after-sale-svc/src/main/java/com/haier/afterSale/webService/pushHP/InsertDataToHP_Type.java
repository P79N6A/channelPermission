
package com.haier.afterSale.webService.pushHP;

import java.math.BigDecimal;
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
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
 *                   &lt;element name="counts" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                   &lt;element name="createdDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                   &lt;element name="prodtypeId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="checkType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="requestServiceRemark" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="requestServiceDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                   &lt;element name="oldOrder" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="ifTk" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="tkReason" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="tkje" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                   &lt;element name="tkRemark" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="attribute1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="attribute2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="attribute3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="attribute4" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="attribute5" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="attribute6" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="attribute7" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="attribute8" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="attribute9" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="attribute10" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="procFlag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="procRemark" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="ifEcjd" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="tcCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="productNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="adressNew" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="customerName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="mobilePhone" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="telephone" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="mopPoi" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="county" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="ifJd" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="ifLh" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="sourceNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="orderDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                   &lt;element name="source" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="sourceSn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="productType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="prov" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="types" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "inputs"
})
@XmlRootElement(name = "InsertDataToHP")
public class InsertDataToHP_Type {

    @XmlElement(name = "Inputs")
    protected List<Inputs> inputs;

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
     * {@link Inputs }
     * 
     * 
     */
    public List<Inputs> getInputs() {
        if (inputs == null) {
            inputs = new ArrayList<Inputs>();
        }
        return this.inputs;
    }


    /**
     * <p>anonymous complex type�� Java �ࡣ
     * 
     * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="orderNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="counts" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *         &lt;element name="createdDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *         &lt;element name="prodtypeId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="checkType" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="requestServiceRemark" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="requestServiceDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *         &lt;element name="oldOrder" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ifTk" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="tkReason" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="tkje" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *         &lt;element name="tkRemark" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="attribute1" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="attribute2" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="attribute3" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="attribute4" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="attribute5" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="attribute6" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="attribute7" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="attribute8" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="attribute9" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="attribute10" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="procFlag" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="procRemark" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ifEcjd" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="tcCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="productNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="adressNew" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="customerName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="mobilePhone" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="telephone" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="mopPoi" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="county" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ifJd" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ifLh" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="sourceNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="orderDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *         &lt;element name="source" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="sourceSn" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="productType" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="prov" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="types" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "orderNo",
        "counts",
        "createdDate",
        "prodtypeId",
        "type",
        "checkType",
        "requestServiceRemark",
        "requestServiceDate",
        "oldOrder",
        "remark",
        "ifTk",
        "tkReason",
        "tkje",
        "tkRemark",
        "attribute1",
        "attribute2",
        "attribute3",
        "attribute4",
        "attribute5",
        "attribute6",
        "attribute7",
        "attribute8",
        "attribute9",
        "attribute10",
        "procFlag",
        "procRemark",
        "ifEcjd",
        "tcCode",
        "productNo",
        "adressNew",
        "customerName",
        "mobilePhone",
        "telephone",
        "mopPoi",
        "county",
        "ifJd",
        "ifLh",
        "sourceNo",
        "orderDate",
        "source",
        "sourceSn",
        "productType",
        "prov",
        "city",
        "types"
    })
    public static class Inputs {

        @XmlElement(required = true)
        protected String orderNo;
        @XmlElement(required = true)
        protected BigDecimal counts;
        @XmlElement(required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar createdDate;
        @XmlElement(required = true)
        protected String prodtypeId;
        @XmlElement(required = true)
        protected String type;
        @XmlElement(required = true)
        protected String checkType;
        @XmlElement(required = true)
        protected String requestServiceRemark;
        @XmlElement(required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar requestServiceDate;
        @XmlElement(required = true)
        protected String oldOrder;
        @XmlElement(required = true)
        protected String remark;
        @XmlElement(required = true)
        protected String ifTk;
        @XmlElement(required = true)
        protected String tkReason;
        @XmlElement(required = true)
        protected BigDecimal tkje;
        @XmlElement(required = true)
        protected String tkRemark;
        @XmlElement(required = true)
        protected String attribute1;
        @XmlElement(required = true)
        protected String attribute2;
        @XmlElement(required = true)
        protected String attribute3;
        @XmlElement(required = true)
        protected String attribute4;
        @XmlElement(required = true)
        protected String attribute5;
        @XmlElement(required = true)
        protected String attribute6;
        @XmlElement(required = true)
        protected String attribute7;
        @XmlElement(required = true)
        protected String attribute8;
        @XmlElement(required = true)
        protected String attribute9;
        @XmlElement(required = true)
        protected String attribute10;
        @XmlElement(required = true)
        protected String procFlag;
        @XmlElement(required = true)
        protected String procRemark;
        @XmlElement(required = true)
        protected String ifEcjd;
        @XmlElement(required = true)
        protected String tcCode;
        @XmlElement(required = true)
        protected String productNo;
        @XmlElement(required = true)
        protected String adressNew;
        @XmlElement(required = true)
        protected String customerName;
        @XmlElement(required = true)
        protected String mobilePhone;
        @XmlElement(required = true)
        protected String telephone;
        @XmlElement(required = true)
        protected String mopPoi;
        @XmlElement(required = true)
        protected String county;
        @XmlElement(required = true)
        protected String ifJd;
        @XmlElement(required = true)
        protected String ifLh;
        @XmlElement(required = true)
        protected String sourceNo;
        @XmlElement(required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar orderDate;
        @XmlElement(required = true)
        protected String source;
        @XmlElement(required = true)
        protected String sourceSn;
        @XmlElement(required = true)
        protected String productType;
        @XmlElement(required = true)
        protected String prov;
        @XmlElement(required = true)
        protected String city;
        @XmlElement(required = true)
        protected String types;

        /**
         * ��ȡorderNo���Ե�ֵ��
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
         * ����orderNo���Ե�ֵ��
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
         * ��ȡcounts���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getCounts() {
            return counts;
        }

        /**
         * ����counts���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setCounts(BigDecimal value) {
            this.counts = value;
        }

        /**
         * ��ȡcreatedDate���Ե�ֵ��
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
         * ����createdDate���Ե�ֵ��
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
         * ��ȡprodtypeId���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProdtypeId() {
            return prodtypeId;
        }

        /**
         * ����prodtypeId���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProdtypeId(String value) {
            this.prodtypeId = value;
        }

        /**
         * ��ȡtype���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getType() {
            return type;
        }

        /**
         * ����type���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setType(String value) {
            this.type = value;
        }

        /**
         * ��ȡcheckType���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCheckType() {
            return checkType;
        }

        /**
         * ����checkType���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCheckType(String value) {
            this.checkType = value;
        }

        /**
         * ��ȡrequestServiceRemark���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRequestServiceRemark() {
            return requestServiceRemark;
        }

        /**
         * ����requestServiceRemark���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRequestServiceRemark(String value) {
            this.requestServiceRemark = value;
        }

        /**
         * ��ȡrequestServiceDate���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getRequestServiceDate() {
            return requestServiceDate;
        }

        /**
         * ����requestServiceDate���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setRequestServiceDate(XMLGregorianCalendar value) {
            this.requestServiceDate = value;
        }

        /**
         * ��ȡoldOrder���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOldOrder() {
            return oldOrder;
        }

        /**
         * ����oldOrder���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOldOrder(String value) {
            this.oldOrder = value;
        }

        /**
         * ��ȡremark���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRemark() {
            return remark;
        }

        /**
         * ����remark���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRemark(String value) {
            this.remark = value;
        }

        /**
         * ��ȡifTk���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIfTk() {
            return ifTk;
        }

        /**
         * ����ifTk���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIfTk(String value) {
            this.ifTk = value;
        }

        /**
         * ��ȡtkReason���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTkReason() {
            return tkReason;
        }

        /**
         * ����tkReason���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTkReason(String value) {
            this.tkReason = value;
        }

        /**
         * ��ȡtkje���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getTkje() {
            return tkje;
        }

        /**
         * ����tkje���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setTkje(BigDecimal value) {
            this.tkje = value;
        }

        /**
         * ��ȡtkRemark���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTkRemark() {
            return tkRemark;
        }

        /**
         * ����tkRemark���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTkRemark(String value) {
            this.tkRemark = value;
        }

        /**
         * ��ȡattribute1���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAttribute1() {
            return attribute1;
        }

        /**
         * ����attribute1���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAttribute1(String value) {
            this.attribute1 = value;
        }

        /**
         * ��ȡattribute2���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAttribute2() {
            return attribute2;
        }

        /**
         * ����attribute2���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAttribute2(String value) {
            this.attribute2 = value;
        }

        /**
         * ��ȡattribute3���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAttribute3() {
            return attribute3;
        }

        /**
         * ����attribute3���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAttribute3(String value) {
            this.attribute3 = value;
        }

        /**
         * ��ȡattribute4���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAttribute4() {
            return attribute4;
        }

        /**
         * ����attribute4���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAttribute4(String value) {
            this.attribute4 = value;
        }

        /**
         * ��ȡattribute5���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAttribute5() {
            return attribute5;
        }

        /**
         * ����attribute5���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAttribute5(String value) {
            this.attribute5 = value;
        }

        /**
         * ��ȡattribute6���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAttribute6() {
            return attribute6;
        }

        /**
         * ����attribute6���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAttribute6(String value) {
            this.attribute6 = value;
        }

        /**
         * ��ȡattribute7���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAttribute7() {
            return attribute7;
        }

        /**
         * ����attribute7���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAttribute7(String value) {
            this.attribute7 = value;
        }

        /**
         * ��ȡattribute8���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAttribute8() {
            return attribute8;
        }

        /**
         * ����attribute8���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAttribute8(String value) {
            this.attribute8 = value;
        }

        /**
         * ��ȡattribute9���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAttribute9() {
            return attribute9;
        }

        /**
         * ����attribute9���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAttribute9(String value) {
            this.attribute9 = value;
        }

        /**
         * ��ȡattribute10���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAttribute10() {
            return attribute10;
        }

        /**
         * ����attribute10���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAttribute10(String value) {
            this.attribute10 = value;
        }

        /**
         * ��ȡprocFlag���Ե�ֵ��
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
         * ����procFlag���Ե�ֵ��
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
         * ��ȡprocRemark���Ե�ֵ��
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
         * ����procRemark���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProcRemark(String value) {
            this.procRemark = value;
        }

        /**
         * ��ȡifEcjd���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIfEcjd() {
            return ifEcjd;
        }

        /**
         * ����ifEcjd���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIfEcjd(String value) {
            this.ifEcjd = value;
        }

        /**
         * ��ȡtcCode���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTcCode() {
            return tcCode;
        }

        /**
         * ����tcCode���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTcCode(String value) {
            this.tcCode = value;
        }

        /**
         * ��ȡproductNo���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProductNo() {
            return productNo;
        }

        /**
         * ����productNo���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProductNo(String value) {
            this.productNo = value;
        }

        /**
         * ��ȡadressNew���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAdressNew() {
            return adressNew;
        }

        /**
         * ����adressNew���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAdressNew(String value) {
            this.adressNew = value;
        }

        /**
         * ��ȡcustomerName���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustomerName() {
            return customerName;
        }

        /**
         * ����customerName���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustomerName(String value) {
            this.customerName = value;
        }

        /**
         * ��ȡmobilePhone���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMobilePhone() {
            return mobilePhone;
        }

        /**
         * ����mobilePhone���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMobilePhone(String value) {
            this.mobilePhone = value;
        }

        /**
         * ��ȡtelephone���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTelephone() {
            return telephone;
        }

        /**
         * ����telephone���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTelephone(String value) {
            this.telephone = value;
        }

        /**
         * ��ȡmopPoi���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMopPoi() {
            return mopPoi;
        }

        /**
         * ����mopPoi���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMopPoi(String value) {
            this.mopPoi = value;
        }

        /**
         * ��ȡcounty���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCounty() {
            return county;
        }

        /**
         * ����county���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCounty(String value) {
            this.county = value;
        }

        /**
         * ��ȡifJd���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIfJd() {
            return ifJd;
        }

        /**
         * ����ifJd���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIfJd(String value) {
            this.ifJd = value;
        }

        /**
         * ��ȡifLh���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIfLh() {
            return ifLh;
        }

        /**
         * ����ifLh���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIfLh(String value) {
            this.ifLh = value;
        }

        /**
         * ��ȡsourceNo���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSourceNo() {
            return sourceNo;
        }

        /**
         * ����sourceNo���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSourceNo(String value) {
            this.sourceNo = value;
        }

        /**
         * ��ȡorderDate���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getOrderDate() {
            return orderDate;
        }

        /**
         * ����orderDate���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setOrderDate(XMLGregorianCalendar value) {
            this.orderDate = value;
        }

        /**
         * ��ȡsource���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSource() {
            return source;
        }

        /**
         * ����source���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSource(String value) {
            this.source = value;
        }

        /**
         * ��ȡsourceSn���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSourceSn() {
            return sourceSn;
        }

        /**
         * ����sourceSn���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSourceSn(String value) {
            this.sourceSn = value;
        }

        /**
         * ��ȡproductType���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProductType() {
            return productType;
        }

        /**
         * ����productType���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProductType(String value) {
            this.productType = value;
        }

        /**
         * ��ȡprov���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProv() {
            return prov;
        }

        /**
         * ����prov���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProv(String value) {
            this.prov = value;
        }

        /**
         * ��ȡcity���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCity() {
            return city;
        }

        /**
         * ����city���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCity(String value) {
            this.city = value;
        }

        /**
         * ��ȡtypes���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTypes() {
            return types;
        }

        /**
         * ����types���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTypes(String value) {
            this.types = value;
        }

    }

}
