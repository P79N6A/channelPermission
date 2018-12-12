package com.haier.eis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * CBS与财务接口数据。
 *
 * <p>Table: <strong>eis_interface_queue_data</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>sourceId</td><td>{@link Integer}</td><td>source_id</td><td>int</td><td>源数据ID</td></tr>
 *   <tr><td>source</td><td>{@link String}</td><td>source</td><td>char</td><td>来源</td></tr>
 *   <tr><td>billType</td><td>{@link String}</td><td>bill_type</td><td>char</td><td>交易类型</td></tr>
 *   <tr><td>interfaceCode</td><td>{@link String}</td><td>interface_code</td><td>varchar</td><td>接口编码<br />0：出<br />1：入<br /></td></tr>
 *   <tr><td>status</td><td>{@link Integer}</td><td>status</td><td>int</td><td>状态<br />-1：出现特殊处理<br />0：未知（需调用状态查询接口）<br />1：成功<br />2：失败（重新发送）<br />3：错误（系统无法处理）</td></tr>
 *   <tr><td>message</td><td>{@link String}</td><td>message</td><td>varchar</td><td>信息</td></tr>
 *   <tr><td>addTime</td><td>{@link Date}</td><td>add_time</td><td>datetime</td><td>新增时间</td></tr>
 *   <tr><td>updateTime</td><td>{@link Date}</td><td>update_time</td><td>datetime</td><td>更新时间</td></tr>
 *   <tr><td>dataLogId</td><td>{@link Integer}</td><td>data_log_id</td><td>int</td><td>EAI接口日志标识</td></tr>
 * </table>
 *
 * @author sjc
 * @date 2015-4-8
 * @email shijincheng@ehaier.com
 */
public class EisInterfaceQueueData implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer sourceId;

    /**
     * 获取 源数据ID
     * @return
     */
    public Integer getSourceId() {
        return sourceId;
    }

    /**
     * 设置 源数据ID
     * @param sourceId 属性值
     */
    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    /**
     * 0-不良品
     */
    public final static int SOURCE_DEFECTIVEPRODUCTS      = 0;
    /**
     * 1-统帅彩电不良品
     */
    public final static int TSCD_SOURCE_DEFECTIVEPRODUCTS = 1;

    private Integer source;

    /**
     * 获取 来源
     * @return
     */
    public Integer getSource() {
        return source;
    }

    /**
     * 设置 来源
     * @param source 属性值
     */
    public void setSource(Integer source) {
        this.source = source;
    }

    /**
     * 出
     */
    public final static int BILLTYPE_OUT = 0;

    /**
     * 入
     */
    public final static int BILLTYPE_IN = 1;

    private Integer billType;

    /**
     * 获取 交易类型
     * @return
     */
    public Integer getBillType() {
        return billType;
    }

    /**
     * 设置 交易类型
     * @param billType 属性值
     */
    public void setBillType(Integer billType) {
        this.billType = billType;
    }

    /**
     * 接口编码 - 发送不良品虚入信息到SAP
     */
    public static String INTERFACE_CODE_DEFECTIVEPRODUCTS_IN = "TransferDefectiveProductsInToGVSModel";

    /**
     * 接口编码 - 发送不良品出信息到SAP
     */
    public static String INTERFACE_CODE_DEFECTIVEPRODUCTS_OUT = "TransferDefectiveProductsOutToGVSModel";

    /**
     * 接口编码 - 统帅彩电不良品发送虚入信息到SAP
     */
    public static String INTERFACE_CODE_TSCD_DEFECTIVEPRODUCTS_IN = "TransferInvThOrderInToSapModel";

    /**
     * 接口编码 - 统帅彩电不良品发送出信息到SAP
     */
    public static String INTERFACE_CODE_TSCD_DEFECTIVEPRODUCTS_OUT = "TransferInvThOrderOutToSapModel";

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
     * 出现特殊处理
     */
    public final static int STATUS_WARN    = -1;
    /**
     * 未知（需要调用状态查询接口）
     */
    public final static int STATUS_UNKNOWN = 0;
    /**
    * 成功
    */
    public final static int STATUS_SUCCESS = 1;

    /**
     * 失败（需要重新处理）
     */
    public final static int STATUS_FAILED = 2;
    /**
     * 错误（无法处理）
     */
    public final static int STATUS_ERROR  = 3;

    private Integer status;

    /**
     * 表示原状态，用于辅助更新状态
     */
    private Integer oldStatus;

    public Integer getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(Integer oldStatus) {
        this.oldStatus = oldStatus;
    }

    /**
     * 获取  状态
     *
     * <p>
     * -1：出现特殊处理<br />
     * 0：未知<br />
     * 1：成功<br />
     * 2：失败（重新发送）<br />
     * 3：错误（系统无法处理）
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置 状态
     *
     * <p>
     * -1：出现特殊处理<br />
     * 0：未知<br />
     * 1：成功<br />
     * 2：失败（重新发送）<br />
     * 3：错误（系统无法处理）
     *
     * @param value 属性值
     */
    public void setStatus(Integer value) {
        this.status = value;
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

    private Integer dataLogId;

    /**
     * 获取 EAI接口日志标识。
     */
    public Integer getDataLogId() {
        return dataLogId;
    }

    /**
     * 设置 EAI接口日志标识。
     *
     * @param dataLogId 属性值
     */
    public void setDataLogId(Integer dataLogId) {
        this.dataLogId = dataLogId;
    }

}