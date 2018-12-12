package com.haier.shop.model;
import java.io.Serializable;


/**
 * 
 * <p>Table: <strong>inv_sg_scode_streets_ref</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
*   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>id</td><td>int</td><td>主键</td></tr>
*   <tr><td>id</td><td>{@link java.lang.String}</td><td>storeId</td><td>varchar</td><td>店铺ID</td></tr>
*   <tr><td>id</td><td>{@link java.lang.String}</td><td>scode</td><td>varchar</td><td>库位</td></tr>
*   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>streetId</td><td>int</td><td>街道ID</td></tr>
*   <tr><td>id</td><td>{@link java.util.Date}</td><td>addTime</td><td>datetime</td><td>创建时间</td></tr>
*   <tr><td>id</td><td>{@link java.util.Date}</td><td>modifyTime</td><td>datetime</td><td>更新时间</td></tr>

 * </table>
 *
 */
public class InvSgScodeStreetsRefEntity implements Serializable {
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
	
    private java.lang.String    storeId;

    /**
     * 获取[店铺ID]
     */
    public java.lang.String getStoreId() {
        return this.storeId;
    }

    /**
     * 设置[店铺ID]
     */
    public void setStoreId(java.lang.String storeId) {
        this.storeId = storeId;
    }
	
    private java.lang.String    scode;

    /**
     * 获取[库位]
     */
    public java.lang.String getScode() {
        return this.scode;
    }

    /**
     * 设置[库位]
     */
    public void setScode(java.lang.String scode) {
        this.scode = scode;
    }
	
    private java.lang.Integer    streetId;

    /**
     * 获取[街道ID]
     */
    public java.lang.Integer getStreetId() {
        return this.streetId;
    }

    /**
     * 设置[街道ID]
     */
    public void setStreetId(java.lang.Integer streetId) {
        this.streetId = streetId;
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