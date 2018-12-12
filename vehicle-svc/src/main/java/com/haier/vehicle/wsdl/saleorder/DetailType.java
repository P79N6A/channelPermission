
package com.haier.vehicle.wsdl.saleorder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>DetailType complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="DetailType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="InvCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="InvSort" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="InvBrand" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="InvState" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Qty" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="UnitPrice" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SumMoney" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="StockPrice" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RetailPrice" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ActPrice" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BateRate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BateMoney" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="VerCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="VerMoney" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ProLossMoney" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="LossRate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsFL" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsKPO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="InvMemo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "DetailType", propOrder = {
    "invCode",
    "invSort",
    "invBrand",
    "invState",
    "qty",
    "unitPrice",
    "sumMoney",
    "stockPrice",
    "retailPrice",
    "actPrice",
    "bateRate",
    "bateMoney",
    "verCode",
    "verMoney",
    "proLossMoney",
    "lossRate",
    "isFL",
    "isKPO",
    "invMemo",
    "add1",
    "add2",
    "add3"
})
public class DetailType {

    @XmlElement(name = "InvCode", required = true, nillable = true)
    protected String invCode;
    @XmlElement(name = "InvSort", required = true, nillable = true)
    protected String invSort;
    @XmlElement(name = "InvBrand", required = true, nillable = true)
    protected String invBrand;
    @XmlElement(name = "InvState", required = true, nillable = true)
    protected String invState;
    @XmlElement(name = "Qty", required = true, nillable = true)
    protected String qty;
    @XmlElement(name = "UnitPrice", required = true, nillable = true)
    protected String unitPrice;
    @XmlElement(name = "SumMoney", required = true, nillable = true)
    protected String sumMoney;
    @XmlElement(name = "StockPrice", required = true, nillable = true)
    protected String stockPrice;
    @XmlElement(name = "RetailPrice", required = true, nillable = true)
    protected String retailPrice;
    @XmlElement(name = "ActPrice", required = true, nillable = true)
    protected String actPrice;
    @XmlElement(name = "BateRate", required = true, nillable = true)
    protected String bateRate;
    @XmlElement(name = "BateMoney", required = true, nillable = true)
    protected String bateMoney;
    @XmlElement(name = "VerCode", required = true, nillable = true)
    protected String verCode;
    @XmlElement(name = "VerMoney", required = true, nillable = true)
    protected String verMoney;
    @XmlElement(name = "ProLossMoney", required = true, nillable = true)
    protected String proLossMoney;
    @XmlElement(name = "LossRate", required = true, nillable = true)
    protected String lossRate;
    @XmlElement(name = "IsFL", required = true, nillable = true)
    protected String isFL;
    @XmlElement(name = "IsKPO", required = true, nillable = true)
    protected String isKPO;
    @XmlElement(name = "InvMemo", required = true, nillable = true)
    protected String invMemo;
    @XmlElement(name = "ADD1", required = true, nillable = true)
    protected String add1;
    @XmlElement(name = "ADD2", required = true, nillable = true)
    protected String add2;
    @XmlElement(name = "ADD3", required = true, nillable = true)
    protected String add3;

    /**
     * ��ȡinvCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvCode() {
        return invCode;
    }

    /**
     * ����invCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvCode(String value) {
        this.invCode = value;
    }

    /**
     * ��ȡinvSort���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvSort() {
        return invSort;
    }

    /**
     * ����invSort���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvSort(String value) {
        this.invSort = value;
    }

    /**
     * ��ȡinvBrand���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvBrand() {
        return invBrand;
    }

    /**
     * ����invBrand���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvBrand(String value) {
        this.invBrand = value;
    }

    /**
     * ��ȡinvState���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvState() {
        return invState;
    }

    /**
     * ����invState���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvState(String value) {
        this.invState = value;
    }

    /**
     * ��ȡqty���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQty() {
        return qty;
    }

    /**
     * ����qty���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQty(String value) {
        this.qty = value;
    }

    /**
     * ��ȡunitPrice���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitPrice() {
        return unitPrice;
    }

    /**
     * ����unitPrice���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitPrice(String value) {
        this.unitPrice = value;
    }

    /**
     * ��ȡsumMoney���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSumMoney() {
        return sumMoney;
    }

    /**
     * ����sumMoney���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSumMoney(String value) {
        this.sumMoney = value;
    }

    /**
     * ��ȡstockPrice���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStockPrice() {
        return stockPrice;
    }

    /**
     * ����stockPrice���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStockPrice(String value) {
        this.stockPrice = value;
    }

    /**
     * ��ȡretailPrice���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRetailPrice() {
        return retailPrice;
    }

    /**
     * ����retailPrice���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRetailPrice(String value) {
        this.retailPrice = value;
    }

    /**
     * ��ȡactPrice���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActPrice() {
        return actPrice;
    }

    /**
     * ����actPrice���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActPrice(String value) {
        this.actPrice = value;
    }

    /**
     * ��ȡbateRate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBateRate() {
        return bateRate;
    }

    /**
     * ����bateRate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBateRate(String value) {
        this.bateRate = value;
    }

    /**
     * ��ȡbateMoney���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBateMoney() {
        return bateMoney;
    }

    /**
     * ����bateMoney���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBateMoney(String value) {
        this.bateMoney = value;
    }

    /**
     * ��ȡverCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVerCode() {
        return verCode;
    }

    /**
     * ����verCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVerCode(String value) {
        this.verCode = value;
    }

    /**
     * ��ȡverMoney���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVerMoney() {
        return verMoney;
    }

    /**
     * ����verMoney���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVerMoney(String value) {
        this.verMoney = value;
    }

    /**
     * ��ȡproLossMoney���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProLossMoney() {
        return proLossMoney;
    }

    /**
     * ����proLossMoney���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProLossMoney(String value) {
        this.proLossMoney = value;
    }

    /**
     * ��ȡlossRate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLossRate() {
        return lossRate;
    }

    /**
     * ����lossRate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLossRate(String value) {
        this.lossRate = value;
    }

    /**
     * ��ȡisFL���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsFL() {
        return isFL;
    }

    /**
     * ����isFL���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsFL(String value) {
        this.isFL = value;
    }

    /**
     * ��ȡisKPO���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsKPO() {
        return isKPO;
    }

    /**
     * ����isKPO���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsKPO(String value) {
        this.isKPO = value;
    }

    /**
     * ��ȡinvMemo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvMemo() {
        return invMemo;
    }

    /**
     * ����invMemo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvMemo(String value) {
        this.invMemo = value;
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
