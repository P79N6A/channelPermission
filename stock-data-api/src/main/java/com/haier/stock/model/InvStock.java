package com.haier.stock.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 库存。
 *
 * <p>Table: <strong>inv_stock</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>stoId</td><td>{@link Integer}</td><td>sto_id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>varchar</td><td>物料编码</td></tr>
 *   <tr><td>secCode</td><td>{@link String}</td><td>sec_code</td><td>varchar</td><td>库位编码。</td></tr>
 *   <tr><td>stockQty</td><td>{@link Integer}</td><td>stock_qty</td><td>int</td><td>总库存量。</td></tr>
 *   <tr><td>frozenQty</td><td>{@link Integer}</td><td>frozen_qty</td><td>int</td><td>冻结数量。</td></tr>
 *   <tr><td>crmFrozenQty</td><td>{@link Integer}</td><td>crm_frozen_qty</td><td>int</td><td>日日顺共享库位冻结数<br />WA库位没有该冻结数</td></tr>
 *   <tr><td>virtualQty</td><td>{@link Integer}</td><td>virtual_qty</td><td>int</td><td>虚拟库存数量。</td></tr>
 *   <tr><td>createTime</td><td>{@link Date}</td><td>create_time</td><td>datetime</td><td>记录创建时间</td></tr>
 *   <tr><td>updateTime</td><td>{@link Date}</td><td>update_time</td><td>datetime</td><td>最后更新时间。</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-7-12
 * @email yudi@sina.com
 */
public class InvStock extends BaseStock implements Serializable {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -1637973162963534468L;

    private Integer           stoId;

    

	public Integer getStoId() {
        return this.stoId;
    }

    public void setStoId(Integer value) {
        this.stoId = value;
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

    


    private Integer crmFrozenQty;

    /**
     * 获取 日日顺共享库位冻结数。
     *
     * <p>
     * WA库位没有该冻结数
     */
    public Integer getCrmFrozenQty() {
        return this.crmFrozenQty;
    }

    /**
     * 设置 日日顺共享库位冻结数。
     *
     * <p>
     * WA库位没有该冻结数
     *
     * @param value 属性值
     */
    public void setCrmFrozenQty(Integer value) {
        this.crmFrozenQty = value;
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

    private Date updateTime = null;

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

    private String whCode;

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    private String channelCode;

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    private String productName;

    /**
     * 获取 产品型号。
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 设置 产品型号。
     *
     * @param value 属性值
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    private String productTypeName;

    /**
     * 获取 品类。
     */
    public String getProductTypeName() {
        return productTypeName;
    }

    /**
     * 设置 品类。
     *
     * @param value 属性值
     */
    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    private String productGroupName;

    /**
     * 获取 产品组。
     */
    public String getProductGroupName() {
        return productGroupName;
    }

    /**
     * 设置 产品组。
     *
     * @param value 属性值
     */
    public void setProductGroupName(String productGroupName) {
        this.productGroupName = productGroupName;
    }

    private String secName;

    /**
     * 获取 库位名称。
     */
    public String getSecName() {
        return secName;
    }

    /**
     * 设置 库位名称。
     *
     * @param value 属性值
     */
    public void setSecName(String secName) {
        this.secName = secName;
    }

//    private String itemProperty;
//
//    /**
//     * 获取 批次。
//     */
//    public String getItemProperty() {
//        return itemProperty;
//    }
//
//    /**
//     * 设置 批次。
//     *
//     * @param value 属性值
//     */
//    public void setItemProperty(String itemProperty) {
//        this.itemProperty = itemProperty;
//    }
}