package com.haier.distribute.data.model;

import java.io.Serializable;

/**
 * <p>Table: <strong>ProductsNew</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>productName</td><td>{@link String}</td><td>productName</td><td>varchar</td><td>商品名称</td></tr>
 *   <tr><td>brandId</td><td>{@link Integer}</td><td>brandId</td><td>int</td><td>品牌id</td></tr>
 *   <tr><td>productTypeId</td><td>{@link Integer}</td><td>productTypeId</td><td>int</td><td>商品类型id</td></tr>
 *   <tr><td>addTime</td><td>{@link Integer}</td><td>addTime</td><td>int</td><td>添加时间</td></tr>
 *   <tr><td>lastModify</td><td>{@link Integer}</td><td>lastModify</td><td>int</td><td>最后更改时间</td></tr>
 *   <tr><td>productCateId</td><td>{@link Integer}</td><td>productCateId</td><td>int</td><td>商品类目id</td></tr>
 *   <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>varchar</td><td>商家编码</td></tr>
 *   <tr><td>shippingMode</td><td>{@link String}</td><td>shippingMode</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>multiStorage</td><td>{@link Integer}</td><td>multiStorage</td><td>tinyint</td><td>是否采用多级库位关系</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-4-30
 * @email yudi@sina.com
 */
public class ProductBase implements Serializable {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -5768812267007445021L;

    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private String productName;

    /**
     * 获取 商品名称。
     */
    public String getProductName() {
        return this.productName;
    }

    /**
     * 设置 商品名称。
     *
     * @param value 属性值
     */
    public void setProductName(String value) {
        this.productName = value;
    }

    private Integer brandId;

    /**
     * 获取 品牌id。
     */
    public Integer getBrandId() {
        return this.brandId;
    }

    /**
     * 设置 品牌id。
     *
     * @param value 属性值
     */
    public void setBrandId(Integer value) {
        this.brandId = value;
    }

    private Integer productTypeId;

    /**
     * 获取 商品类型id。
     */
    public Integer getProductTypeId() {
        return this.productTypeId;
    }

    /**
     * 设置 商品类型id。
     *
     * @param value 属性值
     */
    public void setProductTypeId(Integer value) {
        this.productTypeId = value;
    }

    private Integer addTime;

    /**
     * 获取 添加时间。
     */
    public Integer getAddTime() {
        return this.addTime;
    }

    /**
     * 设置 添加时间。
     *
     * @param value 属性值
     */
    public void setAddTime(Integer value) {
        this.addTime = value;
    }

    private Integer lastModify;

    /**
     * 获取 最后更改时间。
     */
    public Integer getLastModify() {
        return this.lastModify;
    }

    /**
     * 设置 最后更改时间。
     *
     * @param value 属性值
     */
    public void setLastModify(Integer value) {
        this.lastModify = value;
    }

    private Integer productCateId;

    /**
     * 获取 商品类目id。
     */
    public Integer getProductCateId() {
        return this.productCateId;
    }

    /**
     * 设置 商品类目id。
     *
     * @param value 属性值
     */
    public void setProductCateId(Integer value) {
        this.productCateId = value;
    }

    private String sku;

    /**
     * 获取 商家编码。
     */
    public String getSku() {
        return this.sku;
    }

    /**
     * 设置 商家编码。
     *
     * @param value 属性值
     */
    public void setSku(String value) {
        this.sku = value;
    }

    private String scode;

    /**
     * 指定库位编码
     * @return
     */
    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    private String shippingMode;

    public String getShippingMode() {
        return this.shippingMode;
    }

    public void setShippingMode(String value) {
        this.shippingMode = value;
    }

    private Integer multiStorage = 0;

    /**
     * 获取 是否采用多级库位关系。
     */
    public Integer getMultiStorage() {
        return this.multiStorage;
    }

    /**
     * 设置 是否采用多级库位关系。
     *
     * @param value 属性值
     */
    public void setMultiStorage(Integer value) {
        this.multiStorage = value;
    }

    public static final Integer PRODUCTO2OTYPE_1 = 1;
    public static final Integer PRODUCTO2OTYPE_2 = 2;
    public static final Integer PRODCUTO2OTYPE_3 = 3;

    /**
     * 商品o2o类型:1.商城专供型号2.O2O共享型号3.OTO引流型号
     */
    private Integer productO2OType;

    /**
     * 获取 O2O类型
     * @return
     */
    public Integer getProductO2OType() {
        return productO2OType;
    }

    /**
     * 设置 O2O类型
     * @param productO2OType
     */
    public void setProductO2OType(Integer productO2OType) {
        this.productO2OType = productO2OType;
    }

    private Integer onSale;

    public Integer getOnSale() {
        return onSale;
    }

    public void setOnSale(Integer onSale) {
        this.onSale = onSale;
    }

}
