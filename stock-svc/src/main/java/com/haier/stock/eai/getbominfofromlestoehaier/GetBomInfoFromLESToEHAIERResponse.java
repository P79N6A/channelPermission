package com.haier.stock.eai.getbominfofromlestoehaier;

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
 *         &lt;element name="FLAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MESSAGE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Z_OUTPUT_PARA" type="{http://www.example.org/GetBomInfoFromLESToEHAIER/}ZLES_DCMS_24" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "flag", "message", "zoutputpara" })
@XmlRootElement(name = "GetBomInfoFromLESToEHAIERResponse")
public class GetBomInfoFromLESToEHAIERResponse {

    @XmlElement(name = "FLAG")
    protected String           flag;
    @XmlElement(name = "MESSAGE")
    protected String           message;
    @XmlElement(name = "Z_OUTPUT_PARA")
    protected List<ZLESDCMS24> zoutputpara;

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
     * Gets the value of the zoutputpara property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the zoutputpara property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getZOUTPUTPARA().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ZLESDCMS24 }
     * 
     * 
     */
    public List<ZLESDCMS24> getZOUTPUTPARA() {
        if (zoutputpara == null) {
            zoutputpara = new ArrayList<ZLESDCMS24>();
        }
        return this.zoutputpara;
    }

}
