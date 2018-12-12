package com.haier.svc.bean.getucunioninfofromles;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MESSAGE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OUTPUT" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="MBLNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="MJAHR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="ZEILE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="CPUDT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="CPUTM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="BWART" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="SHKZG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="MATNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="MATKL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="CHARG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="LFIMG" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                   &lt;element name="LGORT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="KUNNR_SALETO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="KUNNR_SENDTO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="VBELN_SO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="VBELN_DN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="TKNUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="BSTNK" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="AUART" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="RESERVE1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="RESERVE2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="BSTKD" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="KBETR" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="OUTPUT1" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="KUNNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="LGORT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="MATNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="CHARG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="LFIMG" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
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
@XmlType(name = "", propOrder = { "flag", "message", "output", "output1" })
@XmlRootElement(name = "GetKUCUNInfoFromLESToEHAIERResponse")
public class GetKUCUNInfoFromLESToEHAIERResponse {

    @XmlElement(name = "FLAG", required = true)
    protected String                                              flag;
    @XmlElement(name = "MESSAGE", required = true)
    protected String                                              message;
    @XmlElement(name = "OUTPUT")
    protected List<GetKUCUNInfoFromLESToEHAIERResponseStockTrans> output;
    @XmlElement(name = "OUTPUT1")
    protected List<GetKUCUNInfoFromLESToEHAIERResponseStockQty>   output1;

    /**
     * Gets the value of the flag property.
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
     * Sets the value of the flag property.
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
     * Gets the value of the message property.
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
     * Sets the value of the message property.
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
     * {@link GetKUCUNInfoFromLESToEHAIERResponse.OUTPUT }
     * 
     * 
     */
    public List<GetKUCUNInfoFromLESToEHAIERResponseStockTrans> getOUTPUT() {
        if (output == null) {
            output = new ArrayList<GetKUCUNInfoFromLESToEHAIERResponseStockTrans>();
        }
        return this.output;
    }

    /**
     * Gets the value of the output1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the output1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOUTPUT1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GetKUCUNInfoFromLESToEHAIERResponse.OUTPUT1 }
     * 
     * 
     */
    public List<GetKUCUNInfoFromLESToEHAIERResponseStockQty> getOUTPUT1() {
        if (output1 == null) {
            output1 = new ArrayList<GetKUCUNInfoFromLESToEHAIERResponseStockQty>();
        }
        return this.output1;
    }

}
