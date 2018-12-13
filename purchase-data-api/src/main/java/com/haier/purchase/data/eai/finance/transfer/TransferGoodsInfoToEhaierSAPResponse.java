
package com.haier.purchase.data.eai.finance.transfer;

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
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EX_SUBRC" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="T_MSG" type="{http://www.example.org/TransferGoodsInfoToEhaierSAP/}ZSDS0002" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "exsubrc",
    "tmsg"
})
@XmlRootElement(name = "TransferGoodsInfoToEhaierSAPResponse")
public class TransferGoodsInfoToEhaierSAPResponse {

    @XmlElement(name = "EX_SUBRC")
    protected int exsubrc;
    @XmlElement(name = "T_MSG")
    protected List<ZSDS0002> tmsg;

    /**
     * 获取exsubrc属性的值。
     * 
     */
    public int getEXSUBRC() {
        return exsubrc;
    }

    /**
     * 设置exsubrc属性的值。
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
     * {@link ZSDS0002 }
     * 
     * 
     */
    public List<ZSDS0002> getTMSG() {
        if (tmsg == null) {
            tmsg = new ArrayList<ZSDS0002>();
        }
        return this.tmsg;
    }

}
