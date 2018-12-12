package com.haier.shop.model;

import java.io.Serializable;

/**
 * LESS库存同步接收数据表。
 *
 * <p>Table: <strong>LesStockSyncs</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>receivedData</td><td>{@link Object}</td><td>receivedData</td><td>ntext/long nvarchar</td><td>接收的数据</td></tr>
 *   <tr><td>addTime</td><td>{@link String}</td><td>addTime</td><td>varchar</td><td>日志记录时间</td></tr>
 *   <tr><td>isDone</td><td>{@link Integer}</td><td>isDone</td><td>tinyint</td><td>是否已同步完</td></tr>
 *   <tr><td>doneTime</td><td>{@link String}</td><td>doneTime</td><td>varchar</td><td>同步完成时间</td></tr>
 *   <tr><td>errorMsg</td><td>{@link String}</td><td>errorMsg</td><td>varchar</td><td>错误的信息</td></tr>
 *   <tr><td>isRedone</td><td>{@link Integer}</td><td>isRedone</td><td>tinyint</td><td>是否又同步出库单号信息完成</td></tr>
 *   <tr><td>reDoneSns</td><td>{@link String}</td><td>reDoneSns</td><td>text</td><td>重新同步完成的网单号</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-4-28
 * @email yudi@sina.com
 */
public class LesStockSyncs implements Serializable {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 229797183813687890L;
    private Integer           id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Object receivedData;

    /**
     * 获取 接收的数据。
     */
    public Object getReceivedData() {
        return this.receivedData;
    }

    /**
     * 设置 接收的数据。
     *
     * @param value 属性值
     */
    public void setReceivedData(Object value) {
        this.receivedData = value;
    }

    private String addTime;

    /**
     * 获取 日志记录时间。
     */
    public String getAddTime() {
        return this.addTime;
    }

    /**
     * 设置 日志记录时间。
     *
     * @param value 属性值
     */
    public void setAddTime(String value) {
        this.addTime = value;
    }

    private Integer isDone;

    /**
     * 获取 是否已同步完。
     */
    public Integer getIsDone() {
        return this.isDone;
    }

    /**
     * 设置 是否已同步完。
     *
     * @param value 属性值
     */
    public void setIsDone(Integer value) {
        this.isDone = value;
    }

    private String doneTime;

    /**
     * 获取 同步完成时间。
     */
    public String getDoneTime() {
        return this.doneTime;
    }

    /**
     * 设置 同步完成时间。
     *
     * @param value 属性值
     */
    public void setDoneTime(String value) {
        this.doneTime = value;
    }

    private String errorMsg;

    /**
     * 获取 错误的信息。
     */
    public String getErrorMsg() {
        return this.errorMsg;
    }

    /**
     * 设置 错误的信息。
     *
     * @param value 属性值
     */
    public void setErrorMsg(String value) {
        this.errorMsg = value;
    }

    private Integer isRedone = 0;

    /**
     * 获取 是否又同步出库单号信息完成。
     */
    public Integer getIsRedone() {
        return this.isRedone;
    }

    /**
     * 设置 是否又同步出库单号信息完成。
     *
     * @param value 属性值
     */
    public void setIsRedone(Integer value) {
        this.isRedone = value;
    }

    private String reDoneSns;

    /**
     * 获取 重新同步完成的网单号。
     */
    public String getReDoneSns() {
        return this.reDoneSns;
    }

    /**
     * 设置 重新同步完成的网单号。
     *
     * @param value 属性值
     */
    public void setReDoneSns(String value) {
        this.reDoneSns = value;
    }

}