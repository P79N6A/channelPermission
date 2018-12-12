package com.haier.svc.bean.gettidanzwdfromlestoehaier;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.haier.svc.bean.ZBSTKD;

/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ERDAT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="INPUT" type="{http://www.example.org/GetTidanZWDFromLESToEHAIER/}ZBSTKD" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "erdat", "input" })
@XmlRootElement(name = "GetTidanZWDFromLESToEHAIER")
public class GetTidanZWDFromLESToEHAIER_Type {

    @XmlElement(name = "ERDAT", required = true)
    protected String       erdat;
    @XmlElement(name = "INPUT")
    protected List<ZBSTKD> input;

    /**
     * 获取erdat属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getERDAT() {
        return erdat;
    }

    /**
     * 设置erdat属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setERDAT(String value) {
        this.erdat = value;
    }

    /**
     * Gets the value of the input property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the input property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getINPUT().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ZBSTKD }
     * 
     * 
     */
    public List<ZBSTKD> getINPUT() {
        if (input == null) {
            input = new ArrayList<ZBSTKD>();
        }
        return this.input;
    }

}
