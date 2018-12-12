package com.haier.stock.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 库存渠道对应订单来源。
 *
 * <p>Table: <strong>inv_channel_2_order_source</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>channelCode</td><td>{@link String}</td><td>channel_code</td><td>varchar</td><td>渠道编号</td></tr>
 *   <tr><td>orderSource</td><td>{@link String}</td><td>order_source</td><td>varchar</td><td>订单来源</td></tr>
 *   <tr><td>note</td><td>{@link String}</td><td>note</td><td>nvarchar</td><td>备注</td></tr>
 *   <tr><td>createTime</td><td>{@link Date}</td><td>create_time</td><td>datetime</td><td>创建时间</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-6-8
 * @email yudi@sina.com
 */
public class InvChannel2OrderSource implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private Integer           id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private String channelCode;

    /**
     * 获取 渠道编号。
     */
    public String getChannelCode() {
        return this.channelCode;
    }

    /**
     * 设置 渠道编号。
     *
     * @param value 属性值
     */
    public void setChannelCode(String value) {
        this.channelCode = value;
    }

    private String orderSource;

    /**
     * 获取 订单来源。
     */
    public String getOrderSource() {
        return this.orderSource;
    }

    /**
     * 设置 订单来源。
     *
     * @param value 属性值
     */
    public void setOrderSource(String value) {
        this.orderSource = value;
    }

    private String note;

    /**
     * 获取 备注。
     */
    public String getNote() {
        return this.note;
    }

    /**
     * 设置 备注。
     *
     * @param value 属性值
     */
    public void setNote(String value) {
        this.note = value;
    }

    private Date createTime = null;

    /**
     * 获取 创建时间。
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置 创建时间。
     *
     * @param value 属性值
     */
    public void setCreateTime(Date value) {
        this.createTime = value;
    }

    private String sapChannelCode;

    /**
     * 获取 SAP渠道编号。
     */
    public String getSapChannelCode() {
        return this.sapChannelCode;
    }

    /**
     * 设置 SAP渠道编号。
     *
     * @param value 属性值
     */
    public void setSapChannelCode(String value) {
        this.sapChannelCode = value;
    }

    private String customerCode;

    /**
     * 获取 客户编码。
     */
    public String getCustomerCode() {
        return this.customerCode;
    }

    /**
     * 设置 客户编码。
     *
     * @param value 属性值
     */
    public void setCustomerCode(String value) {
        this.customerCode = value;
    }

}