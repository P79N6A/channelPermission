
package com.haier.svc.purchase.omsquery;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>ResponseData complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ResponseData"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="actualShipDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="cancelreason" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="confirmDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="custOrderCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="custPoDetailCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="custrevqty" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="gvsDN" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="gvsOrderCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="latestArrivalTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="latestLeaveBaseDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="madeFectoryCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="madeFectoryName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oesPredictrevDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="orderSoCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="orderState" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="orderTypeName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="plannedShipDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="podDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="prodSeriesCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="promisedArrivalDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="reqArrivalDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="signDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="submitDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="tradeSendDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="transitArrivalDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="transitCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="wpOrderId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Message" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Flag" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="FaultDetail" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseData", propOrder = {
    "actualShipDate",
    "cancelreason",
    "confirmDate",
    "custOrderCode",
    "custPoDetailCode",
    "custrevqty",
    "gvsDN",
    "gvsOrderCode",
    "latestArrivalTime",
    "latestLeaveBaseDate",
    "madeFectoryCode",
    "madeFectoryName",
    "oesPredictrevDate",
    "orderSoCode",
    "orderState",
    "orderTypeName",
    "plannedShipDate",
    "podDate",
    "prodSeriesCode",
    "promisedArrivalDate",
    "reqArrivalDate",
    "signDate",
    "submitDate",
    "tradeSendDate",
    "transitArrivalDate",
    "transitCode",
    "wpOrderId",
    "message",
    "flag",
    "faultDetail"
})
public class ResponseData {

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar actualShipDate;
    @XmlElement(required = true)
    protected String cancelreason;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar confirmDate;
    @XmlElement(required = true)
    protected String custOrderCode;
    @XmlElement(required = true)
    protected String custPoDetailCode;
    protected double custrevqty;
    @XmlElement(required = true)
    protected String gvsDN;
    @XmlElement(required = true)
    protected String gvsOrderCode;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar latestArrivalTime;
    @XmlElement(required = true)
    protected String latestLeaveBaseDate;
    @XmlElement(required = true)
    protected String madeFectoryCode;
    @XmlElement(required = true)
    protected String madeFectoryName;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar oesPredictrevDate;
    @XmlElement(required = true)
    protected String orderSoCode;
    @XmlElement(required = true)
    protected String orderState;
    @XmlElement(required = true)
    protected String orderTypeName;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar plannedShipDate;
    @XmlElement(required = true)
    protected String podDate;
    @XmlElement(required = true)
    protected String prodSeriesCode;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar promisedArrivalDate;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar reqArrivalDate;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar signDate;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar submitDate;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar tradeSendDate;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar transitArrivalDate;
    @XmlElement(required = true)
    protected String transitCode;
    @XmlElement(required = true)
    protected String wpOrderId;
    @XmlElement(name = "Message", required = true)
    protected String message;
    @XmlElement(name = "Flag", required = true)
    protected String flag;
    @XmlElement(name = "FaultDetail", required = true)
    protected String faultDetail;

    /**
     * 获取actualShipDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getActualShipDate() {
        return actualShipDate;
    }

    /**
     * 设置actualShipDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setActualShipDate(XMLGregorianCalendar value) {
        this.actualShipDate = value;
    }

    /**
     * 获取cancelreason属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCancelreason() {
        return cancelreason;
    }

    /**
     * 设置cancelreason属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCancelreason(String value) {
        this.cancelreason = value;
    }

    /**
     * 获取confirmDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getConfirmDate() {
        return confirmDate;
    }

    /**
     * 设置confirmDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setConfirmDate(XMLGregorianCalendar value) {
        this.confirmDate = value;
    }

    /**
     * 获取custOrderCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustOrderCode() {
        return custOrderCode;
    }

    /**
     * 设置custOrderCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustOrderCode(String value) {
        this.custOrderCode = value;
    }

    /**
     * 获取custPoDetailCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustPoDetailCode() {
        return custPoDetailCode;
    }

    /**
     * 设置custPoDetailCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustPoDetailCode(String value) {
        this.custPoDetailCode = value;
    }

    /**
     * 获取custrevqty属性的值。
     * 
     */
    public double getCustrevqty() {
        return custrevqty;
    }

    /**
     * 设置custrevqty属性的值。
     * 
     */
    public void setCustrevqty(double value) {
        this.custrevqty = value;
    }

