package com.haier.shop.model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Table: <strong>OrderQueueExtend</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>自增列</td></tr>
 *   <tr><td>cordersn</td><td>{@link String}</td><td>cordersn</td><td>varchar</td><td>网单号</td></tr>
 *   <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>varchar</td><td>物料</td></tr>
 *   <tr><td>secCode</td><td>{@link String}</td><td>secCode</td><td>varchar</td><td>库位</td></tr>
 *   <tr><td>source</td><td>{@link String}</td><td>source</td><td>varchar</td><td>来源</td></tr>
 *   <tr><td>qty</td><td>{@link Integer}</td><td>qty</td><td>int</td><td>数量</td></tr>
 *   <tr><td>status</td><td>{@link Integer}</td><td>status</td><td>int</td><td>状态</td></tr>
 *   <tr><td>cancelStatus</td><td>{@link Integer}</td><td>cancel_status</td><td>int</td><td>取消状态</td></tr>
 *   <tr><td>syncOrderStatus</td><td>{@link Integer}</td><td>sync_order_status</td><td>int</td><td>同步状态</td></tr>
 *   <tr><td>financeStatus</td><td>{@link Integer}</td><td>finance_status</td><td>int</td><td>金税同步状态</td></tr>
 *   <tr><td>errorMessage</td><td>{@link String}</td><td>error_message</td><td>varchar</td><td>错误信息</td></tr>
 *   <tr><td>addTime</td><td>{@link Date}</td><td>add_time</td><td>datetime</td><td>创建时间</td></tr>
 *   <tr><td>deliveryTime</td><td>{@link Date}</td><td>delivery_time</td><td>datetime</td><td>出库时间</td></tr>
 *   <tr><td>expressCompany</td><td>{@link String}</td><td>express_company</td><td>varchar</td><td>快递公司</td></tr>
 *   <tr><td>expressNumber</td><td>{@link String}</td><td>express_number</td><td>varchar</td><td>快递编码</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2015-1-15
 * @email yudi@sina.com
 */
public class OrderQueueExtend implements Serializable {

    /**
     * 初始状态
     */
    public static final Integer INIT_STATUS             = 0;
    /**
     * 待出库状态
     */
    public static final Integer WAITOUT_STATUS          = 1;
    /**
     * 出库状态
     */
    public static final Integer OUT_STATUS              = 2;
    /**
     * 二次出库状态(回传网点)
     */
    public static final Integer SECOND_OUT_STATUS       = 3;
    /**
     * 签收状态
     */
    public static final Integer SIGN_STATUS       	= 4;
    /**
     * 完成关闭
     */
    public static final Integer COMPLET_STATUS          = 5;
    /**
     * 取消网单状态
     */
    public static final Integer CANCEL_STATUS           = 1;

    /**
     * CBS待同步网单
     */
    public static final Integer SYNC_STATUS_UNDONE      = 0;
    /**
     * CBS已经同步
     */
    public static final Integer SYNC_STATUS_DONE        = 1;

    /**
     * 下单成功重做
     */
    public static final Integer SYNC_STATUS_INIT_REDO   = 2;

    /**
     * 占用库存完成
     */
    public static final Integer SYNC_STATUS_FROZEN      = 3;

    /**
     * 占用库存不成功重做
     */
    public static final Integer SYNC_STATUS_FROZEN_REDO = 4;

    /**
     * 错误数据
     */
    public static final Integer SYNC_STATUS_ERROR       = 5;

    /**
     * 财务状态
     */

    /**
     * 传财务初始状态
     */
    public static final int     PURCHASE_TO_SAP_INIT    = 0;
    /**
     * 创建采购单及签收完成状态
     */
    public static final int     PURCHASE_TO_SAP_DONE    = 1;

    /**
     * 向财务创建采购发票失败
     */
    public static final int     PURCHASE_TO_SAP_UNDONE  = 2;
    /**
     * 销售出库传财务完成状态
     */
    public static final int     SELLOUT_TO_SAP_DONE     = 3;

    /**
     * 销售出库传财务-未完成
     */
    public static final int     SELLOUT_TO_SAP_UNDONE   = 4;

    /**
     * 发票状态初始
     */
    public static final int     INVOICE_TO_SAP_INIT     = 0;
    /**
     * 发票状态成功
     */
    public static final int     INVOICE_TO_SAP_DONE     = 1;
    /**
     * 发票状态未完成
     */
    public static final int     INVOICE_TO_SAP_UNDONE   = 2;
    /**
     * 发票状态错误
     */
    public static final int     INVOICE_TO_SAP_ERROR    = 4;

    private Integer             id;

    /**
     * 获取 自增列。
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 设置 自增列。
     *
     * @param value 属性值
     */
    public void setId(Integer value) {
        this.id = value;
    }

    private String cordersn;

    /**
     * 获取 网单号。
     */
    public String getCordersn() {
        return this.cordersn;
    }

    /**
     * 设置 网单号。
     *
     * @param value 属性值
     */
    public void setCordersn(String value) {
        this.cordersn = value;
    }

    private String sku;

    /**
     * 获取 物料。
     */
    public String getSku() {
        return this.sku;
    }

