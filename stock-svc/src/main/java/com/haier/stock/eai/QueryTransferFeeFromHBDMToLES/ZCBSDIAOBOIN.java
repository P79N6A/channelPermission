
package com.haier.stock.eai.QueryTransferFeeFromHBDMToLES;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ZCBS_DIAOBO_IN complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ZCBS_DIAOBO_IN"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BSTNK"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="20"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZCBS_DIAOBO_IN", propOrder = {
    "bstnk"
})
public class ZCBSDIAOBOIN {

    @XmlElement(name = "BSTNK", required = true)
    protected String bstnk;

    /**
     * 获取bstnk属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBSTNK() {
        return bstnk;
    }

    /**
     * 设置bstnk属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBSTNK(String value) {
        this.bstnk = value;
    }

}
