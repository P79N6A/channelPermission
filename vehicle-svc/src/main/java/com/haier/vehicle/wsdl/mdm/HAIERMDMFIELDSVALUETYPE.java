
package com.haier.vehicle.wsdl.mdm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>HAIERMDM.FIELDS_VALUE_TYPE complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="HAIERMDM.FIELDS_VALUE_TYPE"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FIELD_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="FIELD_VALUE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="FIELD_QUERY_TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HAIERMDM.FIELDS_VALUE_TYPE", propOrder = {
    "fieldname",
    "fieldvalue",
    "fieldquerytype"
})
public class HAIERMDMFIELDSVALUETYPE {

    @XmlElement(name = "FIELD_NAME", required = true)
    protected String fieldname;
    @XmlElement(name = "FIELD_VALUE", required = true)
    protected String fieldvalue;
    @XmlElement(name = "FIELD_QUERY_TYPE", required = true)
    protected String fieldquerytype;

    /**
     * ��ȡfieldname���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFIELDNAME() {
        return fieldname;
    }

    /**
     * ����fieldname���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFIELDNAME(String value) {
        this.fieldname = value;
    }

    /**
     * ��ȡfieldvalue���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFIELDVALUE() {
        return fieldvalue;
    }

    /**
     * ����fieldvalue���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFIELDVALUE(String value) {
        this.fieldvalue = value;
    }

    /**
     * ��ȡfieldquerytype���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFIELDQUERYTYPE() {
        return fieldquerytype;
    }

    /**
     * ����fieldquerytype���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFIELDQUERYTYPE(String value) {
        this.fieldquerytype = value;
    }

}
