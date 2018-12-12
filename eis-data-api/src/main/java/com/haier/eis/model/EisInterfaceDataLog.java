package com.haier.eis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口数据日志。
 *
 * <p>Table: <strong>eis_interface_data_log</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>Id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>interfaceCode</td><td>{@link String}</td><td>interface_code</td><td>varchar</td><td>接口编号</td></tr>
 *   <tr><td>requestData</td><td>{@link String}</td><td>request_data</td><td>text</td><td>请求数据（json格式）</td></tr>
 *   <tr><td>responseData</td><td>{@link Object}</td><td>response_data</td><td>ntext/long nvarchar</td><td>响应数据</td></tr>
 *   <tr><td>requestTime</td><td>{@link Date}</td><td>request_time</td><td>datetime</td><td>请求时间</td></tr>
 *   <tr><td>responseTime</td><td>{@link Date}</td><td>response_time</td><td>datetime</td><td>响应时间</td></tr>
 *   <tr><td>createTime</td><td>{@link Date}</td><td>create_time</td><td>datetime</td><td>创建时间</td></tr>
 *   <tr><td>responseStatus</td><td>{@link String}</td><td>response_status</td><td>varchar</td><td>响应状态<br />Success：成功<br />Error：失败</td></tr>
 *   <tr><td>errorMessage</td><td>{@link String}</td><td>error_message</td><td>text</td><td>错误信息</td></tr>
 *   <tr><td>foreignKey</td><td>{@link String}</td><td>foreign_key</td><td>varchar</td><td>外关键字<br />例如：跟订单相关，就填订单编号；<br />跟采购单相关，就填采购单号...</td></tr>
 *   <tr><td>elapsedTime</td><td>{@link Long}</td><td>elapsed_time</td><td>bigint</td><td>耗时，毫秒</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-6-6
 * @email yudi@sina.com
 */
public class EisInterfaceDataLog implements Serializable {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 8651203145236030366L;
    /**
     * 接口编码 - 向金税请求修改发票信息
     */
    public static String INTERFACE_CODE_MODIFY_INVOICE = "modify_tax_invoice";
    /**
     * 接口编码 - 从CRM获取采购价格
     */
    public static String INTERFACE_CODE_GET_PURCHASE_PRICE_FROM_CRM = "get_purchase_price_from_crm";
    /**
     * 接口编码 - 从LES获取库存交易
     */
    public static String INTERFACE_CODE_GET_STOCK_TRANS_FROM_LES    = "get_stock_trans_from_les";

    public static String INTERFACE_CODE_GET_STOCK_JIDI_FROM_GVS = "get_jidi_stock_from_gvs";
    /**
     * 接口编码 - 从LES获取套机信息
     */
    public static String INTERFACE_CODE_GET_BOM_INFO_FROM_LES   = "get_bom_info_from_les";
    /**
     * 接口编码 - 向金税请求查询数据
     */
    public static String INTERFACE_CODE_QUERY_INVOICE = "query_tax_invoice";
    /**
     * 接口编码 - 将采购订单信息同步到Les
     */
    public static String INTERFACE_CODE_GET_PURCHASEORDER_TRANS_TO_LES = "get_purchaseorder_trans_to_les";

    /**
     * 接口编码 - 向LES推送网单和调拨单信息
     */
    public static String INTERFACE_CODE_DNF_TO_LES = "dnf_to_les";
    /**
     *  接口编码 - 查询Les库存，cbs库存对账
     */
    public static String INTERFACE_CODE_QUERY_LES_STOCK_TO_CBS = "query_les_stock_to_cbs";
    /**
     * 接口编码 - 向金税请求发票强制作废
     */
    public static String INTERFACE_CODE_INVALID_INVOICE = "invalid_tax_invoice";
    /**
     * 接口编码 - 将采购订单信息同步到Les
     */
    public static String INTERFACE_CODE_GET_OMS_ON_ORDER = "get_oms_onorder";

