package com.haier.shop.model;

import java.io.Serializable;

/**
 * <p>Table: <strong>OrderOperateLogs</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>siteId</td><td>{@link Integer}</td><td>siteId</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>orderId</td><td>{@link Integer}</td><td>orderId</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>orderProductId</td><td>{@link Integer}</td><td>orderProductId</td><td>int</td><td>子订单id</td></tr>
 *   <tr><td>netPointId</td><td>{@link Integer}</td><td>netPointId</td><td>int</td><td>网点id</td></tr>
 *   <tr><td>operator</td><td>{@link String}</td><td>operator</td><td>varchar</td><td>操作人</td></tr>
 *   <tr><td>changeLog</td><td>{@link String}</td><td>changeLog</td><td>text</td><td>变更记录</td></tr>
 *   <tr><td>remark</td><td>{@link String}</td><td>remark</td><td>varchar</td><td>备注</td></tr>
 *   <tr><td>logTime</td><td>{@link Integer}</td><td>logTime</td><td>int</td><td>记录时间</td></tr>
 *   <tr><td>status</td><td>{@link Integer}</td><td>status</td><td>int</td><td>社区店订单状态</td></tr>
 *   <tr><td>paymentStatus</td><td>{@link Integer}</td><td>paymentStatus</td><td>int</td><td>支付状态</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-4-28
 * @email yudi@sina.com
 */
public class OrderOperateLogs implements Serializable {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 4080442616413376757L;
    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer siteId;

    public Integer getSiteId() {
        return this.siteId;
    }

    public void setSiteId(Integer value) {
        this.siteId = value;
    }

    private Integer orderId;

    public Integer getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Integer value) {
        this.orderId = value;
    }

    private Integer orderProductId;

    /**
     * 获取 子订单id。
     */
    public Integer getOrderProductId() {
        return this.orderProductId;
    }

    /**
     * 设置 子订单id。
     *
     * @param value 属性值
     */
    public void setOrderProductId(Integer value) {
        this.orderProductId = value;
    }

    private Integer netPointId;

    /**
     * 获取 网点id。
     */
    public Integer getNetPointId() {
        return this.netPointId;
    }

    /**
     * 设置 网点id。
     *
     * @param value 属性值
     */
    public void setNetPointId(Integer value) {
        this.netPointId = value;
    }

    private String operator;

    /**
     * 获取 操作人。
     */
    public String getOperator() {
        return this.operator;
    }

    /**
     * 设置 操作人。
     *
     * @param value 属性值
     */
    public void setOperator(String value) {
        this.operator = value;
    }

    private String changeLog;

    /**
     * 获取 变更记录。
     */
    public String getChangeLog() {
        return this.changeLog;
    }

    /**
     * 设置 变更记录。
     *
     * @param value 属性值
     */
    public void setChangeLog(String value) {
        this.changeLog = value;
    }

    private String remark;

    /**
     * 获取 备注。
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置 备注。
     *
     * @param value 属性值
     */
    public void setRemark(String value) {
        this.remark = value;
    }

    private Integer logTime;

    /**
     * 获取 记录时间。
     */
    public Integer getLogTime() {
        return this.logTime;
    }

    /**
     * 设置 记录时间。
     *
     * @param value 属性值
     */
    public void setLogTime(Integer value) {
        this.logTime = value;
    }

    private Integer status;

    /**
     * 获取 社区店订单状态。
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置 社区店订单状态。
     *
     * @param value 属性值
     */
    public void setStatus(Integer value) {
        this.status = value;
    }

    private Integer paymentStatus;

    /**
     * 获取 支付状态。
     */
    public Integer getPaymentStatus() {
        return this.paymentStatus;
    }

    /**
     * 设置 支付状态。
     *
     * @param value 属性值
     */
    public void setPaymentStatus(Integer value) {
        this.paymentStatus = value;
    }

}