
package com.haier.svc.bean.getucunioninfofromles;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CRK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DATE_BEGIN" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DATE_END" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="KUWEI" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="TIME_BEGIN" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="TIME_END" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "crk",
    "datebegin",
    "dateend",
    "kuwei",
    "timebegin",
    "timeend"
})
@XmlRootElement(name = "GetKUCUNInfoFromLESToEHAIER")
public class GetKUCUNInfoFromLESToEHAIER_Type {

    @XmlElement(name = "CRK", required = true)
    protected String crk;
    @XmlElement(name = "DATE_BEGIN", required = true)
    protected String datebegin;
    @XmlElement(name = "DATE_END", required = true)
    protected String dateend;
    @XmlElement(name = "KUWEI", required = true)
    protected String kuwei;
    @XmlElement(name = "TIME_BEGIN", required = true)
    protected String timebegin;
    @XmlElement(name = "TIME_END", required = true)
    protected String timeend;

    /**
     * ��ȡcrk���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCRK() {
        return crk;
    }

    /**
     * ����crk���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCRK(String value) {
        this.crk = value;
    }

    /**
     * ��ȡdatebegin���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDATEBEGIN() {
        return datebegin;
    }

    /**
     * ����datebegin���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDATEBEGIN(String value) {
        this.datebegin = value;
    }

    /**
     * ��ȡdateend���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDATEEND() {
        return dateend;
    }

    /**
     * ����dateend���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDATEEND(String value) {
        this.dateend = value;
    }

    /**
     * ��ȡkuwei���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKUWEI() {
        return kuwei;
    }

    /**
     * ����kuwei���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKUWEI(String value) {
        this.kuwei = value;
    }

    /**
     * ��ȡtimebegin���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIMEBEGIN() {
        return timebegin;
    }

    /**
     * ����timebegin���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIMEBEGIN(String value) {
        this.timebegin = value;
    }

    /**
     * ��ȡtimeend���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIMEEND() {
        return timeend;
    }

    /**
     * ����timeend���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIMEEND(String value) {
        this.timeend = value;
    }

}
