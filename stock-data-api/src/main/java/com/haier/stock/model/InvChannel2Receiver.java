package com.haier.stock.model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Table: <strong>inv_channel_2_receiver</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>channelCode</td><td>{@link String}</td><td>channel_code</td><td>varchar</td><td>渠道编号</td></tr>
 *   <tr><td>receiverCode</td><td>{@link String}</td><td>receiver_code</td><td>varchar</td><td>送达方编码<br />与LES系统对应</td></tr>
 *   <tr><td>createTime</td><td>{@link Date}</td><td>create_time</td><td>datetime</td><td>创建时间</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-6-8
 * @email yudi@sina.com
 */
public class InvChannel2Receiver implements Serializable {
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

    private String channelCode;

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

    private String receiverCode;

    /**
     * 获取 送达方编码。
     *
     * <p>
     * 与LES系统对应
     */
    public String getReceiverCode() {
        return this.receiverCode;
    }

    /**
     * 设置 送达方编码。
     *
     * <p>
     * 与LES系统对应
     *
     * @param value 属性值
     */
    public void setReceiverCode(String value) {
        this.receiverCode = value;
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

}