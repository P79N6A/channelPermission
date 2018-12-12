 
package com.haier.afterSale.webService.pushSAP;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>OutType complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="OutType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EX_SUBRC" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="T_MSG" type="{http://www.example.org/PushReturnInfoToGVS/}T_MSGType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OutType", propOrder = {
    "exsubrc",
    "tmsg"
})
public class OutType {

    @XmlElement(name = "EX_SUBRC")
    protected int exsubrc;
    @XmlElement(name = "T_MSG")
    protected List<TMSGType> tmsg;

    /**
     * ��ȡexsubrc���Ե�ֵ��
     * 
     */
    public int getEXSUBRC() {
        return exsubrc;
    }

    /**
     * ����exsubrc���Ե�ֵ��
     * 
     */
    public void setEXSUBRC(int value) {
        this.exsubrc = value;
    }

    /**
     * Gets the value of the tmsg property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tmsg property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTMSG().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TMSGType }
     * 
     * 
     */
    public List<TMSGType> getTMSG() {
        if (tmsg == null) {
            tmsg = new ArrayList<TMSGType>();
        }
        return this.tmsg;
    }

}
