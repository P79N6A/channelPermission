
package com.haier.vehicle.wsdl.mdm;

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
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IN_SYS_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IN_MASTER_TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IN_TABLE_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IN_FIELDS_VALUE_TABLE" type="{http://www.example.org/TransCustomerInfoXDToMDM/}HAIERMDM.FIELDS_VALUE_TABLE" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "insysname",
    "inmastertype",
    "intablename",
    "infieldsvaluetable"
})
@XmlRootElement(name = "TransCustomerInfoXDToMDM")
public class TransCustomerInfoXDToMDM_Type {

    @XmlElement(name = "IN_SYS_NAME", required = true)
    protected String insysname;
    @XmlElement(name = "IN_MASTER_TYPE", required = true)
    protected String inmastertype;
    @XmlElement(name = "IN_TABLE_NAME", required = true)
    protected String intablename;
    @XmlElement(name = "IN_FIELDS_VALUE_TABLE")
    protected List<HAIERMDMFIELDSVALUETABLE> infieldsvaluetable;

    /**
     * ��ȡinsysname���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINSYSNAME() {
        return insysname;
    }

    /**
     * ����insysname���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINSYSNAME(String value) {
        this.insysname = value;
    }

    /**
     * ��ȡinmastertype���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINMASTERTYPE() {
        return inmastertype;
    }

    /**
     * ����inmastertype���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINMASTERTYPE(String value) {
        this.inmastertype = value;
    }

    /**
     * ��ȡintablename���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINTABLENAME() {
        return intablename;
    }

    /**
     * ����intablename���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINTABLENAME(String value) {
        this.intablename = value;
    }

    /**
     * Gets the value of the infieldsvaluetable property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the infieldsvaluetable property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getINFIELDSVALUETABLE().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HAIERMDMFIELDSVALUETABLE }
     * 
     * 
     */
    public List<HAIERMDMFIELDSVALUETABLE> getINFIELDSVALUETABLE() {
        if (infieldsvaluetable == null) {
            infieldsvaluetable = new ArrayList<HAIERMDMFIELDSVALUETABLE>();
        }
        return this.infieldsvaluetable;
    }

}
