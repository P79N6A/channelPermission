package com.haier.shop.model;

import java.io.Serializable;

/**
 * <p>Table: <strong>HPFaileds</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>orderProductId</td><td>{@link Integer}</td><td>orderProductId</td><td>int</td><td>网单id</td></tr>
 *   <tr><td>pushData</td><td>{@link String}</td><td>pushData</td><td>text</td><td>&nbsp;</td></tr>
 *   <tr><td>success</td><td>{@link Integer}</td><td>success</td><td>tinyint</td><td>是否成功</td></tr>
 *   <tr><td>count</td><td>{@link Integer}</td><td>count</td><td>tinyint</td><td>推送次数</td></tr>
 *   <tr><td>addTime</td><td>{@link Integer}</td><td>addTime</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>lastMessage</td><td>{@link String}</td><td>lastMessage</td><td>text</td><td>&nbsp;</td></tr>
 *   <tr><td>successTime</td><td>{@link Integer}</td><td>successTime</td><td>int</td><td>成功时间</td></tr>
 *   <tr><td>createType</td><td>{@link Integer}</td><td>createType</td><td>tinyint</td><td>创建类型：1.手动创建；0.自动创建</td></tr>
 *   <tr><td>operator</td><td>{@link String}</td><td>operator</td><td>varchar</td><td>手动创建人</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2015-6-24
 * @email yudi@sina.com
 */
public class HPFaileds implements Serializable {
    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer orderProductId;

    /**
     * 获取 网单id。
     */
    public Integer getOrderProductId() {
        return this.orderProductId;
    }

    /**
     * 设置 网单id。
     *
     * @param value 属性值
     */
    public void setOrderProductId(Integer value) {
        this.orderProductId = value;
    }

    private String pushData;

    public String getPushData() {
        return this.pushData;
    }

    public void setPushData(String value) {
        this.pushData = value;
    }

    private Integer success;

    /**
     * 获取 是否成功。
     */
    public Integer getSuccess() {
        return this.success;
    }

    /**
     * 设置 是否成功。
     *
     * @param value 属性值
     */
    public void setSuccess(Integer value) {
        this.success = value;
    }

    private Integer count;

    /**
     * 获取 推送次数。
     */
    public Integer getCount() {
        return this.count;
    }

    /**
     * 设置 推送次数。
     *
     * @param value 属性值
     */
    public void setCount(Integer value) {
        this.count = value;
    }

    private Long addTime;

    public Long getAddTime() {
        return this.addTime;
    }

    public void setAddTime(Long value) {
        this.addTime = value;
    }

    private String lastMessage;

    public String getLastMessage() {
        return this.lastMessage;
    }

    public void setLastMessage(String value) {
        this.lastMessage = value;
    }

    private Long successTime;

    /**
     * 获取 成功时间。
     */
    public Long getSuccessTime() {
        return this.successTime;
    }

    /**
     * 设置 成功时间。
     *
     * @param value 属性值
     */
    public void setSuccessTime(Long value) {
        this.successTime = value;
    }

    private Integer createType;

    /**
     * 获取 创建类型：1.手动创建；0.自动创建。
     */
    public Integer getCreateType() {
        return this.createType;
    }

    /**
     * 设置 创建类型：1.手动创建；0.自动创建。
     *
     * @param value 属性值
     */
    public void setCreateType(Integer value) {
        this.createType = value;
    }

    private String operator;

    /**
     * 获取 手动创建人。
     */
    public String getOperator() {
        return this.operator;
    }

    /**
     * 设置 手动创建人。
     *
     * @param value 属性值
     */
    public void setOperator(String value) {
        this.operator = value;
    }

}