package com.haier.shop.model;

import java.io.Serializable;

/**
 * <p>Table: <strong>HPQueues</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>orderProductId</td><td>{@link Integer}</td><td>orderProductId</td><td>int</td><td>子订单id</td></tr>
 *   <tr><td>pushData</td><td>{@link String}</td><td>pushData</td><td>text</td><td>推送数据</td></tr>
 *   <tr><td>success</td><td>{@link Integer}</td><td>success</td><td>tinyint</td><td>是否成功</td></tr>
 *   <tr><td>count</td><td>{@link Integer}</td><td>count</td><td>tinyint</td><td>总共传递的次数</td></tr>
 *   <tr><td>addTime</td><td>{@link Integer}</td><td>addTime</td><td>int</td><td>加入时间</td></tr>
 *   <tr><td>lastMessage</td><td>{@link String}</td><td>lastMessage</td><td>text</td><td>最后返回信息</td></tr>
 *   <tr><td>successTime</td><td>{@link Integer}</td><td>successTime</td><td>int</td><td>成功传递的时间</td></tr>
 * </table>
 * @author rongmai
 */
public class HPQueues implements Serializable {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 9025146588514970668L;
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

    private Integer successTime;

    /**
     * 获取 成功传递的时间
     */
    public Integer getSuccessTime() {
        return this.successTime;
    }

    /**
     * 设置 成功传递的时间
     * @param value 属性值
     */
    public void setSuccessTime(Integer value) {
        this.successTime = value;
    }

    private Integer vomSuccess;
    private Integer vomCount;
    private String  vomLastMessage;
    private Integer vomSuccessTime;
    private String  vomReturnData;

    public Integer getVomSuccess() {
        return vomSuccess;
    }

    public void setVomSuccess(Integer vomSuccess) {
        this.vomSuccess = vomSuccess;
    }

    public Integer getVomCount() {
        return vomCount;
    }

    public void setVomCount(Integer vomCount) {
        this.vomCount = vomCount;
    }

    public String getVomLastMessage() {
        return vomLastMessage;
    }

    public void setVomLastMessage(String vomLastMessage) {
        this.vomLastMessage = vomLastMessage;
    }

    public Integer getVomSuccessTime() {
        return vomSuccessTime;
    }

    public void setVomSuccessTime(Integer vomSuccessTime) {
        this.vomSuccessTime = vomSuccessTime;
    }

    public String getVomReturnData() {
        return vomReturnData;
    }

    public void setVomReturnData(String vomReturnData) {
        this.vomReturnData = vomReturnData;
    }

}