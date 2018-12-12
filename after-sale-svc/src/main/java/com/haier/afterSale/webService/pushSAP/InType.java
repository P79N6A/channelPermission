
package com.haier.afterSale.webService.pushSAP;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>InType complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="InType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ZSYST" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ZWBDR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ZWBDT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ZORDR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ZCHNL" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="KUNNR_AG" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="KUNNR_RG" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AUGRU" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MATNR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="KWMENG" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="KBETR" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="LGORT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ZFGBL" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ZWBDR_O" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InType", propOrder = {
    "zsyst",
    "zwbdr",
    "zwbdt",
    "zordr",
    "zchnl",
    "kunnrag",
    "kunnrrg",
    "augru",
    "matnr",
    "kwmeng",
    "kbetr",
    "lgort",
    "zfgbl",
    "zwbdro"
})
public class InType {

    @XmlElement(name = "ZSYST", required = true)
    protected String zsyst;
    @XmlElement(name = "ZWBDR", required = true)
    protected String zwbdr;
    @XmlElement(name = "ZWBDT", required = true)
    protected String zwbdt;
    @XmlElement(name = "ZORDR", required = true)
    protected String zordr;
    @XmlElement(name = "ZCHNL", required = true)
    protected String zchnl;
    @XmlElement(name = "KUNNR_AG", required = true)
    protected String kunnrag;
    @XmlElement(name = "KUNNR_RG", required = true)
    protected String kunnrrg;
    @XmlElement(name = "AUGRU", required = true)
    protected String augru;
    @XmlElement(name = "MATNR", required = true)
    protected String matnr;
    @XmlElement(name = "KWMENG", required = true)
    protected BigDecimal kwmeng;
    @XmlElement(name = "KBETR", required = true)
    protected BigDecimal kbetr;
    @XmlElement(name = "LGORT", required = true)
    protected String lgort;
    @XmlElement(name = "ZFGBL", required = true)
    protected String zfgbl;
    @XmlElement(name = "ZWBDR_O", required = true)
    protected String zwbdro;

    /**
     * ��ȡzsyst���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZSYST() {
        return zsyst;
    }

    /**
     * ����zsyst���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZSYST(String value) {
        this.zsyst = value;
    }

    /**
     * ��ȡzwbdr���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZWBDR() {
        return zwbdr;
    }

    /**
     * ����zwbdr���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZWBDR(String value) {
        this.zwbdr = value;
    }

    /**
     * ��ȡzwbdt���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZWBDT() {
        return zwbdt;
    }

    /**
     * ����zwbdt���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZWBDT(String value) {
        this.zwbdt = value;
    }

    /**
     * ��ȡzordr���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZORDR() {
        return zordr;
    }

    /**
     * ����zordr���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZORDR(String value) {
        this.zordr = value;
    }

    /**
     * ��ȡzchnl���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZCHNL() {
        return zchnl;
    }

    /**
     * ����zchnl���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZCHNL(String value) {
        this.zchnl = value;
    }

    /**
     * ��ȡkunnrag���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKUNNRAG() {
        return kunnrag;
    }

    /**
     * ����kunnrag���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKUNNRAG(String value) {
        this.kunnrag = value;
    }

    /**
     * ��ȡkunnrrg���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKUNNRRG() {
        return kunnrrg;
    }

    /**
     * ����kunnrrg���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKUNNRRG(String value) {
        this.kunnrrg = value;
    }

    /**
     * ��ȡaugru���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAUGRU() {
        return augru;
    }

    /**
     * ����augru���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAUGRU(String value) {
        this.augru = value;
    }

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
     * ��ȡkwmeng���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getKWMENG() {
        return kwmeng;
    }

    /**
     * ����kwmeng���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setKWMENG(BigDecimal value) {
        this.kwmeng = value;
    }

    /**
     * ��ȡkbetr���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getKBETR() {
        return kbetr;
    }

    /**
     * ����kbetr���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setKBETR(BigDecimal value) {
        this.kbetr = value;
    }

    /**
     * ��ȡlgort���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLGORT() {
        return lgort;
    }

    /**
     * ����lgort���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLGORT(String value) {
        this.lgort = value;
    }

    /**
     * ��ȡzfgbl���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZFGBL() {
        return zfgbl;
    }

    /**
     * ����zfgbl���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZFGBL(String value) {
        this.zfgbl = value;
    }

    /**
     * ��ȡzwbdro���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZWBDRO() {
        return zwbdro;
    }

    /**
     * ����zwbdro���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZWBDRO(String value) {
        this.zwbdro = value;
    }

}
