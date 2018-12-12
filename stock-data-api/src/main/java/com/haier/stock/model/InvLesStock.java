package com.haier.stock.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 库存。
 *
 * <p>Table: <strong>inv_les_stock</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>varchar</td><td>物料编码</td></tr>
 *   <tr><td>secCode</td><td>{@link String}</td><td>sec_code</td><td>varchar</td><td>库位编码。</td></tr>
 *   <tr><td>stockQty</td><td>{@link Integer}</td><td>stock_qty</td><td>int</td><td>总库存量。</td></tr>
 *   <tr><td>createTime</td><td>{@link Date}</td><td>create_time</td><td>datetime</td><td>记录创建时间</td></tr>
 *   <tr><td>updateTime</td><td>{@link Date}</td><td>update_time</td><td>datetime</td><td>最后更新时间。</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-7-12
 * @email yudi@sina.com
 */
public class InvLesStock implements Serializable {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -254670503319714953L;

    private Integer           id;

    public Integer getId() {
        return this.id;
    }

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