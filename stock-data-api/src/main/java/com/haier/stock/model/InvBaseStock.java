package com.haier.stock.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 库存。
 * <p>
 * <p>Table: <strong>inv_base_stock</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 * <tr style="background-color:#ddd;Text-align:Left;">
 * <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 * </tr>
 * <tr><td>stoId</td><td>{@link Integer}</td><td>sto_id</td><td>int</td><td>&nbsp;</td></tr>
 * <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>varchar</td><td>物料编码</td></tr>
 * <tr><td>lesSecCode</td><td>{@link String}</td><td>les_sec_code</td><td>varchar</td><td>库位编码。</td></tr>
 * <tr><td>secCode</td><td>{@link String}</td><td>sec_code</td><td>varchar</td><td>虚拟库位编码</td></tr>
 * <tr><td>stockQty</td><td>{@link Integer}</td><td>stock_qty</td><td>int</td><td>总库存量。</td></tr>
 * <tr><td>frozenQty</td><td>{@link Integer}</td><td>frozen_qty</td><td>int</td><td>冻结数量。</td></tr>
 * <tr><td>createTime</td><td>{@link Date}</td><td>create_time</td><td>datetime</td><td>记录创建时间</td></tr>
 * <tr><td>updateTime</td><td>{@link Date}</td><td>update_time</td><td>datetime</td><td>最后更新时间。</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-11-18
 * @email yudi@sina.com
 */
public class InvBaseStock extends BaseStock implements Serializable {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private Integer stoId;

    public Integer getStoId() {
        return this.stoId;
    }

    public void setStoId(Integer value) {
        this.stoId = value;
    }



    private String lesSecCode;

    /**
     * 获取 库位编码。
     */
    public String getLesSecCode() {
        return this.lesSecCode;
    }

    /**
     * 设置 库位编码。
     *
     * @param value 属性值
     */
    public void setLesSecCode(String value) {
        this.lesSecCode = value;
    }

    private String secCode;

    /**
     * 获取 虚拟库位编码。
     */
    public String getSecCode() {
        return this.secCode;
    }

    /**
     * 设置 虚拟库位编码。
     *
     * @param value 属性值
     */
    public void setSecCode(String value) {
        this.secCode = value;
    }

//    private Integer stockQty = 0;
//
//    /**
//     * 获取 总库存量。
//     */
//    public Integer getStockQty() {
//        return this.stockQty;
//    }
//
//    /**
//     * 设置 总库存量。
//     *
//     * @param value 属性值
//     */
//    public void setStockQty(Integer value) {
//        this.stockQty = value;
//    }

//    private Integer frozenQty = 0;
//
//    /**
//     * 获取 冻结数量。
//     */
//    public Integer getFrozenQty() {
//        return this.frozenQty;
//    }
//
//    /**
//     * 设置 冻结数量。
//     *
//     * @param value 属性值
//     */
//    public void setFrozenQty(Integer value) {
//        this.frozenQty = value;
//    }

    private Date createTime;

    /**
     * 获取 记录创建时间。
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置 记录创建时间。
     *
     * @param value 属性值
     */
    public void setCreateTime(Date value) {
        this.createTime = value;
    }

    private Date updateTime;

    /**
     * 获取 最后更新时间。
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置 最后更新时间。
     *
     * @param value 属性值
     */
    public void setUpdateTime(Date value) {
        this.updateTime = value;
    }

    private String productName;

    /**
     * 获取 产品型号。
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 设置 产品型号。
     *
     * @param value 属性值
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    private String productTypeName;

    /**
     * 获取 品类。
     */
    public String getProductTypeName() {
        return productTypeName;
    }

    /**
     * 设置 品类。
     *
     * @param value 属性值
     */
    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    private String productGroupName;

    /**
     * 获取 产品组。
     */
    public String getProductGroupName() {
        return productGroupName;
    }

    /**
     * 设置 产品组。
     *
     * @param value 属性值
     */
    public void setProductGroupName(String productGroupName) {
        this.productGroupName = productGroupName;
    }

    private String secName;

    /**
     * 获取 库位名称。
     */
    public String getSecName() {
        return secName;
    }

    /**
     * 设置 库位名称。
     *
     * @param value 属性值
     */
    public void setSecName(String secName) {
        this.secName = secName;
    }



    /**
     * 可用库存
     */
    private Integer avaiableQty = 0;

    public Integer getAvaiableQty() {
        return avaiableQty;
    }

    public void setAvaiableQty(Integer avaiableQty) {
        this.avaiableQty = avaiableQty;
    }

    /**
     * 品类
     */
    private String cbsCategory;

    public String getCbsCategory() {
        return cbsCategory;
    }

    public void setCbsCategory(String cbsCategory) {
        this.cbsCategory = cbsCategory;
    }

    private String stockSku;
    private Integer stockFrozenQty;
    private Integer stockStockQty;
    private String stockItemProperty;
    private String avaiableQtyCode;
    private String stockQtyCode;

    public String getStockSku() {
        return stockSku;
    }

    public void setStockSku(String stockSku) {
        this.stockSku = stockSku;
    }

    public Integer getStockFrozenQty() {
        return stockFrozenQty;
    }

    public void setStockFrozenQty(Integer stockFrozenQty) {
        this.stockFrozenQty = stockFrozenQty;
    }

    public Integer getStockStockQty() {
        return stockStockQty;
    }

    public void setStockStockQty(Integer stockStockQty) {
        this.stockStockQty = stockStockQty;
    }

    public String getAvaiableQtyCode() {
        return avaiableQtyCode;
    }

    public void setAvaiableQtyCode(String avaiableQtyCode) {
        this.avaiableQtyCode = avaiableQtyCode;
    }

    public String getStockQtyCode() {
        return stockQtyCode;
    }

    public void setStockQtyCode(String stockQtyCode) {
        this.stockQtyCode = stockQtyCode;
    }

    public String getStockItemProperty() {
        return stockItemProperty;
    }

    public void setStockItemProperty(String stockItemProperty) {
        this.stockItemProperty = stockItemProperty;
    }
}