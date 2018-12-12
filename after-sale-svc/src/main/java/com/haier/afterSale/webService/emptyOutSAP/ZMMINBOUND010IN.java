
package com.haier.afterSale.webService.emptyOutSAP;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ZMM_INBOUND_010_IN complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ZMM_INBOUND_010_IN"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ZOUNB" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="VBELN" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="POSNR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ZSPDT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="LIFNR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MATNR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MENGE" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="LGORT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ZFGLG" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ZLSGI" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ZWBDR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="STAT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BZ1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZMM_INBOUND_010_IN", propOrder = {
    "zounb",
    "vbeln",
    "posnr",
    "zspdt",
    "lifnr",
    "matnr",
    "menge",
    "lgort",
    "zfglg",
    "zlsgi",
    "zwbdr",
    "stat",
    "bz1"
})
public class ZMMINBOUND010IN {

    @XmlElement(name = "ZOUNB", required = true)
    protected String zounb;
    @XmlElement(name = "VBELN", required = true)
    protected String vbeln;
    @XmlElement(name = "POSNR", required = true)
    protected String posnr;
    @XmlElement(name = "ZSPDT", required = true)
    protected String zspdt;
    @XmlElement(name = "LIFNR", required = true)
    protected String lifnr;
    @XmlElement(name = "MATNR", required = true)
    protected String matnr;
    @XmlElement(name = "MENGE", required = true)
    protected BigDecimal menge;
    @XmlElement(name = "LGORT", required = true)
    protected String lgort;
    @XmlElement(name = "ZFGLG", required = true)
    protected String zfglg;
    @XmlElement(name = "ZLSGI", required = true)
    protected String zlsgi;
    @XmlElement(name = "ZWBDR", required = true)
    protected String zwbdr;
    @XmlElement(name = "STAT", required = true)
    protected String stat;
    @XmlElement(name = "BZ1", required = true)
    protected String bz1;

    /**
     * ��ȡzounb���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZOUNB() {
        return zounb;
    }

    /**
     * ����zounb���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZOUNB(String value) {
        this.zounb = value;
    }

    /**
     * ��ȡvbeln���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVBELN() {
        return vbeln;
    }

    /**
     * ����vbeln���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVBELN(String value) {
        this.vbeln = value;
    }

    /**
     * ��ȡposnr���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPOSNR() {
        return posnr;
    }

    /**
     * ����posnr���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPOSNR(String value) {
        this.posnr = value;
    }

    /**
     * ��ȡzspdt���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZSPDT() {
        return zspdt;
    }

    /**
     * ����zspdt���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZSPDT(String value) {
        this.zspdt = value;
    }

    /**
     * ��ȡlifnr���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLIFNR() {
        return lifnr;
    }

    /**
     * ����lifnr���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLIFNR(String value) {
        this.lifnr = value;
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
     * ��ȡmenge���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMENGE() {
        return menge;
    }

    /**
     * ����menge���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMENGE(BigDecimal value) {
        this.menge = value;
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
     * ��ȡzfglg���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZFGLG() {
        return zfglg;
    }

    /**
     * ����zfglg���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZFGLG(String value) {
        this.zfglg = value;
    }

    /**
     * ��ȡzlsgi���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZLSGI() {
        return zlsgi;
    }

    /**
     * ����zlsgi���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZLSGI(String value) {
        this.zlsgi = value;
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
     * ��ȡstat���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTAT() {
        return stat;
    }

    /**
     * ����stat���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTAT(String value) {
        this.stat = value;
    }

    /**
     * ��ȡbz1���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBZ1() {
        return bz1;
    }

    /**
     * ����bz1���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBZ1(String value) {
        this.bz1 = value;
    }

}
