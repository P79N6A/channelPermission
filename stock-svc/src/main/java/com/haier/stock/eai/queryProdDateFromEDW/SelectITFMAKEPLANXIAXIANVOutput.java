
package com.haier.stock.eai.queryProdDateFromEDW;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Select_ITF_MAKE_PLAN_XIAXIAN_VOutput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Select_ITF_MAKE_PLAN_XIAXIAN_VOutput">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GVS_ORDER_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ORDERDETAILNO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PLAN_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="COMPLETE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Select_ITF_MAKE_PLAN_XIAXIAN_VOutput", propOrder = {
    "gvsordercode",
    "orderdetailno",
    "plandate",
    "completedate"
})
public class SelectITFMAKEPLANXIAXIANVOutput {

    @XmlElement(name = "GVS_ORDER_CODE", required = true)
    protected String gvsordercode;
    @XmlElement(name = "ORDERDETAILNO", required = true)
    protected String orderdetailno;
    @XmlElement(name = "PLAN_DATE", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar plandate;
    @XmlElement(name = "COMPLETE_DATE", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar completedate;

    /**
     * Gets the value of the gvsordercode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGVSORDERCODE() {
        return gvsordercode;
    }

    /**
     * Sets the value of the gvsordercode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGVSORDERCODE(String value) {
        this.gvsordercode = value;
    }

    /**
     * Gets the value of the orderdetailno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORDERDETAILNO() {
        return orderdetailno;
    }

    /**
     * Sets the value of the orderdetailno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORDERDETAILNO(String value) {
        this.orderdetailno = value;
    }

    /**
     * Gets the value of the plandate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPLANDATE() {
        return plandate;
    }

    /**
     * Sets the value of the plandate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPLANDATE(XMLGregorianCalendar value) {
        this.plandate = value;
    }

    /**
     * Gets the value of the completedate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCOMPLETEDATE() {
        return completedate;
    }

    /**
     * Sets the value of the completedate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCOMPLETEDATE(XMLGregorianCalendar value) {
        this.completedate = value;
    }

}
