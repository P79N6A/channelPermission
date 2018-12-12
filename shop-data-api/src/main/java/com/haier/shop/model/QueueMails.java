package com.haier.shop.model;

import java.io.Serializable;

/**
 * <p>Table: <strong>queuemails</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>siteId</td><td>{@link Integer}</td><td>siteId</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>subject</td><td>{@link String}</td><td>subject</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>body</td><td>{@link String}</td><td>body</td><td>text</td><td>&nbsp;</td></tr>
 *   <tr><td>to</td><td>{@link String}</td><td>to</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>priority</td><td>{@link Integer}</td><td>priority</td><td>tinyint</td><td>&nbsp;</td></tr>
 *   <tr><td>addTime</td><td>{@link Integer}</td><td>addTime</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>deadline</td><td>{@link Integer}</td><td>deadline</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>sentOk</td><td>{@link Integer}</td><td>sentOk</td><td>tinyint</td><td>&nbsp;</td></tr>
 *   <tr><td>sentTimes</td><td>{@link Integer}</td><td>sentTimes</td><td>tinyint</td><td>&nbsp;</td></tr>
 *   <tr><td>lastSentTime</td><td>{@link Integer}</td><td>lastSentTime</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>lastErrorMessage</td><td>{@link String}</td><td>lastErrorMessage</td><td>varchar</td><td>&nbsp;</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-5-10
 * @email yudi@sina.com
 */
public class QueueMails implements Serializable {
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

    private Integer siteId;

    public Integer getSiteId() {
        return this.siteId;
    }

    public void setSiteId(Integer value) {
        this.siteId = value;
    }

    private String subject;

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String value) {
        this.subject = value;
    }

    private String body;

    public String getBody() {
        return this.body;
    }

    public void setBody(String value) {
        this.body = value;
    }

    private String to;

    public String getTo() {
        return this.to;
    }

    public void setTo(String value) {
        this.to = value;
    }

    private Integer priority;

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer value) {
        this.priority = value;
    }

    private Integer addTime;

    public Integer getAddTime() {
        return this.addTime;
    }

    public void setAddTime(Integer value) {
        this.addTime = value;
    }

    private Integer deadline;

    public Integer getDeadline() {
        return this.deadline;
    }

    public void setDeadline(Integer value) {
        this.deadline = value;
    }

    private Integer sentOk;

    public Integer getSentOk() {
        return this.sentOk;
    }

    public void setSentOk(Integer value) {
        this.sentOk = value;
    }

    private Integer sentTimes;

    public Integer getSentTimes() {
        return this.sentTimes;
    }

    public void setSentTimes(Integer value) {
        this.sentTimes = value;
    }

    private Integer lastSentTime;

    public Integer getLastSentTime() {
        return this.lastSentTime;
    }

    public void setLastSentTime(Integer value) {
        this.lastSentTime = value;
    }

    private String lastErrorMessage;

    public String getLastErrorMessage() {
        return this.lastErrorMessage;
    }

    public void setLastErrorMessage(String value) {
        this.lastErrorMessage = value;
    }

}