
package com.haier.afterSale.webService.pushSAP;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>T_MSGType complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="T_MSGType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MESSAGE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MESSAGE_V1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MESSAGE_V2" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MESSAGE_V3" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MESSAGE_V4" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T_MSGType", propOrder = {
    "type",
    "id",
    "number",
    "message",
    "messagev1",
    "messagev2",
    "messagev3",
    "messagev4"
})
public class TMSGType {

    @XmlElement(name = "TYPE", required = true)
    protected String type;
    @XmlElement(name = "ID", required = true)
    protected String id;
    @XmlElement(name = "NUMBER", required = true)
    protected String number;
    @XmlElement(name = "MESSAGE", required = true)
    protected String message;
    @XmlElement(name = "MESSAGE_V1", required = true)
    protected String messagev1;
    @XmlElement(name = "MESSAGE_V2", required = true)
    protected String messagev2;
    @XmlElement(name = "MESSAGE_V3", required = true)
    protected String messagev3;
    @XmlElement(name = "MESSAGE_V4", required = true)
    protected String messagev4;

    /**
     * ��ȡtype���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTYPE() {
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
    public void setTYPE(String value) {
        this.type = value;
    }

    /**
     * ��ȡid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getID() {
        return id;
    }

    /**
     * ����id���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setID(String value) {
        this.id = value;
    }

    /**
     * ��ȡnumber���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMBER() {
        return number;
    }

    /**
     * ����number���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMBER(String value) {
        this.number = value;
    }

    /**
     * ��ȡmessage���Ե�ֵ��
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
     * ����message���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMESSAGE(String value) {
        this.message = value;
    }

    /**
     * ��ȡmessagev1���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMESSAGEV1() {
        return messagev1;
    }

    /**
     * ����messagev1���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMESSAGEV1(String value) {
        this.messagev1 = value;
    }

    /**
     * ��ȡmessagev2���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMESSAGEV2() {
        return messagev2;
    }

    /**
     * ����messagev2���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMESSAGEV2(String value) {
        this.messagev2 = value;
    }

    /**
     * ��ȡmessagev3���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMESSAGEV3() {
        return messagev3;
    }

    /**
     * ����messagev3���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMESSAGEV3(String value) {
        this.messagev3 = value;
    }

    /**
     * ��ȡmessagev4���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMESSAGEV4() {
        return messagev4;
    }

    /**
     * ����messagev4���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMESSAGEV4(String value) {
        this.messagev4 = value;
    }

}
