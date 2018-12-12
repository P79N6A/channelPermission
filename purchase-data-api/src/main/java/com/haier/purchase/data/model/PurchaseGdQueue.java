package com.haier.purchase.data.model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Table: <strong>purchase_gd_queue</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>自增咧</td></tr>
 *   <tr><td>ordersn</td><td>{@link String}</td><td>ordersn</td><td>varchar</td><td>网单号</td></tr>
 *   <tr><td>quantity</td><td>{@link Integer}</td><td>quantity</td><td>int</td><td>采购数量</td></tr>
 *   <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>char</td><td>物料编号</td></tr>
 *   <tr><td>secCode</td><td>{@link String}</td><td>sec_code</td><td>varchar</td><td>库位编码</td></tr>
 *   <tr><td>status</td><td>{@link String}</td><td>status</td><td>char</td><td>0:初始状态<br />  1：已发GVS<br />  2： 传GVS失败<br />  
 *   3： 已经录入快递单号<br />  4：传SAP系统<br />  5： 传SAP失败<br /> 6:已释放</td></tr>
 *   <tr><td>errorMessage</td><td>{@link String}</td><td>error_message</td><td>varchar</td><td>错误信息</td></tr>
 *   <tr><td>addTime</td><td>{@link Date}</td><td>add_time</td><td>date</td><td>&nbsp;</td></tr>
 *   <tr><td>updateTime</td><td>{@link Date}</td><td>update_time</td><td>date</td><td>&nbsp;</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2014-8-26
 * @email yudi@sina.com
 */
public class PurchaseGdQueue implements Serializable {

    public static String GD_STATUS_INIT                = "0";
    public static String GD_STATUS_SEND_GVS            = "1";
    public static String GD_STATUS_SEND_GVS_FAILD      = "2";
    public static String GD_STATUS_ENTER_INVOICENUMBER = "3";
    public static String GD_STATUS_SEND_SAP            = "4";
    public static String GD_STATUS_SEND_SAP_FAILD      = "5";
    public static String GD_STATUS_CANCEL              = "6";

    private Integer      id;

    /**
     * 获取 自增咧。
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 设置 自增咧。
     *
     * @param value 属性值
     */
    public void setId(Integer value) {
        this.id = value;
    }

    private String ordersn;

    /**
     * 获取 网单号。
     */
    public String getOrdersn() {
        return this.ordersn;
    }

    /**
     * 设置 网单号。
     *
     * @param value 属性值
     */
    public void setOrdersn(String value) {
        this.ordersn = value;
    }

    private Integer quantity;

    /**
     * 获取 采购数量。
     */
    public Integer getQuantity() {
        return this.quantity;
    }

    /**
     * 设置 采购数量。
     *
     * @param value 属性值
     */
    public void setQuantity(Integer value) {
        this.quantity = value;
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

    private String status;

    /**
     * 获取 0:初始状态。
     *
     * <p>
     *   1：已发GVS<br />
     *   2： 传GVS失败<br />
     *   3： 已经录入快递单号<br />
     *   4：传SAP系统<br />
     *   5： 传SAP失败<br />
     * 
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * 设置 0:初始状态。
     *
     * <p>
     *   1：已发GVS<br />
     *   2： 传GVS失败<br />
     *   3： 已经录入快递单号<br />
     *   4：传SAP系统<br />
     *   5： 传SAP失败<br />
     * 
     *
     * @param value 属性值
     */
    public void setStatus(String value) {
        this.status = value;
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