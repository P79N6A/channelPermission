package com.haier.shop.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 人员工贸配置表。
 *
 * <p>Table: <strong>person_trade_cfg</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>area</td><td>{@link String}</td><td>area</td><td>varchar</td><td>区域</td></tr>
 *   <tr><td>trade</td><td>{@link String}</td><td>trade</td><td>varchar</td><td>工贸</td></tr>
 *   <tr><td>commissioner</td><td>{@link String}</td><td>commissioner</td><td>varchar</td><td>专员</td></tr>
 *   <tr><td>manager</td><td>{@link String}</td><td>manager</td><td>varchar</td><td>区域主管</td></tr>
 *   <tr><td>modifyTime</td><td>{@link Date}</td><td>modify_time</td><td>datetime</td><td>修改时间</td></tr>
 *   <tr><td>modifyUser</td><td>{@link String}</td><td>modify_user</td><td>varchar</td><td>修改者</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-9-22
 * @email yudi@sina.com
 */
public class PersonTradeCfg implements Serializable {
    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private String area;

    /**
     * 获取 区域。
     */
    public String getArea() {
        return this.area;
    }

    /**
     * 设置 区域。
     *
     * @param value 属性值
     */
    public void setArea(String value) {
        this.area = value;
    }

    private String trade;

    /**
     * 获取 工贸。
     */
    public String getTrade() {
        return this.trade;
    }

    /**
     * 设置 工贸。
     *
     * @param value 属性值
     */
    public void setTrade(String value) {
        this.trade = value;
    }

    private String commissioner;

    /**
     * 获取 专员。
     */
    public String getCommissioner() {
        return this.commissioner;
    }

    /**
     * 设置 专员。
     *
     * @param value 属性值
     */
    public void setCommissioner(String value) {
        this.commissioner = value;
    }

    private String manager;

    /**
     * 获取 区域主管。
     */
    public String getManager() {
        return this.manager;
    }

    /**
     * 设置 区域主管。
     *
     * @param value 属性值
     */
    public void setManager(String value) {
        this.manager = value;
    }

    private Date modifyTime;

    /**
     * 获取 修改时间。
     */
    public Date getModifyTime() {
        return this.modifyTime;
    }

    /**
     * 设置 修改时间。
     *
     * @param value 属性值
     */
    public void setModifyTime(Date value) {
        this.modifyTime = value;
    }

    private String modifyUser;

    /**
     * 获取 修改者。
     */
    public String getModifyUser() {
        return this.modifyUser;
    }

    /**
     * 设置 修改者。
     *
     * @param value 属性值
     */
    public void setModifyUser(String value) {
        this.modifyUser = value;
    }

    private String smallChannelPeople;

    public String getSmallChannelPeople() {
        return smallChannelPeople;
    }

    public void setSmallChannelPeople(String smallChannelPeople) {
        this.smallChannelPeople = smallChannelPeople;
    }

}