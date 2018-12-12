package com.haier.stock.model;

import java.util.Date;
import java.io.Serializable;

/**
 * 库存批次表。
 *
 * <p>Table: <strong>inv_stock_batch</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>batchNum</td><td>{@link String}</td><td>batch_num</td><td>varchar</td><td>批次号</td></tr>
 *   <tr><td>refno</td><td>{@link String}</td><td>refno</td><td>varchar</td><td>入库单据号</td></tr>
 *   <tr><td>billtype</td><td>{@link String}</td><td>billtype</td><td>varchar</td><td>入库类型</td></tr>
 *   <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>secCode</td><td>{@link String}</td><td>sec_code</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>stockQty</td><td>{@link Integer}</td><td>stock_qty</td><td>int</td><td>结存数量</td></tr>
 *   <tr><td>outQty</td><td>{@link Integer}</td><td>out_qty</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>addTime</td><td>{@link Date}</td><td>add_time</td><td>datetime</td><td>&nbsp;</td></tr>
 *   <tr><td>updateTime</td><td>{@link Date}</td><td>update_time</td><td>datetime</td><td>更新时间</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-12-2
 * @email yudi@sina.com
 */
public class InvStockBatch implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private String batchNum;

    /**
     * 获取 批次号。
     */
    public String getBatchNum() {
        return this.batchNum;
    }

    /**
     * 设置 批次号。
     *
     * @param value 属性值
     */
    public void setBatchNum(String value) {
        this.batchNum = value;
    }

    private String refno;

    /**
     * 获取 入库单据号。
     */
    public String getRefno() {
        return this.refno;
    }

    /**
     * 设置 入库单据号。
     *
     * @param value 属性值
     */
    public void setRefno(String value) {
        this.refno = value;
    }

    private String billtype;

    /**
     * 获取 入库类型。
     */
    public String getBilltype() {
        return this.billtype;
    }

    /**
     * 设置 入库类型。
     *
     * @param value 属性值
     */
    public void setBilltype(String value) {
        this.billtype = value;
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

    private Integer stockQty;

    /**
     * 获取 结存数量。
     */
    public Integer getStockQty() {
        return this.stockQty;
    }

    /**
     * 设置 结存数量。
     *
     * @param value 属性值
     */
    public void setStockQty(Integer value) {
        this.stockQty = value;
    }

    private Integer outQty;

    public Integer getOutQty() {
        return this.outQty;
    }

    public void setOutQty(Integer value) {
        this.outQty = value;
    }

    private Date addTime;

    public Date getAddTime() {
        return this.addTime;
    }

    public void setAddTime(Date value) {
        this.addTime = value;
    }

    private Date updateTime;

    /**
     * 获取 更新时间。
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置 更新时间。
     *
     * @param value 属性值
     */
    public void setUpdateTime(Date value) {
        this.updateTime = value;
    }

}