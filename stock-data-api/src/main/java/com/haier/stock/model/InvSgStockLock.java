package com.haier.stock.model;
import java.io.Serializable;


/**
 * 
 * <p>Table: <strong>inv_sg_stock_lock</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
*   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>id</td><td>int</td><td>主键</td></tr>
*   <tr><td>id</td><td>{@link java.lang.String}</td><td>sku</td><td>varchar</td><td>物料编码</td></tr>
*   <tr><td>id</td><td>{@link java.lang.String}</td><td>itemProperty</td><td>char</td><td>批次</td></tr>
*   <tr><td>id</td><td>{@link java.lang.String}</td><td>storeCode</td><td>varchar</td><td>店铺码</td></tr>
*   <tr><td>id</td><td>{@link java.lang.String}</td><td>scode</td><td>varchar</td><td>库位码</td></tr>
*   <tr><td>id</td><td>{@link java.lang.String}</td><td>refNo</td><td>varchar</td><td>单据号(网单号)</td></tr>
*   <tr><td>id</td><td>{@link java.lang.String}</td><td>source</td><td>varchar</td><td>订单来源</td></tr>
*   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>lockQty</td><td>int</td><td>锁定数量</td></tr>
*   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>releaseQty</td><td>int</td><td>释放数量</td></tr>
*   <tr><td>id</td><td>{@link java.util.Date}</td><td>lockTime</td><td>datetime</td><td>锁定时间</td></tr>
*   <tr><td>id</td><td>{@link java.lang.String}</td><td>lockUser</td><td>varchar</td><td>锁定人</td></tr>
*   <tr><td>id</td><td>{@link java.util.Date}</td><td>releaseTime</td><td>datetime</td><td>释放时间</td></tr>
*   <tr><td>id</td><td>{@link java.lang.String}</td><td>releaseUser</td><td>varchar</td><td>释放人</td></tr>

 * </table>
 *
 */
public class InvSgStockLock implements Serializable  {


	private static final long serialVersionUID = -5109392125208786974L;
	private java.lang.Integer    id;

    /**
     * 获取[主键]
     */
    public java.lang.Integer getId() {
        return this.id;
    }

    /**
     * 设置[主键]
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }
	
    private java.lang.String    sku;

    /**
     * 获取[物料编码]
     */
    public java.lang.String getSku() {
        return this.sku;
    }

    /**
     * 设置[物料编码]
     */
    public void setSku(java.lang.String sku) {
        this.sku = sku;
    }
	
    private java.lang.String    itemProperty;

    /**
     * 获取[批次]
     */
    public java.lang.String getItemProperty() {
        return this.itemProperty;
    }

    /**
     * 设置[批次]
     */
    public void setItemProperty(java.lang.String itemProperty) {
        this.itemProperty = itemProperty;
    }
	
    private java.lang.String    storeCode;

    /**
     * 获取[店铺码]
     */
    public java.lang.String getStoreCode() {
        return this.storeCode;
    }

    /**
     * 设置[店铺码]
     */
    public void setStoreCode(java.lang.String storeCode) {
        this.storeCode = storeCode;
    }
	
    private java.lang.String    scode;

    /**
     * 获取[库位码]
     */
    public java.lang.String getScode() {
        return this.scode;
    }

    /**
     * 设置[库位码]
     */
    public void setScode(java.lang.String scode) {
        this.scode = scode;
    }
	
    private java.lang.String    refNo;

    /**
     * 获取[单据号(网单号)]
     */
    public java.lang.String getRefNo() {
        return this.refNo;
    }

    /**
     * 设置[单据号(网单号)]
     */
    public void setRefNo(java.lang.String refNo) {
        this.refNo = refNo;
    }
	
    private java.lang.String    source;

    /**
     * 获取[订单来源]
     */
    public java.lang.String getSource() {
        return this.source;
    }

    /**
     * 设置[订单来源]
     */
    public void setSource(java.lang.String source) {
        this.source = source;
    }
	
    private java.lang.Integer    lockQty;

    /**
     * 获取[锁定数量]
     */
    public java.lang.Integer getLockQty() {
        return this.lockQty;
    }

    /**
     * 设置[锁定数量]
     */
    public void setLockQty(java.lang.Integer lockQty) {
        this.lockQty = lockQty;
    }
	
    private java.lang.Integer    releaseQty;

    /**
     * 获取[释放数量]
     */
    public java.lang.Integer getReleaseQty() {
        return this.releaseQty;
    }

    /**
     * 设置[释放数量]
     */
    public void setReleaseQty(java.lang.Integer releaseQty) {
        this.releaseQty = releaseQty;
    }
	
    private java.util.Date    lockTime;

    /**
     * 获取[锁定时间]
     */
    public java.util.Date getLockTime() {
        return this.lockTime;
    }

    /**
     * 设置[锁定时间]
     */
    public void setLockTime(java.util.Date lockTime) {
        this.lockTime = lockTime;
    }
	
    private java.lang.String    lockUser;

    /**
     * 获取[锁定人]
     */
    public java.lang.String getLockUser() {
        return this.lockUser;
    }

    /**
     * 设置[锁定人]
     */
    public void setLockUser(java.lang.String lockUser) {
        this.lockUser = lockUser;
    }
	
    private java.util.Date    releaseTime;

    /**
     * 获取[释放时间]
     */
    public java.util.Date getReleaseTime() {
        return this.releaseTime;
    }

    /**
     * 设置[释放时间]
     */
    public void setReleaseTime(java.util.Date releaseTime) {
        this.releaseTime = releaseTime;
    }
	
    private java.lang.String    releaseUser;

    /**
     * 获取[释放人]
     */
    public java.lang.String getReleaseUser() {
        return this.releaseUser;
    }

    /**
     * 设置[释放人]
     */
    public void setReleaseUser(java.lang.String releaseUser) {
        this.releaseUser = releaseUser;
    }
	
}