package com.haier.eis.model;



import java.io.Serializable;
import java.util.Date;

/**
 * LES出库明细。
 *
 * <p>Table: <strong>les_stock_trans_queue</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>lesBatchId</td><td>{@link Integer}</td><td>les_batch_id</td><td>int</td><td>les同步批次</td></tr>
 *   <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>secCode</td><td>{@link String}</td><td>sec_code</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>corderSn</td><td>{@link String}</td><td>corder_sn</td><td>varchar</td><td>单据号</td></tr>
 *   <tr><td>outping</td><td>{@link String}</td><td>outping</td><td>varchar</td><td>les单据号</td></tr>
 *   <tr><td>billTime</td><td>{@link Date}</td><td>bill_time</td><td>datetime</td><td>交易时间</td></tr>
 *   <tr><td>billType</td><td>{@link String}</td><td>bill_type</td><td>varchar</td><td>交易类型 <br />     * ZBCC  销售出库订单<br />     * ZBCR  采购入库订单<br />     * ZBCT  退货入库订单<br />     * ZBCJ  拒收入库订单<br />     * ZGI6  调拨出库订单<br />     * ZGR6  调拨入库订单<br />     * ZRSR 转运入库<br />     * ZRSI  转运第二次出库<br /><br /></td></tr>
 *   <tr><td>quantity</td><td>{@link Integer}</td><td>quantity</td><td>int</td><td>交易数量</td></tr>
 *   <tr><td>zeile</td><td>{@link String}</td><td>zeile</td><td>varchar</td><td>les行号</td></tr>
 *   <tr><td>lesBillNo</td><td>{@link String}</td><td>les_bill_no</td><td>varchar</td><td>les物料凭证编号（MBLNR+ZEILE）<br /></td></tr>
 *   <tr><td>mark</td><td>{@link String}</td><td>mark</td><td>varchar</td><td>借贷标志（S入库、H出库）</td></tr>
 *   <tr><td>matkl</td><td>{@link String}</td><td>matkl</td><td>varchar</td><td>物料组（保留）</td></tr>
 *   <tr><td>kunnrSaleTo</td><td>{@link String}</td><td>kunnr_sale_to</td><td>varchar</td><td>LES物流中心编号</td></tr>
 *   <tr><td>kunnrSendTo</td><td>{@link String}</td><td>kunnr_send_to</td><td>varchar</td><td>送达方编码<br /></td></tr>
 *   <tr><td>tknum</td><td>{@link String}</td><td>tknum</td><td>varchar</td><td>装运编号</td></tr>
 *   <tr><td>bwart</td><td>{@link String}</td><td>bwart</td><td>varchar</td><td>移动类型（保留）</td></tr>
 *   <tr><td>charg</td><td>{@link String}</td><td>charg</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>reserve1</td><td>{@link String}</td><td>reserve1</td><td>varchar</td><td>预留1</td></tr>
 *   <tr><td>reserve2</td><td>{@link String}</td><td>reserve2</td><td>varchar</td><td>预留2</td></tr>
 *   <tr><td>bstkd</td><td>{@link String}</td><td>bstkd</td><td>varchar</td><td>预留3</td></tr>
 *   <tr><td>data</td><td>{@link String}</td><td>data</td><td>nvarchar</td><td>数据内容</td></tr>
 *   <tr><td>releaseStatus</td><td>{@link Integer}</td><td>release_status</td><td>int</td><td>释放库存状态<br />0：待处理<br />1：已处理</td></tr>
 *   <tr><td>status</td><td>{@link Integer}</td><td>status</td><td>int</td><td>处理状态<br />0：待处理<br />1：已处理</td><td>-1：错误</td></tr>
 *   <tr><td>processTime</td><td>{@link Date}</td><td>process_time</td><td>datetime</td><td>订单处理时间</td></tr>
 *   <tr><td>addTime</td><td>{@link Date}</td><td>add_time</td><td>datetime</td><td>创建时间</td></tr>
 *   <tr><td>errorMessage</td><td>{@link String}</td><td>error_message</td><td>nvarchar</td><td>&nbsp;</td></tr>
 *   <tr><td>versionCode</td><td>{@link String}</td><td>version_code</td><td>varchar</td><td>版本编号 - 作为唯一标识使用</td></tr>
 * </table>
 *
 * @author 吴坤洋
 * @date 2017-12-22
 * @email yudi@sina.com
 */
