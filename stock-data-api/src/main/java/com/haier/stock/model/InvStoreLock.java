package com.haier.stock.model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Table: <strong>inv_store_lock</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>主键：自增列</td></tr>
 *   <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>varchar</td><td>物料编码</td></tr>
 *   <tr><td>storeCode</td><td>{@link String}</td><td>store_code</td><td>varchar</td><td>店铺码</td></tr>
 *   <tr><td>refNo</td><td>{@link String}</td><td>ref_no</td><td>varchar</td><td>外部单号</td></tr>
 *   <tr><td>source</td><td>{@link String}</td><td>source</td><td>varchar</td><td>订单来源</td></tr>
 *   <tr><td>lockQty</td><td>{@link Integer}</td><td>lock_qty</td><td>int</td><td>锁定数量</td></tr>
 *   <tr><td>releaseQty</td><td>{@link Integer}</td><td>release_qty</td><td>int</td><td>释放数量</td></tr>
 *   <tr><td>lockTime</td><td>{@link Date}</td><td>lock_time</td><td>datetime</td><td>占用时间</td></tr>
 *   <tr><td>releaseTime</td><td>{@link Date}</td><td>release_time</td><td>datetime</td><td>释放时间</td></tr>
 *   <tr><td>optUser</td><td>{@link String}</td><td>opt_user</td><td>varchar</td><td>操作人</td></tr>
 * </table>
 *
 * @author 
 * @date 2015-5-27
 * @email yudi@sina.com
 */
public class InvStoreLock implements Serializable {
    private Integer id;

    /**
     * 获取 主键：自增列。
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 设置 主键：自增列。
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

    private String refNo;

    /**
     * 获取 外部单号。
     */
    public String getRefNo() {
        return this.refNo;
    }

    /**
     * 设置 外部单号。
     *
     * @param value 属性值
     */
    public void setRefNo(String value) {
        this.refNo = value;
    }

    private String source;

    /**
     * 获取 订单来源。
     */
    public String getSource() {
        return this.source;
    }

    /**
     * 设置 订单来源。
     *
     * @param value 属性值
     */
    public void setSource(String value) {
        this.source = value;
    }

    private Integer lockQty;

    /**
     * 获取 锁定数量。
     */
    public Integer getLockQty() {
        return this.lockQty;
    }

    /**
     * 设置 锁定数量。
     *
     * @param value 属性值
     */
    public void setLockQty(Integer value) {
        this.lockQty = value;
    }

    private Integer releaseQty;

    /**
     * 获取 释放数量。
     */
    public Integer getReleaseQty() {
        return this.releaseQty;
    }

    /**
     * 设置 释放数量。
     *
     * @param value 属性值
     */
    public void setReleaseQty(Integer value) {
        this.releaseQty = value;
    }

    private Date lockTime;

    /**
     * 获取 占用时间。
     */
    public Date getLockTime() {
        return this.lockTime;
    }

    /**
     * 设置 占用时间。
     *
     * @param value 属性值
     */
    public void setLockTime(Date value) {
        this.lockTime = value;
    }

    private Date releaseTime;

    /**
     * 获取 释放时间。
     */
    public Date getReleaseTime() {
        return this.releaseTime;
    }

    /**
     * 设置 释放时间。
     *
     * @param value 属性值
     */
    public void setReleaseTime(Date value) {
        this.releaseTime = value;
    }

    private String optUser;

    /**
     * 获取 操作人。
     */
    public String getOptUser() {
        return this.optUser;
    }

    /**
     * 设置 操作人。
     *
     * @param value 属性值
     */
    public void setOptUser(String value) {
        this.optUser = value;
    }

    private String itemProperty;

    public String getItemProperty() {
        return itemProperty;
    }

    public void setItemProperty(String itemProperty) {
        this.itemProperty = itemProperty;
    }

}