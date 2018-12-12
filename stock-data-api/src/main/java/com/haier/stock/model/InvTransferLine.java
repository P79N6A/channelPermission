package com.haier.stock.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 库存调拨明细（库存调拨网单）。
 *
 * <p>Table: <strong>inv_transfer_line</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>lineId</td><td>{@link Integer}</td><td>line_id</td><td>int</td><td>调拨单明细（网单）ID</td></tr>
 *   <tr><td>lineNum</td><td>{@link String}</td><td>line_num</td><td>varchar</td><td>调拨网单号码。<br />号码生成规则参考需求文档。</td></tr>
 *   <tr><td>transferReason</td><td>{@link Integer}</td><td>transfer_reason</td><td>int</td><td>调拨原因。<br />1: 库存平铺调货。<br /> &nbsp;&nbsp;&nbsp;该调货原因时调货网单号生成规则：20130721SHBJ001，前面8位为年月日，从上海库位调往北京库位，后面3位为递增序号。<br />2: 订单缺货调货。<br /> &nbsp;&nbsp;&nbsp;此时调货网单号生成规则：网单号+DH。</td></tr>
 *   <tr><td>soLineNum</td><td>{@link String}</td><td>so_line_num</td><td>varchar</td><td>销售网单号码。<br />调货原因为订单缺货调货时必须输入，其他情况无需输入该字段值。</td></tr>
 *   <tr><td>channelId</td><td>{@link String}</td><td>channel_id</td><td>char</td><td>渠道ID，表示调拨哪个渠道的货。<br />TB：淘宝；SC：商城；DK：大客户；</td></tr>
 *   <tr><td>reqDep</td><td>{@link String}</td><td>req_dep</td><td>varchar</td><td>Request Department，发起调货请求的部门。<br />SC: 商城；TB: 淘宝；DK: 企业大客户；WL: 物流；SH: 售后；</td></tr>
 *   <tr><td>lineStatus</td><td>{@link Integer}</td><td>line_status</td><td>int</td><td>调拨状态。<br />-1: 删除状态；<br />0: 初始状态；<br />10: 待录费用；<br />20: 待审核费用；<br />30: 待传LES；<br />40: 待出库；<br />50: 待入库；<br />100: 已完成；<br />110: 已取消；</td></tr>
 *   <tr><td>itemId</td><td>{@link Integer}</td><td>item_id</td><td>int</td><td>商品ID</td></tr>
 *   <tr><td>itemCode</td><td>{@link String}</td><td>item_code</td><td>varchar</td><td>物料编码。<br />冗余字段，避免关联商品表查询。</td></tr>
 *   <tr><td>itemName</td><td>{@link String}</td><td>item_name</td><td>varchar</td><td>物料名称。<br />冗余字段，避免关联商品表查询。</td></tr>
 *   <tr><td>secFrom</td><td>{@link String}</td><td>sec_from</td><td>varchar</td><td>调出库位。</td></tr>
 *   <tr><td>secTo</td><td>{@link String}</td><td>sec_to</td><td>varchar</td><td>调入库位。</td></tr>
 *   <tr><td>transferQty</td><td>{@link Integer}</td><td>transfer_qty</td><td>int</td><td>调货数量。</td></tr>
 *   <tr><td>transferFee</td><td>{@link BigDecimal}</td><td>transfer_fee</td><td>decimal</td><td>调拨费用。</td></tr>
 *   <tr><td>transferFeeUser</td><td>{@link String}</td><td>transfer_fee_user</td><td>varchar</td><td>录入费用的人</td></tr>
 *   <tr><td>transferFeeTime</td><td>{@link Date}</td><td>transfer_fee_time</td><td>datetime</td><td>录入费用的时间</td></tr>
 *   <tr><td>approveStatus</td><td>{@link Integer}</td><td>approve_status</td><td>int</td><td>调拨费用审核状态。<br />0: 初始状态；<br />10: 通过；<br />20: 拒绝；</td></tr>
 *   <tr><td>approveTime</td><td>{@link Date}</td><td>approve_time</td><td>datetime</td><td>审核费用的时间。</td></tr>
 *   <tr><td>approveUser</td><td>{@link String}</td><td>approve_user</td><td>varchar</td><td>审核费用的人。</td></tr>
 *   <tr><td>approveRemark</td><td>{@link String}</td><td>approve_remark</td><td>varchar</td><td>调拨费用审核备注说明。</td></tr>
 *   <tr><td>lesNum</td><td>{@link String}</td><td>les_num</td><td>varchar</td><td>LES提单号。</td></tr>
 *   <tr><td>createTime</td><td>{@link Date}</td><td>create_time</td><td>datetime</td><td>创建时间。</td></tr>
 *   <tr><td>createUser</td><td>{@link String}</td><td>create_user</td><td>varchar</td><td>创建者。</td></tr>
 *   <tr><td>remark</td><td>{@link String}</td><td>remark</td><td>varchar</td><td>备注。</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-7-22
 * @email yudi@sina.com
 */
