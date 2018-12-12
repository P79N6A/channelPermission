package com.haier.stock.model;

import java.io.Serializable;


/**
 * 
 * <p>Table: <strong>inv_sg_stock_log</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
*   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>id</td><td>int</td><td>主键</td></tr>
*   <tr><td>id</td><td>{@link java.lang.String}</td><td>storeCode</td><td>varchar</td><td>店铺码</td></tr>
*   <tr><td>id</td><td>{@link java.lang.String}</td><td>scode</td><td>varchar</td><td>库位码</td></tr>
*   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>oldStockQty</td><td>int</td><td>旧可用库存</td></tr>
*   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>newStockQty</td><td>int</td><td>新可用库存</td></tr>
*   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>changeQty</td><td>int</td><td>变更库存</td></tr>
*   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>oldFrozenQty</td><td>int</td><td>旧占用库存</td></tr>
*   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>newFrozenQty</td><td>int</td><td>新占用库存</td></tr>
*   <tr><td>id</td><td>{@link java.lang.String}</td><td>billType</td><td>varchar</td><td>交易类型</td></tr>
*   <tr><td>id</td><td>{@link java.lang.String}</td><td>mARK</td><td>char</td><td>借贷标志：H-出 S-入</td></tr>
*   <tr><td>id</td><td>{@link java.lang.String}</td><td>refNo</td><td>varchar</td><td>关联编号</td></tr>
*   <tr><td>id</td><td>{@link java.util.Date}</td><td>modifyTime</td><td>datetime</td><td>更新时间</td></tr>
*   <tr><td>id</td><td>{@link java.lang.String}</td><td>modifyUser</td><td>varchar</td><td>更新人</td></tr>

 * </table>
 *
 */
public class InvSgStockLogEntity implements Serializable {
    /**
    *Comment for <code>serialVersionUID</code>
    */
    private static final long serialVersionUID = -1L;

	
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
	
    private java.lang.Integer    oldStockQty;

    /**
     * 获取[旧可用库存]
     */
    public java.lang.Integer getOldStockQty() {
        return this.oldStockQty;
    }

    /**
     * 设置[旧可用库存]
     */
    public void setOldStockQty(java.lang.Integer oldStockQty) {
        this.oldStockQty = oldStockQty;
    }
	
    private java.lang.Integer    newStockQty;

    /**
     * 获取[新可用库存]
     */
    public java.lang.Integer getNewStockQty() {
        return this.newStockQty;
    }

    /**
     * 设置[新可用库存]
     */
    public void setNewStockQty(java.lang.Integer newStockQty) {
        this.newStockQty = newStockQty;
    }
	
    private java.lang.Integer    changeQty;

    /**
     * 获取[变更库存]
     */
    public java.lang.Integer getChangeQty() {
        return this.changeQty;
    }

    /**
     * 设置[变更库存]
     */
    public void setChangeQty(java.lang.Integer changeQty) {
        this.changeQty = changeQty;
    }
	
    private java.lang.Integer    oldFrozenQty;

    /**
     * 获取[旧占用库存]
     */
    public java.lang.Integer getOldFrozenQty() {
        return this.oldFrozenQty;
    }

    /**
     * 设置[旧占用库存]
     */
    public void setOldFrozenQty(java.lang.Integer oldFrozenQty) {
        this.oldFrozenQty = oldFrozenQty;
    }
	
    private java.lang.Integer    newFrozenQty;

    /**
     * 获取[新占用库存]
     */
    public java.lang.Integer getNewFrozenQty() {
        return this.newFrozenQty;
    }

    /**
     * 设置[新占用库存]
     */
    public void setNewFrozenQty(java.lang.Integer newFrozenQty) {
        this.newFrozenQty = newFrozenQty;
    }
	
    private java.lang.String    billType;

    /**
     * 获取[交易类型]
     */
    public java.lang.String getBillType() {
        return this.billType;
    }

    /**
     * 设置[交易类型]
     */
    public void setBillType(java.lang.String billType) {
        this.billType = billType;
    }
	
    private java.lang.String    mARK;

    /**
     * 获取[借贷标志：H-出 S-入]
     */
    public java.lang.String getMARK() {
        return this.mARK;
    }

    /**
     * 设置[借贷标志：H-出 S-入]
     */
    public void setMARK(java.lang.String mARK) {
        this.mARK = mARK;
    }
	
    private java.lang.String    refNo;

    /**
     * 获取[关联编号]
     */
    public java.lang.String getRefNo() {
        return this.refNo;
    }

    /**
     * 设置[关联编号]
     */
    public void setRefNo(java.lang.String refNo) {
        this.refNo = refNo;
    }
	
    private java.util.Date    modifyTime;

    /**
     * 获取[更新时间]
     */
    public java.util.Date getModifyTime() {
        return this.modifyTime;
    }

    /**
     * 设置[更新时间]
     */
    public void setModifyTime(java.util.Date modifyTime) {
        this.modifyTime = modifyTime;
    }
	
    private java.lang.String    modifyUser;

    /**
     * 获取[更新人]
     */
    public java.lang.String getModifyUser() {
        return this.modifyUser;
    }

    /**
     * 设置[更新人]
     */
    public void setModifyUser(java.lang.String modifyUser) {
        this.modifyUser = modifyUser;
    }
	
    private String sku;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
    
}