package com.haier.stock.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.haier.common.BusinessException;



/**
 * 出入库记录。
 *
 * <p>Table: <strong>inv_stock_in_out</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>type</td><td>{@link String}</td><td>type</td><td>varchar</td><td>类型<br />ZBCC  销售出库订单<br />ZBCR  采购入库订单<br />ZBCT  退货入库订单<br />ZBCJ  拒收入库订单<br />ZGI6  调拨出库订单<br />ZGR6  调拨入库订单</td></tr>
 *   <tr><td>itemId</td><td>{@link Integer}</td><td>item_id</td><td>int</td><td>商品id</td></tr>
 *   <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>varchar</td><td>物料编号</td></tr>
 *   <tr><td>secCode</td><td>{@link String}</td><td>sec_code</td><td>varchar</td><td>库位编码。</td></tr>
 *   <tr><td>channelCode</td><td>{@link String}</td><td>channel_code</td><td>varchar</td><td>渠道编号</td></tr>
 *   <tr><td>quantity</td><td>{@link Integer}</td><td>quantity</td><td>int</td><td>数量</td></tr>
 *   <tr><td>createTime</td><td>{@link Date}</td><td>create_time</td><td>datetime</td><td>创建时间</td></tr>
 *   <tr><td>billTime</td><td>{@link Date}</td><td>bill_time</td><td>datetime</td><td>交易时间（变更发生时间）</td></tr>
 *   <tr><td>billNo</td><td>{@link String}</td><td>bill_no</td><td>varchar</td><td>交易单据号</td></tr>
 *   <tr><td>cbsBillNo</td><td>{@link String}</td><td>cbs_bill_no</td><td>varchar</td><td>交易单据号</td></tr>
 *   <tr><td>note</td><td>{@link String}</td><td>note</td><td>nvarchar</td><td>备注</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-6-8
 * @email yudi@sina.com
 */
public class InvStockInOut implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 7073286121447137723L;
    private Integer           id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    public static boolean isIn(String type) {
        if (!isType(type))
            throw new BusinessException("未知的类型:" + type);
        return IN_PURCHASE_TYPE.equalsIgnoreCase(type) || IN_TRANSFER_TYPE.equalsIgnoreCase(type)
               || IN_RETURNED_TYPE.equalsIgnoreCase(type) || IN_REFUSE_TYPE.equalsIgnoreCase(type);
    }

    public static boolean isOut(String type) {
        return !isIn(type);
    }

    public static boolean isType(String type) {
        return IN_PURCHASE_TYPE.equalsIgnoreCase(type) || IN_TRANSFER_TYPE.equalsIgnoreCase(type)
               || IN_RETURNED_TYPE.equalsIgnoreCase(type) || IN_REFUSE_TYPE.equalsIgnoreCase(type)
               || OUT_SALE_TYPE.equalsIgnoreCase(type) || OUT_TRANSFER_TYPE.equalsIgnoreCase(type);
    }

    private String             type;

    public static final String OUT_SALE_TYPE     = "ZBCC";
    public static final String OUT_TRANSFER_TYPE = "ZGI6";
    public static final String IN_PURCHASE_TYPE  = "ZBCR";
    public static final String IN_RETURNED_TYPE  = "ZBCT";
    public static final String IN_TRANSFER_TYPE  = "ZGR6";
    public static final String IN_REFUSE_TYPE    = "ZBCJ";

    /**
     * 获取 类型。
     *
     * <p>
     * ZBCC  销售出库订单<br />
     * ZBCR  采购入库订单<br />
     * ZBCT  退货入库订单<br />
     * ZBCJ  拒收入库订单<br />
     * ZGI6  调拨出库订单<br />
     * ZGR6  调拨入库订单
     */
    public String getType() {
        return this.type;
    }

    /**
     * 设置 类型。
     *
     * <p>
     * ZBCC  销售出库订单<br />
     * ZBCR  采购入库订单<br />
     * ZBCT  退货入库订单<br />
     * ZBCJ  拒收入库订单<br />
     * ZGI6  调拨出库订单<br />
     * ZGR6  调拨入库订单
     *
     * @param value 属性值
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * 基础计算，增加库存和扣减库存
     */
    public static final int AGE_TYPE_SAMPLE     = 0;
    /**
     * 库龄平移，从A渠道平移到B渠道
     */
    public static final int AGE_TYPE_PAN_OF_AGE = 1;
    /**
     * 增加共享数量
     */
    public static final int AGE_TYPE_ADD_WA_QTY = 2;

    /**
     * 计算类型：0-普通 1-库存平移 2-共享占用 3-共享释放
     */
    private Integer         ageType;

    public Integer getAgeType() {
        return ageType;
    }

    public void setAgeType(Integer ageType) {
        this.ageType = ageType;
    }

    public static final int AGE_STATUS_WAIT = 0;
    public static final int AGE_STATUS_DOWN = 1;

    private Integer         ageStatus;

    public Integer getAgeStatus() {
        return ageStatus;
    }

    public void setAgeStatus(Integer ageStatus) {
        this.ageStatus = ageStatus;
    }

    /**
     * 借贷标志：S-入库 H-出库
     */
    private String mark;

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    private Integer itemId;

    /**
     * 获取 商品id。
     */
    public Integer getItemId() {
        return this.itemId;
    }

    /**
     * 设置 商品id。
     *
     * @param value 属性值
     */
    public void setItemId(Integer value) {
        this.itemId = value;
    }

    private String sku;

    /**
     * 获取 物料编号。
     */
    public String getSku() {
        return this.sku;
    }

    /**
     * 设置 物料编号。
     *
     * @param value 属性值
     */
    public void setSku(String value) {
        this.sku = value;
    }

    private String secCode;

    /**
     * 获取 库位编码。
     */
    public String getSecCode() {
        return this.secCode;
    }

    /**
     * 设置 库位编码。
     *
     * @param value 属性值
     */
    public void setSecCode(String value) {
        this.secCode = value;
    }

    private String virtualSecCode;

    public String getVirtualSecCode() {
        return virtualSecCode;
    }

    public void setVirtualSecCode(String virtualSecCode) {
        this.virtualSecCode = virtualSecCode;
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

    private Integer quantity;

    /**
     * 获取 数量。
     */
    public Integer getQuantity() {
        return this.quantity;
    }

    /**
     * 设置 数量。
     *
     * @param value 属性值
     */
    public void setQuantity(Integer value) {
        this.quantity = value;
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

    private Date billTime;

    /**
     * 获取 交易时间（变更发生时间）。
     */
    public Date getBillTime() {
        return this.billTime;
    }

    /**
     * 设置 交易时间（变更发生时间）。
     *
     * @param value 属性值
     */
    public void setBillTime(Date value) {
        this.billTime = value;
    }

    private String billNo;

    /**
     * 获取 交易单据号。
     */
    public String getBillNo() {
        return this.billNo;
    }

    /**
     * 设置 交易单据号。
     *
     * @param value 属性值
     */
    public void setBillNo(String value) {
        this.billNo = value;
    }

    /**
     * 网单号或其他
     */
    private String cbsBillNo;

    public String getCbsBillNo() {
        return cbsBillNo;
    }

    public void setCbsBillNo(String cbsBillNo) {
        this.cbsBillNo = cbsBillNo;
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