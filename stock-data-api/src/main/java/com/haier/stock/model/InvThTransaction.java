package com.haier.stock.model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Table: <strong>inv_th_transaction</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>corderSn</td><td>{@link String}</td><td>corder_sn</td><td>varchar</td><td>网单号</td></tr>
 *   <tr><td>vbelnSo</td><td>{@link String}</td><td>vbeln_so</td><td>varchar</td><td>GVS46编号</td></tr>
 *   <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>varchar</td><td>物料号</td></tr>
 *   <tr><td>secCode</td><td>{@link String}</td><td>sec_code</td><td>varchar</td><td>WA库位</td></tr>
 *   <tr><td>inAddTime</td><td>{@link Date}</td><td>in_add_time</td><td>datetime</td><td>入库_取数时间</td></tr>
 *   <tr><td>outTime</td><td>{@link Date}</td><td>out_time</td><td>datetime</td><td>出库_出库交易时间</td></tr>
 *   <tr><td>outAddTime</td><td>{@link Date}</td><td>out_add_time</td><td>datetime</td><td>出库_取数时间</td></tr>
 *   <tr><td>inStatus</td><td>{@link Integer}</td><td>in_status</td><td>int</td><td>入库_数据状态</td></tr>
 *   <tr><td>outStatus</td><td>{@link Integer}</td><td>out_status</td><td>int</td><td>出库_数据状态</td></tr>
 *   <tr><td>charg</td><td>{@link Integer}</td><td>charg</td><td>int</td><td>出库_批次</td></tr>
 *   <tr><td>channel</td><td>{@link String}</td><td>channel</td><td>varchar</td><td>渠道</td></tr>
 *   <tr><td>productNo</td><td>{@link String}</td><td>product_no</td><td>char</td><td>入库_机编</td></tr>
 *   <tr><td>soNum</td><td>{@link String}</td><td>so_num</td><td>varchar</td><td>入库_行项目号</td></tr>
 *   <tr><td>inwhDate</td><td>{@link Date}</td><td>inwh_date</td><td>datetime</td><td>入库_出库日期</td></tr>
 *   <tr><td>hpLesId</td><td>{@link String}</td><td>hp_les_id</td><td>varchar</td><td>入库_hp到les 的300号</td></tr>
 *   <tr><td>countNum</td><td>{@link Integer}</td><td>count_num</td><td>int</td><td>入库_数量</td></tr>
 *   <tr><td>kw</td><td>{@link String}</td><td>kw</td><td>varchar</td><td>入库_库存地点</td></tr>
 *   <tr><td>inwhId</td><td>{@link String}</td><td>inwh_id</td><td>varchar</td><td>入库_LES入库单号-LES入RA凭证号</td></tr>
 *   <tr><td>storePlace</td><td>{@link String}</td><td>store_place</td><td>varchar</td><td>出库_协同仓库库位</td></tr>
 *   <tr><td>channelSoNum</td><td>{@link String}</td><td>channel_so_num</td><td>varchar</td><td>出库_京东订单行号</td></tr>
 *   <tr><td>channelOrderSn</td><td>{@link String}</td><td>channel_order_sn</td><td>varchar</td><td>出库_京东订单号</td></tr>
 *   <tr><td>accountPingNo</td><td>{@link String}</td><td>account_ping_no</td><td>varchar</td><td>出库_LES49记账凭证号</td></tr>
 *   <tr><td>accountSoNo</td><td>{@link String}</td><td>account_so_no</td><td>varchar</td><td>出库_LES49记账凭行号</td></tr>
 *   <tr><td>outping</td><td>{@link String}</td><td>outping</td><td>varchar</td><td>出库_LES-300提单号</td></tr>
 *   <tr><td>channelProductCode</td><td>{@link String}</td><td>channel_product_code</td><td>varchar</td><td>出库_京东产品编码</td></tr>
 *   <tr><td>lesProductCode</td><td>{@link String}</td><td>les_product_code</td><td>varchar</td><td>出库_LES产品编码</td></tr>
 *   <tr><td>tknum</td><td>{@link String}</td><td>tknum</td><td>varchar</td><td>出库_装运编号</td></tr>
 *   <tr><td>bstkd</td><td>{@link String}</td><td>bstkd</td><td>varchar</td><td>出库_京东出库单号</td></tr>
 *   <tr><td>quantity</td><td>{@link Integer}</td><td>quantity</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>matkl</td><td>{@link String}</td><td>matkl</td><td>varchar</td><td>出库_物料组</td></tr>
 *   <tr><td>bwart</td><td>{@link String}</td><td>bwart</td><td>varchar</td><td>出库_移动类型</td></tr>
 *   <tr><td>centerCode</td><td>{@link String}</td><td>center_code</td><td>varchar</td><td>出库_中心编码</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2014-7-7
 * @email yudi@sina.com
 */
