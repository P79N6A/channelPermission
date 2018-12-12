
package com.haier.vehicle.wsdl.saleorder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>eaiMonitorInfo complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="eaiMonitorInfo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="processInstanceID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="processID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="processName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nodeNum" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nodeName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="isEnd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fixStatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="mainRecordCount" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="childRecordCount" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nodeStartTime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nodeEndTime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nodeMessageCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nodeMessageDetail" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="bodyData" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="returnData" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eaiMonitorInfo", namespace = "http://www.example.org/eaiMonitor", propOrder = {
    "processInstanceID",
    "processID",
    "processName",
    "nodeNum",
    "nodeName",
    "isEnd",
    "fixStatus",
    "mainRecordCount",
    "childRecordCount",
    "nodeStartTime",
    "nodeEndTime",
    "nodeMessageCode",
    "nodeMessageDetail",
    "bodyData",
    "returnData"
})
public class EaiMonitorInfo {

    @XmlElement(namespace = "", required = true)
    protected String processInstanceID;
    @XmlElement(namespace = "", required = true)
    protected String processID;
    @XmlElement(namespace = "", required = true)
    protected String processName;
    @XmlElement(namespace = "", required = true)
    protected String nodeNum;
    @XmlElement(namespace = "", required = true)
    protected String nodeName;
    @XmlElement(namespace = "", required = true)
    protected String isEnd;
    @XmlElement(namespace = "", required = true)
    protected String fixStatus;
    @XmlElement(namespace = "", required = true)
    protected String mainRecordCount;
    @XmlElement(namespace = "", required = true)
    protected String childRecordCount;
    @XmlElement(namespace = "", required = true)
    protected String nodeStartTime;
    @XmlElement(namespace = "", required = true)
    protected String nodeEndTime;
    @XmlElement(namespace = "", required = true)
    protected String nodeMessageCode;
    @XmlElement(namespace = "", required = true)
    protected String nodeMessageDetail;
    @XmlElement(namespace = "", required = true)
    protected String bodyData;
    @XmlElement(namespace = "", required = true)
    protected String returnData;

    /**
     * ��ȡprocessInstanceID���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessInstanceID() {
        return processInstanceID;
    }

    /**
     * ����processInstanceID���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessInstanceID(String value) {
        this.processInstanceID = value;
    }

    /**
     * ��ȡprocessID���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessID() {
        return processID;
    }

    /**
     * ����processID���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessID(String value) {
        this.processID = value;
    }

    /**
     * ��ȡprocessName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessName() {
        return processName;
    }

    /**
     * ����processName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessName(String value) {
        this.processName = value;
    }

    /**
     * ��ȡnodeNum���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNodeNum() {
        return nodeNum;
    }

    /**
     * ����nodeNum���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNodeNum(String value) {
        this.nodeNum = value;
    }

    /**
     * ��ȡnodeName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     * ����nodeName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNodeName(String value) {
        this.nodeName = value;
    }

    /**
     * ��ȡisEnd���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsEnd() {
        return isEnd;
    }

    /**
     * ����isEnd���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsEnd(String value) {
        this.isEnd = value;
    }

    /**
     * ��ȡfixStatus���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFixStatus() {
        return fixStatus;
    }

    /**
     * ����fixStatus���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFixStatus(String value) {
        this.fixStatus = value;
    }

    /**
     * ��ȡmainRecordCount���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMainRecordCount() {
        return mainRecordCount;
    }

    /**
     * ����mainRecordCount���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMainRecordCount(String value) {
        this.mainRecordCount = value;
    }

    /**
     * ��ȡchildRecordCount���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChildRecordCount() {
        return childRecordCount;
    }

    /**
     * ����childRecordCount���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChildRecordCount(String value) {
        this.childRecordCount = value;
    }

    /**
     * ��ȡnodeStartTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNodeStartTime() {
        return nodeStartTime;
    }

    /**
     * ����nodeStartTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNodeStartTime(String value) {
        this.nodeStartTime = value;
    }

    /**
     * ��ȡnodeEndTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNodeEndTime() {
        return nodeEndTime;
    }

    /**
     * ����nodeEndTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNodeEndTime(String value) {
        this.nodeEndTime = value;
    }

    /**
     * ��ȡnodeMessageCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNodeMessageCode() {
        return nodeMessageCode;
    }

    /**
     * ����nodeMessageCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNodeMessageCode(String value) {
        this.nodeMessageCode = value;
    }

    /**
     * ��ȡnodeMessageDetail���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNodeMessageDetail() {
        return nodeMessageDetail;
    }

    /**
     * ����nodeMessageDetail���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNodeMessageDetail(String value) {
        this.nodeMessageDetail = value;
    }

    /**
     * ��ȡbodyData���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBodyData() {
        return bodyData;
    }

    /**
     * ����bodyData���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBodyData(String value) {
        this.bodyData = value;
    }

    /**
     * ��ȡreturnData���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnData() {
        return returnData;
    }

    /**
     * ����returnData���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnData(String value) {
        this.returnData = value;
    }

}
