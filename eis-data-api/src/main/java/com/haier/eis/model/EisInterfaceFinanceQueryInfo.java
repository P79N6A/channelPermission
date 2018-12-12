package com.haier.eis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * CBS与财务接口。
 *
 * <p>Table: <strong>eis_interface_finance_query_info</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>financeId</td><td>{@link Integer}</td><td>les_stock_trans_queue_id</td><td>int</td><td>EisInterfaceFinance ID</td></tr>
 *   <tr><td>interfaceCode</td><td>{@link String}</td><td>interface_code</td><td>varchar</td><td>接口编码</td></tr>
 *   <tr><td>cordersn</td><td>{@link String}</td><td>cordersn</td><td>varchar</td><td>网单号</td></tr>
 *   <tr><td>system</td><td>{@link String}</td><td>system</td><td>varchar</td><td>系统</td></tr>
 *   <tr><td>eaiType</td><td>{@link String}</td><td>eai_type</td><td>varchar</td><td>EAI反馈类型</td></tr>
 *   <tr><td>mark</td><td>{@link String}</td><td>mark</td><td>varchar</td><td>标示</td></tr>
 *   <tr><td>eaiMessage</td><td>{@link String}</td><td>message</td><td>varchar</td><td>信息</td></tr>
 *   <tr><td>addTime</td><td>{@link Date}</td><td>add_time</td><td>datetime</td><td>&nbsp;</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-8-8
 * @email yudi@sina.com
 */
public class EisInterfaceFinanceQueryInfo implements Serializable {
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

    private Integer financeId;

    /**
     * 获取 EisInterfaceFinance ID。
     */
    public Integer getFinanceId() {
        return this.financeId;
    }

    /**
     * 设置 EisInterfaceFinance ID。
     *
     * @param value 属性值
     */
    public void setFinanceId(Integer value) {
        this.financeId = value;
    }

    private String interfaceCode;

    /**
     * 获取 接口编码。
     */
    public String getInterfaceCode() {
        return this.interfaceCode;
    }

    /**
     * 设置 接口编码。
     *
     * @param value 属性值
     */
    public void setInterfaceCode(String value) {
        this.interfaceCode = value;
    }

    private String cordersn;

    /**
     * 获取 网单号。
     */
    public String getCordersn() {
        return this.cordersn;
    }

    /**
     * 设置 网单号。
     *
     * @param value 属性值
     */
    public void setCordersn(String value) {
        this.cordersn = value;
    }

    private String system;

    /**
     * 获取 系统。
     */
    public String getSystem() {
        return this.system;
    }

    /**
     * 设置 系统。
     *
     * @param value 属性值
     */
    public void setSystem(String value) {
        this.system = value;
    }

    private String eaiType;

    /**
     * 获取 EAI反馈类型。
     */
    public String getEaiType() {
        return this.eaiType;
    }

    /**
     * 设置 EAI反馈类型。
     *
     * @param value 属性值
     */
    public void setEaiType(String value) {
        this.eaiType = value;
    }

    private String mark;

    /**
     * 获取 标示。
     */
    public String getMark() {
        return this.mark;
    }

    /**
     * 设置 标示。
     *
     * @param value 属性值
     */
    public void setMark(String value) {
        this.mark = value;
    }

    private String eaiMessage;

    /**
     * 获取 信息。
     */
    public String getEaiMessage() {
        return this.eaiMessage;
    }

    /**
     * 设置 信息。
     *
     * @param value 属性值
     */
    public void setEaiMessage(String value) {
        this.eaiMessage = value;
    }

    private Date addTime;

    public Date getAddTime() {
        return this.addTime;
    }

    public void setAddTime(Date value) {
        this.addTime = value;
    }

}