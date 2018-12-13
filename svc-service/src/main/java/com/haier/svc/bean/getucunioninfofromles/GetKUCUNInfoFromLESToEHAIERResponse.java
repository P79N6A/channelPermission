
package com.haier.svc.bean.getucunioninfofromles;

import java.math.BigDecimal;
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
 *         &lt;element name="FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MESSAGE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="OUTPUT" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="MBLNR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="MJAHR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="ZEILE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="CPUDT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="CPUTM" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="BWART" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="SHKZG" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="MATNR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="MATKL" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="CHARG" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="LFIMG" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *                   &lt;element name="LGORT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="KUNNR_SALETO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="KUNNR_SENDTO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="VBELN_SO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="VBELN_DN" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="TKNUM" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="BSTNK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="AUART" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="RESERVE1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="RESERVE2" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="BSTKD" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="KBETR" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="OUTPUT1" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="KUNNR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="LGORT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="MATNR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="CHARG" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="LFIMG" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
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
@XmlType(name = "", propOrder = {
    "flag",
    "message",
    "output",
    "output1"
})
@XmlRootElement(name = "GetKUCUNInfoFromLESToEHAIERResponse")
public class GetKUCUNInfoFromLESToEHAIERResponse {

    @XmlElement(name = "FLAG", required = true)
    protected String flag;
    @XmlElement(name = "MESSAGE", required = true)
    protected String message;
    @XmlElement(name = "OUTPUT")
    protected List<OUTPUT> output;
    @XmlElement(name = "OUTPUT1")
    protected List<OUTPUT1> output1;

    /**
     * ��ȡflag���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLAG() {
        return flag;
    }

    /**
     * ����flag���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLAG(String value) {
        this.flag = value;
    }

    /**
     * ��ȡmessage���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMESSAGE() {
        return message;
    }

    /**
     * ����message���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMESSAGE(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the output property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the output property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOUTPUT().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OUTPUT }
     * 
     * 
     */
    public List<OUTPUT> getOUTPUT() {
        if (output == null) {
            output = new ArrayList<OUTPUT>();
        }
        return this.output;
    }

