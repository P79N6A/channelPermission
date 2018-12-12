package com.haier.eis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Table: <strong>vom_in_out_store_order</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>receivedId</td><td>{@link Integer}</td><td>received_id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>orderNo</td><td>{@link String}</td><td>order_no</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>expNo</td><td>{@link String}</td><td>exp_no</td><td>varchar</td><td>运单号/快递单号</td></tr>
 *   <tr><td>busType</td><td>{@link Integer}</td><td>bus_type</td><td>int</td><td>业务类型:1出库 2入库</td></tr>
 *   <tr><td>orderType</td><td>{@link Integer}</td><td>order_type</td><td>int</td><td>订单类型：1.采购入库 2.销售出库 3.退货入库 4.取件 5.普通出库（自提）<br />   6.调拨 7.第三方运输订单 8.客户调货<br /></td></tr>
 *   <tr><td>outInDate</td><td>{@link Date}</td><td>out_in_date</td><td>datetime</td><td>出入库时间</td></tr>
 *   <tr><td>storeCode</td><td>{@link String}</td><td>store_code</td><td>varchar</td><td>仓库：日日顺C码</td></tr>
 *   <tr><td>isComplete</td><td>{@link Integer}</td><td>is_complete</td><td>int</td><td>完成状态：订单是否全部完成 1.已完成 2.未完成</td></tr>
 *   <tr><td>remark</td><td>{@link String}</td><td>remark</td><td>varchar</td><td>备注</td></tr>
 *   <tr><td>certification</td><td>{@link String}</td><td>certification</td><td>varchar</td><td>les单据号</td></tr>
 *   <tr><td>remark1</td><td>{@link String}</td><td>remark1</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>remark2</td><td>{@link String}</td><td>remark2</td><td>char</td><td>&nbsp;</td></tr>
 *   <tr><td>remark3</td><td>{@link String}</td><td>remark3</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>processStatus</td><td>{@link Integer}</td><td>process_status</td><td>int</td><td>0:未处理 1：新增库存交易完成 2：财务处理完成</td></tr>
 *   <tr><td>delay</td><td>{@link Integer}</td><td>delay</td><td>int</td><td>0：不延后 1：延后处理 2：延后处理完成</td></tr>
 * </table>
 *
 * @author 吴坤洋
 * @date 2017-12-22
 * @email yudi@sina.com
 */
public class VomInOutStoreOrder implements Serializable {
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

    private String orderNo;

    public String getOrderNo() {
        return this.orderNo;
    }

    public void setOrderNo(String value) {
        this.orderNo = value;
    }

    private String expNo;

    /**
     * 获取 运单号/快递单号。
     */
    public String getExpNo() {
        return this.expNo;
    }

    /**
     * 设置 运单号/快递单号。
     *
     * @param value 属性值
     */
    public void setExpNo(String value) {
        this.expNo = value;
    }

    private Integer busType;

    /**
     * 获取 业务类型:1出库 2入库。
     */
    public Integer getBusType() {
        return this.busType;
    }

    /**
     * 设置 业务类型:1出库 2入库。
     *
     * @param value 属性值
     */
    public void setBusType(Integer value) {
        this.busType = value;
    }

    private Integer orderType;

    /**
     * 获取 订单类型：1.采购入库 2.销售出库 3.退货入库 4.取件 5.普通出库（自提）。
     *
     * <p>
     *    6.调拨 7.第三方运输订单 8.客户调货<br />
     * 
     */
    public Integer getOrderType() {
        return this.orderType;
    }

    /**
     * 设置 订单类型：1.采购入库 2.销售出库 3.退货入库 4.取件 5.普通出库（自提）。
     *
     * <p>
     *    6.调拨 7.第三方运输订单 8.客户调货<br />
     * 
     *
     * @param value 属性值
     */
    public void setOrderType(Integer value) {
        this.orderType = value;
    }

    private Date outInDate;

    /**
     * 获取 出入库时间。
     */
    public Date getOutInDate() {
        return this.outInDate;
    }

    /**
     * 设置 出入库时间。
     *
     * @param value 属性值
     */
    public void setOutInDate(Date value) {
        this.outInDate = value;
    }

    private String storeCode;

    /**
     * 获取 仓库：日日顺C码。
     */
    public String getStoreCode() {
        return this.storeCode;
    }

    /**
     * 设置 仓库：日日顺C码。
     *
     * @param value 属性值
     */
    public void setStoreCode(String value) {
        this.storeCode = value;
    }

    private Integer isComplete;

    /**
     * 获取 完成状态：订单是否全部完成 1.已完成 2.未完成。
     */
    public Integer getIsComplete() {
        return this.isComplete;
    }

    /**
     * 设置 完成状态：订单是否全部完成 1.已完成 2.未完成。
     *
     * @param value 属性值
     */
    public void setIsComplete(Integer value) {
        this.isComplete = value;
    }

    private String remark;

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

    private String certification;

    /**
     * 获取 les单据号。
     */
    public String getCertification() {
        return this.certification;
    }

    /**
     * 设置 les单据号。
     *
     * @param value 属性值
     */
    public void setCertification(String value) {
        this.certification = value;
    }

    private String remark1;

    public String getRemark1() {
        return this.remark1;
    }

    public void setRemark1(String value) {
        this.remark1 = value;
    }

    private String remark2;

    public String getRemark2() {
        return this.remark2;
    }

    public void setRemark2(String value) {
        this.remark2 = value;
    }

    private String remark3;

    public String getRemark3() {
        return this.remark3;
    }

    public void setRemark3(String value) {
        this.remark3 = value;
    }

    public static final Integer PROCESS_STATUS_NEW                             = 0;
    public static final Integer PROCESS_STATUS_STOCK_TRANSACTION_HAS_GENERATED = 1;
    public static final Integer PROCESS_STATUS_FINANCE_DONE                    = 2;
    public static final Integer PROCESS_STATUS_ERROR                           = -1;

    private Integer             processStatus;

    /**
     * 获取 0:未处理 1：新增库存交易完成 2：财务处理完成。
     */
    public Integer getProcessStatus() {
        return this.processStatus;
    }

    /**
     * 设置 0:未处理 1：新增库存交易完成 2：财务处理完成。
     *
     * @param value 属性值
     */
    public void setProcessStatus(Integer value) {
        this.processStatus = value;
    }

    public static final Integer DELAY_YES = 1;
    public static final Integer DELAY_NO  = 0;

    private Integer             delay;

    /**
     * 获取 0：不延后 1：延后处理 2：延后处理完成。
     */
    public Integer getDelay() {
        return this.delay;
    }

    /**
     * 设置 0：不延后 1：延后处理 2：延后处理完成。
     *
     * @param value 属性值
     */
    public void setDelay(Integer value) {
        this.delay = value;
    }

    private Date addTime;

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}