@SuppressWarnings("serial")
public class InvThTransaction implements Serializable {

    /**
     * in_status
        0:初始状态
        1:新插入
        2:已发ICMS
        3:发送ICMS失败重传
        4:ICMS返回SAP已接收
        5:错误数据不处理
     */
    public static Integer TRANS_IN_STATUS_HPLES          = -1;
    public static Integer TRANS_IN_STATUS_INIT           = 0;
    public static Integer TRANS_IN_STATUS_ARRIV          = 1;
    public static Integer TRANS_IN_STATUS_SUC_TOICMS     = 2;
    public static Integer TRANS_IN_STATUS_FAILED_TOICMS  = 3;
    public static Integer TRANS_IN_STATUS_ACCEPTED_BYSAP = 4;

    public static Integer TRANS_ERROR                   = 5;
    /**
     * out_status
        0:初始状态
        1:新插入，
        2:已发SAP，
        3:发送SAP失败重传
        5:错误数据不处理
     */
    public static Integer TRANS_OUT_STATUS_INIT         = 0;
    public static Integer TRANS_OUT_STATUS_ARRIV        = 1;
    public static Integer TRANS_OUT_STATUS_SEND_TOSAP   = 2;
    public static Integer TRANS_OUT_STATUS_FAILED_TOSAP = 3;

    public static final String CHANNEL_SHANGCHENG = "SHANGCHENG";
    public static final String CHANNEL_JINGDONG   = "JINGDONG";
    public static final String CHANNEL_SHANGCHENG_3W = "SHANGCHENG_3W";
    public static final String CHANNEL_TSCD       = "TSCD";

    /**
     * repair_status
     * 0:初始状态
     * 1:成功
     * 2;失败
     * 3:错误
     */
    public static Integer TRANS_IN_REPAIR_STATUS_INIT                = 0;
    public static Integer TRANS_IN_REPAIR_STATUS_SUC                 = 1; //作废发票，等更新退货单都成功 
    public static Integer TRANS_IN_REPAIR_STATUS_FAILD               = 2; //作废发票，更更新退货单失败
    public static Integer TRANS_IN_REPAIR_STATUS_ERROR               = 3;
    public static Integer TRANS_IN_REPAIR_STATUS_ORDER_CANCEL_DONE   = 4; //分支流程，强制关单
    public static Integer TRANS_IN_REPAIR_STATUS_ORDER_CANCEL_UNDONE = 5;
    public static Integer TRANS_IN_REPAIR_STATUS_CANCELHP            = 6; //分支流程，作废HP工单
    public static Integer TRANS_IN_REPAIR_STATUS_CANCEL_HP_UNDONE    = 7;

    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private String corderSn;

    /**
     * 获取 网单号。
     */
    public String getCorderSn() {
        return this.corderSn;
    }

    /**
     * 设置 网单号。
     *
     * @param value 属性值
     */
    public void setCorderSn(String value) {
        this.corderSn = value;
    }

    private String vbelnSo;

    /**
     * 获取 GVS46编号。
     */
    public String getVbelnSo() {
        return this.vbelnSo;
    }

