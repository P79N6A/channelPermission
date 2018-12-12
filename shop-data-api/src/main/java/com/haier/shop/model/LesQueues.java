package com.haier.shop.model;

import java.io.Serializable;

/**
 * <p>Table: <strong>LesQueues</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>orderProductId</td><td>{@link Integer}</td><td>orderProductId</td><td>int</td><td>子订单id</td></tr>
 *   <tr><td>action</td><td>{@link String}</td><td>action</td><td>varchar</td><td>动作 createOrder syncStatus等</td></tr>
 *   <tr><td>pushData</td><td>{@link String}</td><td>pushData</td><td>text</td><td>推送数据</td></tr>
 *   <tr><td>success</td><td>{@link Integer}</td><td>success</td><td>tinyint</td><td>是否成功</td></tr>
 *   <tr><td>count</td><td>{@link Integer}</td><td>count</td><td>tinyint</td><td>总共传递的次数</td></tr>
 *   <tr><td>addTime</td><td>{@link Integer}</td><td>addTime</td><td>int</td><td>加入时间</td></tr>
 *   <tr><td>lastMessage</td><td>{@link String}</td><td>lastMessage</td><td>text</td><td>最后返回信息</td></tr>
 *   <tr><td>isLock</td><td>{@link Integer}</td><td>isLock</td><td>tinyint</td><td>&nbsp;</td></tr>
 *   <tr><td>isStop</td><td>{@link Integer}</td><td>isStop</td><td>tinyint</td><td>是否为暂停传LES状态</td></tr>
 *   <tr><td>successTime</td><td>{@link Integer}</td><td>successTime</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>lastTryTime</td><td>{@link Integer}</td><td>lastTryTime</td><td>int</td><td>上次推送时间</td></tr>
 * </table>
 * @author rongmai
 */
public class LesQueues implements Serializable {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -3908081670037735426L;
    private Integer           id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer orderProductId;

    /**
     * 获取 子订单id
     */
    public Integer getOrderProductId() {
        return this.orderProductId;
    }

    /**
     * 设置 子订单id
     * @param value 属性值
     */
    public void setOrderProductId(Integer value) {
        this.orderProductId = value;
    }

    private String action;

    /**
     * 获取 动作 createOrder syncStatus等
     */
    public String getAction() {
        return this.action;
    }

    /**
     * 设置 动作 createOrder syncStatus等
     * @param value 属性值
     */
    public void setAction(String value) {
        this.action = value;
    }

    private String pushData;

    /**
     * 获取 推送数据
     */
    public String getPushData() {
        return this.pushData;
    }

    /**
     * 设置 推送数据
     * @param value 属性值
     */
    public void setPushData(String value) {
        this.pushData = value;
    }

    private Integer success;

    /**
     * 获取 是否成功
     */
    public Integer getSuccess() {
        return this.success;
    }

    /**
     * 设置 是否成功
     * @param value 属性值
     */
    public void setSuccess(Integer value) {
        this.success = value;
    }

    private Integer count;

    /**
     * 获取 总共传递的次数
     */
    public Integer getCount() {
        return this.count;
    }

    /**
     * 设置 总共传递的次数
     * @param value 属性值
     */
    public void setCount(Integer value) {
        this.count = value;
    }

    private Integer addTime;

    /**
     * 获取 加入时间
     */
    public Integer getAddTime() {
        return this.addTime;
    }

    /**
     * 设置 加入时间
     * @param value 属性值
     */
    public void setAddTime(Integer value) {
        this.addTime = value;
    }

    private String lastMessage;

    /**
     * 获取 最后返回信息
     */
    public String getLastMessage() {
        return this.lastMessage;
    }

    /**
     * 设置 最后返回信息
     * @param value 属性值
     */
    public void setLastMessage(String value) {
        this.lastMessage = value;
    }

    private Integer isLock = 0;

    public Integer getIsLock() {
        return this.isLock;
    }

    public void setIsLock(Integer value) {
        this.isLock = value;
    }

    private Integer isStop = 0;

    /**
     * 获取 是否为暂停传LES状态
     */
    public Integer getIsStop() {
        return this.isStop;
    }

    /**
     * 设置 是否为暂停传LES状态
     * @param value 属性值
     */
    public void setIsStop(Integer value) {
        this.isStop = value;
    }

    private Long successTime;

    public Long getSuccessTime() {
        return this.successTime;
    }

    public void setSuccessTime(Long value) {
        this.successTime = value;
    }

    private Long lastTryTime;

    /**
     * 获取 上次推送时间
     */
    public Long getLastTryTime() {
        return this.lastTryTime;
    }

    /**
     * 设置 上次推送时间
     * @param value 属性值
     */
    public void setLastTryTime(Long value) {
        this.lastTryTime = value;
    }

}