package com.haier.stock.model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Table: <strong>inv_base_store</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>主键：自动增长列</td></tr>
 *   <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>varchar</td><td>物料编码</td></tr>
 *   <tr><td>itemProperty</td><td>{@link String}</td><td>item_property</td><td>char</td><td>批次</td></tr>
 *   <tr><td>storeCode</td><td>{@link String}</td><td>store_code</td><td>varchar</td><td>店铺码</td></tr>
 *   <tr><td>stockQty</td><td>{@link Integer}</td><td>stock_qty</td><td>int</td><td>库存总量</td></tr>
 *   <tr><td>frozenQty</td><td>{@link Integer}</td><td>frozen_qty</td><td>int</td><td>冻结数量</td></tr>
 *   <tr><td>storeTs</td><td>{@link Integer}</td><td>store_ts</td><td>int</td><td>EC库存更新时间</td></tr>
 *   <tr><td>createTime</td><td>{@link Date}</td><td>create_time</td><td>datetime</td><td>创建时间</td></tr>
 *   <tr><td>updateTime</td><td>{@link Date}</td><td>update_time</td><td>datetime</td><td>更新时间</td></tr>
 * </table>
 *
 * @author 
 * @date 2015-5-27
 * @email yudi@sina.com
 */
public class InvBaseStore extends BaseStock implements Serializable {
    private Integer id;

    /**
     * 获取 主键：自动增长列。
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 设置 主键：自动增长列。
     *
     * @param value 属性值
     */
    public void setId(Integer value) {
        this.id = value;
    }

    private String sku;

    /**
     * 获取 物料编码。
     */
    public String getSku() {
        return this.sku;
    }

    /**
     * 设置 物料编码。
     *
     * @param value 属性值
     */
    public void setSku(String value) {
        this.sku = value;
    }

    private String itemProperty;

    /**
     * 获取 批次。
     */
    public String getItemProperty() {
        return this.itemProperty;
    }

    /**
     * 设置 批次。
     *
     * @param value 属性值
     */
    public void setItemProperty(String value) {
        this.itemProperty = value;
    }

    private String storeCode;

    /**
     * 获取 店铺码。
     */
    public String getStoreCode() {
        return this.storeCode;
    }

    /**
     * 设置 店铺码。
     *
     * @param value 属性值
     */
    public void setStoreCode(String value) {
        this.storeCode = value;
    }

    private Integer stockQty;

    /**
     * 获取 库存总量。
     */
    public Integer getStockQty() {
        return this.stockQty;
    }

    /**
     * 设置 库存总量。
     *
     * @param value 属性值
     */
    public void setStockQty(Integer value) {
        this.stockQty = value;
    }

    private Integer frozenQty;

    /**
     * 获取 冻结数量。
     */
    public Integer getFrozenQty() {
        return this.frozenQty;
    }

    /**
     * 设置 冻结数量。
     *
     * @param value 属性值
     */
    public void setFrozenQty(Integer value) {
        this.frozenQty = value;
    }

    private Long storeTs;

    /**
     * 获取 EC库存更新时间。
     */
    public Long getStoreTs() {
        return this.storeTs;
    }

    /**
     * 设置 EC库存更新时间。
     *
     * @param value 属性值
     */
    public void setStoreTs(Long value) {
        this.storeTs = value;
    }

    private Date createTime;

    /**
     * 获取 创建时间。
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置 创建时间。
     *
     * @param value 属性值
     */
    public void setCreateTime(Date value) {
        this.createTime = value;
    }

    private Date updateTime;

    /**
     * 获取 更新时间。
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置 更新时间。
     *
     * @param value 属性值
     */
    public void setUpdateTime(Date value) {
        this.updateTime = value;
    }

}