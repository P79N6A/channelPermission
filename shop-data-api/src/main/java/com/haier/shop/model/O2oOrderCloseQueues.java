package com.haier.shop.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>Table: <strong>O2oOrderCloseQueues</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>orderId</td><td>{@link Integer}</td><td>order_id</td><td>int</td><td>订单ID</td></tr>
 *   <tr><td>orderProductId</td><td>{@link Integer}</td><td>order_product_id</td><td>int</td><td>网单ID</td></tr>
 *   <tr><td>type</td><td>{@link Integer}</td><td>type</td><td>tinyint</td><td>网单状态类型，1完成关闭，2取消关闭</td></tr>
 *   <tr><td>addTime</td><td>{@link Integer}</td><td>add_time</td><td>int</td><td>添加时间</td></tr>
 *   <tr><td>pushData</td><td>{@link String}</td><td>push_data</td><td>text</td><td>推送数据</td></tr>
 *   <tr><td>returnData</td><td>{@link String}</td><td>return_data</td><td>text</td><td>反馈数据</td></tr>
 *   <tr><td>success</td><td>{@link Integer}</td><td>success</td><td>tinyint</td><td>是否成功</td></tr>
 *   <tr><td>count</td><td>{@link Integer}</td><td>count</td><td>tinyint</td><td>推送次数,大于30就不在推送</td></tr>
 *   <tr><td>successTime</td><td>{@link Integer}</td><td>success_time</td><td>int</td><td>推送成功时间</td></tr>
 *   <tr><td>lastTryTime</td><td>{@link Integer}</td><td>last_try_time</td><td>int</td><td>最晚推送时间</td></tr>
 *   <tr><td>lastMessage</td><td>{@link String}</td><td>last_message</td><td>text</td><td>返回信息</td></tr>
 *   <tr><td>modifyTime</td><td>{@link Timestamp}</td><td>modify_time</td><td>timestamp</td><td>数据更新时间</td></tr>
 * </table>
 * @author qin.k
 */
public class O2oOrderCloseQueues implements Serializable {

    private Integer   id;
    private Integer   orderId;
    private Integer   orderProductId;
    private Integer   type;
    private Integer   addTime;
    private String    pushData;
    private String    returnData;
    private Integer   success;
    private Integer   count;
    private Integer   successTime;
    private Integer   lastTryTime;
    private String    lastMessage;
    private Timestamp modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(Integer orderProductId) {
        this.orderProductId = orderProductId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    public String getPushData() {
        return pushData;
    }

    public void setPushData(String pushData) {
        this.pushData = pushData;
    }

    public String getReturnData() {
        return returnData;
    }

    public void setReturnData(String returnData) {
        this.returnData = returnData;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Integer successTime) {
        this.successTime = successTime;
    }

    public Integer getLastTryTime() {
        return lastTryTime;
    }

    public void setLastTryTime(Integer lastTryTime) {
        this.lastTryTime = lastTryTime;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

}
