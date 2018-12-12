
package com.haier.svc.bean.transforecastpracticalfromb2ctooms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>outType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="outType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="categorycode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="catn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="itemcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="itemname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="plname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pltype" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="productLineId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="productSeriesId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="salesChanManagerID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="salesChanManagerName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="smbCcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="smbName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tradeCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Msg" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Flag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Msg_detail" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "categorycode",
    "catn",
    "itemcode",
    "itemname",
    "message",
    "plname",
    "pltype",
    "productLineId",
    "productSeriesId",
    "salesChanManagerID",
    "salesChanManagerName",
    "smbCcode",
    "smbName",
    "tradeCode",
    "msg",
    "flag",
    "msgDetail"
})
public class OutType {

    @XmlElement(required = true)
    protected String categorycode;
    @XmlElement(required = true)
    protected String catn;
    @XmlElement(required = true)
    protected String itemcode;
    @XmlElement(required = true)
    protected String itemname;
    @XmlElement(required = true)
    protected String message;
    @XmlElement(required = true)
    protected String plname;
    @XmlElement(required = true)
    protected String pltype;
    @XmlElement(required = true)
    protected String productLineId;
    @XmlElement(required = true)
    protected String productSeriesId;
    @XmlElement(required = true)
    protected String salesChanManagerID;
    @XmlElement(required = true)
    protected String salesChanManagerName;
    @XmlElement(required = true)
    protected String smbCcode;
    @XmlElement(required = true)
    protected String smbName;
    @XmlElement(required = true)
    protected String tradeCode;
    @XmlElement(name = "Msg", required = true)
    protected String msg;
    @XmlElement(name = "Flag", required = true)
    protected String flag;
    @XmlElement(name = "Msg_detail", required = true)
    protected String msgDetail;

    /**
     * 获取categorycode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategorycode() {
        return categorycode;
    }

    /**
     * 设置categorycode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategorycode(String value) {
        this.categorycode = value;
    }

    /**
     * 获取catn属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCatn() {
        return catn;
    }

    /**
     * 设置catn属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCatn(String value) {
        this.catn = value;
    }

    /**
     * 获取itemcode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemcode() {
        return itemcode;
    }

    /**
     * 设置itemcode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemcode(String value) {
        this.itemcode = value;
    }

    /**
     * 获取itemname属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemname() {
        return itemname;
    }

    /**
     * 设置itemname属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemname(String value) {
        this.itemname = value;
    }

    /**
     * 获取message属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置message属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * 获取plname属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlname() {
        return plname;
    }

    /**
     * 设置plname属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlname(String value) {
        this.plname = value;
    }

    /**
     * 获取pltype属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPltype() {
        return pltype;
    }

    /**
     * 设置pltype属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPltype(String value) {
        this.pltype = value;
    }

    /**
     * 获取productLineId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductLineId() {
        return productLineId;
    }

    /**
     * 设置productLineId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductLineId(String value) {
        this.productLineId = value;
    }

    /**
     * 获取productSeriesId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductSeriesId() {
        return productSeriesId;
    }

    /**
     * 设置productSeriesId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductSeriesId(String value) {
        this.productSeriesId = value;
    }

    /**
     * 获取salesChanManagerID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesChanManagerID() {
        return salesChanManagerID;
    }

    /**
     * 设置salesChanManagerID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesChanManagerID(String value) {
        this.salesChanManagerID = value;
    }

    /**
     * 获取salesChanManagerName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesChanManagerName() {
        return salesChanManagerName;
    }

    /**
     * 设置salesChanManagerName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesChanManagerName(String value) {
        this.salesChanManagerName = value;
    }

    /**
     * 获取smbCcode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmbCcode() {
        return smbCcode;
    }

    /**
     * 设置smbCcode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmbCcode(String value) {
        this.smbCcode = value;
    }

    /**
     * 获取smbName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmbName() {
        return smbName;
    }

    /**
     * 设置smbName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmbName(String value) {
        this.smbName = value;
    }

    /**
     * 获取tradeCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTradeCode() {
        return tradeCode;
    }

    /**
     * 设置tradeCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTradeCode(String value) {
        this.tradeCode = value;
    }

    /**
     * 获取msg属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置msg属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsg(String value) {
        this.msg = value;
    }

    /**
     * 获取flag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlag() {
        return flag;
    }

    /**
     * 设置flag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlag(String value) {
        this.flag = value;
    }

    /**
     * 获取msgDetail属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsgDetail() {
        return msgDetail;
    }

    /**
     * 设置msgDetail属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsgDetail(String value) {
        this.msgDetail = value;
    }

}