    /**
     * 获取gvsDN属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGvsDN() {
        return gvsDN;
    }

    /**
     * 设置gvsDN属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGvsDN(String value) {
        this.gvsDN = value;
    }

    /**
     * 获取gvsOrderCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGvsOrderCode() {
        return gvsOrderCode;
    }

    /**
     * 设置gvsOrderCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGvsOrderCode(String value) {
        this.gvsOrderCode = value;
    }

    /**
     * 获取latestArrivalTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLatestArrivalTime() {
        return latestArrivalTime;
    }

    /**
     * 设置latestArrivalTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLatestArrivalTime(XMLGregorianCalendar value) {
        this.latestArrivalTime = value;
    }

    /**
     * 获取latestLeaveBaseDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLatestLeaveBaseDate() {
        return latestLeaveBaseDate;
    }

    /**
     * 设置latestLeaveBaseDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLatestLeaveBaseDate(String value) {
        this.latestLeaveBaseDate = value;
    }

    /**
     * 获取madeFectoryCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMadeFectoryCode() {
        return madeFectoryCode;
    }

    /**
     * 设置madeFectoryCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMadeFectoryCode(String value) {
        this.madeFectoryCode = value;
    }

    /**
     * 获取madeFectoryName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMadeFectoryName() {
        return madeFectoryName;
    }

    /**
     * 设置madeFectoryName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMadeFectoryName(String value) {
        this.madeFectoryName = value;
    }

    /**
     * 获取oesPredictrevDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOesPredictrevDate() {
        return oesPredictrevDate;
    }

    /**
     * 设置oesPredictrevDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOesPredictrevDate(XMLGregorianCalendar value) {
        this.oesPredictrevDate = value;
    }

    /**
     * 获取orderSoCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderSoCode() {
        return orderSoCode;
    }

    /**
     * 设置orderSoCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderSoCode(String value) {
        this.orderSoCode = value;
    }

    /**
     * 获取orderState属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderState() {
        return orderState;
    }

    /**
     * 设置orderState属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderState(String value) {
        this.orderState = value;
    }

    /**
     * 获取orderTypeName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderTypeName() {
        return orderTypeName;
    }

    /**
     * 设置orderTypeName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderTypeName(String value) {
        this.orderTypeName = value;
    }

    /**
     * 获取plannedShipDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPlannedShipDate() {
        return plannedShipDate;
    }

    /**
     * 设置plannedShipDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPlannedShipDate(XMLGregorianCalendar value) {
        this.plannedShipDate = value;
    }

    /**
     * 获取podDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPodDate() {
        return podDate;
    }

    /**
     * 设置podDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPodDate(String value) {
        this.podDate = value;
    }

    /**
     * 获取prodSeriesCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProdSeriesCode() {
        return prodSeriesCode;
    }

    /**
     * 设置prodSeriesCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProdSeriesCode(String value) {
        this.prodSeriesCode = value;
    }

    /**
     * 获取promisedArrivalDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPromisedArrivalDate() {
        return promisedArrivalDate;
    }

    /**
     * 设置promisedArrivalDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPromisedArrivalDate(XMLGregorianCalendar value) {
        this.promisedArrivalDate = value;
    }

    /**
     * 获取reqArrivalDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReqArrivalDate() {
        return reqArrivalDate;
    }

    /**
     * 设置reqArrivalDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReqArrivalDate(XMLGregorianCalendar value) {
        this.reqArrivalDate = value;
    }

    /**
     * 获取signDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSignDate() {
        return signDate;
    }

    /**
     * 设置signDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSignDate(XMLGregorianCalendar value) {
        this.signDate = value;
    }

    /**
     * 获取submitDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSubmitDate() {
        return submitDate;
    }

    /**
     * 设置submitDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSubmitDate(XMLGregorianCalendar value) {
        this.submitDate = value;
    }

    /**
     * 获取tradeSendDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTradeSendDate() {
        return tradeSendDate;
    }

    /**
     * 设置tradeSendDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTradeSendDate(XMLGregorianCalendar value) {
        this.tradeSendDate = value;
    }

    /**
     * 获取transitArrivalDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTransitArrivalDate() {
        return transitArrivalDate;
    }

    /**
     * 设置transitArrivalDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTransitArrivalDate(XMLGregorianCalendar value) {
        this.transitArrivalDate = value;
    }

    /**
     * 获取transitCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransitCode() {
        return transitCode;
    }

    /**
     * 设置transitCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransitCode(String value) {
        this.transitCode = value;
    }

    /**
     * 获取wpOrderId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWpOrderId() {
        return wpOrderId;
    }

    /**
     * 设置wpOrderId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWpOrderId(String value) {
        this.wpOrderId = value;
    }

    /**
     * 获取message属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
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
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * 获取flag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlag() {
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
    public void setFlag(String value) {
        this.flag = value;
    }

    /**
     * 获取faultDetail属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaultDetail() {
        return faultDetail;
    }

    /**
     * 设置faultDetail属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFaultDetail(String value) {
        this.faultDetail = value;
    }

}
