package com.haier.stock.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 库存。
 *
 * <p>Table: <strong>inv_stock_2_channel</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>stoId</td><td>{@link Integer}</td><td>sto_id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>varchar</td><td>物料编码</td></tr>
 *   <tr><td>secCode</td><td>{@link String}</td><td>sec_code</td><td>varchar</td><td>库位编码。</td></tr>
 *   <tr><td>channelCode</td><td>{@link String}</td><td>channel_code</td><td>varchar</td><td>渠道编号</td></tr>
 *   <tr><td>stockQty</td><td>{@link Integer}</td><td>stock_qty</td><td>int</td><td>总库存量。</td></tr>
 *   <tr><td>frozenQty</td><td>{@link Integer}</td><td>frozen_qty</td><td>int</td><td>冻结数量。</td></tr>
 *   <tr><td>virtualQty</td><td>{@link Integer}</td><td>virtual_qty</td><td>int</td><td>虚拟库存数量。</td></tr>
 *   <tr><td>createTime</td><td>{@link Date}</td><td>create_time</td><td>datetime</td><td>记录创建时间</td></tr>
 *   <tr><td>updateTime</td><td>{@link Date}</td><td>update_time</td><td>datetime</td><td>最后更新时间。</td></tr>
 * </table>
 *
 * @author吴坤洋
 * @date 2017-12-26
 * @email yudi@sina.com
 */
public class InvStock2Channel implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private Integer           stoId;

    public Integer getStoId() {
        return this.stoId;
    }

    public void setStoId(Integer value) {
        this.stoId = value;
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

    private Integer stockQty = 0;

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

    private Integer frozenQty = 0;

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

    private Integer virtualQty = 0;

    /**
     * 获取 虚拟库存数量。
     */
    public Integer getVirtualQty() {
        return this.virtualQty;
    }

    /**
     * 设置 虚拟库存数量。
     *
     * @param value 属性值
     */
    public void setVirtualQty(Integer value) {
        this.virtualQty = value;
    }

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

}