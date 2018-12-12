
package com.haier.afterSale.webService.emptyOutSAP;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>outType complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="outType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EX_SUBRC" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="T_MSG" type="{http://www.example.org/TransBadGoodsInfoFromGVSToEHAIER/}ZMM_INBOUND_010_OUT" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="FAULT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "outType", propOrder = {
    "exsubrc",
    "tmsg",
    "flag",
    "fault"
})
public class OutType {

    @XmlElement(name = "EX_SUBRC")
    protected int exsubrc;
    @XmlElement(name = "T_MSG")
    protected List<ZMMINBOUND010OUT> tmsg;
    @XmlElement(name = "FLAG", required = true)
    protected String flag;
    @XmlElement(name = "FAULT", required = true)
    protected String fault;

    /**
     * ��ȡexsubrc���Ե�ֵ��
     * 
     */
    public int getEXSUBRC() {
        return exsubrc;
    }

    /**
     * ����exsubrc���Ե�ֵ��
     * 
     */
    public void setEXSUBRC(int value) {
        this.exsubrc = value;
    }

    /**
     * Gets the value of the tmsg property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tmsg property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTMSG().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ZMMINBOUND010OUT }
     * 
     * 
     */
    public List<ZMMINBOUND010OUT> getTMSG() {
        if (tmsg == null) {
            tmsg = new ArrayList<ZMMINBOUND010OUT>();
        }
        return this.tmsg;
    }

    /**
     * ��ȡflag���Ե�ֵ��
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
     * ����flag���Ե�ֵ��
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
     * ��ȡfault���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFAULT() {
        return fault;
    }

    /**
     * ����fault���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFAULT(String value) {
        this.fault = value;
    }

}
