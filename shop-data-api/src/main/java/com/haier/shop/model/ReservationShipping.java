package com.haier.shop.model;

import java.io.Serializable;

/**
 * <p>Table: <strong>ReservationShipping</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>orderId</td><td>{@link Integer}</td><td>orderId</td><td>int</td><td>订单ID</td></tr>
 *   <tr><td>date</td><td>{@link String}</td><td>date</td><td>varchar</td><td>预约日期</td></tr>
 *   <tr><td>time</td><td>{@link String}</td><td>time</td><td>varchar</td><td>预约时间</td></tr>
 * </table>
 * @author rongmai
 */
public class ReservationShipping implements Serializable {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -1193646606845575448L;
    private Integer           orderId;

    /**
     * 获取 订单ID
     */
    public Integer getOrderId() {
        return this.orderId;
    }

    /**
     * 设置 订单ID
     * @param value 属性值
     */
    public void setOrderId(Integer value) {
        this.orderId = value;
    }

    private String date;

    /**
     * 获取 预约日期
     */
    public String getDate() {
        return this.date;
    }

    /**
     * 设置 预约日期
     * @param value 属性值
     */
    public void setDate(String value) {
        this.date = value;
    }

    private String time;

    /**
     * 获取 预约时间
     */
    public String getTime() {
        return this.time;
    }

    /**
     * 设置 预约时间
     * @param value 属性值
     */
    public void setTime(String value) {
        this.time = value;
    }

}