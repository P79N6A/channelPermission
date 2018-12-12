package com.haier.stock.model;

import java.io.Serializable;


/**
 * 
 * <p>Table: <strong>inv_sg_stock</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
*   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>id</td><td>int</td><td>主键</td></tr>
*   <tr><td>id</td><td>{@link java.lang.String}</td><td>sku</td><td>varchar</td><td>物料编码</td></tr>
*   <tr><td>id</td><td>{@link java.lang.String}</td><td>itemProperty</td><td>char</td><td>批次</td></tr>
*   <tr><td>id</td><td>{@link java.lang.String}</td><td>storeCode</td><td>varchar</td><td>店铺码</td></tr>
*   <tr><td>id</td><td>{@link java.lang.String}</td><td>scode</td><td>varchar</td><td>库位码</td></tr>
*   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>stockQty</td><td>int</td><td>可用存量下单锁定库存 不计算在内</td></tr>
*   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>frozenQty</td><td>int</td><td>占用库存</td></tr>
*   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>storeTs</td><td>int</td><td>库存更新时间</td></tr>
*   <tr><td>id</td><td>{@link java.lang.String}</td><td>model</td><td>varchar</td><td>产品型号编码</td></tr>
*   <tr><td>id</td><td>{@link java.lang.String}</td><td>type</td><td>varchar</td><td>品类编码</td></tr>
*   <tr><td>id</td><td>{@link java.lang.String}</td><td>group</td><td>varchar</td><td>产品组编码</td></tr>
*   <tr><td>id</td><td>{@link java.util.Date}</td><td>addTime</td><td>datetime</td><td>创建时间</td></tr>
*   <tr><td>id</td><td>{@link java.util.Date}</td><td>modifyTime</td><td>datetime</td><td>更新时间</td></tr>

 * </table>
 *
 */
public class InvSgStock implements Serializable  {
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
	
    private java.lang.Integer    stockQty;

    /**
     * 获取[可用存量下单锁定库存 不计算在内]
     */
    public java.lang.Integer getStockQty() {
        return this.stockQty;
    }

    /**
     * 设置[可用存量下单锁定库存 不计算在内]
     */
    public void setStockQty(java.lang.Integer stockQty) {
        this.stockQty = stockQty;
    }
	
    private java.lang.Integer    frozenQty;

    /**
     * 获取[占用库存]
     */
    public java.lang.Integer getFrozenQty() {
        return this.frozenQty;
    }

    /**
     * 设置[占用库存]
     */
    public void setFrozenQty(java.lang.Integer frozenQty) {
        this.frozenQty = frozenQty;
    }
	
    private java.lang.Integer    storeTs;

    /**
     * 获取[库存更新时间]
     */
    public java.lang.Integer getStoreTs() {
        return this.storeTs;
    }

    /**
     * 设置[库存更新时间]
     */
    public void setStoreTs(java.lang.Integer storeTs) {
        this.storeTs = storeTs;
    }
	
    private java.lang.String    model;

    /**
     * 获取[产品型号编码]
     */
    public java.lang.String getModel() {
        return this.model;
    }

    /**
     * 设置[产品型号编码]
     */
    public void setModel(java.lang.String model) {
        this.model = model;
    }
	
    private java.lang.String    type;

    /**
     * 获取[品类编码]
     */
    public java.lang.String getType() {
        return this.type;
    }

    /**
     * 设置[品类编码]
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }
	
    private java.lang.String    group;

    /**
     * 获取[产品组编码]
     */
    public java.lang.String getGroup() {
        return this.group;
    }

    /**
     * 设置[产品组编码]
     */
    public void setGroup(java.lang.String group) {
        this.group = group;
    }
	
    private java.util.Date    addTime;

    /**
     * 获取[创建时间]
     */
    public java.util.Date getAddTime() {
        return this.addTime;
    }

    /**
     * 设置[创建时间]
     */
    public void setAddTime(java.util.Date addTime) {
        this.addTime = addTime;
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
	
}