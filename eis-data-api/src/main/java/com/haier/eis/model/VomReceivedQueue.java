package com.haier.eis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Table: <strong>vom_received_queue</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>outCode</td><td>{@link String}</td><td>out_code</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>buType</td><td>{@link String}</td><td>bu_type</td><td>varchar</td><td>通知类型：接口方法名</td></tr>
 *   <tr><td>notifyTime</td><td>{@link Date}</td><td>notify_time</td><td>datetime</td><td>通知时间</td></tr>
 *   <tr><td>source</td><td>{@link String}</td><td>source</td><td>varchar</td><td>系统来源</td></tr>
 *   <tr><td>type</td><td>{@link String}</td><td>type</td><td>varchar</td><td>报文格式：xml/json</td></tr>
 *   <tr><td>sign</td><td>{@link String}</td><td>sign</td><td>varchar</td><td>签名</td></tr>
 *   <tr><td>content</td><td>{@link String}</td><td>content</td><td>text</td><td>消息内容</td></tr>
 *   <tr><td>status</td><td>{@link Integer}</td><td>status</td><td>tinyint</td><td>状态：0：未处理 1：完成 2：失败</td></tr>
 * </table>
 *
 * @author 吴坤洋
 * @date 2017-12-22
 * @email yudi@sina.com
 */
public class VomReceivedQueue implements Serializable {
    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private String outCode;

    public String getOutCode() {
        return this.outCode;
    }

    public void setOutCode(String value) {
        this.outCode = value;
    }

    private String buType;

    /**
     * 获取 通知类型：接口方法名。
     */
    public String getBuType() {
        return this.buType;
    }

    /**
     * 设置 通知类型：接口方法名。
     *
     * @param value 属性值
     */
    public void setBuType(String value) {
        this.buType = value;
    }

    private Date notifyTime;

    /**
     * 获取 通知时间。
     */
    public Date getNotifyTime() {
        return this.notifyTime;
    }

    /**
     * 设置 通知时间。
     *
     * @param value 属性值
     */
    public void setNotifyTime(Date value) {
        this.notifyTime = value;
    }

    private String source;

    /**
     * 获取 系统来源。
     */
    public String getSource() {
        return this.source;
    }

    /**
     * 设置 系统来源。
     *
     * @param value 属性值
     */
    public void setSource(String value) {
        this.source = value;
    }

    private String type;

    /**
     * 获取 报文格式：xml/json。
     */
    public String getType() {
        return this.type;
    }

    /**
     * 设置 报文格式：xml/json。
     *
     * @param value 属性值
     */
    public void setType(String value) {
        this.type = value;
    }

    private String sign;

    /**
     * 获取 签名。
     */
    public String getSign() {
        return this.sign;
    }

    /**
     * 设置 签名。
     *
     * @param value 属性值
     */
    public void setSign(String value) {
        this.sign = value;
    }

    private String content;

    /**
     * 获取 消息内容。
     */
    public String getContent() {
        return this.content;
    }

    /**
     * 设置 消息内容。
     *
     * @param value 属性值
     */
    public void setContent(String value) {
        this.content = value;
    }

    public static final int STATUS_NEW   = 0;

    public static final int STATUS_DONE  = 1;

    public static final int STATUS_FAIL  = 2;

    public static final int STATUS_SYNCH = 3;

    private Integer         status;

    /**
     * 获取 状态：0：未处理 1：完成 2：失败 3：已同步采购系统。
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置 状态：0：未处理 1：完成 2：失败。
     *
     * @param value 属性值
     */
    public void setStatus(Integer value) {
        this.status = value;
    }

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    private Date addTime;

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}