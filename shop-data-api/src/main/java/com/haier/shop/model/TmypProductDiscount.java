package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author:JinXueqian
 * @Date: 2018/8/21 16:57
 */
public class TmypProductDiscount implements Serializable {

    private static final long serialVersionUID = 45647859706570882L;


    private Integer id;

    /**
     * 商品品牌型号名称
     */
    private String productName;
    /**
     *商品类型
     */
    private String productType;
    /**
     *零售价
     */
    private BigDecimal salePrice;
    /**
     *采购价
     */
    private BigDecimal purchasePrice;
    /**
     *折扣率
     */
    private BigDecimal discount;
    /**
     *添加时间
     */
    private int addTime;
    /**
     *修改时间
     */
    private int modifyTime;
    /**
     *sku物料
     */
    private String sku;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public int getAddTime() {
        return addTime;
    }

    public void setAddTime(int addTime) {
        this.addTime = addTime;
    }

    public int getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(int modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
