package com.haier.invoice.model.einvoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;

/**
 * 商品条目
 *                       
 * @Filename: Item.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Item implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 5507889170545350548L;
    /**
     * 总额
     */
    @XmlAttribute
    String amount;
    /**
     * 商品代码
     */
    @XmlAttribute
    String code;
    /**
     * 商品名称
     */
    @XmlAttribute
    String name;
    /**
     * 非税价格
     */
    @XmlAttribute
    String noTaxAmount;
    /**
     * 单价
     */
    @XmlAttribute
    String price;
    /**
     * 数量
     */
    @XmlAttribute
    String quantity;
    /**
     * 商品备注
     */
    @XmlAttribute
    String remark;
    /**
     * 税金
     */
    @XmlAttribute
    String taxAmount;
    /**
     * 产品税率
     */
    @XmlAttribute
    String taxRate;
    /**
     * 商品单位
     */
    @XmlAttribute
    String uom;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoTaxAmount() {
        return noTaxAmount;
    }

    public void setNoTaxAmount(String noTaxAmount) {
        this.noTaxAmount = noTaxAmount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }
}
