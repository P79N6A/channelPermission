
package com.haier.afterSale.webService.cancelDispatchedWorkers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>inputs complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="inputs"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ORDER_NO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CANCEL_DATE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inputs", propOrder = {
    "orderno",
    "canceldate"
})
public class Inputs {

    @XmlElement(name = "ORDER_NO", required = true)
    protected String orderno;
    @XmlElement(name = "CANCEL_DATE", required = true)
    protected String canceldate;

    /**
     * ��ȡorderno���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORDERNO() {
        return orderno;
    }

    /**
     * ����orderno���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORDERNO(String value) {
        this.orderno = value;
    }

    /**
     * ��ȡcanceldate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCANCELDATE() {
        return canceldate;
    }

    /**
     * ����canceldate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCANCELDATE(String value) {
        this.canceldate = value;
    }

}
