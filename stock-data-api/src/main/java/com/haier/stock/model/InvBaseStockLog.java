package com.haier.stock.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 库存日志。
 *
 * <p>Table: <strong>inv_base_stock_log</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>varchar</td><td>物料编码</td></tr>
 *   <tr><td>lesSecCode</td><td>{@link String}</td><td>les_sec_code</td><td>varchar</td><td>库位编码</td></tr>
 *   <tr><td>secCode</td><td>{@link String}</td><td>sec_code</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>oldStockQty</td><td>{@link Integer}</td><td>old_stock_qty</td><td>int</td><td>旧库存</td></tr>
 *   <tr><td>oldFrozenQty</td><td>{@link Integer}</td><td>old_frozen_qty</td><td>int</td><td>旧冻结库存</td></tr>
 *   <tr><td>newStockQty</td><td>{@link Integer}</td><td>new_stock_qty</td><td>int</td><td>新库存</td></tr>
 *   <tr><td>newFrozenQty</td><td>{@link Integer}</td><td>new_frozen_qty</td><td>int</td><td>新冻结库存</td></tr>
 *   <tr><td>billType</td><td>{@link String}</td><td>bill_type</td><td>varchar</td><td>交易类型</td></tr>
 *   <tr><td>refNo</td><td>{@link String}</td><td>ref_no</td><td>varchar</td><td>关联编号</td></tr>
 *   <tr><td>mark</td><td>{@link String}</td><td>mark</td><td>char</td><td>借贷标志：H-出 S-入</td></tr>
 *   <tr><td>createTime</td><td>{@link Date}</td><td>create_time</td><td>datetime</td><td>创建时间</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-11-18
 * @email yudi@sina.com
 */
public class InvBaseStockLog implements Serializable {
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

    private String sku;

    /**
     * 获取 物料编码。
     */
    public String getSku() {
        return this.sku;
    }

    /**
     * 设置 物料编码。
     *
     * @param value 属性值
     */
    public void setSku(String value) {
        this.sku = value;
    }

    private String lesSecCode;

    /**
     * 获取 库位编码。
     */
    public String getLesSecCode() {
        return this.lesSecCode;
    }

    /**
     * 设置 库位编码。
     *
     * @param value 属性值
     */
    public void setLesSecCode(String value) {
        this.lesSecCode = value;
    }

    private String secCode;

    public String getSecCode() {
        return this.secCode;
    }

    public void setSecCode(String value) {
        this.secCode = value;
    }

    private Integer oldStockQty;

    /**
     * 获取 旧库存。
     */
    public Integer getOldStockQty() {
        return this.oldStockQty;
    }

    /**
     * 设置 旧库存。
     *
     * @param value 属性值
     */
    public void setOldStockQty(Integer value) {
        this.oldStockQty = value;
    }

    private Integer oldFrozenQty;

    /**
     * 获取 旧冻结库存。
     */
    public Integer getOldFrozenQty() {
        return this.oldFrozenQty;
    }

    /**
     * 设置 旧冻结库存。
     *
     * @param value 属性值
     */
    public void setOldFrozenQty(Integer value) {
        this.oldFrozenQty = value;
    }

    private Integer newStockQty;

    /**
     * 获取 新库存。
     */
    public Integer getNewStockQty() {
        return this.newStockQty;
    }

    /**
     * 设置 新库存。
     *
     * @param value 属性值
     */
    public void setNewStockQty(Integer value) {
        this.newStockQty = value;
    }

    private Integer newFrozenQty;

    /**
     * 获取 新冻结库存。
     */
    public Integer getNewFrozenQty() {
        return this.newFrozenQty;
    }

    /**
     * 设置 新冻结库存。
     *
     * @param value 属性值
     */
    public void setNewFrozenQty(Integer value) {
        this.newFrozenQty = value;
    }

    private String billType;

    /**
     * 获取 交易类型。
     */
    public String getBillType() {
        return this.billType;
    }

    /**
     * 设置 交易类型。
     *
     * @param value 属性值
     */
    public void setBillType(String value) {
        this.billType = value;
    }

    private String refNo;

    /**
     * 获取 关联编号。
     */
    public String getRefNo() {
        return this.refNo;
    }

    /**
     * 设置 关联编号。
     *
     * @param value 属性值
     */
    public void setRefNo(String value) {
        this.refNo = value;
    }

    private String mark;

    /**
     * 获取 借贷标志：H-出 S-入。
     */
    public String getMark() {
        return this.mark;
    }

    /**
     * 设置 借贷标志：H-出 S-入。
     *
     * @param value 属性值
     */
    public void setMark(String value) {
        this.mark = value;
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

    private String secName;

    /**
     * 获取 库位名称。
     */
    public String getSecName() {
        return secName;
    }

    /**
     * 设置 库位名称。
     *
     * @param value 属性值
     */
    public void setSecName(String secName) {
        this.secName = secName;
    }

    private String productName;

    /**
     * 获取 产品型号。
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 设置 产品型号。
     *
     * @param value 属性值
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    private String startCreateTime;
    private String endCreateTime;
    private Date startCreateDate;
    private Date endCreateDate;
    private String content;
    @SuppressWarnings("serial")
    public static Map<String, String> billTypeName     = new HashMap<String, String>() {
        {
            put("ZBCC", "销售出库");
            put("YTIB", "存性变更出库");
            put("ZGI6", "调拨出库");
            put("BRSI", "转运出库");
            put("ZBCR", "采购入库 ");
            put("YTRB", "存性变更入库");
            put("ZBCT", "退货入库");
            put("ZBCJ", "拒收入库");
            put("ZGR6", "调拨入库");
            put("ZRSR", "转运入库");
            put("DBFF", "调拨冻结");
            put("XSFF", "销售冻结");
            put("DBRR", "取消调拨释放");
            put("XSRR", "取消销售释放");
        }

    };
    /**
     * 日志内容拼接
     */
    public String getContent() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("借贷标志：" + this.mark);
        buffer.append(",");
        buffer.append("交易类型：" + billTypeName.get(this.billType));
        buffer.append(",");
        buffer.append("原实际库存为" + this.oldStockQty);
        buffer.append(",");
        buffer.append("原占有库存为" + this.oldFrozenQty);
        buffer.append(",");
        buffer.append("原有可用库存为" + (this.oldStockQty - this.oldFrozenQty));
        buffer.append(",");
        buffer.append("更新后的实际库存为" + this.newStockQty);
        buffer.append(",");
        buffer.append("更新后的占有库存" + this.newFrozenQty);
        buffer.append(",");
        buffer.append("更新后的可用库存为" + (this.newStockQty - this.newFrozenQty));
        if (content != null) {
            return content;
        } else {
            return buffer.toString();
        }
    }
    private String logSku;

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartCreateTime() {
        return startCreateTime;
    }

    public void setStartCreateTime(String startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public String getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(String endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public Date getStartCreateDate() {
        return startCreateDate;
    }

    public void setStartCreateDate(Date startCreateDate) {
        this.startCreateDate = startCreateDate;
    }

    public Date getEndCreateDate() {
        return endCreateDate;
    }

    public void setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    public String getLogSku() {
        return logSku;
    }

    public void setLogSku(String logSku) {
        this.logSku = logSku;
    }
}