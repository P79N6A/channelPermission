
package com.haier.order.wsdl.mdm;

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
 *         &lt;element name="in" type="{http://www.example.org/SelectInfoFromMDM/}inType"/&gt;
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
    "in"
})
@XmlRootElement(name = "SelectInfoFromMDM_OP")
public class SelectInfoFromMDMOP {

    @XmlElement(required = true)
    protected InType in;

    /**
     * ��ȡin���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link InType }
     *     
     */
    public InType getIn() {
        return in;
    }

    /**
     * ����in���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link InType }
     *     
     */
    public void setIn(InType value) {
        this.in = value;
    }

}