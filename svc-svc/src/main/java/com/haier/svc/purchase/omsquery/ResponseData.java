
package com.haier.svc.purchase.omsquery;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>ResponseData complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
     * ��ȡactualShipDate���Ե�ֵ��
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
     * ����actualShipDate���Ե�ֵ��
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
     * ��ȡcancelreason���Ե�ֵ��
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
     * ����cancelreason���Ե�ֵ��
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
     * ��ȡconfirmDate���Ե�ֵ��
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
     * ����confirmDate���Ե�ֵ��
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
     * ��ȡcustOrderCode���Ե�ֵ��
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
     * ����custOrderCode���Ե�ֵ��
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
     * ��ȡcustPoDetailCode���Ե�ֵ��
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
     * ����custPoDetailCode���Ե�ֵ��
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
     * ��ȡcustrevqty���Ե�ֵ��
     * 
     */
    public double getCustrevqty() {
        return custrevqty;
    }

    /**
     * ����custrevqty���Ե�ֵ��
     * 
     */
    public void setCustrevqty(double value) {
        this.custrevqty = value;
    }

    /**
     * ��ȡgvsDN���Ե�ֵ��
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
     * ����gvsDN���Ե�ֵ��
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
     * ��ȡgvsOrderCode���Ե�ֵ��
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
     * ����gvsOrderCode���Ե�ֵ��
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
     * ��ȡlatestArrivalTime���Ե�ֵ��
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
     * ����latestArrivalTime���Ե�ֵ��
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
     * ��ȡlatestLeaveBaseDate���Ե�ֵ��
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
     * ����latestLeaveBaseDate���Ե�ֵ��
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
     * ��ȡmadeFectoryCode���Ե�ֵ��
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
     * ����madeFectoryCode���Ե�ֵ��
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
     * ��ȡmadeFectoryName���Ե�ֵ��
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
     * ����madeFectoryName���Ե�ֵ��
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
     * ��ȡoesPredictrevDate���Ե�ֵ��
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
     * ����oesPredictrevDate���Ե�ֵ��
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
     * ��ȡorderSoCode���Ե�ֵ��
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
     * ����orderSoCode���Ե�ֵ��
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
     * ��ȡorderState���Ե�ֵ��
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
     * ����orderState���Ե�ֵ��
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
     * ��ȡorderTypeName���Ե�ֵ��
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
     * ����orderTypeName���Ե�ֵ��
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
     * ��ȡplannedShipDate���Ե�ֵ��
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
     * ����plannedShipDate���Ե�ֵ��
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
     * ��ȡpodDate���Ե�ֵ��
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
     * ����podDate���Ե�ֵ��
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
     * ��ȡprodSeriesCode���Ե�ֵ��
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
     * ����prodSeriesCode���Ե�ֵ��
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
     * ��ȡpromisedArrivalDate���Ե�ֵ��
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
     * ����promisedArrivalDate���Ե�ֵ��
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
     * ��ȡreqArrivalDate���Ե�ֵ��
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
     * ����reqArrivalDate���Ե�ֵ��
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
     * ��ȡsignDate���Ե�ֵ��
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
     * ����signDate���Ե�ֵ��
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
     * ��ȡsubmitDate���Ե�ֵ��
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
     * ����submitDate���Ե�ֵ��
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
     * ��ȡtradeSendDate���Ե�ֵ��
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
     * ����tradeSendDate���Ե�ֵ��
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
     * ��ȡtransitArrivalDate���Ե�ֵ��
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
     * ����transitArrivalDate���Ե�ֵ��
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
     * ��ȡtransitCode���Ե�ֵ��
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
     * ����transitCode���Ե�ֵ��
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
     * ��ȡwpOrderId���Ե�ֵ��
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
     * ����wpOrderId���Ե�ֵ��
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
     * ��ȡmessage���Ե�ֵ��
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
     * ����message���Ե�ֵ��
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
     * ��ȡflag���Ե�ֵ��
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
     * ����flag���Ե�ֵ��
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
     * ��ȡfaultDetail���Ե�ֵ��
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
     * ����faultDetail���Ե�ֵ��
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
