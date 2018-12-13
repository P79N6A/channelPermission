package com.haier.svc.purchase.queryDNFrom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ZINT_WX_WT_LOG complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ZINT_WX_WT_LOG"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="MANDT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BSTNK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BSTKD" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="POSNR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="TKNUM" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SOURCE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SOURCE_SN" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CRDAT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CRZET" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SDABW" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="KUNNR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="KUNWE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="NAME1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MESSAGE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD2" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD3" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD4" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD5" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD6" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD7" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD8" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD9" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ADD10" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZINT_WX_WT_LOG", propOrder = {
    "mandt",
    "bstnk",
    "bstkd",
    "posnr",
    "tknum",
    "source",
    "sourcesn",
    "crdat",
    "crzet",
    "sdabw",
    "kunnr",
    "kunwe",
    "name1",
    "message",
    "add1",
    "add2",
    "add3",
    "add4",
    "add5",
    "add6",
    "add7",
    "add8",
    "add9",
    "add10"
})
public class ZINTWXWTLOG {

    @XmlElement(name = "MANDT", required = true)
    protected String mandt;
    @XmlElement(name = "BSTNK", required = true)
    protected String bstnk;
    @XmlElement(name = "BSTKD", required = true)
    protected String bstkd;
    @XmlElement(name = "POSNR", required = true)
    protected String posnr;
    @XmlElement(name = "TKNUM", required = true)
    protected String tknum;
    @XmlElement(name = "SOURCE", required = true)
    protected String source;
    @XmlElement(name = "SOURCE_SN", required = true)
    protected String sourcesn;
    @XmlElement(name = "CRDAT", required = true)
    protected String crdat;
    @XmlElement(name = "CRZET", required = true)
    protected String crzet;
    @XmlElement(name = "SDABW", required = true)
    protected String sdabw;
    @XmlElement(name = "KUNNR", required = true)
    protected String kunnr;
    @XmlElement(name = "KUNWE", required = true)
    protected String kunwe;
    @XmlElement(name = "NAME1", required = true)
    protected String name1;
    @XmlElement(name = "MESSAGE", required = true)
    protected String message;
    @XmlElement(name = "ADD1", required = true)
    protected String add1;
    @XmlElement(name = "ADD2", required = true)
    protected String add2;
    @XmlElement(name = "ADD3", required = true)
    protected String add3;
    @XmlElement(name = "ADD4", required = true)
    protected String add4;
    @XmlElement(name = "ADD5", required = true)
    protected String add5;
    @XmlElement(name = "ADD6", required = true)
    protected String add6;
    @XmlElement(name = "ADD7", required = true)
    protected String add7;
    @XmlElement(name = "ADD8", required = true)
    protected String add8;
    @XmlElement(name = "ADD9", required = true)
    protected String add9;
    @XmlElement(name = "ADD10", required = true)
    protected String add10;

    /**
     * ��ȡmandt���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMANDT() {
        return mandt;
    }

    /**
     * ����mandt���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMANDT(String value) {
        this.mandt = value;
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
     * ��ȡsource���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSOURCE() {
        return source;
    }

    /**
     * ����source���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSOURCE(String value) {
        this.source = value;
    }

    /**
     * ��ȡsourcesn���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSOURCESN() {
        return sourcesn;
    }

    /**
     * ����sourcesn���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSOURCESN(String value) {
        this.sourcesn = value;
    }

    /**
     * ��ȡcrdat���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCRDAT() {
        return crdat;
    }

    /**
     * ����crdat���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCRDAT(String value) {
        this.crdat = value;
    }

    /**
     * ��ȡcrzet���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCRZET() {
        return crzet;
    }

    /**
     * ����crzet���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCRZET(String value) {
        this.crzet = value;
    }

    /**
     * ��ȡsdabw���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSDABW() {
        return sdabw;
    }

    /**
     * ����sdabw���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSDABW(String value) {
        this.sdabw = value;
    }

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
     * ��ȡkunwe���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKUNWE() {
        return kunwe;
    }

    /**
     * ����kunwe���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKUNWE(String value) {
        this.kunwe = value;
    }

    /**
     * ��ȡname1���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNAME1() {
        return name1;
    }

    /**
     * ����name1���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNAME1(String value) {
        this.name1 = value;
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

    /**
     * ��ȡadd4���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD4() {
        return add4;
    }

    /**
     * ����add4���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD4(String value) {
        this.add4 = value;
    }

    /**
     * ��ȡadd5���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD5() {
        return add5;
    }

    /**
     * ����add5���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD5(String value) {
        this.add5 = value;
    }

    /**
     * ��ȡadd6���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD6() {
        return add6;
    }

    /**
     * ����add6���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD6(String value) {
        this.add6 = value;
    }

    /**
     * ��ȡadd7���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD7() {
        return add7;
    }

    /**
     * ����add7���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD7(String value) {
        this.add7 = value;
    }

    /**
     * ��ȡadd8���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD8() {
        return add8;
    }

    /**
     * ����add8���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD8(String value) {
        this.add8 = value;
    }

    /**
     * ��ȡadd9���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD9() {
        return add9;
    }

    /**
     * ����add9���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD9(String value) {
        this.add9 = value;
    }

    /**
     * ��ȡadd10���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADD10() {
        return add10;
    }

    /**
     * ����add10���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADD10(String value) {
        this.add10 = value;
    }

}
