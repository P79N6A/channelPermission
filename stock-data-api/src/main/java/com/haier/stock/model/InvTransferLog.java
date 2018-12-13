package com.haier.stock.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 调拨单日志。
 *
 * <p>Table: <strong>inv_transfer_log</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>logId</td><td>{@link Integer}</td><td>log_id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>lineId</td><td>{@link Integer}</td><td>line_id</td><td>int</td><td>调拨单明细（网单）ID</td></tr>
 *   <tr><td>logType</td><td>{@link Integer}</td><td>log_type</td><td>int</td><td>日志类型。<br />-1: 删除调拨网单。<br />1: 创建调拨网单。<br />2: 修改调拨网单（暂时只允许修改调拨数量）。<br />3: 提交物流中心录入费用。<br />4: 录入费用（每次费用值有变化时记录）。<br />5: 提交费用审核。<br />6: 费用审核。<br />7: 传LES。<br />8: LES出库。<br />9: LES入库。<br />10: 取消。</td></tr>
 *   <tr><td>logTime</td><td>{@link Date}</td><td>log_time</td><td>datetime</td><td>日志时间。</td></tr>
 *   <tr><td>optTime</td><td>{@link Date}</td><td>opt_time</td><td>datetime</td><td>业务操作时间。<br />LES出库、LES入库时记录LES传过来的实际出入库时间；其他情况无需记录opt_time。</td></tr>
 *   <tr><td>logUser</td><td>{@link String}</td><td>log_user</td><td>varchar</td><td>操作人。</td></tr>
 *   <tr><td>logRemark</td><td>{@link String}</td><td>log_remark</td><td>varchar</td><td>备注。</td></tr>
 * </table>
 *
 * @author 
 * 吴坤洋
 */
public class InvTransferLog implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -4525118793204730794L;

    /**
     * 删除调拨网单
     */
    public static Integer LOG_TYPE_DEL                     = -1;
    /**
     * 创建调拨网单
     */
    public static Integer LOG_TYPE_NEW                     = 1;
    /**
     * 修改调拨网单
     */
    public static Integer LOG_TYPE_MODIFY                  = 2;
    /**
     * 确认调拨单
     */
    public static Integer LOG_TYPE_CONFIRM                 = 3;
    /**
     * 提交物流中心录入费用
     */
    public static Integer LOG_TYPE_LOGISTICS_CENTER_SUBMIT = 4;
    /**
     * 录入费用（每次费用值有变化时记录）
     */
    public static Integer LOG_TYPE_FEE_INPUT               = 5;
    /**
     * 提交费用审核
     */
    public static Integer LOG_TYPE_FEE_SUBMIT              = 6;
    /**
     * 费用审核
     */
    public static Integer LOG_TYPE_FEE_AUDIT               = 7;
    /**
     * 传LES
     */
    public static Integer LOG_TYPE_LES_TRANSFER            = 8;
    /**
     * LES出库
     */
    public static Integer LOG_TYPE_LES_OUT                 = 9;
    /**
     * LES入库
     */
    public static Integer LOG_TYPE_LES_IN                  = 10;
    /**
     * 取消
     */
    public static Integer LOG_TYPE_CANCEL                  = 11;
    /**
     * 完成
     */
    public static Integer LOG_TYPE_COMPLETE                = 12;
    /**
     * 3W转库-传VOM出库
     */
    public static Integer LOG_TYPE_3W_CBSTOVOM             = 21;

    /**
     * 3W转库-加入3W取消物流队列，自动调用物流接口
     */
    public static Integer LOG_TYPE_3W_CANCEL_QUEUE = 22;

    private Integer logId;

    public Integer getLogId() {
        return this.logId;
    }

    public void setLogId(Integer value) {
        this.logId = value;
    }

    private Integer lineId = 0;

    /**
     * 获取 调拨单明细（网单）ID。
     */
    public Integer getLineId() {
        return this.lineId;
    }

    /**
     * 设置 调拨单明细（网单）ID。
     *
     * @param value 属性值
     */
    public void setLineId(Integer value) {
        this.lineId = value;
    }

    private Integer logType = 0;

    /**
     * 获取 日志类型。
     *
     * <p>
     * 1: 创建调拨网单。<br />
     * 2: 修改调拨网单（暂时只允许修改调拨数量）。<br />
     * 3: 提交物流中心录入费用。<br />
     * 4: 录入费用（每次费用值有变化时记录）。<br />
     * 5: 提交费用审核。<br />
     * 6: 费用审核。<br />
     * 7: 传LES。<br />
     * 8: LES出库。<br />
     * 9: LES入库。<br />
     * 10: 取消。
     */
    public Integer getLogType() {
        return this.logType;
    }

    /**
     * 设置 日志类型。
     *
     * <p>
     * 1: 创建调拨网单。<br />
     * 2: 修改调拨网单（暂时只允许修改调拨数量）。<br />
     * 3: 提交物流中心录入费用。<br />
     * 4: 录入费用（每次费用值有变化时记录）。<br />
     * 5: 提交费用审核。<br />
     * 6: 费用审核。<br />
     * 7: 传LES。<br />
     * 8: LES出库。<br />
     * 9: LES入库。<br />
     * 10: 取消。
     *
     * @param value 属性值
     */
    public void setLogType(Integer value) {
        this.logType = value;
    }

    private Date logTime = null;

    /**
     * 获取 日志时间。
     */
    public Date getLogTime() {
        return this.logTime;
    }

    /**
     * 设置 日志时间。
     *
     * @param value 属性值
     */
    public void setLogTime(Date value) {
        this.logTime = value;
    }

    private Date optTime = null;

    /**
     * 获取 业务操作时间。
     *
     * <p>
     * LES出库、LES入库时记录LES传过来的实际出入库时间；其他情况无需记录opt_time。
     */
    public Date getOptTime() {
        return this.optTime;
    }

    /**
     * 设置 业务操作时间。
     *
     * <p>
     * LES出库、LES入库时记录LES传过来的实际出入库时间；其他情况无需记录opt_time。
     *
     * @param value 属性值
     */
    public void setOptTime(Date value) {
        this.optTime = value;
    }

    private String logUser = "";

    /**
     * 获取 操作人。
     */
    public String getLogUser() {
        return this.logUser;
    }

    /**
     * 设置 操作人。
     *
     * @param value 属性值
     */
    public void setLogUser(String value) {
        this.logUser = value;
    }

    private String logRemark = "";

    /**
     * 获取 备注。
     */
    public String getLogRemark() {
        return this.logRemark;
    }

    public String getContentXml() {
        return contentXml;
    }

    public void setContentXml(String contentXml) {
        this.contentXml = contentXml;
    }

    /**
     * 设置 备注。
     *
     * @param value 属性值
     */
    public void setLogRemark(String value) {
        this.logRemark = value;

    }

    private String contentXml;

}
