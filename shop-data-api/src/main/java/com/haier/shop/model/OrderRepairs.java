package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>Table: <strong>OrderRepairs</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>siteId</td><td>{@link Integer}</td><td>siteId</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>addTime</td><td>{@link Long}</td><td>addTime</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>memberId</td><td>{@link Integer}</td><td>memberId</td><td>int</td><td>会员ID</td></tr>
 *   <tr><td>orderId</td><td>{@link Integer}</td><td>orderId</td><td>int</td><td>订单ID</td></tr>
 *   <tr><td>orderProductId</td><td>{@link Integer}</td><td>orderProductId</td><td>int</td><td>订单商品（网单）ID</td></tr>
 *   <tr><td>paymentStatus</td><td>{@link Integer}</td><td>paymentStatus</td><td>tinyint</td><td>退款处理状态</td></tr>
 *   <tr><td>paymentTime</td><td>{@link Integer}</td><td>paymentTime</td><td>int</td><td>退款时间</td></tr>
 *   <tr><td>paymentName</td><td>{@link String}</td><td>paymentName</td><td>varchar</td><td>支付方式</td></tr>
 *   <tr><td>receiptStatus</td><td>{@link Integer}</td><td>receiptStatus</td><td>tinyint</td><td>冲票状态</td></tr>
 *   <tr><td>receiptTime</td><td>{@link Integer}</td><td>receiptTime</td><td>int</td><td>冲票时间</td></tr>
 *   <tr><td>storageStatus</td><td>{@link Integer}</td><td>storageStatus</td><td>tinyint</td><td>入库状态</td></tr>
 *   <tr><td>storageTime</td><td>{@link Integer}</td><td>storageTime</td><td>int</td><td>入库时间</td></tr>
 *   <tr><td>typeExpect</td><td>{@link Integer}</td><td>typeExpect</td><td>tinyint</td><td>期望处理方式（预留字段）</td></tr>
 *   <tr><td>typeActual</td><td>{@link Integer}</td><td>typeActual</td><td>tinyint</td><td>实际处理方式（预留字段）</td></tr>
 *   <tr><td>reason</td><td>{@link String}</td><td>reason</td><td>varchar</td><td>退款原因</td></tr>
 *   <tr><td>description</td><td>{@link String}</td><td>description</td><td>char</td><td>描述</td></tr>
 *   <tr><td>contactName</td><td>{@link String}</td><td>contactName</td><td>varchar</td><td>联系人姓名</td></tr>
 *   <tr><td>contactMobile</td><td>{@link String}</td><td>contactMobile</td><td>varchar</td><td>联系人手机</td></tr>
 *   <tr><td>handleStatus</td><td>{@link Integer}</td><td>handleStatus</td><td>int</td><td>处理状态（展现给客户）</td></tr>
 *   <tr><td>handleRemark</td><td>{@link String}</td><td>handleRemark</td><td>text</td><td>受理备注，如取消原因等</td></tr>
 *   <tr><td>repairSn</td><td>{@link String}</td><td>repairSn</td><td>varchar</td><td>退货单号</td></tr>
 *   <tr><td>count</td><td>{@link Integer}</td><td>count</td><td>int</td><td>退货数量</td></tr>
 *   <tr><td>requestServiceRemark</td><td>{@link String}</td><td>requestServiceRemark</td><td>varchar</td><td>要求描述</td></tr>
 *   <tr><td>requestServiceDate</td><td>{@link Integer}</td><td>requestServiceDate</td><td>int</td><td>要求服务时间</td></tr>
 *   <tr><td>offlineFlag</td><td>{@link Boolean}</td><td>offlineFlag</td><td>tinyint</td><td>是否线下退款</td></tr>
 *   <tr><td>offlineReason</td><td>{@link String}</td><td>offlineReason</td><td>varchar</td><td>要求线下退款原因</td></tr>
 *   <tr><td>offlineAmount</td><td>{@link BigDecimal}</td><td>offlineAmount</td><td>decimal</td><td>退款金额</td></tr>
 *   <tr><td>offlineRemark</td><td>{@link String}</td><td>offlineRemark</td><td>varchar</td><td>退款金额备注</td></tr>
 *   <tr><td>offlineConfirmFlag</td><td>{@link Boolean}</td><td>offlineConfirmFlag</td><td>tinyint</td><td>线下退款确认标志</td></tr>
 *   <tr><td>cOrderSnStatus</td><td>{@link Integer}</td><td>cOrderSnStatus</td><td>tinyint</td><td>产生逆向单时正向单状态</td></tr>
 *   <tr><td>vomRepairSn</td><td>{@link String}</td><td>vomRepairSn</td><td>varchar</td><td>3Wvom退货单号</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-4-28
 * @email yudi@sina.com
 */
