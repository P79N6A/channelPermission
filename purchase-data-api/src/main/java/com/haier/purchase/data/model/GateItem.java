package com.haier.purchase.data.model;

import java.io.Serializable;

/**
 * <p>Table: <strong>haier_time_gate_t</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>setting_id</td><td>{@link String}</td><td>setting_id</td><td>varchar</td><td>名称</td></tr>
 *   <tr><td>ed_channel_id</td><td>{@link String}</td><td>ed_channel_id</td><td>varchar</td><td>渠道</td></tr>
 *   <tr><td>product_group_id</td><td>{@link String}</td><td>product_group_id</td><td>varchar</td><td>产品线</td></tr>
 *   <tr><td>setting_year</td><td>{@link String}</td><td>setting_year</td><td>smallint</td><td>年</td></tr>
 *   <tr><td>setting_month</td><td>{@link String}</td><td>setting_month</td><td>smallint</td><td>月</td></tr>
 *   <tr><td>setting_week</td><td>{@link String}</td><td>setting_week</td><td>smallint</td><td>星期</td></tr>
 *   <tr><td>setting_day</td><td>{@link String}</td><td>setting_day</td><td>smallint</td><td>日</td></tr>
 *   <tr><td>setting_hour</td><td>{@link String}</td><td>setting_hour</td><td>smallint</td><td>小时</td></tr>
 *   <tr><td>setting_minute</td><td>{@link String}</td><td>setting_minute</td><td>smallint</td><td>分钟</td></tr>
 *   <tr><td>setting_second</td><td>{@link String}</td><td>setting_second</td><td>smallint</td><td>秒</td></tr>
 *   <tr><td>default_setting_year</td><td>{@link String}</td><td>default_setting_year</td><td>smallint</td><td>默认年</td></tr>
 *   <tr><td>default_setting_month</td><td>{@link String}</td><td>default_setting_month</td><td>smallint</td><td>默认月</td></tr>
 *   <tr><td>default_setting_week</td><td>{@link String}</td><td>default_setting_week</td><td>smallint</td><td>默认星期</td></tr>
 *   <tr><td>default_setting_day</td><td>{@link String}</td><td>default_setting_day</td><td>smallint</td><td>默认日</td></tr>
 *   <tr><td>default_setting_hour</td><td>{@link String}</td><td>default_setting_hour</td><td>smallint</td><td>默认小时</td></tr>
 *   <tr><td>default_setting_minute</td><td>{@link String}</td><td>default_setting_minute</td><td>smallint</td><td>默认分钟</td></tr>
 *   <tr><td>default_setting_second</td><td>{@link String}</td><td>default_setting_second</td><td>smallint</td><td>默认秒</td></tr>
 *   <tr><td>is_enabled</td><td>{@link String}</td><td>is_enabled</td><td>smallint</td><td>是否启用</td></tr>
 *   <tr><td>modify_user</td><td>{@link String}</td><td>modify_user</td><td>varchar</td><td>修改人</td></tr>
 *   <tr><td>modify_time</td><td>{@link String}</td><td>modify_time</td><td>timestamp</td><td>修改时间</td></tr>
 * </table>
 *
 * @author 刘骥飞
 * @date 2014-7-28
 * @email jifei.liu@dhc.com.cn
 */
