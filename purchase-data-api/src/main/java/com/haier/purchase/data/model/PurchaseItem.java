package com.haier.purchase.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>Table: <strong>purchase_item</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>poItemId</td><td>{@link Integer}</td><td>po_item_id</td><td>int</td><td>订单明细ID</td></tr>
 *   <tr><td>poItemNo</td><td>{@link String}</td><td>po_item_no</td><td>varchar</td><td>采购网单号（规则：WD开头，商城分配16位内）</td></tr>
 *   <tr><td>sourceSn</td><td>{@link String}</td><td>source_sn</td><td>varchar</td><td>订单来源号(事业部单号)</td></tr>
 *   <tr><td>poId</td><td>{@link Integer}</td><td>po_id</td><td>int</td><td>自增ID</td></tr>
 *   <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>varchar</td><td>产品物料编码</td></tr>
 *   <tr><td>brand</td><td>{@link String}</td><td>brand</td><td>varchar</td><td>产品品牌</td></tr>
 *   <tr><td>poQty</td><td>{@link Integer}</td><td>po_qty</td><td>int</td><td>产品采购数量</td></tr>
 *   <tr><td>inputQty</td><td>{@link Integer}</td><td>input_qty</td><td>int</td><td>入库数量</td></tr>
 *   <tr><td>price</td><td>{@link BigDecimal}</td><td>price</td><td>decimal</td><td>产品单价</td></tr>
 *   <tr><td>poItemAmount</td><td>{@link BigDecimal}</td><td>po_item_amount</td><td>decimal</td><td>网单总价(采购数量*产品单价)</td></tr>
 *   <tr><td>unit</td><td>{@link String}</td><td>unit</td><td>varchar</td><td>基本单位</td></tr>
 *   <tr><td>secCode</td><td>{@link String}</td><td>sec_code</td><td>varchar</td><td>产品库位</td></tr>
 *   <tr><td>itemType</td><td>{@link String}</td><td>item_tyoe</td><td>varchar</td><td>产品类型，又称批次（10），10为良品</td></tr>
 *   <tr><td>sign</td><td>{@link String}</td><td>sign</td><td>varchar</td><td>特殊标记（传1表示：自提，无需物流提货）</td></tr>
 *   <tr><td>collect</td><td>{@link BigDecimal}</td><td>collect</td><td>decimal</td><td>待收欠款（2位小数，货到付款订单时必输，其他订单可赋空值）</td></tr>
 *   <tr><td>payStatus</td><td>{@link String}</td><td>pay_status</td><td>varchar</td><td>付款状态（P1：已付款、P2：货到付款）</td></tr>
 *   <tr><td>status</td><td>{@link Integer}</td><td>status</td><td>smallint</td><td>采购网单状态，10：新建，20：入库中，100：完成<br /></td></tr>
 *   <tr><td>inputTime</td><td>{@link Date}</td><td>input_time</td><td>datetime</td><td>入库时间</td></tr>
 *   <tr><td>poTime</td><td>{@link Date}</td><td>po_time</td><td>datetime</td><td>订单时间</td></tr>
 *   <tr><td>createTime</td><td>{@link Date}</td><td>create_time</td><td>datetime</td><td>创建时间</td></tr>
 *   <tr><td>updateTime</td><td>{@link Date}</td><td>update_time</td><td>datetime</td><td>更新时间</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-6-8
 * @email yudi@sina.com
 */