public class OrderRepairs implements Serializable {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -3533983915184224946L;
    /**
     * 入库状态 - 已出库
     */
    public static Integer STORAGE_STATUS_STOCKOUT        = 1;
    /**
     * 入库状态 - 未出库
     */
    public static Integer STORAGE_STATUS_NOTSTOCKOUT     = 2;
    /**
     * 入库状态 - 已召回
     */
    public static Integer STORAGE_STATUS_WAIT_IN         = 3;
    /**
     * 入库状态 - 已入库
     */
    public static Integer STORAGE_STATUS_STOCKIN         = 4;
    /**
     * 入库状态 - 待召回
     */
    public static Integer STORAGE_STATUS_WAIT_RECALL     = 10;
    /**
     * 入库状态 - 已入22库
     */
    public static Integer STORAGE_STATUS_IN22            = 122;
    /**
     * 入库状态 - 已入21库
     */
    public static Integer STORAGE_STATUS_IN21            = 121;
    /**
     * 入库状态 - 已入10库
     */
    public static Integer STORAGE_STATUS_IN10            = 110;
    /**
     * 入库状态 - 已入日日顺21库
     */
    public static Integer STORAGE_STATUS_INRRS21         = 221;

    /**
     * 入库状态 - 已入40库
     */
    public static Integer STORAGE_STATUS_IN40            = 140;
    /**
     * 入库状态 - 已入40库
     */
    public static Integer STORAGE_STATUS_IN41            = 141;

    /**
     * 实际处理类型 -  退货退款
     */
    public static Integer TYPE_ACTUAL_RETURN             = 1;
    /**
     * 实际处理类型 -  退货不退款
     */
    public static Integer TYPE_ACTUAL_CHANGE             = 2;
    /**
     * 处理状态 -  审核中
     */
    public static Integer HANDLE_STATUS_REVIEW           = 1;
    /**
     * 处理状态 -  进行中
     */
    public static Integer HANDLE_STATUS_HANDLE           = 2;
    /**
     * 处理状态 -  受理完成
     */
    public static Integer HANDLE_STATUS_CONFIRM          = 3;
    /**
     * 处理状态 -  已完结
     */
    public static Integer HANDLE_STATUS_CLOSE            = 4;
    /**
     * 处理状态 -  已驳回
     */
    public static Integer HANDLE_STATUS_CANCEL           = 5;
    /**
     * 处理状态 -  已终止
     */
    public static Integer HANDLE_STATUS_STOP             = 6;
    /**
     * 处理状态 -  线下已退款
     */
    public static Integer HANDLE_STATUS_REFUNDED_OFFLINE = 7;

    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer siteId = 0;

    public Integer getSiteId() {
        return this.siteId;
    }

    public void setSiteId(Integer value) {
        this.siteId = value;
    }

    private Integer addTime = 0;

    public Integer getAddTime() {
        return this.addTime;
    }

    public void setAddTime(Integer value) {
        this.addTime = value;
    }

    private Integer memberId = 0;

    /**
     * 获取 会员ID。
     */
    public Integer getMemberId() {
        return this.memberId;
    }