    /**
     * Gets the value of the output1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the output1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOUTPUT1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OUTPUT1 }
     * 
     * 
     */
    public List<OUTPUT1> getOUTPUT1() {
        if (output1 == null) {
            output1 = new ArrayList<OUTPUT1>();
        }
        return this.output1;
    }


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
     *         &lt;element name="MBLNR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="MJAHR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="ZEILE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="CPUDT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="CPUTM" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="BWART" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="SHKZG" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="MATNR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="MATKL" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="CHARG" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="LFIMG" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
     *         &lt;element name="LGORT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="KUNNR_SALETO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="KUNNR_SENDTO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="VBELN_SO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="VBELN_DN" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="TKNUM" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="BSTNK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="AUART" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="RESERVE1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="RESERVE2" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="BSTKD" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="KBETR" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
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
        "mblnr",
        "mjahr",
        "zeile",
        "cpudt",
        "cputm",
        "bwart",
        "shkzg",
        "matnr",
        "matkl",
        "charg",
        "lfimg",
        "lgort",
        "kunnrsaleto",
        "kunnrsendto",
        "vbelnso",
        "vbelndn",
        "tknum",
        "bstnk",
        "auart",
        "reserve1",
        "reserve2",
        "bstkd",
        "kbetr"
    })
    public static class OUTPUT {

        @XmlElement(name = "MBLNR", required = true)
        protected String mblnr;
        @XmlElement(name = "MJAHR", required = true)
        protected String mjahr;
        @XmlElement(name = "ZEILE", required = true)
        protected String zeile;
        @XmlElement(name = "CPUDT", required = true)
        protected String cpudt;
        @XmlElement(name = "CPUTM", required = true)
        protected String cputm;
        @XmlElement(name = "BWART", required = true)
        protected String bwart;
        @XmlElement(name = "SHKZG", required = true)
        protected String shkzg;
        @XmlElement(name = "MATNR", required = true)
        protected String matnr;
        @XmlElement(name = "MATKL", required = true)
        protected String matkl;
        @XmlElement(name = "CHARG", required = true)
        protected String charg;
        @XmlElement(name = "LFIMG", required = true)
        protected BigDecimal lfimg;
        @XmlElement(name = "LGORT", required = true)
        protected String lgort;
        @XmlElement(name = "KUNNR_SALETO", required = true)
        protected String kunnrsaleto;
        @XmlElement(name = "KUNNR_SENDTO", required = true)
        protected String kunnrsendto;
        @XmlElement(name = "VBELN_SO", required = true)
        protected String vbelnso;
        @XmlElement(name = "VBELN_DN", required = true)
        protected String vbelndn;
        @XmlElement(name = "TKNUM", required = true)
        protected String tknum;
        @XmlElement(name = "BSTNK", required = true)
        protected String bstnk;
        @XmlElement(name = "AUART", required = true)
        protected String auart;
        @XmlElement(name = "RESERVE1", required = true)
        protected String reserve1;
        @XmlElement(name = "RESERVE2", required = true)
        protected String reserve2;
        @XmlElement(name = "BSTKD", required = true)
        protected String bstkd;
        @XmlElement(name = "KBETR", required = true)
        protected BigDecimal kbetr;

        /**
         * ��ȡmblnr���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMBLNR() {
            return mblnr;
        }

        /**
         * ����mblnr���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMBLNR(String value) {
            this.mblnr = value;
        }

        /**
         * ��ȡmjahr���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMJAHR() {
            return mjahr;
        }

        /**
         * ����mjahr���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMJAHR(String value) {
            this.mjahr = value;
        }

        /**
         * ��ȡzeile���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getZEILE() {
            return zeile;
        }

        /**
         * ����zeile���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setZEILE(String value) {
            this.zeile = value;
        }

        /**
         * ��ȡcpudt���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCPUDT() {
            return cpudt;
        }

        /**
         * ����cpudt���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCPUDT(String value) {
            this.cpudt = value;
        }

        /**
         * ��ȡcputm���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCPUTM() {
            return cputm;
        }

        /**
         * ����cputm���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCPUTM(String value) {
            this.cputm = value;
        }

        /**
         * ��ȡbwart���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBWART() {
            return bwart;
        }

        /**
         * ����bwart���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBWART(String value) {
            this.bwart = value;
        }

        /**
         * ��ȡshkzg���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSHKZG() {
            return shkzg;
        }

        /**
         * ����shkzg���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSHKZG(String value) {
            this.shkzg = value;
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
         * ��ȡmatkl���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMATKL() {
            return matkl;
        }

        /**
         * ����matkl���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMATKL(String value) {
            this.matkl = value;
        }

        /**
         * ��ȡcharg���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCHARG() {
            return charg;
        }

        /**
         * ����charg���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCHARG(String value) {
            this.charg = value;
        }

        /**
         * ��ȡlfimg���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getLFIMG() {
            return lfimg;
        }

        /**
         * ����lfimg���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setLFIMG(BigDecimal value) {
            this.lfimg = value;
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
         * ��ȡkunnrsaleto���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getKUNNRSALETO() {
            return kunnrsaleto;
        }

        /**
         * ����kunnrsaleto���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setKUNNRSALETO(String value) {
            this.kunnrsaleto = value;
        }

        /**
         * ��ȡkunnrsendto���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getKUNNRSENDTO() {
            return kunnrsendto;
        }

        /**
         * ����kunnrsendto���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setKUNNRSENDTO(String value) {
            this.kunnrsendto = value;
        }

        /**
         * ��ȡvbelnso���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getVBELNSO() {
            return vbelnso;
        }

        /**
         * ����vbelnso���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setVBELNSO(String value) {
            this.vbelnso = value;
        }

        /**
         * ��ȡvbelndn���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getVBELNDN() {
            return vbelndn;
        }

        /**
         * ����vbelndn���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setVBELNDN(String value) {
            this.vbelndn = value;
        }

        /**
         * ��ȡtknum���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTKNUM() {
            return tknum;
        }

        /**
         * ����tknum���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTKNUM(String value) {
            this.tknum = value;
        }

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
         * ��ȡauart���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAUART() {
            return auart;
        }

        /**
         * ����auart���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAUART(String value) {
            this.auart = value;
        }

        /**
         * ��ȡreserve1���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRESERVE1() {
            return reserve1;
        }

        /**
         * ����reserve1���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRESERVE1(String value) {
            this.reserve1 = value;
        }

        /**
         * ��ȡreserve2���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRESERVE2() {
            return reserve2;
        }

        /**
         * ����reserve2���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRESERVE2(String value) {
            this.reserve2 = value;
        }

        /**
         * ��ȡbstkd���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBSTKD() {
            return bstkd;
        }

        /**
         * ����bstkd���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBSTKD(String value) {
            this.bstkd = value;
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

    }


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
     *         &lt;element name="KUNNR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="LGORT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="MATNR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="CHARG" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="LFIMG" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
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
        "kunnr",
        "lgort",
        "matnr",
        "charg",
        "lfimg"
    })
    public static class OUTPUT1 {

        @XmlElement(name = "KUNNR", required = true)
        protected String kunnr;
        @XmlElement(name = "LGORT", required = true)
        protected String lgort;
        @XmlElement(name = "MATNR", required = true)
        protected String matnr;
        @XmlElement(name = "CHARG", required = true)
        protected String charg;
        @XmlElement(name = "LFIMG", required = true)
        protected BigDecimal lfimg;

        /**
         * ��ȡkunnr���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getKUNNR() {
            return kunnr;
        }

        /**
         * ����kunnr���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setKUNNR(String value) {
            this.kunnr = value;
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
         * ��ȡcharg���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCHARG() {
            return charg;
        }

        /**
         * ����charg���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCHARG(String value) {
            this.charg = value;
        }

        /**
         * ��ȡlfimg���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getLFIMG() {
            return lfimg;
        }

        /**
         * ����lfimg���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setLFIMG(BigDecimal value) {
            this.lfimg = value;
        }

    }

}
