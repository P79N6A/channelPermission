
package com.haier.svc.purchase.getLastPurchasePriceFromGVSToEHAIER;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>outType complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="outType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MATNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SPART" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="WRBTR_BHS" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="WRBTR_HS" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "outType", propOrder = {
    "matnr",
    "spart",
    "wrbtrbhs",
    "wrbtrhs"
})
public class OutType {

    @XmlElement(name = "MATNR", required = true)
    protected String matnr;
    @XmlElement(name = "SPART", required = true)
    protected String spart;
    @XmlElement(name = "WRBTR_BHS", required = true)
    protected BigDecimal wrbtrbhs;
    @XmlElement(name = "WRBTR_HS", required = true)
    protected BigDecimal wrbtrhs;

    /**
     * ��ȡmatnr���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMATNR() {
        return matnr;
    }

    /**
     * ����matnr���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMATNR(String value) {
        this.matnr = value;
    }

    /**
     * ��ȡspart���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSPART() {
        return spart;
    }

    /**
     * ����spart���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSPART(String value) {
        this.spart = value;
    }

    /**
     * ��ȡwrbtrbhs���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getWRBTRBHS() {
        return wrbtrbhs;
    }

    /**
     * ����wrbtrbhs���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setWRBTRBHS(BigDecimal value) {
        this.wrbtrbhs = value;
    }

    /**
     * ��ȡwrbtrhs���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getWRBTRHS() {
        return wrbtrhs;
    }

    /**
     * ����wrbtrhs���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setWRBTRHS(BigDecimal value) {
        this.wrbtrhs = value;
    }

}
