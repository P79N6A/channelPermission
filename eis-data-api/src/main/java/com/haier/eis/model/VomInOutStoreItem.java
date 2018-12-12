package com.haier.eis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Table: <strong>vom_in_out_store_item</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>orderId</td><td>{@link Integer}</td><td>order_id</td><td>int</td><td>关联的vom_in_out_store_order id</td></tr>
 *   <tr><td>itemNo</td><td>{@link String}</td><td>item_no</td><td>varchar</td><td>原订单行号</td></tr>
 *   <tr><td>storageType</td><td>{@link String}</td><td>storage_type</td><td>varchar</td><td>产品批次</td></tr>
 *   <tr><td>productCode</td><td>{@link String}</td><td>product_code</td><td>varchar</td><td>客户产品编号</td></tr>
 *   <tr><td>hrCode</td><td>{@link String}</td><td>hr_code</td><td>varchar</td><td>海尔产品编号</td></tr>
 *   <tr><td>proDes</td><td>{@link String}</td><td>pro_des</td><td>varchar</td><td>产品描述</td></tr>
 *   <tr><td>number</td><td>{@link Integer}</td><td>number</td><td>int</td><td>数量</td></tr>
 *   <tr><td>isComplete</td><td>{@link Integer}</td><td>is_complete</td><td>int</td><td>完成状态：订单是否全部完成 1.已完成 2.未完成</td></tr>
 *   <tr><td>volume</td><td>{@link String}</td><td>volume</td><td>varchar</td><td>体积</td></tr>
 *   <tr><td>weight</td><td>{@link String}</td><td>weight</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>remark</td><td>{@link String}</td><td>remark</td><td>varchar</td><td>备注</td></tr>
 *   <tr><td>remark1</td><td>{@link String}</td><td>remark1</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>remark2</td><td>{@link String}</td><td>remark2</td><td>char</td><td>&nbsp;</td></tr>
 *   <tr><td>remark3</td><td>{@link String}</td><td>remark3</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>processStatus</td><td>{@link Integer}</td><td>process_status</td><td>int</td><td>处理状态：0：未处理 1：新增库存交易完成</td></tr>
 * </table>
 *
 * @author 吴坤洋
 * @date 2017-12-22
 * @email yudi@sina.com
 */
public class VomInOutStoreItem implements Serializable {
    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer orderId;

    /**
     * 获取 关联的vom_in_out_store_order id。
     */
    public Integer getOrderId() {
        return this.orderId;
    }

    /**
     * 设置 关联的vom_in_out_store_order id。
     *
     * @param value 属性值
     */
    public void setOrderId(Integer value) {
        this.orderId = value;
    }

    private String itemNo;

    /**
     * 获取 原订单行号。
     */
    public String getItemNo() {
        return this.itemNo;
    }

    /**
     * 设置 原订单行号。
     *
     * @param value 属性值
     */
    public void setItemNo(String value) {
        this.itemNo = value;
    }

    private String storageType;

    /**
     * 获取 产品批次。
     */
    public String getStorageType() {
        return this.storageType;
    }

    /**
     * 设置 产品批次。
     *
     * @param value 属性值
     */
    public void setStorageType(String value) {
        this.storageType = value;
    }

    private String productCode;

    /**
     * 获取 客户产品编号。
     */
    public String getProductCode() {
        return this.productCode;
    }

    /**
     * 设置 客户产品编号。
     *
     * @param value 属性值
     */
    public void setProductCode(String value) {
        this.productCode = value;
    }

    private String hrCode;

    /**
     * 获取 海尔产品编号。
     */
    public String getHrCode() {
        return this.hrCode;
    }

    /**
     * 设置 海尔产品编号。
     *
     * @param value 属性值
     */
    public void setHrCode(String value) {
        this.hrCode = value;
    }

    private String proDes;

    /**
     * 获取 产品描述。
     */
    public String getProDes() {
        return this.proDes;
    }

    /**
     * 设置 产品描述。
     *
     * @param value 属性值
     */
    public void setProDes(String value) {
        this.proDes = value;
    }

    private Integer number;

    /**
     * 获取 数量。
     */
    public Integer getNumber() {
        return this.number;
    }

    /**
     * 设置 数量。
     *
     * @param value 属性值
     */
    public void setNumber(Integer value) {
        this.number = value;
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

    private String volume;

    /**
     * 获取 体积。
     */
    public String getVolume() {
        return this.volume;
    }

    /**
     * 设置 体积。
     *
     * @param value 属性值
     */
    public void setVolume(String value) {
        this.volume = value;
    }

    private String weight;

    public String getWeight() {
        return this.weight;
    }

    public void setWeight(String value) {
        this.weight = value;
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

    private Integer processStatus;

    /**
     * 获取 处理状态：0：未处理 1：新增库存交易完成。
     */
    public Integer getProcessStatus() {
        return this.processStatus;
    }

    /**
     * 设置 处理状态：0：未处理 1：新增库存交易完成。
     *
     * @param value 属性值
     */
    public void setProcessStatus(Integer value) {
        this.processStatus = value;
    }


    private Date addTime;

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTIme(Date addTime) {
        this.addTime = addTime;
    }
}