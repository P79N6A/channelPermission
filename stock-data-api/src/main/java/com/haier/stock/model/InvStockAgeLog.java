package com.haier.stock.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 库龄日志。
 *
 * <p>Table: <strong>inv_stock_age_log</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>itemId</td><td>{@link Integer}</td><td>item_id</td><td>int</td><td>商品Id</td></tr>
 *   <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>varchar</td><td>物料编号</td></tr>
 *   <tr><td>secCode</td><td>{@link String}</td><td>sec_code</td><td>varchar</td><td>库位编码。</td></tr>
 *   <tr><td>channelCode</td><td>{@link String}</td><td>channel_code</td><td>varchar</td><td>渠道编号</td></tr>
 *   <tr><td>createTime</td><td>{@link Integer}</td><td>create_time</td><td>int</td><td>创建时间</td></tr>
 *   <tr><td>age</td><td>{@link Integer}</td><td>age</td><td>int</td><td>库龄（天）</td></tr>
 *   <tr><td>quantity</td><td>{@link Integer}</td><td>quantity</td><td>int</td><td>数量</td></tr>
 *   <tr><td>note</td><td>{@link String}</td><td>note</td><td>nvarchar</td><td>备注</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-6-4
 * @email yudi@sina.com
 */
public class InvStockAgeLog implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private Integer           id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer itemId;

    /**
     * 获取 商品Id。
     */
    public Integer getItemId() {
        return this.itemId;
    }

    /**
     * 设置 商品Id。
     *
     * @param value 属性值
     */
    public void setItemId(Integer value) {
        this.itemId = value;
    }

    private String sku;

    /**
     * 获取 物料编号。
     */
    public String getSku() {
        return this.sku;
    }

    /**
     * 设置 物料编号。
     *
     * @param value 属性值
     */
    public void setSku(String value) {
        this.sku = value;
    }

    private String secCode;

    /**
     * 获取 库位编码。
     */
    public String getSecCode() {
        return this.secCode;
    }

    /**
     * 设置 库位编码。
     *
     * @param value 属性值
     */
    public void setSecCode(String value) {
        this.secCode = value;
    }

    private String channelCode;

    /**
     * 获取 渠道编号。
     */
    public String getChannelCode() {
        return this.channelCode;
    }

    /**
     * 设置 渠道编号。
     *
     * @param value 属性值
     */
    public void setChannelCode(String value) {
        this.channelCode = value;
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

    private Integer age;

    /**
     * 获取 库龄（天）。
     */
    public Integer getAge() {
        return this.age;
    }

    /**
     * 设置 库龄（天）。
     *
     * @param value 属性值
     */
    public void setAge(Integer value) {
        this.age = value;
    }

    private Integer quantity;

    /**
     * 获取 数量。
     */
    public Integer getQuantity() {
        return this.quantity;
    }

    /**
     * 设置 数量。
     *
     * @param value 属性值
     */
    public void setQuantity(Integer value) {
        this.quantity = value;
    }

    private Integer waStockQty;

    public Integer getWaStockQty() {
        return waStockQty;
    }

    public void setWaStockQty(Integer waStockQty) {
        this.waStockQty = waStockQty;
    }

    private String note;

    /**
     * 获取 备注。
     */
    public String getNote() {
        return this.note;
    }

    /**
     * 设置 备注。
     *
     * @param value 属性值
     */
    public void setNote(String value) {
        this.note = value;
    }

    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}