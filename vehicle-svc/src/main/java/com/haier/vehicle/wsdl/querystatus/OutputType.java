
package com.haier.vehicle.wsdl.querystatus;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>OutputType complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="OutputType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BSTNK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="VBELN" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="VBELN_DN1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DELI_RI" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PSTIME" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="STATUS" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ABGRU" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PDATE1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PDATE2" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BUDAT_SJ" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="LFIMG_DN3" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="PLANT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="JDDM" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DATE_GM" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BUDAT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ERDAT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ERDAT1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="FDQSSL" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="PODAT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD2" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD3" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OutputType", propOrder = {
    "bstnk",
    "vbeln",
    "vbelndn1",
    "deliri",
    "pstime",
    "status",
    "abgru",
    "pdate1",
    "pdate2",
    "budatsj",
    "lfimgdn3",
    "plant",
    "jddm",
    "dategm",
    "budat",
    "erdat",
    "erdat1",
    "fdqssl",
    "podat",
    "add1",
    "add2",
    "add3"
})
public class OutputType {

    @XmlElement(name = "BSTNK", required = true)
    protected String bstnk;
    @XmlElement(name = "VBELN", required = true)
    protected String vbeln;
    @XmlElement(name = "VBELN_DN1", required = true)
    protected String vbelndn1;
    @XmlElement(name = "DELI_RI", required = true)
    protected String deliri;
    @XmlElement(name = "PSTIME", required = true)
    protected String pstime;
    @XmlElement(name = "STATUS", required = true)
    protected String status;
    @XmlElement(name = "ABGRU", required = true)
    protected String abgru;
    @XmlElement(name = "PDATE1", required = true)
    protected String pdate1;
    @XmlElement(name = "PDATE2", required = true)
    protected String pdate2;
    @XmlElement(name = "BUDAT_SJ", required = true)
    protected String budatsj;
    @XmlElement(name = "LFIMG_DN3", required = true)
    protected BigDecimal lfimgdn3;
    @XmlElement(name = "PLANT", required = true)
    protected String plant;
    @XmlElement(name = "JDDM", required = true)
    protected String jddm;
    @XmlElement(name = "DATE_GM", required = true)
    protected String dategm;
    @XmlElement(name = "BUDAT", required = true)
    protected String budat;
    @XmlElement(name = "ERDAT", required = true)
    protected String erdat;
    @XmlElement(name = "ERDAT1", required = true)
    protected String erdat1;
    @XmlElement(name = "FDQSSL", required = true)
    protected BigDecimal fdqssl;
    @XmlElement(name = "PODAT", required = true)
    protected String podat;
    @XmlElement(name = "ADD1", required = true)
    protected String add1;
    @XmlElement(name = "ADD2", required = true)
    protected String add2;
    @XmlElement(name = "ADD3", required = true)
    protected String add3;

    /**
     * ��ȡbstnk���Ե�ֵ��
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
     * ����bstnk���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBSTNK(String value) {
        this.bstnk = value;
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
     * ��ȡvbelndn1���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVBELNDN1() {
        return vbelndn1;
    }

    /**
     * ����vbelndn1���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVBELNDN1(String value) {
        this.vbelndn1 = value;
    }

    /**
     * ��ȡdeliri���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDELIRI() {
        return deliri;
    }

    /**
     * ����deliri���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDELIRI(String value) {
        this.deliri = value;
    }

    /**
     * ��ȡpstime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPSTIME() {
        return pstime;
    }

    /**
     * ����pstime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPSTIME(String value) {
        this.pstime = value;
    }

    /**
     * ��ȡstatus���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTATUS() {
        return status;
    }

    /**
     * ����status���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTATUS(String value) {
        this.status = value;
    }

    /**
     * ��ȡabgru���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getABGRU() {
        return abgru;
    }

    /**
     * ����abgru���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setABGRU(String value) {
        this.abgru = value;
    }

    /**
     * ��ȡpdate1���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPDATE1() {
        return pdate1;
    }

    /**
     * ����pdate1���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPDATE1(String value) {
        this.pdate1 = value;
    }

    /**
     * ��ȡpdate2���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPDATE2() {
        return pdate2;
    }

    /**
     * ����pdate2���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPDATE2(String value) {
        this.pdate2 = value;
    }

    /**
     * ��ȡbudatsj���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBUDATSJ() {
        return budatsj;
    }

    /**
     * ����budatsj���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBUDATSJ(String value) {
        this.budatsj = value;
    }

    /**
     * ��ȡlfimgdn3���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLFIMGDN3() {
        return lfimgdn3;
    }

    /**
     * ����lfimgdn3���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLFIMGDN3(BigDecimal value) {
        this.lfimgdn3 = value;
    }

    /**
     * ��ȡplant���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPLANT() {
        return plant;
    }

    /**
     * ����plant���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPLANT(String value) {
        this.plant = value;
    }

    /**
     * ��ȡjddm���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJDDM() {
        return jddm;
    }

    /**
     * ����jddm���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJDDM(String value) {
        this.jddm = value;
    }

    /**
     * ��ȡdategm���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDATEGM() {
        return dategm;
    }

    /**
     * ����dategm���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDATEGM(String value) {
        this.dategm = value;
    }

    /**
     * ��ȡbudat���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBUDAT() {
        return budat;
    }

    /**
     * ����budat���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBUDAT(String value) {
        this.budat = value;
    }

    /**
     * ��ȡerdat���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getERDAT() {
        return erdat;
    }

    /**
     * ����erdat���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setERDAT(String value) {
        this.erdat = value;
    }

    /**
     * ��ȡerdat1���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getERDAT1() {
        return erdat1;
    }

    /**
     * ����erdat1���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setERDAT1(String value) {
        this.erdat1 = value;
    }

    /**
     * ��ȡfdqssl���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFDQSSL() {
        return fdqssl;
    }

    /**
     * ����fdqssl���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFDQSSL(BigDecimal value) {
        this.fdqssl = value;
    }

    /**
     * ��ȡpodat���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPODAT() {
        return podat;
    }

    /**
     * ����podat���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPODAT(String value) {
        this.podat = value;
    }

    /**
     * ��ȡadd1���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD1() {
        return add1;
    }

    /**
     * ����add1���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD1(String value) {
        this.add1 = value;
    }

    /**
     * ��ȡadd2���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD2() {
        return add2;
    }

    /**
     * ����add2���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD2(String value) {
        this.add2 = value;
    }

    /**
     * ��ȡadd3���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD3() {
        return add3;
    }

    /**
     * ����add3���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD3(String value) {
        this.add3 = value;
    }

}