public class LesStockTransQueue implements Serializable {
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

    private Integer lesBatchId;

    /**
     * 获取 les同步批次。
     */
    public Integer getLesBatchId() {
        return this.lesBatchId;
    }

    /**
     * 设置 les同步批次。
     *
     * @param value 属性值
     */
    public void setLesBatchId(Integer value) {
        this.lesBatchId = value;
    }

    private String sku;

    public String getSku() {
        return this.sku;
    }

    public void setSku(String value) {
        this.sku = value;
    }

    private String secCode;

    public String getSecCode() {
        return this.secCode;
    }

    public void setSecCode(String value) {
        this.secCode = value;
    }

    private String corderSn;

    /**
     * 获取 单据号。
     */
    public String getCorderSn() {
        return this.corderSn;
    }

    /**
     * 设置 单据号。
     *
     * @param value 属性值
     */
    public void setCorderSn(String value) {
        this.corderSn = value;
    }

    private String outping;

    /**
     * 获取 les单据号。
     */
    public String getOutping() {
        return this.outping;
    }

    /**
     * 设置 les单据号。
     *
     * @param value 属性值
     */
    public void setOutping(String value) {
        this.outping = value;
    }

    private Date billTime;

    /**
     * 获取 交易时间。
     */
    public Date getBillTime() {
        return this.billTime;
    }

    /**
     * 设置 交易时间。
     *
     * @param value 属性值
     */
    public void setBillTime(Date value) {
        this.billTime = value;
    }

    @Deprecated
    public static final String OUT_SALE_TYPE      = "ZBCC";
    @Deprecated
    public static final String OUT_TRANSFER_TYPE  = "ZGI6";
    @Deprecated
    public static final String IN_PURCHASE_TYPE   = "ZBCR";
    @Deprecated
    public static final String IN_RETURNED_TYPE   = "ZBCT";
    @Deprecated
    public static final String IN_TRANSFER_TYPE   = "ZGR6";
    @Deprecated
    public static final String IN_REFUSE_TYPE     = "ZBCJ";
    @Deprecated
    public static final String IN_TRANSPORT_TYPE  = "ZRSR";
    @Deprecated
    public static final String OUT_TRANSPORT_TYPE = "BRSI";

    @Deprecated
    public static boolean isIn(String type) {
        if (!isType(type))
            throw new BusinessException("未知的类型:" + type);
        return IN_PURCHASE_TYPE.equalsIgnoreCase(type) || IN_TRANSFER_TYPE.equalsIgnoreCase(type)
               || IN_RETURNED_TYPE.equalsIgnoreCase(type) || IN_REFUSE_TYPE.equalsIgnoreCase(type)
               || IN_TRANSPORT_TYPE.equalsIgnoreCase(type);
    }

    @Deprecated
    public static boolean isOut(String type) {
        return !isIn(type);
    }

    @Deprecated
    public static boolean isType(String type) {
        return IN_PURCHASE_TYPE.equalsIgnoreCase(type) || IN_TRANSFER_TYPE.equalsIgnoreCase(type)
               || IN_RETURNED_TYPE.equalsIgnoreCase(type) || IN_REFUSE_TYPE.equalsIgnoreCase(type)
               || OUT_SALE_TYPE.equalsIgnoreCase(type) || OUT_TRANSFER_TYPE.equalsIgnoreCase(type)
               || IN_TRANSPORT_TYPE.equalsIgnoreCase(type)
               || OUT_TRANSPORT_TYPE.equalsIgnoreCase(type);
    }

    private String billType;

    /**
     * 获取 交易类型 。
     *
     * <p>
     *      * ZBCC  销售出库订单<br />
     *      * ZBCR  采购入库订单<br />
     *      * ZBCT  退货入库订单<br />
     *      * ZBCJ  拒收入库订单<br />
     *      * ZGI6  调拨出库订单<br />
     *      * ZGR6  调拨入库订单<br />
     *      * ZRSR 转运入库<br />
     *      * ZRSI  转运第二次出库

     * <p>
     */
    public String getBillType() {
        return this.billType;
    }

