
package com.haier.svc.purchase.querydninfofromlestoehaier;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 *
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MESSAGE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FAULT_DETAIL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZFLAG1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OUTPUT" type="{http://www.example.org/QueryDNinfoFromLEStoEhaier/}ZBSTKD_WD" maxOccurs="unbounded" minOccurs="0"/>
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
    "flag",
    "message",
    "faultdetail",
    "zflag1",
    "output"
})
@XmlRootElement(name = "QueryDNinfoFromLEStoEhaierResponse")
public class QueryDNinfoFromLEStoEhaierResponse {

    @XmlElement(name = "FLAG", required = true)
    protected String flag;
    @XmlElement(name = "MESSAGE", required = true)
    protected String message;
    @XmlElement(name = "FAULT_DETAIL", required = true)
    protected String faultdetail;
    @XmlElement(name = "ZFLAG1", required = true)
    protected String zflag1;
    @XmlElement(name = "OUTPUT")
    protected List<ZBSTKDWD> output;

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
     * ��ȡfaultdetail���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFAULTDETAIL() {
        return faultdetail;
    }

    /**
     * ����faultdetail���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFAULTDETAIL(String value) {
        this.faultdetail = value;
    }

    /**
     * ��ȡzflag1���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZFLAG1() {
        return zflag1;
    }

    /**
     * ����zflag1���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZFLAG1(String value) {
        this.zflag1 = value;
    }

    /**
     * Gets the value of the output property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the output property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOUTPUT().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ZBSTKDWD }
     * 
     * 
     */
    public List<ZBSTKDWD> getOUTPUT() {
        if (output == null) {
            output = new ArrayList<ZBSTKDWD>();
        }
        return this.output;
    }

}
