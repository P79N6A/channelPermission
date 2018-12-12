
package com.haier.svc.purchase.crmmanual;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="SysName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Master" type="{http://www.example.org/InsertWAOrderBillFromEhaierToCRM/}MasterType"/>
 *         &lt;element name="Detail" type="{http://www.example.org/InsertWAOrderBillFromEhaierToCRM/}DetailType" maxOccurs="unbounded" minOccurs="0"/>
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
    "sysName",
    "master",
    "detail"
})
@XmlRootElement(name = "InsertWAOrderBillFromEhaierToCRM")
public class InsertWAOrderBillFromEhaierToCRM_Type {

    @XmlElement(name = "SysName", required = true)
    protected String sysName;
    @XmlElement(name = "Master", required = true)
    protected MasterType master;
    @XmlElement(name = "Detail")
    protected List<DetailType> detail;

    /**
     * 获取sysName属性的值。
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
     * 设置sysName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSysName(String value) {
        this.sysName = value;
    }

    /**
     * 获取master属性的值。
     * 
     * @return
     *     possible object is
     *     {@link MasterType }
     *     
     */
    public MasterType getMaster() {
        return master;
    }

    /**
     * 设置master属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link MasterType }
     *     
     */
    public void setMaster(MasterType value) {
        this.master = value;
    }

    /**
     * Gets the value of the detail property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the detail property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDetail().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DetailType }
     * 
     * 
     */
    public List<DetailType> getDetail() {
        if (detail == null) {
            detail = new ArrayList<DetailType>();
        }
        return this.detail;
    }

}