    /**
     * 设置 会员ID。
     *
     * @param value 属性值
     */
    public void setMemberId(Integer value) {
        this.memberId = value;
    }

    private Integer orderId = 0;

    /**
     * 获取 订单ID。
     */
    public Integer getOrderId() {
        return this.orderId;
    }

    /**
     * 设置 订单ID。
     *
     * @param value 属性值
     */
    public void setOrderId(Integer value) {
        this.orderId = value;
    }

    private Integer orderProductId = 0;

    /**
     * 获取 订单商品（网单）ID。
     */
    public Integer getOrderProductId() {
        return this.orderProductId;
    }

    /**
     * 设置 订单商品（网单）ID。
     *
     * @param value 属性值
     */
    public void setOrderProductId(Integer value) {
        this.orderProductId = value;
    }

    private Integer paymentStatus = 1;

    /**
     * 获取 退款处理状态。
     */
    public Integer getPaymentStatus() {
        return this.paymentStatus;
    }

    /**
     * 设置 退款处理状态。
     *
     * @param value 属性值
     */
    public void setPaymentStatus(Integer value) {
        this.paymentStatus = value;
    }

    private Integer paymentTime = 0;

    /**
     * 获取 退款时间。
     */
    public Integer getPaymentTime() {
        return this.paymentTime;
    }

    /**
     * 设置 退款时间。
     *
     * @param value 属性值
     */
    public void setPaymentTime(Integer value) {
        this.paymentTime = value;
    }

    private String paymentName;

    /**
     * 获取 支付方式。
     */
    public String getPaymentName() {
        return this.paymentName;
    }

    /**
     * 设置 支付方式。
     *
     * @param value 属性值
     */
    public void setPaymentName(String value) {
        this.paymentName = value;
    }

    private Integer receiptStatus = 1;

    /**
     * 获取 冲票状态。
     */
    public Integer getReceiptStatus() {
        return this.receiptStatus;
    }

    /**
     * 设置 冲票状态。
     *
     * @param value 属性值
     */
    public void setReceiptStatus(Integer value) {
        this.receiptStatus = value;
    }

    private Integer receiptTime = 0;

    /**
     * 获取 冲票时间。
     */
    public Integer getReceiptTime() {
        return this.receiptTime;
    }

    /**
     * 设置 冲票时间。
     *
     * @param value 属性值
     */
    public void setReceiptTime(Integer value) {
        this.receiptTime = value;
    }

    private Integer storageStatus = 1;

    /**
     * 获取 入库状态。
     */
    public Integer getStorageStatus() {
        return this.storageStatus;
    }

    /**
     * 设置 入库状态。
     *
     * @param value 属性值
     */
    public void setStorageStatus(Integer value) {
        this.storageStatus = value;
    }

    private Integer storageTime = 0;

    /**
     * 获取 入库时间。
     */
    public Integer getStorageTime() {
        return this.storageTime;
    }

    /**
     * 设置 入库时间。
     *
     * @param value 属性值
     */
    public void setStorageTime(Integer value) {
        this.storageTime = value;
    }

    private Integer typeExpect = 0;

    /**
     * 获取 期望处理方式（预留字段）。
     */
    public Integer getTypeExpect() {
        return this.typeExpect;
    }

    /**
     * 设置 期望处理方式（预留字段）。
     *
     * @param value 属性值
     */
    public void setTypeExpect(Integer value) {
        this.typeExpect = value;
    }

    private Integer typeActual = 0;

    /**
     * 获取 实际处理方式（预留字段）。
     */
    public Integer getTypeActual() {
        return this.typeActual;
    }

    /**
     * 设置 实际处理方式（预留字段）。
     *
     * @param value 属性值
     */
    public void setTypeActual(Integer value) {
        this.typeActual = value;
    }

    private String reason;

    /**
     * 获取 退款原因。
     */
    public String getReason() {
        return this.reason;
    }

    /**
     * 设置 退款原因。
     *
     * @param value 属性值
     */
    public void setReason(String value) {
        this.reason = value;
    }

