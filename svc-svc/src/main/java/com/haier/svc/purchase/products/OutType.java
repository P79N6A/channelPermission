
package com.haier.svc.purchase.products;

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
 * &lt;complexType name="OutType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="materialDesc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="primaryUom" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="department" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="proBrand" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="kindOne" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="kindAd" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="productList" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="productLineCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="proType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cirChar" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flag1010" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="conTaxCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="retcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="retmsg" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OutType", propOrder = {
    "materialDesc",
    "primaryUom",
    "department",
    "proBrand",
    "kindOne",
    "kindAd",
    "productList",
    "productLineCode",
    "proType",
    "cirChar",
    "flag1010",
    "conTaxCode",
    "retcode",
    "retmsg"
})
public class OutType {

    @XmlElement(required = true)
    protected String materialDesc;
    @XmlElement(required = true)
    protected String primaryUom;
    @XmlElement(required = true)
    protected String department;
    @XmlElement(required = true)
    protected String proBrand;
    @XmlElement(required = true)
    protected String kindOne;
    @XmlElement(required = true)
    protected String kindAd;
    @XmlElement(required = true)
    protected String productList;
    @XmlElement(required = true)
    protected String productLineCode;
    @XmlElement(required = true)
    protected String proType;
    @XmlElement(required = true)
    protected String cirChar;
    @XmlElement(required = true)
    protected String flag1010;
    @XmlElement(required = true)
    protected String conTaxCode;
    @XmlElement(required = true)
    protected String retcode;
    @XmlElement(required = true)
    protected String retmsg;

    /**
     * ��ȡmaterialDesc���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaterialDesc() {
        return materialDesc;
    }

    /**
     * ����materialDesc���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaterialDesc(String value) {
        this.materialDesc = value;
    }

    /**
     * ��ȡprimaryUom���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimaryUom() {
        return primaryUom;
    }

    /**
     * ����primaryUom���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimaryUom(String value) {
        this.primaryUom = value;
    }

    /**
     * ��ȡdepartment���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartment() {
        return department;
    }

    /**
     * ����department���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartment(String value) {
        this.department = value;
    }

    /**
     * ��ȡproBrand���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProBrand() {
        return proBrand;
    }

    /**
     * ����proBrand���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProBrand(String value) {
        this.proBrand = value;
    }

    /**
     * ��ȡkindOne���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKindOne() {
        return kindOne;
    }

    /**
     * ����kindOne���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKindOne(String value) {
        this.kindOne = value;
    }

    /**
     * ��ȡkindAd���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKindAd() {
        return kindAd;
    }

    /**
     * ����kindAd���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKindAd(String value) {
        this.kindAd = value;
    }

    /**
     * ��ȡproductList���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductList() {
        return productList;
    }

    /**
     * ����productList���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductList(String value) {
        this.productList = value;
    }

    /**
     * ��ȡproductLineCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductLineCode() {
        return productLineCode;
    }

    /**
     * ����productLineCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductLineCode(String value) {
        this.productLineCode = value;
    }

    /**
     * ��ȡproType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProType() {
        return proType;
    }

    /**
     * ����proType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProType(String value) {
        this.proType = value;
    }

    /**
     * ��ȡcirChar���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCirChar() {
        return cirChar;
    }

    /**
     * ����cirChar���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCirChar(String value) {
        this.cirChar = value;
    }

    /**
     * ��ȡflag1010���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlag1010() {
        return flag1010;
    }

    /**
     * ����flag1010���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlag1010(String value) {
        this.flag1010 = value;
    }

    /**
     * ��ȡconTaxCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConTaxCode() {
        return conTaxCode;
    }

    /**
     * ����conTaxCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConTaxCode(String value) {
        this.conTaxCode = value;
    }

    /**
     * ��ȡretcode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRetcode() {
        return retcode;
    }

    /**
     * ����retcode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRetcode(String value) {
        this.retcode = value;
    }

    /**
     * ��ȡretmsg���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRetmsg() {
        return retmsg;
    }

    /**
     * ����retmsg���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRetmsg(String value) {
        this.retmsg = value;
    }

}