    /**
     * 设置 GVS46编号。
     *
     * @param value 属性值
     */
    public void setVbelnSo(String value) {
        this.vbelnSo = value;
    }

    private String sku;

    /**
     * 获取 物料号。
     */
    public String getSku() {
        return this.sku;
    }

    /**
     * 设置 物料号。
     *
     * @param value 属性值
     */
    public void setSku(String value) {
        this.sku = value;
    }

    private String secCode;

    /**
     * 获取 WA库位。
     */
    public String getSecCode() {
        return this.secCode;
    }

    /**
     * 设置 WA库位。
     *
     * @param value 属性值
     */
    public void setSecCode(String value) {
        this.secCode = value;
    }

    private Date inAddTime;

    /**
     * 获取 入库_取数时间。
     */
    public Date getInAddTime() {
        return this.inAddTime;
    }

    /**
     * 设置 入库_取数时间。
     *
     * @param value 属性值
     */
    public void setInAddTime(Date value) {
        this.inAddTime = value;
    }

    private Date outTime;

    /**
     * 获取 出库_出库交易时间。
     */
    public Date getOutTime() {
        return this.outTime;
    }

    /**
     * 设置 出库_出库交易时间。
     *
     * @param value 属性值
     */
    public void setOutTime(Date value) {
        this.outTime = value;
    }

    private Date outAddTime;

    /**
     * 获取 出库_取数时间。
     */
    public Date getOutAddTime() {
        return this.outAddTime;
    }

    /**
     * 设置 出库_取数时间。
     *
     * @param value 属性值
     */
    public void setOutAddTime(Date value) {
        this.outAddTime = value;
    }

    private Integer inStatus;

    /**
     * 获取 入库_数据状态。
     */
    public Integer getInStatus() {
        return this.inStatus;
    }

    /**
     * 设置 入库_数据状态。
     *
     * @param value 属性值
     */
    public void setInStatus(Integer value) {
        this.inStatus = value;
    }

    private Integer outStatus;

    /**
     * 获取 出库_数据状态。
     */
    public Integer getOutStatus() {
        return this.outStatus;
    }

    /**
     * 设置 出库_数据状态。
     *
     * @param value 属性值
     */
    public void setOutStatus(Integer value) {
        this.outStatus = value;
    }

    private Integer charg;

    /**
     * 获取 出库_批次。
     */
    public Integer getCharg() {
        return this.charg;
    }

    /**
     * 设置 出库_批次。
     *
     * @param value 属性值
     */
    public void setCharg(Integer value) {
        this.charg = value;
    }

    private String channel;

    /**
     * 获取 渠道。
     */
    public String getChannel() {
        return this.channel;
    }

    /**
     * 设置 渠道。
     *
     * @param value 属性值
     */
    public void setChannel(String value) {
        this.channel = value;
    }

    private String productNo;

    /**
     * 获取 入库_机编。
     */
    public String getProductNo() {
        return this.productNo;
    }

    /**
     * 设置 入库_机编。
     *
     * @param value 属性值
     */
    public void setProductNo(String value) {
        this.productNo = value;
    }

    private String soNum;

    /**
     * 获取 入库_行项目号。
     */
    public String getSoNum() {
        return this.soNum;
    }

    /**
     * 设置 入库_行项目号。
     *
     * @param value 属性值
     */
    public void setSoNum(String value) {
        this.soNum = value;
    }

    private Date inwhDate;

    /**
     * 获取 入库_出库日期。
     */
    public Date getInwhDate() {
        return this.inwhDate;
    }

    /**
     * 设置 入库_出库日期。
     *
     * @param value 属性值
     */
    public void setInwhDate(Date value) {
        this.inwhDate = value;
    }

    private String hpLesId;

    /**
     * 获取 入库_hp到les 的300号。
     */
    public String getHpLesId() {
        return this.hpLesId;
    }

    /**
     * 设置 入库_hp到les 的300号。
     *
     * @param value 属性值
     */
    public void setHpLesId(String value) {
        this.hpLesId = value;
    }

