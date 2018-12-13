package com.haier.purchase.data.model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Table: <strong>purchase_order_queue_4_daily</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 * <tr style="background-color:#ddd;Text-align:Left;">
 * <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 * </tr>
 * <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 * <tr><td>refNo</td><td>{@link String}</td><td>ref_no</td><td>varchar</td><td>网单号（采购单号）</td></tr>
 * <tr><td>status</td><td>{@link Integer}</td><td>status</td><td>int</td><td>状态（100：待生成采购单 101：待更新生产信息 102：待更新提单信息 110：已完成 120：已取消</td></tr>
 * <tr><td>omsNo</td><td>{@link String}</td><td>oms_no</td><td>varchar</td><td>OMS订单号</td></tr>
 * <tr><td>omsStatus</td><td>{@link String}</td><td>oms_status</td><td>varchar</td><td>OMS状态</td></tr>
 * <tr><td>message</td><td>{@link String}</td><td>message</td><td>varchar</td><td>&nbsp;</td></tr>
 * <tr><td>addTime</td><td>{@link Date}</td><td>add_time</td><td>datetime</td><td>&nbsp;</td></tr>
 * <tr><td>updateTime</td><td>{@link Date}</td><td>update_time</td><td>datetime</td><td>&nbsp;</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2014-5-20
 * @email yudi@sina.com
 */
public class PurchaseOrderQueue4Daily implements Serializable {
    /**
     * 新建：待生产采购单
     */
    public final static int STATUS_NEW = 100;
    /**
     * 生产中：待更新生产信息
     */
    public final static int STATUS_PRODUCE = 101;
    /**
     * 生产完成待入库：待更新CRM开入库单信息
     */
    public final static int STATUS_LOGISTICS = 102;
    /**
     * 完成：带入库
     */
    public final static int STATUS_DOWN = 110;
    /**
     * 取消：采购单已经取消
     */
    public final static int STATUS_CANCEL = 120;

    /**
     * 日日单网单 ：生产中
     */
    public final static int ORDER_STATUS_PRODUCING = 10;
    /**
     * 日日单网单：工厂已发货
     */
    public final static int ORDER_STATUS_FACTORY_HAS_DELIVERY = 12;
    /**
     * 日日单网单：已到当地仓库
     */
    public final static int ORDER_STATUS_HAS_ENTRY_WA_WAREHOUSE = 50;

    /**
     * 订单正在XX工厂排定生产
     */
    @Deprecated
    public final static int DETAIL_INFO_SYNC_STATUS_PD1 = 1;
    /**
     * 订单已从XX工厂发货，海尔物流运单：4000111
     */
    @Deprecated
    public final static int DETAIL_INFO_SYNC_STATUS_PD2 = 2;
    /**
     * 订单已发货到 xx 仓库
     */
    @Deprecated
    public final static int DETAIL_INFO_SYNC_STATUS_PD3 = 3;
    /**
     * 完成
     */
    @Deprecated
    public final static int DETAIL_INFO_SYNC_STATUS_PD10 = 10;

    /**
     * 订单已排产
     */
    public final static int DETAIL_INFO_SYNC_STATUS_PP1 = 110;
    /**
     * 生产下线
     */
    public final static int DETAIL_INFO_SYNC_STATUS_PP2 = 120;
    /**
     * 事业部已发货
     */
    public final static int DETAIL_INFO_SYNC_STATUS_PP3 = 130;
    /**
     * 已入当地中心仓
     */
    public final static int DETAIL_INFO_SYNC_STATUS_PP4 = 140;
    /**
     * 完成
     */
    public final static int DETAIL_INFO_SYNC_STATUS_PP10 = 200;

    /**
     * 需要同步生产日期/下线日期
     */
    public static final int SYNC_PROD_DATE_STATUS_INIT = 0;
    /**
     * 同步生产日期/下线日期完成
     */
    public static final int SYNC_PROD_DATE_STATUS_DOWN = 10;


    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private String refNo;

    /**
     * 获取 网单号（采购单号）。
     */
    public String getRefNo() {
        return this.refNo;
    }

    /**
     * 设置 网单号（采购单号）。
     *
     * @param value 属性值
     */
    public void setRefNo(String value) {
        this.refNo = value;
    }

    private Integer status;

    /**
     * 获取 状态（100：待生成采购单 101：待更新生产信息 102：待更新提单信息 110：已完成 120：已取消。
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置 状态（100：待生成采购单 101：待更新生产信息 102：待更新提单信息 110：已完成 120：已取消。
     *
     * @param value 属性值
     */
    public void setStatus(Integer value) {
        this.status = value;
    }

    private Integer orderStatus;

    /**
     * 获取更新网单状态
     *
     * @return 更新日日单网单状态
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * 同步详细信息到ehaier状态
     */
    private Integer detailInfoSyncStatus;

    public Integer getDetailInfoSyncStatus() {
        return detailInfoSyncStatus;
    }

    public void setDetailInfoSyncStatus(Integer detailInfoSyncStatus) {
        this.detailInfoSyncStatus = detailInfoSyncStatus;
    }

    /**
     * 从EDW同步生产日期/下线日期状态
     */
    private Integer syncProdDateStatus;

    public Integer getSyncProdDateStatus() {
        return syncProdDateStatus;
    }

    public void setSyncProdDateStatus(Integer syncProdDateStatus) {
        this.syncProdDateStatus = syncProdDateStatus;
    }

    private String omsNo;

    /**
     * 获取 OMS订单号。
     */
    public String getOmsNo() {
        return this.omsNo;
    }

    /**
     * 设置 OMS订单号。
     *
     * @param value 属性值
     */
    public void setOmsNo(String value) {
        this.omsNo = value;
    }

    private String omsStatus;

    /**
     * 获取 OMS状态。
     */
    public String getOmsStatus() {
        return this.omsStatus;
    }

    /**
     * 设置 OMS状态。
     *
     * @param value 属性值
     */
    public void setOmsStatus(String value) {
        this.omsStatus = value;
    }

    private String message;

    public String getMessage() {
        return this.message;
    }

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

}