    private String description;

    /**
     * 获取 描述。
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * 设置 描述。
     *
     * @param value 属性值
     */
    public void setDescription(String value) {
        this.description = value;
    }

    private String contactName;

    /**
     * 获取 联系人姓名。
     */
    public String getContactName() {
        return this.contactName;
    }

    /**
     * 设置 联系人姓名。
     *
     * @param value 属性值
     */
    public void setContactName(String value) {
        this.contactName = value;
    }

    private String contactMobile;

    /**
     * 获取 联系人手机。
     */
    public String getContactMobile() {
        return this.contactMobile;
    }

    /**
     * 设置 联系人手机。
     *
     * @param value 属性值
     */
    public void setContactMobile(String value) {
        this.contactMobile = value;
    }

    private Integer handleStatus = 0;

    /**
     * 获取 处理状态（展现给客户）。
     */
    public Integer getHandleStatus() {
        return this.handleStatus;
    }

    /**
     * 设置 处理状态（展现给客户）。
     *
     * @param value 属性值
     */
    public void setHandleStatus(Integer value) {
        this.handleStatus = value;
    }

    private String handleRemark;

    /**
     * 获取 受理备注，如取消原因等。
     */
    public String getHandleRemark() {
        return this.handleRemark;
    }

    /**
     * 设置 受理备注，如取消原因等。
     *
     * @param value 属性值
     */
    public void setHandleRemark(String value) {
        this.handleRemark = value;
    }

    private String repairSn;

    /**
     * 获取 退货单号。
     */
    public String getRepairSn() {
        return this.repairSn;
    }

    /**
     * 设置 退货单号。
     *
     * @param value 属性值
     */
    public void setRepairSn(String value) {
        this.repairSn = value;
    }

    private Integer count = 0;

    /**
     * 获取 退货数量。
     */
    public Integer getCount() {
        return this.count;
    }

    /**
     * 设置 退货数量。
     *
     * @param value 属性值
     */
    public void setCount(Integer value) {
        this.count = value;
    }

    private String requestServiceRemark;

    /**
     * 获取 要求描述。
     */
    public String getRequestServiceRemark() {
        return this.requestServiceRemark;
    }

    /**
     * 设置 要求描述。
     *
     * @param value 属性值
     */
    public void setRequestServiceRemark(String value) {
        this.requestServiceRemark = value;
    }

    private Long requestServiceDate = 0L;

    /**
     * 获取 要求服务时间。
     */
    public Long getRequestServiceDate() {
        return this.requestServiceDate;
    }

    /**
     * 设置 要求服务时间。
     *
     * @param value 属性值
     */
    public void setRequestServiceDate(Long value) {
        this.requestServiceDate = value;
    }

    private Integer offlineFlag;

    /**
     * 获取 是否线下退款。
     */
    public Integer getOfflineFlag() {
        return this.offlineFlag;
    }

    /**
     * 设置 是否线下退款。
     *
     * @param value 属性值
     */
    public void setOfflineFlag(Integer value) {
        this.offlineFlag = value;
    }

    private String offlineReason;

    /**
     * 获取 要求线下退款原因。
     */
    public String getOfflineReason() {
        return this.offlineReason;
    }

    /**
     * 设置 要求线下退款原因。
     *
     * @param value 属性值
     */
    public void setOfflineReason(String value) {
        this.offlineReason = value;
    }

    private BigDecimal offlineAmount;

    /**
     * 获取 退款金额。
     */
    public BigDecimal getOfflineAmount() {
        return this.offlineAmount;
    }

    /**
     * 设置 退款金额。
     *
     * @param value 属性值
     */
    public void setOfflineAmount(BigDecimal value) {
        this.offlineAmount = value;
    }

    private String offlineRemark;

    /**
     * 获取 退款金额备注。
     */
    public String getOfflineRemark() {
        return this.offlineRemark;
    }

