package com.haier.stock.model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Table: <strong>inv_store_synch</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>主键</td></tr>
 *   <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>varchar</td><td>物料编码</td></tr>
 *   <tr><td>itemProperty</td><td>{@link String}</td><td>item_property</td><td>char</td><td>批次</td></tr>
 *   <tr><td>storeCode</td><td>{@link String}</td><td>store_code</td><td>varchar</td><td>店铺码</td></tr>
 *   <tr><td>stockQty</td><td>{@link Integer}</td><td>stock_qty</td><td>int</td><td>总库存量</td></tr>
 *   <tr><td>status</td><td>{@link Integer}</td><td>status</td><td>int</td><td>状态：0，未更新；1，已更新；2，过期数据</td></tr>
 *   <tr><td>createTime</td><td>{@link Date}</td><td>create_time</td><td>datetime</td><td>创建时间</td></tr>
 *   <tr><td>updateTime</td><td>{@link Date}</td><td>update_time</td><td>datetime</td><td>更新时间</td></tr>
 * </table>
 *
 * @author 
 * @date 2015-5-27
 * @email yudi@sina.com
 */
public class InvStoreSynch implements Serializable {

    /**
     * 初始状态
     */
    public static final Integer STATUS_INIT   = 0;
    /**
     * 完成状态
     */
    public static final Integer STATUS_DONE   = 1;

    /**
     * 重做状态
     */
    public static final Integer STATUS_REDO   = 2;

    /**
     * 时间已过期
     */
    public static final Integer STATUS_EXPIRE = 3;

    /**
     * 自动获取
     */
    public static final Integer STATUS_GET    = 4;

    private Integer             id;

    /**
     * 获取 主键。
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 设置 主键。
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

    private String extSku;

    public String getExtSku() {
        return extSku;
    }

    public void setExtSku(String extSku) {
        this.extSku = extSku;
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
     * 获取 总库存量。
     */
    public Integer getStockQty() {
        return this.stockQty;
    }

    /**
     * 设置 总库存量。
     *
     * @param value 属性值
     */
    public void setStockQty(Integer value) {
        this.stockQty = value;
    }

    private Integer status;

    /**
     * 获取 状态：0，未更新；1，已更新；2，过期数据。
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置 状态：0，未更新；1，已更新；2，过期数据。
     *
     * @param value 属性值
     */
    public void setStatus(Integer value) {
        this.status = value;
    }

    private String createTime;

    /**
     * 获取 创建时间。
     */
    public String getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置 创建时间。
     *
     * @param value 属性值
     */
    public void setCreateTime(String value) {
        this.createTime = value;
    }

    private String updateTime;

    /**
     * 获取 更新时间。
     */
    public String getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置 更新时间。
     *
     * @param value 属性值
     */
    public void setUpdateTime(String value) {
        this.updateTime = value;
    }

    /**
     * 获取 信息
     */
    private String message;

    public String getMessage() {
        return message;
    }

    /**
     * 设置 信息
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

}