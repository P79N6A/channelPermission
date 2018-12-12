package com.haier.shop.model;

import java.io.Serializable;

/**
 * <p>Table: <strong>InvoiceApiLogs</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>orderProductId</td><td>{@link Integer}</td><td>orderProductId</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>cOrderSn</td><td>{@link String}</td><td>cOrderSn</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>invoiceId</td><td>{@link Integer}</td><td>invoiceId</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>type</td><td>{@link Integer}</td><td>type</td><td>tinyint</td><td>&nbsp;</td></tr>
 *   <tr><td>pushData</td><td>{@link String}</td><td>pushData</td><td>text</td><td>&nbsp;</td></tr>
 *   <tr><td>returnData</td><td>{@link String}</td><td>returnData</td><td>text</td><td>&nbsp;</td></tr>
 *   <tr><td>receiveData</td><td>{@link String}</td><td>receiveData</td><td>text</td><td>&nbsp;</td></tr>
 *   <tr><td>addTime</td><td>{@link String}</td><td>addTime</td><td>char</td><td>&nbsp;</td></tr>
 *   <tr><td>isSuccess</td><td>{@link Integer}</td><td>isSuccess</td><td>tinyint</td><td>&nbsp;</td></tr>
 *   <tr><td>lastTime</td><td>{@link Integer}</td><td>lastTime</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>countNum</td><td>{@link Integer}</td><td>countNum</td><td>int</td><td>&nbsp;</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2014-11-3
 * @email yudi@sina.com
 */
public class InvoiceApiLogs implements Serializable {
    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer orderProductId;

    public Integer getOrderProductId() {
        return this.orderProductId;
    }

    public void setOrderProductId(Integer value) {
        this.orderProductId = value;
    }

    private String cOrderSn;

    public String getCOrderSn() {
        return this.cOrderSn;
    }

    public void setCOrderSn(String value) {
        this.cOrderSn = value;
    }

    private Integer invoiceId;

    public Integer getInvoiceId() {
        return this.invoiceId;
    }

    public void setInvoiceId(Integer value) {
        this.invoiceId = value;
    }

    private Integer type;

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer value) {
        this.type = value;
    }

    private String pushData;

    public String getPushData() {
        return this.pushData;
    }

    public void setPushData(String value) {
        this.pushData = value;
    }

    private String returnData;

    public String getReturnData() {
        return this.returnData;
    }

    public void setReturnData(String value) {
        this.returnData = value;
    }

    private String receiveData;

    public String getReceiveData() {
        return this.receiveData;
    }

    public void setReceiveData(String value) {
        this.receiveData = value;
    }

    private String addTime;

    public String getAddTime() {
        return this.addTime;
    }

    public void setAddTime(String value) {
        this.addTime = value;
    }

    private Integer isSuccess;

    public Integer getIsSuccess() {
        return this.isSuccess;
    }

    public void setIsSuccess(Integer value) {
        this.isSuccess = value;
    }

    private Integer lastTime;

    public Integer getLastTime() {
        return this.lastTime;
    }

    public void setLastTime(Integer value) {
        this.lastTime = value;
    }

    private Integer countNum;

    public Integer getCountNum() {
        return this.countNum;
    }

    public void setCountNum(Integer value) {
        this.countNum = value;
    }

    private String lastMessage;

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

}