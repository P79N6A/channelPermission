
package com.haier.vehicle.wsdl.mdm;

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
 *         &lt;element name="OUT_RESULT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="OUT_RETMSG" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="OUT_RETCODE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "outresult",
    "outretmsg",
    "outretcode"
})
@XmlRootElement(name = "TransCustomerInfoXDToMDMResponse")
public class TransCustomerInfoXDToMDMResponse {

    @XmlElement(name = "OUT_RESULT", required = true)
    protected String outresult;
    @XmlElement(name = "OUT_RETMSG", required = true)
    protected String outretmsg;
    @XmlElement(name = "OUT_RETCODE", required = true)
    protected String outretcode;

    /**
     * 获取outresult属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOUTRESULT() {
        return outresult;
    }

    /**
     * 设置outresult属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOUTRESULT(String value) {
        this.outresult = value;
    }

    /**
     * 获取outretmsg属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOUTRETMSG() {
        return outretmsg;
    }

    /**
     * 设置outretmsg属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOUTRETMSG(String value) {
        this.outretmsg = value;
    }

    /**
     * 获取outretcode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOUTRETCODE() {
        return outretcode;
    }

    /**
     * 设置outretcode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOUTRETCODE(String value) {
        this.outretcode = value;
    }

}
