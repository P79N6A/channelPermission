
package com.haier.svc.purchase.getLastPurchasePriceFromGVSToEHAIER;

import java.util.ArrayList;
import java.util.List;
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
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="in" type="{http://www.example.org/GetLastPurchasePriceFromGVSToEHAIER/}inType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="SysName" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "in",
    "sysName"
})
@XmlRootElement(name = "GetLastPurchasePriceFromGVSToEHAIER")
public class GetLastPurchasePriceFromGVSToEHAIER_Type {

    protected List<InType> in;
    @XmlElement(name = "SysName", required = true)
    protected String sysName;

    /**
     * Gets the value of the in property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the in property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InType }
     * 
     * 
     */
    public List<InType> getIn() {
        if (in == null) {
            in = new ArrayList<InType>();
        }
        return this.in;
    }

    /**
     * ��ȡsysName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSysName() {
        return sysName;
    }

    /**
     * ����sysName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSysName(String value) {
        this.sysName = value;
    }

}
