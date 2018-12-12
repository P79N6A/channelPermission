package com.haier.shop.model;

import java.io.Serializable;

/*
* 作者:张波
* 2017/12/25
*/

/**
 * <p>Table: <strong>smslogs</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>siteId</td><td>{@link Integer}</td><td>siteId</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>mobile</td><td>{@link String}</td><td>mobile</td><td>char</td><td>&nbsp;</td></tr>
 *   <tr><td>name</td><td>{@link String}</td><td>name</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>addTime</td><td>{@link Integer}</td><td>addTime</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>isSuccess</td><td>{@link Integer}</td><td>isSuccess</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>message</td><td>{@link String}</td><td>message</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>priority</td><td>{@link Integer}</td><td>priority</td><td>tinyint</td><td>&nbsp;</td></tr>
 *   <tr><td>lastTime</td><td>{@link Integer}</td><td>lastTime</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>tryNum</td><td>{@link Integer}</td><td>tryNum</td><td>int</td><td>&nbsp;</td></tr>
 * </table>
 *

 */
public class SmsLogs implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 6000710393210049941L;
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

    private String mobile;

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String value) {
        this.mobile = value;
    }

    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    private Integer addTime;

    public Integer getAddTime() {
        return this.addTime;
    }

    public void setAddTime(Integer value) {
        this.addTime = value;
    }

    private Integer isSuccess;

    public Integer getIsSuccess() {
        return this.isSuccess;
    }

    public void setIsSuccess(Integer value) {
        this.isSuccess = value;
    }

    private String message;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String value) {
        this.message = value;
    }

    private Integer priority;

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer value) {
        this.priority = value;
    }

    private Integer lastTime;

    public Integer getLastTime() {
        return this.lastTime;
    }

    public void setLastTime(Integer value) {
        this.lastTime = value;
    }

    private Integer tryNum;

    public Integer getTryNum() {
        return this.tryNum;
    }

    public void setTryNum(Integer value) {
        this.tryNum = value;
    }

}