    /**
     * 接口编码 - 推送HP派工
     */
    public static String INTERFACE_CODE_DISPATCH_ORDER_TO_HP = "dispatch_order_to_hp";
    /**
     * 接口编码 - 将调拨单信息同步到Les
     */
    public static String INTERFACE_CODE_GET_TRANSFER_LINE_TO_LES = "get_transfer_line_to_les";

    /**
     * 接口编码 - 推送les开提单
     */
    public static String INTERFACE_CODE_ORDER_TO_LES = "order_to_les";
    /**
     * 接口编码 - 从mdm获取物料信息
     */
    public static String INTERFACE_CODE_GET_SKU_INFO_FROM_MDM = "get_sku_info_from_mdm";

    /**
     * 接口编码 - 传递用于录入费用的调拨单信息到LES
     */
    public static String INTERFACE_CODE_EXCHANGE_GOODS_TO_LES = "exchange_goods_to_les";
    /**
     * 接口编码 - 不良品支付中心回退
     */
    public static String INTERFACE_CODE_PAYCENTER_FALLBACK = "paycenter_fallback";
    /**
     * 接口编码 - LES传递费用信息到CBS
     */
    public static String INTERFACE_CODE_EXCHANGE_FEE_LES_TO_CBS = "exchange_fee_les_to_cbs";

    /**
     * 接口编码 - ICMS从CBS获取可用库存 - 批量接口
     */
    public static String INTERFACE_CODE_GET_AVAILABLE_STOCK_FROM_CBS = "get_available_stock_from_cbs";

    /**
     * 接口编码 - 冻结CBS库存
     */
    public static String INTERFACE_CODE_FROZEN_STOCK_FROM_CBS = "frozen_stock_from_cbs";

    /**
     * 接口编码 - 释放CBS库存
     */
    public static String INTERFACE_CODE_RELEASE_STOCK_FROM_CBS = "release_stock_from_cbs";

    /**
     * 接口编码 - 取消LES调拨单
     */
    public static String INTERFACE_CODE_CANCEL_TRANSFERLINE_TO_LES = "cancel_transferline_to_les";
    /**
     * 接口编码- 向HP推送作废HP工单
     */
    public static String INTERFACE_CODE_GOODS_RETURN_TO_HP = "goods_return_to_hp";
    /**
     * 接口编码 - 创建订单到OMS
     */
    public static String INTERFACE_CODE_CREATE_ORDER_TO_OMS = "create_order_to_oms";
    /**
     * 接口编码 - 向金税发送开票数据
     */
    public static String INTERFACE_CODE_CREATE_INVOICE = "create_tax_invoice";
    /**
     * 同步发票状态
     */
    
    public static String INTERFACE_CODE_INVOICE_STATUS_SYNC = "invoice_status_sync";
    /**
     * 接口编码 - 向EC查询库存信息
     */
    public static String INTERFACE_QUERY_ESTORE_STOCK      = "query_estore_stock";

    /**
     * 接口编码 - 从OMS获取生产信息
     */
    public static String QUERY_ORDER_INFO_FROM_OMS          = "query_order_info_from_oms";
    /**
     * 接口编码 - 从LESS获取日日单CRM开提单信息
     */
    public static String QUERY_CRM_TIDAN_INFO_FROM_LESS     = "query_crm_tidan_info_from_less";

    /**
     * 接口编码 - 从EDW获取生产/下线日期
     */
    public static String QUERY_PROD_DATE_FROM_EDW = "query_prod_date_from_edw";
    /**
     * 接口编码 - 电子发票推送或作废
     */
    public static String INTERFACE_CODE_IENVOICE_TO_BILL = "einvoice_bill";
    /**
     * 接口编码- 京东21批次入库记录
     */
    public static String INTERFACE_CODE_HP_JD_IN_TRANS   = "jd_hp_in_trans";
    /**
     * 接口编码- 京东21批次出库记录
     */
    public static String INTERFACE_CODE_LES_JD_OUT_TRANS = "jd_les_out_trans";
    /**
     * 接口编码- 京东21批次ICMS传SAP回传状态
     */
    public static String INTERFACE_CODE_JD_ICMS_SAP_SUCC = "jd_icms_sap_succ";
    /**
     * 接口编码- 发送定时库存至less
     */
    public static String INTERFACE_CODE_STOCK_STORE      = "stock_store";
    /**
     * 接口编码- 发送定时流水至less
     */
    public static String INTERFACE_CODE_STOCK_ITEM       = "stock_item";
    /**
     * 接口编码- 接收退货质检XML
     */
    public static String INTERFACE_CODE_HPREPAIR_XML     = "hprepair_xml";