    /**
     * 开单时间
     */
    private Date hpLesDate;

    /**
     * 获取 入库_开单时间
     * @return
     */
    public Date getHpLesDate() {
        return this.hpLesDate;
    }

    /**
     * 设置 入库_开单时间
     * @param hpLesDate
     */
    public void setHpLesDate(Date hpLesDate) {
        this.hpLesDate = hpLesDate;
    }

    private Integer countNum;

    /**
     * 获取 入库_数量。
     */
    public Integer getCountNum() {
        return this.countNum;
    }

    /**
     * 设置 入库_数量。
     *
     * @param value 属性值
     */
    public void setCountNum(Integer value) {
        this.countNum = value;
    }

    private String kw;

    /**
     * 获取 入库_库存地点。
     */
    public String getKw() {
        return this.kw;
    }

    /**
     * 设置 入库_库存地点。
     *
     * @param value 属性值
     */
    public void setKw(String value) {
        this.kw = value;
    }

    private String inwhId;

    /**
     * 获取 入库_LES入库单号-LES入RA凭证号。
     */
    public String getInwhId() {
        return this.inwhId;
    }

    /**
     * 设置 入库_LES入库单号-LES入RA凭证号。
     *
     * @param value 属性值
     */
    public void setInwhId(String value) {
        this.inwhId = value;
    }

    private String storePlace;

    /**
     * 获取 出库_协同仓库库位。
     */
    public String getStorePlace() {
        return this.storePlace;
    }

    /**
     * 设置 出库_协同仓库库位。
     *
     * @param value 属性值
     */
    public void setStorePlace(String value) {
        this.storePlace = value;
    }

    private String channelSoNum;

    /**
     * 获取 出库_京东订单行号。
     */
    public String getChannelSoNum() {
        return this.channelSoNum;
    }

    /**
     * 设置 出库_京东订单行号。
     *
     * @param value 属性值
     */
    public void setChannelSoNum(String value) {
        this.channelSoNum = value;
    }

    private String channelOrderSn;

    /**
     * 获取 出库_京东订单号。
     */
    public String getChannelOrderSn() {
        return this.channelOrderSn;
    }

    /**
     * 设置 出库_京东订单号。
     *
     * @param value 属性值
     */
    public void setChannelOrderSn(String value) {
        this.channelOrderSn = value;
    }

    private String accountPingNo;

    /**
     * 获取 出库_LES49记账凭证号。
     */
    public String getAccountPingNo() {
        return this.accountPingNo;
    }

    /**
     * 设置 出库_LES49记账凭证号。
     *
     * @param value 属性值
     */
    public void setAccountPingNo(String value) {
        this.accountPingNo = value;
    }

    private String accountSoNo;

    /**
     * 获取 出库_LES49记账凭行号。
     */
    public String getAccountSoNo() {
        return this.accountSoNo;
    }

    /**
     * 设置 出库_LES49记账凭行号。
     *
     * @param value 属性值
     */
    public void setAccountSoNo(String value) {
        this.accountSoNo = value;
    }

    private String outping;

    /**
     * 获取 出库_LES-300提单号。
     */
    public String getOutping() {
        return this.outping;
    }

    /**
     * 设置 出库_LES-300提单号。
     *
     * @param value 属性值
     */
    public void setOutping(String value) {
        this.outping = value;
    }

    private String channelProductCode;

    /**
     * 获取 出库_京东产品编码。
     */
    public String getChannelProductCode() {
        return this.channelProductCode;
    }

    /**
     * 设置 出库_京东产品编码。
     *
     * @param value 属性值
     */
    public void setChannelProductCode(String value) {
        this.channelProductCode = value;
    }

    private String lesProductCode;

    /**
     * 获取 出库_LES产品编码。
     */
    public String getLesProductCode() {
        return this.lesProductCode;
    }

    /**
     * 设置 出库_LES产品编码。
     *
     * @param value 属性值
     */
    public void setLesProductCode(String value) {
        this.lesProductCode = value;
    }

