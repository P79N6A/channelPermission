package com.haier.stock.model;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>Table: <strong>inv_stock_transaction</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>secCode</td><td>{@link String}</td><td>sec_code</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>corderSn</td><td>{@link String}</td><td>corder_sn</td><td>varchar</td><td>单据号</td></tr>
 *   <tr><td>channelCode</td><td>{@link String}</td><td>channel_code</td><td>varchar</td><td>渠道</td></tr>
 *   <tr><td>quantity</td><td>{@link Integer}</td><td>quantity</td><td>int</td><td>数量</td></tr>
 *   <tr><td>mark</td><td>{@link String}</td><td>mark</td><td>varchar</td><td>借贷标志：S-入库 H-出库</td></tr>
 *   <tr><td>itemProperty</td><td>{@link Integer}</td><td>item_property</td><td>int</td><td> 产品批次：10-正品 90- 非卖品 21不良品 22-开箱正品 40-样品</td></tr>
 *   <tr><td>billType</td><td>{@link String}</td><td>bill_type</td><td>varchar</td><td>交易类型</td></tr>
 *   <tr><td>billTime</td><td>{@link java.util.Date}</td><td>bill_time</td><td>datetime</td><td>交易时间</td></tr>
 *   <tr><td>isFrozen</td><td>{@link Integer}</td><td>is_froze</td><td>int</td><td>是否立即占用库存 0-否 1-是</td></tr>
 *   <tr><td>processStatus</td><td>{@link Integer}</td><td>process_status</td><td>int</td><td>-1 异常 0-初始 1-库存计算完成 2-生成库龄数据完成 </td></tr>
 *   <tr><td>lastProcessTime</td><td>{@link java.util.Date}</td><td>last_process_time</td><td>datetime</td><td>最近处理时间</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2014-3-26
 * @email yudi@sina.com
 */
public class InvStockTransaction implements Serializable, Cloneable {
    private Integer id;

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

    private String externalSecCode;

    public String getExternalSecCode() {
        return externalSecCode;
    }

    public void setExternalSecCode(String externalSecCode) {
        this.externalSecCode = externalSecCode;
    }

    public void setSecCode(String value) {
        this.secCode = value;
    }

    private String corderSn;

    /**
     * 获取 单据号。
     */
    public String getCorderSn() {
        return this.corderSn;
    }

    /**
     * 设置 单据号。
     *
     * @param value 属性值
     */
    public void setCorderSn(String value) {
        this.corderSn = value;
    }

    private String channelCode;

    /**
     * 获取 渠道。
     */
    public String getChannelCode() {
        return this.channelCode;
    }

    /**
     * 设置 渠道。
     *
     * @param value 属性值
     */
    public void setChannelCode(String value) {
        this.channelCode = value;
    }

    private Integer quantity;

    /**
     * 获取 数量。
     */
    public Integer getQuantity() {
        return this.quantity;
    }

    /**
     * 设置 数量。
     *
     * @param value 属性值
     */
    public void setQuantity(Integer value) {
        this.quantity = value;
    }

    private String mark;

    /**
     * 获取 借贷标志：S-入库 H-出库。
     */
    public String getMark() {
        return this.mark;
    }

    /**
     * 设置 借贷标志：S-入库 H-出库。
     *
     * @param value 属性值
     */
    public void setMark(String value) {
        this.mark = value;
    }

    private String itemProperty;

    /**
     * 获取  产品批次：10-正品 90- 非卖品 21不良品 22-开箱正品 40-样品。
     */
    public String getItemProperty() {
        return this.itemProperty;
    }

    /**
     * 设置  产品批次：10-正品 90- 非卖品 21不良品 22-开箱正品 40-样品。
     *
     * @param value 属性值
     */
    public void setItemProperty(String value) {
        this.itemProperty = value;
    }

    private String billType;

    /**
     * 获取 交易类型。
     */
    public String getBillType() {
        return this.billType;
    }

    /**
     * 设置 交易类型。
     *
     * @param value 属性值
     */
    public void setBillType(String value) {
        this.billType = value;
    }

    private Date billTime;

    /**
     * 获取 交易时间。
     */
    public Date getBillTime() {
        return this.billTime;
    }

    /**
     * 设置 交易时间。
     *
     * @param value 属性值
     */
    public void setBillTime(Date value) {
        this.billTime = value;
    }

    private Integer isFrozen;

    /**
     * 获取 是否立即占用库存 0-否 1-是。
     */
    public Integer getIsFrozen() {
        return this.isFrozen;
    }

    /**
     * 设置 是否立即占用库存 0-否 1-是。
     *
     * @param value 属性值
     */
    public void setIsFrozen(Integer value) {
        this.isFrozen = value;
    }

    /**
     * 是否释放库存
     */
    private Integer isRelease;

    public Integer getIsRelease() {
        return isRelease;
    }

    public void setIsRelease(Integer isRelease) {
        this.isRelease = isRelease;
    }

    public final static int PROCESS_STATUS_ERROR                = -1;
    public final static int PROCESS_STATUS_INIT                 = 0;
    public final static int PROCESS_STATUS_UPDATE_STOCK_DOWN    = 1;
    public final static int PROCESS_STATUS_BUILD_STOCK_AGE_DOWN = 2;

    private Integer         processStatus;

    /**
     * 获取 -1 异常 0-初始 1-库存计算完成 2-生成库龄数据完成 。
     */
    public Integer getProcessStatus() {
        return this.processStatus;
    }

    /**
     * 设置 -1 异常 0-初始 1-更新库存完成 2-生成库龄数据完成 。
     *
     * @param value 属性值
     */
    public void setProcessStatus(Integer value) {
        this.processStatus = value;
    }

    private Date lastProcessTime;

    /**
     * 获取 最近处理时间。
     */
    public Date getLastProcessTime() {
        return this.lastProcessTime;
    }

    /**
     * 设置 最近处理时间。
     *
     * @param value 属性值
     */
    public void setLastProcessTime(Date value) {
        this.lastProcessTime = value;
    }

    /**
     * 是否延后：0-不延后 1-延后处理 2-延后处理完成
     */
    private Integer isDelay;

    public Integer getIsDelay() {
        return isDelay;
    }

    public void setIsDelay(Integer isDelay) {
        this.isDelay = isDelay;
    }

    private Integer businessProcessStatus;

    private String  message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取 业务处理状态
     * @return
     */
    public Integer getBusinessProcessStatus() {
        return businessProcessStatus;
    }

    /**
     * 设置 业务处理状态
     * @param businessProcessStatus
     */
    public void setBusinessProcessStatus(Integer businessProcessStatus) {
        this.businessProcessStatus = businessProcessStatus;
    }

    private Date businessProcessTime;

    public Date getBusinessProcessTime() {
        return businessProcessTime;
    }

    public void setBusinessProcessTime(Date businessProcessTime) {
        this.businessProcessTime = businessProcessTime;
    }

    private Date addTime;

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Override
    public final Object clone() {

        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

}