    /**
     * 接口编码 - 从CBS获取可用门店库存
     */
    public static String INTERFACE_CODE_GET_STORE_AVAILABLE_STOCK_FROM_CBS = "get_available_store_stock_from_cbs";

    /**
     * 接口编码- 释放门店库存
     */
    public static String INTERFACE_CODE_RELEASE_STORE_FROM_CBS = "release_store_stock_from_cbs";
    /**
     * 接口编码 - 电子发票查询
     */
    public static String INTERFACE_CODE_EINVOICE_QRY = "einvoice_qry";
    /**
     * 接口编码- JL回传入库记录
     */
    public static String INTERFACE_CODE_JL_TRANS_IN = "jl_trans_in";
    /**
     * 响应状态 - 成功
     */
    public static String RESPONSE_STATUS_SUCCESS    = "S";
    /**
     * 响应状态 - 失败
     */
    public static String RESPONSE_STATUS_ERROR      = "F";

    private Integer id;

    

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

    private String requestData;

    /**
     * 获取 请求数据（json格式）。
     */
    public String getRequestData() {
        return this.requestData;
    }

    /**
     * 设置 请求数据（json格式）。
     *
     * @param value 属性值
     */
    public void setRequestData(String value) {
        this.requestData = value;
    }

    private Object responseData;

    /**
     * 获取 响应数据。
     */
    public Object getResponseData() {
        return this.responseData;
    }

    /**
     * 设置 响应数据。
     *
     * @param value 属性值
     */
    public void setResponseData(Object value) {
        this.responseData = value;
    }

    private Date requestTime;

    /**
     * 获取 请求时间。
     */
    public Date getRequestTime() {
        return this.requestTime;
    }

    /**
     * 设置 请求时间。
     *
     * @param value 属性值
     */
    public void setRequestTime(Date value) {
        this.requestTime = value;
    }

    private Date responseTime;

    /**
     * 获取 响应时间。
     */
    public Date getResponseTime() {
        return this.responseTime;
    }

    /**
     * 设置 响应时间。
     *
     * @param value 属性值
     */
    public void setResponseTime(Date value) {
        this.responseTime = value;
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

    private String responseStatus;

    /**
     * 获取 响应状态。
     *
     * <p>
     * Success：成功<br />
     * Error：失败
     */
    public String getResponseStatus() {
        return this.responseStatus;
    }

    /**
     * 设置 响应状态。
     *
     * <p>
     * Success：成功<br />
     * Error：失败
     *
     * @param value 属性值
     */
    public void setResponseStatus(String value) {
        this.responseStatus = value;
    }

    private String errorMessage;

    /**
     * 获取 错误信息。
     */
    public String getErrorMessage() {
        return this.errorMessage;
    }

    /**
     * 设置 错误信息。
     *
     * @param value 属性值
     */
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    private String foreignKey;

    /**
     * 获取 外关键字。
     *
     * <p>
     * 例如：跟订单相关，就填订单编号；<br />
     * 跟采购单相关，就填采购单号...
     */
    public String getForeignKey() {
        return this.foreignKey;
    }

    /**
     * 设置 外关键字。
     *
     * <p>
     * 例如：跟订单相关，就填订单编号；<br />
     * 跟采购单相关，就填采购单号...
     *
     * @param value 属性值
     */
    public void setForeignKey(String value) {
        this.foreignKey = value;
    }

    private Long elapsedTime = 0L;

    /**
     * 获取 耗时，毫秒。
     */
    public Long getElapsedTime() {
        return this.elapsedTime;
    }

    /**
     * 设置 耗时，毫秒。
     *
     * @param value 属性值
     */
    public void setElapsedTime(Long value) {
        this.elapsedTime = value;
    }

}