    /**
     * 设置 交易类型 。
     *
     * <p>
     *      * ZBCC  销售出库订单<br />
     *      * ZBCR  采购入库订单<br />
     *      * ZBCT  退货入库订单<br />
     *      * ZBCJ  拒收入库订单<br />
     *      * ZGI6  调拨出库订单<br />
     *      * ZGR6  调拨入库订单<br />
     *      * ZRSR 转运入库<br />
     *      * ZRSI  转运第二次出库

     * <p>
     *
     * @param value 属性值
     */
    public void setBillType(String value) {
        this.billType = value;
    }

    private Integer quantity;

    /**
     * 获取 交易数量。
     */
    public Integer getQuantity() {
        return this.quantity;
    }

    /**
     * 设置 交易数量。
     *
     * @param value 属性值
     */
    public void setQuantity(Integer value) {
        this.quantity = value;
    }

    private String zeile;

    /**
     * 获取 les行号。
     */
    public String getZeile() {
        return this.zeile;
    }

    /**
     * 设置 les行号。
     *
     * @param value 属性值
     */
    public void setZeile(String value) {
        this.zeile = value;
    }

    private String lesBillNo;

    /**
     * 获取 les物料凭证编号（MBLNR+ZEILE）。
     */
    public String getLesBillNo() {
        return this.lesBillNo;
    }

    /**
     * 设置 les物料凭证编号（MBLNR+ZEILE）。
     *
     * @param value 属性值
     */
    public void setLesBillNo(String value) {
        this.lesBillNo = value;
    }

    private String mark;

    /**
     * 获取 借贷标志（S入库、H出库）。
     */
    public String getMark() {
        return this.mark;
    }

    /**
     * 设置 借贷标志（S入库、H出库）。
     *
     * @param value 属性值
     */
    public void setMark(String value) {
        this.mark = value;
    }

    private String matkl;

    /**
     * 获取 物料组（保留）。
     */
    public String getMatkl() {
        return this.matkl;
    }

    /**
     * 设置 物料组（保留）。
     *
     * @param value 属性值
     */
    public void setMatkl(String value) {
        this.matkl = value;
    }

    private String kunnrSaleTo;

    /**
     * 获取 LES物流中心编号。
     */
    public String getKunnrSaleTo() {
        return this.kunnrSaleTo;
    }

    /**
     * 设置 LES物流中心编号。
     *
     * @param value 属性值
     */
    public void setKunnrSaleTo(String value) {
        this.kunnrSaleTo = value;
    }

    private String kunnrSendTo;

    /**
     * 获取 送达方编码。
     */
    public String getKunnrSendTo() {
        return this.kunnrSendTo;
    }

    /**
     * 设置 送达方编码。
     *
     * @param value 属性值
     */
    public void setKunnrSendTo(String value) {
        this.kunnrSendTo = value;
    }

    private String tknum;

    /**
     * 获取 装运编号。
     */
    public String getTknum() {
        return this.tknum;
    }

    /**
     * 设置 装运编号。
     *
     * @param value 属性值
     */
    public void setTknum(String value) {
        this.tknum = value;
    }

    private String bwart;

    /**
     * 获取 移动类型（保留）。
     */
    public String getBwart() {
        return this.bwart;
    }

    /**
     * 设置 移动类型（保留）。
     *
     * @param value 属性值
     */
    public void setBwart(String value) {
        this.bwart = value;
    }

    private String charg;

    public String getCharg() {
        return charg;
    }

    public void setCharg(String charg) {
        this.charg = charg;
    }

    private String reserve1;

    /**
     * 获取 预留1。
     */
    public String getReserve1() {
        return this.reserve1;
    }

    /**
     * 设置 预留1。
     *
     * @param value 属性值
     */
    public void setReserve1(String value) {
        this.reserve1 = value;
    }

    private String reserve2;

    /**
     * 获取 预留2。
     */
    public String getReserve2() {
        return this.reserve2;
    }

    /**
     * 设置 预留2。
     *
     * @param value 属性值
     */
    public void setReserve2(String value) {
        this.reserve2 = value;
    }

    private String bstkd;

