
package com.haier.vehicle.wsdl.saleorder;

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
 *         &lt;element name="MASTER" type="{http://xmlns.oracle.com/Application/InsertSaleBill_ZKToCRM/BPELProcess}MasterType" minOccurs="0"/&gt;
 *         &lt;element name="DETAIL" type="{http://xmlns.oracle.com/Application/InsertSaleBill_ZKToCRM/BPELProcess}DetailType" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "master",
    "detail"
})
@XmlRootElement(name = "InsertSaleBill_GVS2ProcessRequest")
public class InsertSaleBillGVS2ProcessRequest {

    @XmlElement(name = "MASTER")
    protected MasterType master;
    @XmlElement(name = "DETAIL")
    protected List<DetailType> detail;

    /**
     * ��ȡmaster���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link MasterType }
     *     
     */
    public MasterType getMASTER() {
        return master;
    }

    /**
     * ����master���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link MasterType }
     *     
     */
    public void setMASTER(MasterType value) {
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
     *    getDETAIL().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DetailType }
     * 
     * 
     */
    public List<DetailType> getDETAIL() {
        if (detail == null) {
            detail = new ArrayList<DetailType>();
        }
        return this.detail;
    }

}