    /**
     * 设置 物料。
     *
     * @param value 属性值
     */
    public void setSku(String value) {
        this.sku = value;
    }

    private String secCode;

    /**
     * 获取 库位。
     */
    public String getSecCode() {
        return this.secCode;
    }

    /**
     * 设置 库位。
     *
     * @param value 属性值
     */
    public void setSecCode(String value) {
        this.secCode = value;
    }

    private String source;

    /**
     * 获取 来源。
     */
    public String getSource() {
        return this.source;
    }

    /**
     * 设置 来源。
     *
     * @param value 属性值
     */
    public void setSource(String value) {
        this.source = value;
    }

    private Integer qty;

    /**
     * 获取 数量。
     */
    public Integer getQty() {
        return this.qty;
    }

    /**
     * 设置 数量。
     *
     * @param value 属性值
     */
    public void setQty(Integer value) {
        this.qty = value;
    }

    private Integer status;

    /**
     * 获取 状态。
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置 状态。
     *
     * @param value 属性值
     */
    public void setStatus(Integer value) {
        this.status = value;
    }

    private Integer cancelStatus;

    /**
     * 获取 取消状态。
     */
    public Integer getCancelStatus() {
        return this.cancelStatus;
    }

    /**
     * 设置 取消状态。
     *
     * @param value 属性值
     */
    public void setCancelStatus(Integer value) {
        this.cancelStatus = value;
    }

    private Integer syncOrderStatus;

    /**
     * 获取 同步状态。
     */
    public Integer getSyncOrderStatus() {
        return this.syncOrderStatus;
    }

    /**
     * 设置 同步状态。
     *
     * @param value 属性值
     */
    public void setSyncOrderStatus(Integer value) {
        this.syncOrderStatus = value;
    }

    private Integer financeStatus;

    /**
     * 获取 金税同步状态。
     */
    public Integer getFinanceStatus() {
        return this.financeStatus;
    }

    /**
     * 设置 金税同步状态。
     *
     * @param value 属性值
     */
    public void setFinanceStatus(Integer value) {
        this.financeStatus = value;
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

    private Date updateTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    private Date deliveryTime;

    /**
     * 获取 出库时间。
     */
    public Date getDeliveryTime() {
        return this.deliveryTime;
    }

    /**
     * 设置 出库时间。
     *
     * @param value 属性值
     */
    public void setDeliveryTime(Date value) {
        this.deliveryTime = value;
    }

    private String expressCompany;

    /**
     * 获取 快递公司。
     */
    public String getExpressCompany() {
        return this.expressCompany;
    }

    /**
     * 设置 快递公司。
     *
     * @param value 属性值
     */
    public void setExpressCompany(String value) {
        this.expressCompany = value;
    }

    private String expressNumber;

    /**
     * 获取 快递编码。
     */
    public String getExpressNumber() {
        return this.expressNumber;
    }

    /**
     * 设置 快递编码。
     *
     * @param value 属性值
     */
    public void setExpressNumber(String value) {
        this.expressNumber = value;
    }

    /**
     * 出库回传的价格等信息
     */
    private String outJson;

    public String getOutJson() {
        return outJson;
    }

    public void setOutJson(String outJson) {
        this.outJson = outJson;
    }

    /**
     * 传财务返回信息
     */
    private String sapMessage;

    public String getSapMessage() {
        return sapMessage;
    }

    public void setSapMessage(String sapMessage) {
        this.sapMessage = sapMessage;
    }

    /**
     * 发票状态
     */
    private Integer invoiceStatus;

    public Integer getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(Integer invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    /**
     * 接受的发票信息
     */
    private String invoiceJson;

    public String getInvoiceJson() {
        return invoiceJson;
    }

    public void setInvoiceJson(String invoiceJson) {
        this.invoiceJson = invoiceJson;
    }

    /**
     * 同步发票返回信息
     */
    private String invoiceMessage;

    public String getInvoiceMessage() {
        return invoiceMessage;
    }

    public void setInvoiceMessage(String invoiceMessage) {
        this.invoiceMessage = invoiceMessage;
    }

    /**
     * 出库凭证号
     */
    private String outping;

	public String getOutping() {
		return outping;
	}

	public void setOutping(String outping) {
		this.outping = outping;
	}

    /**
     * 网点编码
     */
    private String netpointCode;
	
	public String getNetpointCode() {
		return netpointCode;
	}

	public void setNetpointCode(String netpointCode) {
		this.netpointCode = netpointCode;
	}
	
    /**
     * 网点名称
     */
    private String netpointName;

	public String getNetpointName() {
		return netpointName;
	}

	public void setNetpointName(String netpointName) {
		this.netpointName = netpointName;
	}

    /**
     * 网点联系方式
     */
    private String netpointTel;

	public String getNetpointTel() {
		return netpointTel;
	}

	public void setNetpointTel(String netpointTel) {
		this.netpointTel = netpointTel;
	}

    /**
     * 交货单号
     */
    private String dnNumber;
    
	public String getDnNumber() {
		return dnNumber;
	}

	public void setDnNumber(String dnNumber) {
		this.dnNumber = dnNumber;
	}

}