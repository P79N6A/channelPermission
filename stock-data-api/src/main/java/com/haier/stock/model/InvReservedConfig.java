package com.haier.stock.model;

import java.io.Serializable;

/**
 * <p>Table: <strong>inv_reserved_config</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>channelCode</td><td>{@link String}</td><td>channel_code</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>status</td><td>{@link Integer}</td><td>status</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>lockHours</td><td>{@link Integer}</td><td>lock_hours</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>createTime</td><td>{@link Object}</td><td>create_time</td><td>datetimn</td><td>&nbsp;</td></tr>
 *   <tr><td>createUser</td><td>{@link String}</td><td>create_user</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>updateTime</td><td>{@link Object}</td><td>update_time</td><td>datetimn</td><td>&nbsp;</td></tr>
 *   <tr><td>updateUser</td><td>{@link String}</td><td>update_user</td><td>varchar</td><td>&nbsp;</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-11-28
 * @email yudi@sina.com
 */
public class InvReservedConfig implements Serializable {

    public static final Integer STATUS_ON  = 1;
    public static final Integer STATUS_OFF = 0;

    private Integer             id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private String channelCode;

    public String getChannelCode() {
        return this.channelCode;
    }

    public void setChannelCode(String value) {
        this.channelCode = value;
    }

    private Integer status;

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer value) {
        this.status = value;
    }

    private Integer lockHours;

    public Integer getLockHours() {
        return this.lockHours;
    }

    public void setLockHours(Integer value) {
        this.lockHours = value;
    }

    private Object createTime;

    public Object getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Object value) {
        this.createTime = value;
    }

    private String createUser;

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String value) {
        this.createUser = value;
    }

    private Object updateTime;

    public Object getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Object value) {
        this.updateTime = value;
    }

    private String updateUser;

    public String getUpdateUser() {
        return this.updateUser;
    }

    public void setUpdateUser(String value) {
        this.updateUser = value;
    }

    /**
     * 是否允许修改， 1：允许；0不允许
     */
    private Integer allowUpdate;

    /**
     * 是否允许修改， 1：允许；0不允许
     * @return
     */
    public Integer getAllowUpdate() {
        return allowUpdate;
    }

    public void setAllowUpdate(Integer allowUpdate) {
        this.allowUpdate = allowUpdate;
    }

    /**
     * 外部单号
     */
    private String ref;

    /**
     * 外部单号
     * @return
     */
    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

}