public class InvTransferLine implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -264584636750741280L;

    /** 删除状态 */
    public static Integer LINE_STATUS_DEL       = -1;
    /** 初始状态 */
    public static Integer LINE_STATUS_INIT      = 0;
    /** 确认状态 Job会将该状态的网单传递给LES录入费用*/
    public static Integer LINE_STATUS_CONFIRM   = 5;
    /** 待录费用*/
    public static Integer LINE_STATUS_FEE_INPUT = 10;
    /** 待审核费用*/
    public static Integer LINE_STATUS_FEE_ADUIT = 20;
    /** 待传LES*/
    public static Integer LINE_STATUS_LES       = 30;
    /** 待出库*/
    public static Integer LINE_STATUS_STORE_OUT = 40;
    /** 待入库*/
    public static Integer LINE_STATUS_STORE_IN  = 50;
    /** 已完成*/
    public static Integer LINE_STATUS_COMPLETE  = 100;
    /** 已取消*/
    public static Integer LINE_STATUS_CANCEL    = 110;

    /** 3W确认状态*/
    public static Integer LINE_STATUS_3W_CONFIRM = 210;
    
    /** 3W成功推送到SAP*/
    public static Integer LINE_STATUS_3W_TO_SAP = 140;
    /** 3W取消状态*/
    public static Integer LINE_STATUS_3W_CANCEL  = 310;

    /** 调拨原因：平铺 */
    public static Integer TRANSFER_REASON_PP = 1;
    /** 调拨原因：缺货 */
    public static Integer TRANSFER_REASON_QH = 2;
    /** 调拨原因：虚拟 */
    public static Integer TRANSFER_REASON_XN = 3;
    /** 调拨原因：3W */
    public static Integer TRANSFER_REASON_3W = 4;

    /** 发起部门 SC */
    public static String REQ_DEP_SC = "SC";
    /** 发起部门 TB */
    public static String REQ_DEP_TB = "TB";
    /** 发起部门 DK */
    public static String REQ_DEP_DK = "DK";
    /** 发起部门 YY */
    public static String REQ_DEP_YY = "YY";
    /** 发起部门 CX */
    public static String REQ_DEP_CX = "CX";

    /** 渠道 SC */
    public static String CHANNEL_SC  = "SC";
    /** 渠道 TB */
    public static String CHANNEL_TB  = "TB";
    /** 渠道 DK */
    public static String CHANNEL_DKH = "DKH";

    /**
     * 京东渠道
     */
    public static String CHANNEL_JD = "JD";
    /**
     * 易迅渠道
     */
    public static String CHANNEL_YX = "YX";

    public static String CHANNEL_SHH = "SHH";

    /**调拨费用审核状态：初始状态*/
    public static Integer APPROVE_STATUS_INIT   = 0;
    /**调拨费用审核状态：通过*/
    public static Integer APPROVE_STATUS_OK     = 10;
    /**调拨费用审核状态：拒绝*/
    public static Integer APPROVE_STATUS_REJECT = 20;

    /**调拨费用审核操作：同意 */
    public static String TRANSFER_FEE_AUDIT_OPERATION_AGREE  = "agreeControl";
    /**调拨费用审核操作：驳回 */
    public static String TRANSFER_FEE_AUDIT_OPERATION_REJECT = "rejectControl";
    /**调拨费用审核操作：取消*/
    public static String TRANSFER_FEE_AUDIT_OPERATION_CANCEL = "cancelControl";

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

    private String lineNum = "";

    /**
     * 获取 调拨网单号码。
     *
     * <p>
     * 号码生成规则参考需求文档。
     */
    public String getLineNum() {
        return this.lineNum;
    }

    /**
     * 设置 调拨网单号码。
     *
     * <p>
     * 号码生成规则参考需求文档。
     *
     * @param value 属性值
     */
    public void setLineNum(String value) {
        this.lineNum = value;
    }

    private Integer transferReason = 0;

    /**
     * 获取 调拨原因。
     *
     * <p>
     * 1: 库存平铺调货。<br />
     *  &nbsp;&nbsp;&nbsp;该调货原因时调货网单号生成规则：20130721SHBJ001，前面8位为年月日，从上海库位调往北京库位，后面3位为递增序号。<br />
     * 2: 订单缺货调货。<br />
     *  &nbsp;&nbsp;&nbsp;此时调货网单号生成规则：网单号+DH。
     */
    public Integer getTransferReason() {
        return this.transferReason;
    }

    /**
     * 设置 调拨原因。
     *
     * <p>
     * 1: 库存平铺调货。<br />
     *  &nbsp;&nbsp;&nbsp;该调货原因时调货网单号生成规则：20130721SHBJ001，前面8位为年月日，从上海库位调往北京库位，后面3位为递增序号。<br />
     * 2: 订单缺货调货。<br />
     *  &nbsp;&nbsp;&nbsp;此时调货网单号生成规则：网单号+DH。
     *
     * @param value 属性值
     */
    public void setTransferReason(Integer value) {
        this.transferReason = value;
    }

    private String soLineNum = "";

    /**
     * 获取 销售网单号码。
     *
     * <p>
     * 调货原因为订单缺货调货时必须输入，其他情况无需输入该字段值。
     */
    public String getSoLineNum() {
        return this.soLineNum;
    }

    /**
     * 设置 销售网单号码。
     *
     * <p>
     * 调货原因为订单缺货调货时必须输入，其他情况无需输入该字段值。
     *
     * @param value 属性值
     */
    public void setSoLineNum(String value) {
        this.soLineNum = value;
    }

    private String channelId = "";

    /**
      * 获取 渠道ID，表示调拨哪个渠道的货。
      *
      * <p>
      * TB：天猫；SC：商城；DK：大客户；
      */

    public String getChannelId() {
        return this.channelId;
    }

    /**
     * 调出渠道
     */
    private String channelFrom;

    /**
     * 调出渠道
     * @return
     */
    public String getChannelFrom() {
        return channelFrom;
    }

    /**
     * 调出渠道
     * @param channelFrom
     */
    public void setChannelFrom(String channelFrom) {
        this.channelFrom = channelFrom;
    }

    /**
     * 调入渠道
     */
    private String channelTo;

    /**
     * 调入渠道
     * @return
     */
    public String getChannelTo() {
        return channelTo;
    }

    /**
     * 调入渠道
     * @param channelTo
     */
    public void setChannelTo(String channelTo) {
        this.channelTo = channelTo;
    }

    /*  *//**
                     * 设置 渠道ID，表示调拨哪个渠道的货。
                     *
                     * <p>
                     * TB：天猫；SC：商城；DK：大客户；
                     *
                     * @param value 属性值
                     */

    public void setChannelId(String value) {
        this.channelId = value;
    }

    private String reqDep = "";

    /**
     * 获取 Request Department，发起调货请求的部门。
     *
     * <p>
     * SC: 商城；TB: 天猫；DK: 企业大客户；WL: 物流；SH: 售后；
     */
    public String getReqDep() {
        return this.reqDep;
    }

    /**
     * 设置 Request Department，发起调货请求的部门。
     *
     * <p>
     * SC: 商城；TB: 天猫；DK: 企业大客户；WL: 物流；SH: 售后；
     *
     * @param value 属性值
     */
    public void setReqDep(String value) {
        this.reqDep = value;
    }

    private Integer lineStatus = 0;

    /**
     * 获取 调拨状态。
     *
     * <p>
     * -1: 删除状态；<br />
     * 0: 初始状态；<br />
     * 10: 待录费用；<br />
     * 20: 待审核费用；<br />
     * 30: 待传LES；<br />
     * 40: 待出库；<br />
     * 50: 待入库；<br />
     * 100: 已完成；<br />
     * 110: 已取消；
     */
    public Integer getLineStatus() {
        return this.lineStatus;
    }

    /**
     * 设置 调拨状态。
     *
     * <p>
     * -1: 删除状态；<br />
     * 0: 初始状态；<br />
     * 10: 待录费用；<br />
     * 20: 待审核费用；<br />
     * 30: 待传LES；<br />
     * 40: 待出库；<br />
     * 50: 待入库；<br />
     * 100: 已完成；<br />
     * 110: 已取消；
     *
     * @param value 属性值
     */
    public void setLineStatus(Integer value) {
        this.lineStatus = value;
    }

    private Integer itemId = 0;

    /**
     * 获取 商品ID。
     */
    public Integer getItemId() {
        return this.itemId;
    }

    /**
     * 设置 商品ID。
     *
     * @param value 属性值
     */
    public void setItemId(Integer value) {
        this.itemId = value;
    }

    private String itemCode = "";

    /**
     * 获取 物料编码。
     *
     * <p>
     * 冗余字段，避免关联商品表查询。
     */
    public String getItemCode() {
        return this.itemCode;
    }

    /**
     * 设置 物料编码。
     *
     * <p>
     * 冗余字段，避免关联商品表查询。
     *
     * @param value 属性值
     */
    public void setItemCode(String value) {
        this.itemCode = value;
    }

    private String itemName = "";

    /**
     * 获取 物料名称。
     *
     * <p>
     * 冗余字段，避免关联商品表查询。
     */
    public String getItemName() {
        return this.itemName;
    }

    /**
     * 设置 物料名称。
     *
     * <p>
     * 冗余字段，避免关联商品表查询。
     *
     * @param value 属性值
     */
    public void setItemName(String value) {
        this.itemName = value;
    }

    private String secFrom = "";

    /**
     * 获取 调出库位。
     */
    public String getSecFrom() {
        return this.secFrom;
    }

    /**
     * 设置 调出库位。
     *
     * @param value 属性值
     */
    public void setSecFrom(String value) {
        this.secFrom = value;
    }

    private String secTo = "";

    /**
     * 获取 调入库位。
     */
    public String getSecTo() {
        return this.secTo;
    }

    /**
     * 设置 调入库位。
     *
     * @param value 属性值
     */
    public void setSecTo(String value) {
        this.secTo = value;
    }

    private Integer transferQty = 0;

    /**
     * 获取 调货数量。
     */
    public Integer getTransferQty() {
        return this.transferQty;
    }

    /**
     * 设置 调货数量。
     *
     * @param value 属性值
     */
    public void setTransferQty(Integer value) {
        this.transferQty = value;
    }

    /**
     * 实际数
     */
    private Integer qty = 0;

    /**
     * 实际数
     * @return
     */
    public Integer getQty() {
        return qty;
    }

    /**
     * 实际数
     * @param qty
     */
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    private BigDecimal transferFee = BigDecimal.ZERO;

    /**
     * 获取 调拨费用。
     */
    public BigDecimal getTransferFee() {
        return this.transferFee;
    }

    /**
     * 设置 调拨费用。
     *
     * @param value 属性值
     */
    public void setTransferFee(BigDecimal value) {
        this.transferFee = value;
    }

    private String transferFeeUser;

    /**
     * 获取 录入费用的人。
     */
    public String getTransferFeeUser() {
        return this.transferFeeUser;
    }

    /**
     * 设置 录入费用的人。
     *
     * @param value 属性值
     */
    public void setTransferFeeUser(String value) {
        this.transferFeeUser = value;
    }

    private Date transferFeeTime = null;

    /**
     * 获取 录入费用的时间。
     */
    public Date getTransferFeeTime() {
        return this.transferFeeTime;
    }

    /**
     * 设置 录入费用的时间。
     *
     * @param value 属性值
     */
    public void setTransferFeeTime(Date value) {
        this.transferFeeTime = value;
    }

    private Integer approveStatus = 0;

    /**
     * 获取 调拨费用审核状态。
     *
     * <p>
     * 0: 初始状态；<br />
     * 10: 通过；<br />
     * 20: 拒绝；
     */
    public Integer getApproveStatus() {
        return this.approveStatus;
    }

    /**
     * 设置 调拨费用审核状态。
     *
     * <p>
     * 0: 初始状态；<br />
     * 10: 通过；<br />
     * 20: 拒绝；
     *
     * @param value 属性值
     */
    public void setApproveStatus(Integer value) {
        this.approveStatus = value;
    }

    private Date approveTime = null;

    /**
     * 获取 审核费用的时间。
     */
    public Date getApproveTime() {
        return this.approveTime;
    }

    /**
     * 设置 审核费用的时间。
     *
     * @param value 属性值
     */
    public void setApproveTime(Date value) {
        this.approveTime = value;
    }

    private String approveUser = "";

    /**
     * 获取 审核费用的人。
     */
    public String getApproveUser() {
        return this.approveUser;
    }

    /**
     * 设置 审核费用的人。
     *
     * @param value 属性值
     */
    public void setApproveUser(String value) {
        this.approveUser = value;
    }

    private String approveRemark = "";

    /**
     * 获取 调拨费用审核备注说明。
     */
    public String getApproveRemark() {
        return this.approveRemark;
    }

    /**
     * 设置 调拨费用审核备注说明。
     *
     * @param value 属性值
     */
    public void setApproveRemark(String value) {
        this.approveRemark = value;
    }

    private String lesNum = "";

    /**
     * 获取 LES提单号。
     */
    public String getLesNum() {
        return this.lesNum;
    }

    /**
     * 设置 LES提单号。
     *
     * @param value 属性值
     */
    public void setLesNum(String value) {
        this.lesNum = value;
    }

    private Date createTime = null;

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

    private String createUser = "";

    /**
     * 获取 创建者。
     */
    public String getCreateUser() {
        return this.createUser;
    }

    /**
     * 设置 创建者。
     *
     * @param value 属性值
     */
    public void setCreateUser(String value) {
        this.createUser = value;
    }

    /**
     * 运输周期
     */
    private Integer haulCycle;

    public Integer getHaulCycle() {
        return haulCycle;
    }

    public void setHaulCycle(Integer haulCycle) {
        this.haulCycle = haulCycle;
    }

    private String remark = "";

    /**
     * 获取 备注。
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置 备注。
     *
     * @param value 属性值
     */
    public void setRemark(String value) {
        this.remark = value;
    }

    private String freeTransfer = "";

    /**
     * 获取 是否免费调货。
     */
    public String getFreeTransfer() {
        return this.freeTransfer;
    }

    /**
     * 设置 是否免费调货。
     *
     * @param value 属性值
     */
    public void setFreeTransfer(String value) {
        this.freeTransfer = value;
    }

}