public class PurchaseItem implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -5547952970375222614L;
    private Integer           poItemId;

    /**
     * 获取 订单明细ID。
     */
    public Integer getPoItemId() {
        return this.poItemId;
    }

    /**
     * 设置 订单明细ID。
     *
     * @param value 属性值
     */
    public void setPoItemId(Integer value) {
        this.poItemId = value;
    }

    private String poItemNo;

    /**
     * 获取 采购网单号（规则：WD开头，商城分配16位内）。
     */
    public String getPoItemNo() {
        return this.poItemNo;
    }

    /**
     * 设置 采购网单号（规则：WD开头，商城分配16位内）。
     *
     * @param value 属性值
     */
    public void setPoItemNo(String value) {
        this.poItemNo = value;
    }

    private String sourceSn;

    /**
     * 获取 订单来源号(事业部单号)。
     */
    public String getSourceSn() {
        return this.sourceSn;
    }

    /**
     * 设置 订单来源号(事业部单号)。
     *
     * @param value 属性值
     */
    public void setSourceSn(String value) {
        this.sourceSn = value;
    }

    private Integer poId;

    /**
     * 获取 自增ID。
     */
    public Integer getPoId() {
        return this.poId;
    }

    /**
     * 设置 自增ID。
     *
     * @param value 属性值
     */
    public void setPoId(Integer value) {
        this.poId = value;
    }

    private String sku;

    /**
     * 获取 产品物料编码。
     */
    public String getSku() {
        return this.sku;
    }

    /**
     * 设置 产品物料编码。
     *
     * @param value 属性值
     */
    public void setSku(String value) {
        this.sku = value;
    }

    private String productName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    private String brand;

    /**
     * 获取 产品品牌。
     */
    public String getBrand() {
        return this.brand;
    }

    /**
     * 设置 产品品牌。
     *
     * @param value 属性值
     */
    public void setBrand(String value) {
        this.brand = value;
    }

    private Integer poQty;

    /**
     * 获取 产品采购数量。
     */
    public Integer getPoQty() {
        return this.poQty;
    }

    /**
     * 设置 产品采购数量。
     *
     * @param value 属性值
     */
    public void setPoQty(Integer value) {
        this.poQty = value;
    }

    private Integer inputQty;

    /**
     * 获取 入库数量。
     */
    public Integer getInputQty() {
        return this.inputQty;
    }

    /**
     * 设置 入库数量。
     *
     * @param value 属性值
     */
    public void setInputQty(Integer value) {
        this.inputQty = value;
    }

    private BigDecimal price;

    /**
     * 获取 产品单价。
     */
    public BigDecimal getPrice() {
        return this.price;
    }

    /**
     * 设置 产品单价。
     *
     * @param value 属性值
     */
    public void setPrice(BigDecimal value) {
        this.price = value;
    }

    private BigDecimal poItemAmount;

    public BigDecimal getPoItemAmount() {
        return poItemAmount;
    }

    public void setPoItemAmount(BigDecimal poItemAmount) {
        this.poItemAmount = poItemAmount;
    }

    private String unit;

    /**
     * 获取 基本单位。
     */
    public String getUnit() {
        return this.unit;
    }

    /**
     * 设置 基本单位。
     *
     * @param value 属性值
     */
    public void setUnit(String value) {
        this.unit = value;
    }

    private String secCode;

    /**
     * 获取 产品库位。
     */
    public String getSecCode() {
        return this.secCode;
    }

    /**
     * 设置 产品库位。
     *
     * @param value 属性值
     */
    public void setSecCode(String value) {
        this.secCode = value;
    }

    private String itemType;

    /**
     * 获取 产品类型，又称批次（10），10为良品。
     */
    public String getItemType() {
        return this.itemType;
    }

    /**
     * 设置 产品类型，又称批次（10），10为良品。
     *
     * @param value 属性值
     */
    public void setItemType(String value) {
        this.itemType = value;
    }

    private String sign;

    /**
     * 获取 特殊标记（传1表示：自提，无需物流提货）。
     */
    public String getSign() {
        return this.sign;
    }

    /**
     * 设置 特殊标记（传1表示：自提，无需物流提货）。
     *
     * @param value 属性值
     */
    public void setSign(String value) {
        this.sign = value;
    }

    private BigDecimal collect;

    /**
     * 获取 待收欠款（2位小数，货到付款订单时必输，其他订单可赋空值）。
     */
    public BigDecimal getCollect() {
        return this.collect;
    }

    /**
     * 设置 待收欠款（2位小数，货到付款订单时必输，其他订单可赋空值）。
     *
     * @param value 属性值
     */
    public void setCollect(BigDecimal value) {
        this.collect = value;
    }

    private String payStatus;

    /**
     * 获取 付款状态（P1：已付款、P2：货到付款）。
     */
    public String getPayStatus() {
        return this.payStatus;
    }

    /**
     * 设置 付款状态（P1：已付款、P2：货到付款）。
     *
     * @param value 属性值
     */
    public void setPayStatus(String value) {
        this.payStatus = value;
    }

    private Integer status;

    /**
     * 获取 采购网单状态，10：新建，20：入库中，100：完成。
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置 采购网单状态，10：新建，20：入库中，100：完成。
     *
     * @param value 属性值
     */
    public void setStatus(Integer value) {
        this.status = value;
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

    private Date updateTime;

    /**
     * 获取 更新时间。
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置 更新时间。
     *
     * @param value 属性值
     */
    public void setUpdateTime(Date value) {
        this.updateTime = value;
    }

    private Date poTime;

    /**
     * 获取 订单时间
     */
    public Date getPoTime() {
        return poTime;
    }

    /**
     * 设置 订单时间
     */
    public void setPoTime(Date poTime) {
        this.poTime = poTime;
    }

    private Date inputTime;

    public Date getInputTime() {
        return inputTime;
    }

    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }

    /**
     * 型号编码
     */
    private String productType;

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    /**
     * 型号
     */
    private String productTypeName;

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    /**
     * 日日顺库位
     */
    private String rrsSecCode;

    public String getRrsSecCode() {
        return rrsSecCode;
    }

    public void setRrsSecCode(String rrsSecCode) {
        this.rrsSecCode = rrsSecCode;
    }

    /**
     * 机器编码
     */
    private String machineCode;

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    /**
     * 系列
     */
    private String series;

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    /**
     * 最晚到货时间
     */
    private Date latestRevDate;

    public Date getLatestRevDate() {
        return latestRevDate;
    }

    public void setLatestRevDate(Date latestRevDate) {
        this.latestRevDate = latestRevDate;
    }

    /**
     * 计划发货日期
     */
    private Date planSendDate;

    public Date getPlanSendDate() {
        return planSendDate;
    }

    public void setPlanSendDate(Date planSendDate) {
        this.planSendDate = planSendDate;
    }

    /**
     * 承诺到货日期
     */
    private Date planCustRevDate;

    public Date getPlanCustRevDate() {
        return planCustRevDate;
    }

    public void setPlanCustRevDate(Date planCustRevDate) {
        this.planCustRevDate = planCustRevDate;
    }

    /**
     * 实际发货日期
     */
    private Date custSendDate;

    public Date getCustSendDate() {
        return custSendDate;
    }

    public void setCustSendDate(Date custSendDate) {
        this.custSendDate = custSendDate;
    }

    /**
     * 预计到货日期
     */
    private Date expectReceiveDate;

    public Date getExpectReceiveDate() {
        return expectReceiveDate;
    }

    public void setExpectReceiveDate(Date expectReceiveDate) {
        this.expectReceiveDate = expectReceiveDate;
    }

    /**
     * 工贸到货日期
     */
    private Date transitArrivalDate;

    public Date getTransitArrivalDate() {
        return transitArrivalDate;
    }

    public void setTransitArrivalDate(Date transitArrivalDate) {
        this.transitArrivalDate = transitArrivalDate;
    }

    /**
     * 工贸发货日期
     */
    private Date tradeSendDate;

    public Date getTradeSendDate() {
        return tradeSendDate;
    }

    public void setTradeSendDate(Date tradeSendDate) {
        this.tradeSendDate = tradeSendDate;
    }

    /**
     * 工贸签收日期
     */
    private Date signDate;

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    /**
     * 返单日期
     */
    private Date podDate;

    public Date getPodDate() {
        return podDate;
    }

    public void setPodDate(Date podDate) {
        this.podDate = podDate;
    }

    /**
     * 运单号
     */
    private String transitCode;

    public String getTransitCode() {
        return transitCode;
    }

    public void setTransitCode(String transitCode) {
        this.transitCode = transitCode;
    }

    /**
     * 生产工厂名称
     */
    private String madeFactoryName;

    public String getMadeFactoryName() {
        return madeFactoryName;
    }

    public void setMadeFactoryName(String madeFactoryName) {
        this.madeFactoryName = madeFactoryName;
    }

    /**
     * 生产工厂编码
     */
    private String madeFactoryCode;

    public String getMadeFactoryCode() {
        return madeFactoryCode;
    }

    public void setMadeFactoryCode(String madeFactoryCode) {
        this.madeFactoryCode = madeFactoryCode;
    }

    /**
     * 签收数量
     */
    private Integer custRevQty;

    public Integer getCustRevQty() {
        return custRevQty;
    }

    public void setCustRevQty(Integer custRevQty) {
        this.custRevQty = custRevQty;
    }

    /**
     * 不通过原因
     */
    private String abortReason;

    public String getAbortReason() {
        return abortReason;
    }

    public void setAbortReason(String abortReason) {
        this.abortReason = abortReason;
    }

    /**
     * GVS订单号
     */
    private String gvsOrderCode;

    public String getGvsOrderCode() {
        return gvsOrderCode;
    }

    public void setGvsOrderCode(String gvsOrderCode) {
        this.gvsOrderCode = gvsOrderCode;
    }

    /**
     * DN号
     */
    private String gvsDN;

    public String getGvsDN() {
        return gvsDN;
    }

    public void setGvsDN(String gvsDN) {
        this.gvsDN = gvsDN;
    }

    /**
     * 提交日期
     */
    private Date submitDate;

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    /**
     * 入WA库开提单时间
     */
    private Date createOrderToLessDate;

    public Date getCreateOrderToLessDate() {
        return createOrderToLessDate;
    }

    public void setCreateOrderToLessDate(Date createOrderToLessDate) {
        this.createOrderToLessDate = createOrderToLessDate;
    }

    /**
     * LES提单号
     */
    private String lesPing;

    public String getLesPing() {
        return lesPing;
    }

    public void setLesPing(String lesPing) {
        this.lesPing = lesPing;
    }

    /**
     * 到货年
     */
    private String orderRevYear;

    public String getOrderRevYear() {
        return orderRevYear;
    }

    public void setOrderRevYear(String orderRevYear) {
        this.orderRevYear = orderRevYear;
    }

    /**
     * 到货周
     */
    private String orderRevWeekNumber;

    public String getOrderRevWeekNumber() {
        return orderRevWeekNumber;
    }

    public void setOrderRevWeekNumber(String orderRevWeekNumber) {
        this.orderRevWeekNumber = orderRevWeekNumber;
    }

    /**
     * 客户订单号
     */
    private String custPoDetailCode;

    public String getCustPoDetailCode() {
        return custPoDetailCode;
    }

    public void setCustPoDetailCode(String custPoDetailCode) {
        this.custPoDetailCode = custPoDetailCode;
    }

    /**
     * 评审日期
     */
    private Date reviewDate;

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    /**
     * 生产日期/产品下线日期
     */
    private Date prodDate;

    /**
     * 获取生产日期/产品下线日期
     * @return 生产日期
     */
    public Date getProdDate() {
        return prodDate;
    }

    /**
     * 设置：生产日期/产品下线日期
     * @param prodDate 生产日期
     */
    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    @Override
    public String toString() {
        return "PurchaseItem [poItemId=" + poItemId + ", poItemNo=" + poItemNo + ", sourceSn="
               + sourceSn + ", poId=" + poId + ", sku=" + sku + ", brand=" + brand + ", poQty="
               + poQty + ", inputQty=" + inputQty + ", price=" + price + ", poItemAmount="
               + poItemAmount + ", unit=" + unit + ", secCode=" + secCode + ", itemType="
               + itemType + ", sign=" + sign + ", collect=" + collect + ", payStatus=" + payStatus
               + ", status=" + status + ", createTime=" + createTime + ", updateTime=" + updateTime
               + ", poTime=" + poTime + ", inputTime=" + inputTime + "]";
    }

}