public class GateItem implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -7315758884676253862L;

    private String            setting_id;
    private String            ed_channel_id;
    private String            product_group_id;
    private String            setting_year;
    private String            setting_month;
    private String            setting_week;
    private String            setting_day;
    private String            setting_hour;
    private String            setting_minute;
    private String            setting_second;
    private String            default_setting_year;
    private String            default_setting_month;
    private String            default_setting_week;
    private String            default_setting_day;
    private String            default_setting_hour;
    private String            default_setting_minute;
    private String            default_setting_second;
    private String            is_enabled;
    private String            modify_user;
    private String            modify_time;

    /**
     * @return Returns the setting_id
     */
    public String getSetting_id() {
        return setting_id;
    }

    /**
     * @param setting_id
     * The setting_id to set.
     */
    public void setSetting_id(String setting_id) {
        this.setting_id = setting_id;
    }

    /**
     * @return Returns the ed_channel_id
     */
    public String getEd_channel_id() {
        return ed_channel_id;
    }

    /**
     * @param ed_channel_id
     * The ed_channel_id to set.
     */
    public void setEd_channel_id(String ed_channel_id) {
        this.ed_channel_id = ed_channel_id;
    }

    /**
     * @return Returns the product_group_id
     */
    public String getProduct_group_id() {
        return product_group_id;
    }

    /**
     * @param product_group_id
     * The product_group_id to set.
     */
    public void setProduct_group_id(String product_group_id) {
        this.product_group_id = product_group_id;
    }

    /**
     * @return Returns the setting_year
     */
    public String getSetting_year() {
        return setting_year;
    }

    /**
     * @param setting_year
     * The setting_year to set.
     */
    public void setSetting_year(String setting_year) {
        this.setting_year = setting_year;
    }

    /**
     * @return Returns the setting_month
     */
    public String getSetting_month() {
        return setting_month;
    }

    /**
     * @param setting_month
     * The setting_month to set.
     */
    public void setSetting_month(String setting_month) {
        this.setting_month = setting_month;
    }

    /**
     * @return Returns the setting_week
     */
    public String getSetting_week() {
        return setting_week;
    }

    /**
     * @param setting_week
     * The setting_week to set.
     */
    public void setSetting_week(String setting_week) {
        this.setting_week = setting_week;
    }

    /**
     * @return Returns the setting_day
     */
    public String getSetting_day() {
        return setting_day;
    }

    /**
     * @param setting_day
     * The setting_day to set.
     */
    public void setSetting_day(String setting_day) {
        this.setting_day = setting_day;
    }

    /**
     * @return Returns the setting_hour
     */
    public String getSetting_hour() {
        return setting_hour;
    }

    /**
     * @param setting_hour
     * The setting_hour to set.
     */
    public void setSetting_hour(String setting_hour) {
        this.setting_hour = setting_hour;
    }

    /**
     * @return Returns the setting_minute
     */
    public String getSetting_minute() {
        return setting_minute;
    }

    /**
     * @param setting_minute
     * The setting_minute to set.
     */
    public void setSetting_minute(String setting_minute) {
        this.setting_minute = setting_minute;
    }

    /**
     * @return Returns the setting_second
     */
    public String getSetting_second() {
        return setting_second;
    }

    /**
     * @param setting_second
     * The setting_second to set.
     */
    public void setSetting_second(String setting_second) {
        this.setting_second = setting_second;
    }

    /**
     * @return Returns the default_setting_year
     */
    public String getDefault_setting_year() {
        return default_setting_year;
    }

    /**
     * @param default_setting_year
     * The default_setting_year to set.
     */
    public void setDefault_setting_year(String default_setting_year) {
        this.default_setting_year = default_setting_year;
    }

    /**
     * @return Returns the default_setting_month
     */
    public String getDefault_setting_month() {
        return default_setting_month;
    }

    /**
     * @param default_setting_month
     * The default_setting_month to set.
     */
    public void setDefault_setting_month(String default_setting_month) {
        this.default_setting_month = default_setting_month;
    }

    /**
     * @return Returns the default_setting_week
     */
    public String getDefault_setting_week() {
        return default_setting_week;
    }

    /**
     * @param default_setting_week
     * The default_setting_week to set.
     */
    public void setDefault_setting_week(String default_setting_week) {
        this.default_setting_week = default_setting_week;
    }

    /**
     * @return Returns the default_setting_day
     */
    public String getDefault_setting_day() {
        return default_setting_day;
    }

    /**
     * @param default_setting_day
     * The default_setting_day to set.
     */
    public void setDefault_setting_day(String default_setting_day) {
        this.default_setting_day = default_setting_day;
    }

    /**
     * @return Returns the default_setting_hour
     */
    public String getDefault_setting_hour() {
        return default_setting_hour;
    }

    /**
     * @param default_setting_hour
     * The default_setting_hour to set.
     */
    public void setDefault_setting_hour(String default_setting_hour) {
        this.default_setting_hour = default_setting_hour;
    }

    /**
     * @return Returns the default_setting_minute
     */
    public String getDefault_setting_minute() {
        return default_setting_minute;
    }

    /**
     * @param default_setting_minute
     * The default_setting_minute to set.
     */
    public void setDefault_setting_minute(String default_setting_minute) {
        this.default_setting_minute = default_setting_minute;
    }

    /**
     * @return Returns the default_setting_second
     */
    public String getDefault_setting_second() {
        return default_setting_second;
    }

    /**
     * @param default_setting_second
     * The default_setting_second to set.
     */
    public void setDefault_setting_second(String default_setting_second) {
        this.default_setting_second = default_setting_second;
    }

    /**
     * @return Returns the is_enabled
     */
    public String getIs_enabled() {
        return is_enabled;
    }

    /**
     * @param is_enabled
     * The is_enabled to set.
     */
    public void setIs_enabled(String is_enabled) {
        this.is_enabled = is_enabled;
    }

    /**
     * @return Returns the modify_user
     */
    public String getModify_user() {
        return modify_user;
    }

    /**
     * @param modify_user
     * The modify_user to set.
     */
    public void setModify_user(String modify_user) {
        this.modify_user = modify_user;
    }

    /**
     * @return Returns the modify_time
     */
    public String getModify_time() {
        return modify_time;
    }

    /**
     * @param modify_time
     * The modify_time to set.
     */
    public void setModify_time(String modify_time) {
        this.modify_time = modify_time;
    }

    /**
     * @return Returns the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}