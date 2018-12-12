package com.haier.eis.model;

import java.io.Serializable;
import java.util.Date;

/*
*
* 作者:张波
* 2017/12/22
* */

/**
 * <p>Table: <strong>vom_shipping_status</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>receivedId</td><td>{@link Integer}</td><td>received_id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>storeCode</td><td>{@link String}</td><td>store_code</td><td>varchar</td><td>仓库编码：日日顺C码</td></tr>
 *   <tr><td>orderNo</td><td>{@link String}</td><td>order_no</td><td>varchar</td><td>订单号</td></tr>
 *   <tr><td>expNo</td><td>{@link String}</td><td>exp_no</td><td>varchar</td><td>快递单号</td></tr>
 *   <tr><td>operator</td><td>{@link String}</td><td>operator</td><td>varchar</td><td>操作人</td></tr>
 *   <tr><td>operContact</td><td>{@link String}</td><td>oper_contact</td><td>varchar</td><td>操作人连心方式</td></tr>
 *   <tr><td>operDate</td><td>{@link Date}</td><td>oper_date</td><td>datetime</td><td>操作时间</td></tr>
 *   <tr><td>status</td><td>{@link String}</td><td>status</td><td>varchar</td><td>状态：WMS_ACCEPT -接单<br />WMS_FAILED -拒单<br />TMS_ACCEPT 揽收<br />TMS_REJECT -揽收失败<br />TMS_DELIVERING -派送<br />TMS_STATION_IN -分站进<br />TMS_STATION_OUT -分站出<br />TMS_ERROR -异常<br />TMS_SIGN -签收成功<br />TMS_FAILED -拒签<br />TMS_RETURN-退货入库<br />TMS_result-鉴定结果<br /></td></tr>
 *   <tr><td>content</td><td>{@link String}</td><td>content</td><td>varchar</td><td>状态说明</td></tr>
 *   <tr><td>remark</td><td>{@link String}</td><td>remark</td><td>varchar</td><td>备注</td></tr>
 *   <tr><td>processStatus</td><td>{@link Integer}</td><td>process_status</td><td>int</td><td>&nbsp;</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2014-7-28
 * @email yudi@sina.com
 */

public class VomShippingStatus implements Serializable {
    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer receivedId;

    public Integer getReceivedId() {
        return this.receivedId;
    }

    public void setReceivedId(Integer value) {
        this.receivedId = value;
    }

    private String storeCode;

    /**
     * 获取 仓库编码：日日顺C码。
     */
    public String getStoreCode() {
        return this.storeCode;
    }

    /**
     * 设置 仓库编码：日日顺C码。
     *
     * @param value 属性值
     */
    public void setStoreCode(String value) {
        this.storeCode = value;
    }

    private String orderNo;

    /**
     * 获取 订单号。
     */
    public String getOrderNo() {
        return this.orderNo;
    }

    /**
     * 设置 订单号。
     *
     * @param value 属性值
     */
    public void setOrderNo(String value) {
        this.orderNo = value;
    }

    private String expNo;

    /**
     * 获取 快递单号。
     */
    public String getExpNo() {
        return this.expNo;
    }

    /**
     * 设置 快递单号。
     *
     * @param value 属性值
     */
    public void setExpNo(String value) {
        this.expNo = value;
    }

    private String operator = "";

    /**
     * 获取 操作人。
     */
    public String getOperator() {
        return this.operator;
    }

    /**
     * 设置 操作人。
     *
     * @param value 属性值
     */
    public void setOperator(String value) {
        this.operator = value;
    }

    private String operContact;

    /**
     * 获取 操作人连心方式。
     */
    public String getOperContact() {
        return this.operContact;
    }

    /**
     * 设置 操作人连心方式。
     *
     * @param value 属性值
     */
    public void setOperContact(String value) {
        this.operContact = value;
    }

    private Date operDate;

    /**
     * 获取 操作时间。
     */
    public Date getOperDate() {
        return this.operDate;
    }

    /**
     * 设置 操作时间。
     *
     * @param value 属性值
     */
    public void setOperDate(Date value) {
        this.operDate = value;
    }

    private String status;

    /**
     * 获取 状态：WMS_ACCEPT -接单。
     *
     * <p>
     * WMS_FAILED -拒单<br />
     * TMS_ACCEPT 揽收<br />
     * TMS_REJECT -揽收失败<br />
     * TMS_DELIVERING -派送<br />
     * TMS_STATION_IN -分站进<br />
     * TMS_STATION_OUT -分站出<br />
     * TMS_ERROR -异常<br />
     * TMS_SIGN -签收成功<br />
     * TMS_FAILED -拒签<br />
     * TMS_RETURN-退货入库<br />
     * TMS_result-鉴定结果<br />
     *
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * 设置 状态：WMS_ACCEPT -接单。
     *
     * <p>
     * WMS_FAILED -拒单<br />
     * TMS_ACCEPT 揽收<br />
     * TMS_REJECT -揽收失败<br />
     * TMS_DELIVERING -派送<br />
     * TMS_STATION_IN -分站进<br />
     * TMS_STATION_OUT -分站出<br />
     * TMS_ERROR -异常<br />
     * TMS_SIGN -签收成功<br />
     * TMS_FAILED -拒签<br />
     * TMS_RETURN-退货入库<br />
     * TMS_result-鉴定结果<br />
     *
     *
     * @param value 属性值
     */
    public void setStatus(String value) {
        this.status = value;
    }

    private String content;

    /**
     * 获取 状态说明。
     */
    public String getContent() {
        return this.content;
    }

    /**
     * 设置 状态说明。
     *
     * @param value 属性值
     */
    public void setContent(String value) {
        this.content = value;
    }

    private String remark = "";

    /**
     * 获取 备注。
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置 备注。
     *
     * @param value 属性值
     */
    public void setRemark(String value) {
        this.remark = value;
    }

    public static final Integer PROCESS_STATUS_WAIT  = 0;
    public static final Integer PROCESS_STATUS_DOWN  = 1;
    public static final Integer PROCESS_STATUS_ERROR = -1;

    private Integer             processStatus;

    public Integer getProcessStatus() {
        return this.processStatus;
    }

    public void setProcessStatus(Integer value) {
        this.processStatus = value;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private Date addTime;

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

}