    /**
     * 设置 退款金额备注。
     *
     * @param value 属性值
     */
    public void setOfflineRemark(String value) {
        this.offlineRemark = value;
    }

    private Boolean offlineConfirmFlag = false;

    /**
     * 获取 线下退款确认标志。
     */
    public Boolean getOfflineConfirmFlag() {
        return this.offlineConfirmFlag;
    }

    /**
     * 设置 线下退款确认标志。
     *
     * @param value 属性值
     */
    public void setOfflineConfirmFlag(Boolean value) {
        this.offlineConfirmFlag = value;
    }

    //添加的字段
    private BigDecimal refundAmount;

    /**
     * 获取 退款金额。
     */
    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    /**
     * 设置 退款金额。
     *
     * @param value 属性值
     */
    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    private Integer typeFlag;

    /**
     * 获取 退货单类型（普通网单、基地库网单、延保卡标识）。
     */
    public Integer getTypeFlag() {
        return typeFlag;
    }

    /**
     * 设置 退货单类型（普通网单、基地库网单、延保卡标识）。
     *
     * @param value 属性值
     */
    public void setTypeFlag(Integer typeFlag) {
        this.typeFlag = typeFlag;
    }

    private Integer hpFirstAddTime;

    /**
     * 获取 HP一次质检推送时间。
     */
    public Integer getHpFirstAddTime() {
        return hpFirstAddTime;
    }

    /**
     * 设置 HP一次质检推送时间。
     *
     * @param value 属性值
     */
    public void setHpFirstAddTime(Integer hpFirstAddTime) {
        this.hpFirstAddTime = hpFirstAddTime;
    }

    private Integer hpSecondAddTime;

    /**
     * 获取 HP二次质检推送时间。
     */
    public Integer getHpSecondAddTime() {
        return hpSecondAddTime;
    }

    /**
     * 设置 HP二次质检推送时间。
     *
     * @param value 属性值
     */
    public void setHpSecondAddTime(Integer hpSecondAddTime) {
        this.hpSecondAddTime = hpSecondAddTime;
    }

    private Long handleTime;

    /**
     * 获取 获取审核时间。
     */
    public Long getHandleTime() {
        return handleTime;
    }

    /**
     * 设置 审核时间。
     *
     * @param value 属性值
     */
    public void setHandleTime(Long handleTime) {
        this.handleTime = handleTime;
    }

    private Date modified;

    /**
     * 获取 最后更新时间。
     */
    public Date getModified() {
        return modified;
    }

    /**
     * 设置 最后更新时间。
     *
     * @param value 属性值
     */
    public void setModified(Date modified) {
        this.modified = modified;
    }

    private Long finishTime;

    /**
     * 获取 完成时间。
     */
    public Long getFinishTime() {
        return finishTime;
    }

    /**
     * 设置 完成时间。
     *
     * @param value 属性值
     */
    public void setFinishTime(Long finishTime) {
        this.finishTime = finishTime;
    }

    private Integer ybkExchangeType;

    /**
     * 获取 延保卡退卡类型。
     */
    public Integer getYbkExchangeType() {
        return ybkExchangeType;
    }

    /**
     * 设置 延保卡退卡类型。
     */
    public void setYbkExchangeType(Integer ybkExchangeType) {
        this.ybkExchangeType = ybkExchangeType;
    }

    private Integer cOrderSnStatus;

    /**
     * 获取 产生逆向单时正向单状态
     */
    public Integer getCOrderSnStatus() {
        return cOrderSnStatus;
    }

    public void setCOrderSnStatus(Integer cOrderSnStatus) {
        this.cOrderSnStatus = cOrderSnStatus;
    }

    private String vomRepairSn;

    /**
     * 获取 3Wvom退货单号
     * @return
     */
    public String getVomRepairSn() {
        return vomRepairSn;
    }

    /**
     * 设置 3Wvom退货单号
     * @param vomRepairSn
     */
    public void setVomRepairSn(String vomRepairSn) {
        this.vomRepairSn = vomRepairSn;
    }

}