    /**
     * 获取 预留3。
     */
    public String getBstkd() {
        return this.bstkd;
    }

    /**
     * 设置 预留3。
     *
     * @param value 属性值
     */
    public void setBstkd(String value) {
        this.bstkd = value;
    }

    private String data;

    /**
     * 获取 数据内容。
     */
    public String getData() {
        return this.data;
    }

    /**
     * 设置 数据内容。
     *
     * @param value 属性值
     */
    public void setData(String value) {
        this.data = value;
    }

    @Deprecated
    public final static Integer STATUS_RECEIVED     = 10;
    public final static Integer STATUS_UNDONE       = 0;
    public final static Integer STATUS_DONE         = 1;
    public final static Integer STATUS_ERROR        = -1;
    public final static Integer STATUS_DELAY        = 2;
    public final static Integer STATUS_UNIDENTIFIED = 13;

    private Integer             status;

    /**
     * 获取 库龄处理状态。
     *
     * <p>
     * 0：待处理<br />
     * 1：已处理<br />
     * -1 : 错误
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置 处理状态。
     *
     * <p>
     * 0：待处理<br />
     * 1：已处理<br />
     * -1:错误
     *
     * @param value 属性值
     */
    public void setStatus(Integer value) {
        this.status = value;
    }

    private Integer financeStatus;

    public Integer getFinanceStatus() {
        return financeStatus;
    }

    public void setFinanceStatus(Integer financeStatus) {
        this.financeStatus = financeStatus;
    }

    private Date processTime = null;

    /**
     * 获取 订单处理时间。
     */
    public Date getProcessTime() {
        return this.processTime;
    }

    /**
     * 设置 订单处理时间。
     *
     * @param value 属性值
     */
    public void setProcessTime(Date value) {
        this.processTime = value;
    }

    private Date addTime;

    /**
     * 获取 创建时间。
     */
    public Date getAddTime() {
        return this.addTime;
    }

    /**
     * 设置 创建时间。
     *
     * @param value 属性值
     */
    public void setAddTime(Date value) {
        this.addTime = value;
    }

    private String errorMessage;

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    private String versionCode;

    /**
     * 获取 版本编号 - 作为唯一标识使用。
     */
    public String getVersionCode() {
        return this.versionCode;
    }

    /**
     * 设置 版本编号 - 作为唯一标识使用。
     *
     * @param value 属性值
     */
    public void setVersionCode(String value) {
        this.versionCode = value;
    }

    public final static String CHANNEL_RRS = "RRS";
    public final static String CHANNEL_WA  = "WA";

    private String             channelCode;

    /**
     * 获取 WA:自有。
     *
     * <p>
     * RRS:共享
     */
    public String getChannelCode() {
        return this.channelCode;
    }

    /**
     * 设置 WA:自有。
     *
     * <p>
     * RRS:共享
     *
     * @param value 属性值
     */
    public void setChannelCode(String value) {
        this.channelCode = value;
    }

    private String externalSku;

    /**
     * 获取转换前的SKU，更新转换后的SKU时使用，其他地方不能使用
     * @return
     */
    public String getExternalSku() {
        return externalSku;
    }

    public void setExternalSku(String externalSku) {
        this.externalSku = externalSku;
    }

    private String merchantCode = "YIXUN";

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    /**
     * 是否手工设置渠道：0-否 1-是
     */
    private Integer isManualSetChannel;

    public Integer getIsManualSetChannel() {
        return isManualSetChannel;
    }

    public void setIsManualSetChannel(Integer isManualSetChannel) {
        this.isManualSetChannel = isManualSetChannel;
    }

    /**
     * 修订的渠道
     */
    private String channelRevised;

    public String getChannelRevised() {
        return channelRevised;
    }

    public void setChannelRevised(String channelRevised) {
        this.channelRevised = channelRevised;
    }

    /**
     * 修订人
     */
    private String reviser;

    public String getReviser() {
        return reviser;
    }

    public void setReviser(String reviser) {
        this.reviser = reviser;
    }

    /**
     * 修订时间
     */
    private Date reviseTime;

    public Date getReviseTime() {
        return reviseTime;
    }

    public void setReviseTime(Date reviseTime) {
        this.reviseTime = reviseTime;
    }
}