    private String tknum;

    /**
     * 获取 出库_装运编号。
     */
    public String getTknum() {
        return this.tknum;
    }

    /**
     * 设置 出库_装运编号。
     *
     * @param value 属性值
     */
    public void setTknum(String value) {
        this.tknum = value;
    }

    private String bstkd;

    /**
     * 获取 出库_京东出库单号。
     */
    public String getBstkd() {
        return this.bstkd;
    }

    /**
     * 设置 出库_京东出库单号。
     *
     * @param value 属性值
     */
    public void setBstkd(String value) {
        this.bstkd = value;
    }

    private Integer quantity;

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer value) {
        this.quantity = value;
    }

    private String matkl;

    /**
     * 获取 出库_物料组。
     */
    public String getMatkl() {
        return this.matkl;
    }

    /**
     * 设置 出库_物料组。
     *
     * @param value 属性值
     */
    public void setMatkl(String value) {
        this.matkl = value;
    }

    private String bwart;

    /**
     * 获取 出库_移动类型。
     */
    public String getBwart() {
        return this.bwart;
    }

    /**
     * 设置 出库_移动类型。
     *
     * @param value 属性值
     */
    public void setBwart(String value) {
        this.bwart = value;
    }

    private String centerCode;

    /**
     * 获取 出库_中心编码。
     */
    public String getCenterCode() {
        return this.centerCode;
    }

    /**
     * 设置 出库_中心编码。
     *
     * @param value 属性值
     */
    public void setCenterCode(String value) {
        this.centerCode = value;
    }

    /**
     * 设置入库_套机物料编码
     */
    private String mainSku;

    public String getMainSku() {
        return mainSku;
    }

    public void setMainSku(String mainSku) {
        this.mainSku = mainSku;
    }

    /**
     * 设置入库_外机机编码
     */
    private String keyProductNo;

    public String getKeyProductNo() {
        return keyProductNo;
    }

    public void setKeyProductNo(String keyProductNo) {
        this.keyProductNo = keyProductNo;
    }

    /**
     * 设置入库_套机组成个数
     */
    private Integer subCount;

    public Integer getSubCount() {
        return subCount;
    }

    public void setSubCount(Integer subCount) {
        this.subCount = subCount;
    }

    /**
     * 标识子件是否到齐
     */
    private Boolean subReady;

    public Boolean getSubReady() {
        return subReady;
    }

    public void setSubReady(Boolean subReady) {
        this.subReady = subReady;
    }

    /**
     * 退货单状态：0， 初始； 1，完结
     */
    private Integer repair_status;

    public Integer getRepair_status() {
        return repair_status;
    }

    public void setRepair_status(Integer repair_status) {
        this.repair_status = repair_status;
    }

    public static final Integer HP_STATUS_INIT                = 0;
    public static final Integer HP_STATUS_HPLESID             = 1;
    public static final Integer HP_STATUS_HPLESID_UNDON       = 2;
    public static final Integer HP_STATUS_HPLESID_INWH        = 3;
    public static final Integer HP_STATUS_HPLESID_INWH_UNDONE = 4;

    /**
     * hp传输状态
     */
    private Integer hp_states;

    /**
     * 查询 hp传值状态
     * @return
     */
    public Integer getHp_states() {
        return hp_states;
    }

    /**
     * 设置 hp传值状态
     * @param hp_states
     */
    public void setHp_states(Integer hp_states) {
        this.hp_states = hp_states;
    }

    /**
     * 折旧费
     */
    private String depCharge;

    public String getDepCharge() {
        return depCharge;
    }

    public void setDepCharge(String depCharge) {
        this.depCharge = depCharge;
    }
    
    /**
     * hp质检责任批次
     */
    private int hpCheckType;
    
    /**
     * hp质检责任批次
     */
	public int getHpCheckType() {
		return hpCheckType;
	}

	/**
	 * hp质检责任批次
	 */
	public void setHpCheckType(int hpCheckType) {
		this.hpCheckType = hpCheckType;
	}
 


}