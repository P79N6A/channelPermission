package com.haier.stock.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 库存渠道设置。
 *
 * <p>Table: <strong>inv_stock_channel</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>channelCode</td><td>{@link String}</td><td>channel_code</td><td>varchar</td><td>渠道编号</td></tr>
 *   <tr><td>name</td><td>{@link String}</td><td>name</td><td>nvarchar</td><td>渠道名称</td></tr>
 *   <tr><td>createTime</td><td>{@link Date}</td><td>create_time</td><td>datetime</td><td>创建时间</td></tr>
 *   <tr><td>createUser</td><td>{@link String}</td><td>create_user</td><td>varchar</td><td>创建人</td></tr>
 *   <tr><td>updateTime</td><td>{@link Date}</td><td>update_time</td><td>datetime</td><td>修改时间</td></tr>
 *   <tr><td>updateUser</td><td>{@link String}</td><td>update_user</td><td>varchar</td><td>修改人</td></tr>
 *   <tr><td>note</td><td>{@link String}</td><td>note</td><td>nvarchar</td><td>备注</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-6-8
 * @email yudi@sina.com
 */
public class InvStockChannel implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long  serialVersionUID   = 1L;
    private String             channelCode;

    /**
     * 全渠道
     */
    public static final String ALL                = "ALL";
    public static final String CHANNEL_DAKEHU     = "DKH";
    public static final String CHANNEL_GONGXIANG  = "DSGX";
    public static final String CHANNEL_SHANGCHENG = "SC";
    public static final String JD                 = "JD";
    public static final String YX                 = "YX";
    public static final String CHANNEL_TAOBAO     = "TB";
    public static final String CHANNEL_QITA       = "QT";
    public static final String CHANNEL_RRS        = "RS";
    public static final String CHANNEL_MK         = "MK";

    /**
     * 获取 渠道编号。
     */
    public String getChannelCode() {
        return this.channelCode;
    }

    /**
     * 设置 渠道编号。
     *
     * @param value 属性值
     */
    public void setChannelCode(String value) {
        this.channelCode = value;
    }

    private String name;

    /**
     * 获取 渠道名称。
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置 渠道名称。
     *
     * @param value 属性值
     */
    public void setName(String value) {
        this.name = value;
    }

    private Date createTime;

    /**
     * 获取 创建时间。
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置 创建时间。
     *
     * @param value 属性值
     */
    public void setCreateTime(Date value) {
        this.createTime = value;
    }

    private String createUser;

    /**
     * 获取 创建人。
     */
    public String getCreateUser() {
        return this.createUser;
    }

    /**
     * 设置 创建人。
     *
     * @param value 属性值
     */
    public void setCreateUser(String value) {
        this.createUser = value;
    }

    private Date updateTime = null;

    /**
     * 获取 修改时间。
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置 修改时间。
     *
     * @param value 属性值
     */
    public void setUpdateTime(Date value) {
        this.updateTime = value;
    }

    private String updateUser;

    /**
     * 获取 修改人。
     */
    public String getUpdateUser() {
        return this.updateUser;
    }

    /**
     * 设置 修改人。
     *
     * @param value 属性值
     */
    public void setUpdateUser(String value) {
        this.updateUser = value;
    }

    private String stockChannelCodes;

    /**
     * 可用库存渠道
     * @return
     */
    public String getStockChannelCodes() {
        return stockChannelCodes;
    }

    public void setStockChannelCodes(String stockChannelCodes) {
        this.stockChannelCodes = stockChannelCodes;
    }

    private Integer isMultilevelSection;

    /**
     * 是否支持多层级
     * @return
     */
    public Integer getIsMultilevelSection() {
        return isMultilevelSection;
    }

    public void setIsMultilevelSection(Integer isMultilevelSection) {
        this.isMultilevelSection = isMultilevelSection;
    }

    private String note;

    /**
     * 获取 备注。
     */
    public String getNote() {
        return this.note;
    }

    /**
     * 设置 备注。
     *
     * @param value 属性值
     */
    public void setNote(String value) {
        this.note = value;
    }

}