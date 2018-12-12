package com.haier.purchase.data.model.vehcile;

import java.io.Serializable;
import java.util.Date;

/**
 * CBS与财务接口。
 *
 * <p>Table: <strong>eis_interface_finance</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>lesStockTransQueueId</td><td>{@link Integer}</td><td>les_stock_trans_queue_id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>interfaceCode</td><td>{@link String}</td><td>interface_code</td><td>varchar</td><td>接口编码</td></tr>
 *   <tr><td>status</td><td>{@link Integer}</td><td>status</td><td>int</td><td>0：未知（需调用状态查询接口）<br />1：成功<br />2：失败（重新发送）<br />3：错误（系统无法处理）</td></tr>
 *   <tr><td>eaiType</td><td>{@link String}</td><td>eai_type</td><td>varchar</td><td>EAI反馈类型</td></tr>
 *   <tr><td>message</td><td>{@link String}</td><td>message</td><td>varchar</td><td>信息</td></tr>
 *   <tr><td>addTime</td><td>{@link Date}</td><td>add_time</td><td>datetime</td><td>&nbsp;</td></tr>
 *   <tr><td>updateTime</td><td>{@link Date}</td><td>update_time</td><td>datetime</td><td>&nbsp;</td></tr>
 *   <tr><td>eaiDataLogId</td><td>{@link Integer}</td><td>eai_data_log_id</td><td>int</td><td>EAI接口日志标识</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-8-8
 * @email yudi@sina.com
 */
public class EisInterfaceFinance implements Serializable {
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

    private Integer lesStockTransQueueId;

    public Integer getLesStockTransQueueId() {
        return this.lesStockTransQueueId;
    }

    public void setLesStockTransQueueId(Integer value) {
        this.lesStockTransQueueId = value;
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

    /**
    * 成功
    */
    public final static int STATUS_SUCCESS = 1;
    /**
     * 未知（需要调用状态查询接口）
     */
    public final static int STATUS_UNKNOWN = 0;
    /**
     * 失败（需要重新处理）
     */
    public final static int STATUS_FAILED  = 2;
    /**
     * 错误（无法处理）
     */
    public final static int STATUS_ERROR   = 3;

    /**
     * 出现特殊处理
     */
    public final static int STATUS_WARN    = -1;

    private Integer         status;

    /**
     * 获取 0：未知（需调用状态查询接口）。
     *
     * <p>
     * 1：成功<br />
     * 2：失败（重新发送）<br />
     * 3：错误（系统无法处理）
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置 0：未知（需调用状态查询接口）。
     *
     * <p>
     * 1：成功<br />
     * 2：失败（重新发送）<br />
     * 3：错误（系统无法处理）
     *
     * @param value 属性值
     */
    public void setStatus(Integer value) {
        this.status = value;
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

    private String message;

    /**
     * 获取 信息。
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * 设置 信息。
     *
     * @param value 属性值
     */
    public void setMessage(String value) {
        this.message = value;
    }

    private Date addTime;

    public Date getAddTime() {
        return this.addTime;
    }

    public void setAddTime(Date value) {
        this.addTime = value;
    }

    private Date updateTime;

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date value) {
        this.updateTime = value;
    }

    private Integer eaiDataLogId;

    /**
     * 获取 EAI接口日志标识。
     */
    public Integer getEaiDataLogId() {
        return this.eaiDataLogId;
    }

    /**
     * 设置 EAI接口日志标识。
     *
     * @param value 属性值
     */
    public void setEaiDataLogId(Integer value) {
        this.eaiDataLogId = value;
    }

}