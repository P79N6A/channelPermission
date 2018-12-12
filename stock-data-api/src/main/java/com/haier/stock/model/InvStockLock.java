package com.haier.stock.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 库存锁定记录表。
 *
 * <p>Table: <strong>inv_stock_lock</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>secCode</td><td>{@link String}</td><td>sec_code</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>refno</td><td>{@link String}</td><td>refno</td><td>varchar</td><td>单据号</td></tr>
 *   <tr><td>lockQty</td><td>{@link Integer}</td><td>lockQty</td><td>int</td><td>锁定数量</td></tr>
 *   <tr><td>realeaseQty</td><td>{@link Integer}</td><td>realeaseQty</td><td>int</td><td>释放数量</td></tr>
 *   <tr><td>lockTime</td><td>{@link Date}</td><td>lock_time</td><td>datetime</td><td>锁定时间</td></tr>
 *   <tr><td>releaseTime</td><td>{@link Date}</td><td>release_time</td><td>datetime</td><td>释放时间</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-12-2
 * @email yudi@sina.com
 */
public class InvStockLock implements Serializable {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 3546193661388552777L;

    private Integer           id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private String sku;

    public String getSku() {
        return this.sku;
    }

    public void setSku(String value) {
        this.sku = value;
    }

    private String secCode;

    public String getSecCode() {
        return this.secCode;
    }

    public void setSecCode(String value) {
        this.secCode = value;
    }

    private String channel;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    private String refno;

    /**
     * 获取 单据号。
     */
    public String getRefno() {
        return this.refno;
    }

    /**
     * 设置 单据号。
     *
     * @param value 属性值
     */
    public void setRefno(String value) {
        this.refno = value;
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

    private Integer realeaseQty;

    /**
     * 获取 释放数量。
     */
    public Integer getRealeaseQty() {
        return this.realeaseQty;
    }

    /**
     * 设置 释放数量。
     *
     * @param value 属性值
     */
    public void setRealeaseQty(Integer value) {
        this.realeaseQty = value;
    }

    private Date lockTime;

    /**
     * 获取 锁定时间。
     */
    public Date getLockTime() {
        return this.lockTime;
    }

    /**
     * 设置 锁定时间。
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
     * 操作人
     * @return
     */
    public String getOptUser() {
        return optUser;
    }

    /**
     * 操作人
     * @param optUser
     */
    public void setOptUser(String optUser) {
        this.optUser = optUser;
    }

}