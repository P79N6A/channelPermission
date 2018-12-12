package com.haier.eis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * EIS 接口状态表。
 *
 * <p>Table: <strong>eis_interface_status</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>interfaceCode</td><td>{@link String}</td><td>interface_code</td><td>varchar</td><td>接口编号</td></tr>
 *   <tr><td>lastTime</td><td>{@link Date}</td><td>last_time</td><td>datetime</td><td>最后的截至时间</td></tr>
 *   <tr><td>lastId</td><td>{@link Integer}</td><td>last_id</td><td>int</td><td>最后的订单号</td></tr>
 *   <tr><td>updateTime</td><td>{@link Date}</td><td>update_time</td><td>datetime</td><td>更新时间</td></tr>
 * </table>
 *
 * @author 吴坤洋
 * @date 2017-12-22
 * @email yudi@sina.com
 */
public class EisInterfaceStatus implements Serializable {
    /** Comment for <code>serialVersionUID</code> */
    private static final long  serialVersionUID                          = -5863630144561759787L;
    /**
     * 接口编码 - 从LES获取库存交易
     */
    public static String       INTERFACE_CODE_GET_STOCK_TRANS_FROM_LES   = "get_stock_trans_from_les";
    /**
     * 接口编码 - 库存出入库调用财务接口
     */
    public static String       INTERFACE_CODE_GET_STOCK_TRANS_FINANCE    = "get_stock_trans_finance";

    /**
     * 库存预留同步
     */
    public static String       INTERFACE_CODE_SYNC_RESERVED_STOCK        = "sync_reserved_stock";

    public static String       PREDICT_STOCK_CODE                        = "predict_stock_code";
    public static final String NTERFACE_CODE_GET_ITEM_ATTRIBUTE          = "get_item_attribute";
    public static final String NTERFACE_CODE_GET_ITEM_BASE               = "get_item_base";
    public static final String NTERFACE_CODE_GET_STOCK_TRANS_FINANCE     = "get_stock_trans_finance";
    public static final String NTERFACE_CODE_SHIPPING_INFO_TO_TAOBAO     = "shipping_info_to_taobao";

    public static final String INTERFACE_CODE_SYNC_CITY_STOCK_STATUS     = "sync_city_stock_status";

    public static final String INTERFACE_CODE_SYNC_REGION_STOCK_STATUS   = "sync_region_stock_status";
    
    public static final String INTERFACE_SG_STOCK_SYNC_EIS_TO_SHOP   = "sg_stock_sync_eis_to_shop";
    
    public static final String INTERFACE_STOCK_SYNC_LES_TO_CBS   = "stock_sync_les_to_cbs";
    
    public static final String INTERFACE_CODE_SYNC_REGION_EC_STOCK_STATUS   = "sync_region_ec_stock_status";

    /**
     * 统计并更新网单活动id
     */
    public static String       NTERFACE_CODE_CALCULATE_ORDER_ACTIVITY_ID = "calculate_order_activity_id";

    /**
     * VOM消息队列Id，处理正品退货拒单情况下验证失败的数据
     */
    public static String       NTERFACE_CODE_VOM_RECEIVED_QUEUE_ID       = "vom_received_queue_id";
    /**定时同步vom数据 菜鸟出入库数据 平时使用*/
    public static final String VOMWWW_OUTINSTOCK_SYNCHRONISE             = "vomwww_outinstock_synchronise";
    /**定时同步vom数据 菜鸟出入库数据 补数据专用*/
    public static final String VOMWWW_OUTINSTOCK_SYNCHRONISE_LOST_DATA   = "vomwww_outinstock_synchronise_lost_data";
    /**定时解析vom数据条数 菜鸟出入库数据*/
    public static final String VOMWWW_OUTINSTOCK_ANALYSIS                = "vomwww_outinstock_analysis";
    /**定时获取处理vom解析数据条数 菜鸟出入库数据*/
    public static final String VOMWWW_OUTINSTOCK_SyncAnalysisToCBS       = "vomwww_outinstock_syncAnalysisToCBS";
    /**定时获取处理失败的vom解析数据条数 菜鸟出入库数据*/
    public static final String VOMWWW_OUTINSTOCK_SyncAnalysisToCBS_Error = "vomwww_outinstock_syncAnalysisToCBS_error";
    /**定时同步vom漏处理的数据  菜鸟出入库数据 */
    public static final String VOMWWW_OUTINSTOCK_SYNCHRONIZE_QUEUES                 = "vomwww_outinstock_synchronize_queues";
    private Integer            id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private String interfaceCode;

    /**
     * 获取 接口编号。
     */
    public String getInterfaceCode() {
        return this.interfaceCode;
    }

    /**
     * 设置 接口编号。
     *
     * @param value 属性值
     */
    public void setInterfaceCode(String value) {
        this.interfaceCode = value;
    }

    private Date lastTime;

    /**
     * 获取 最后的截至时间。
     */
    public Date getLastTime() {
        return this.lastTime;
    }

    /**
     * 设置 最后的截至时间。
     *
     * @param value 属性值
     */
    public void setLastTime(Date value) {
        this.lastTime = value;
    }

    private Date now;

    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
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

    private Integer lastId;

    /**
     * 获取 最后更新id。
     */
    public Integer getLastId() {
        return this.lastId;
    }

    /**
     * 设置 最后更新id。
     */
    public void setLastId(Integer value) {
        this.lastId = value;
    }
}