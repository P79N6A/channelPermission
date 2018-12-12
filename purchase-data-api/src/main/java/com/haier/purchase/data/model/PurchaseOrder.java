package com.haier.purchase.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>Table: <strong>purchase_order</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>poId</td><td>{@link Integer}</td><td>po_id</td><td>int</td><td>自增ID</td></tr>
 *   <tr><td>poNo</td><td>{@link String}</td><td>po_no</td><td>varchar</td><td>采购订单编号</td></tr>
 *   <tr><td>channelCode</td><td>{@link String}</td><td>channel_code</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>suplier</td><td>{@link String}</td><td>suplier</td><td>varchar</td><td>供应商</td></tr>
 *   <tr><td>soldTo</td><td>{@link String}</td><td>sold_to</td><td>varchar</td><td>售达方</td></tr>
 *   <tr><td>deliveryTo</td><td>{@link String}</td><td>delivery_to</td><td>varchar</td><td>送达方</td></tr>
 *   <tr><td>amount</td><td>{@link BigDecimal}</td><td>amount</td><td>decimal</td><td>采购订单金额（2位小数）</td></tr>
 *   <tr><td>type</td><td>{@link String}</td><td>type</td><td>varchar</td><td>采购订单类型(ZBCR，采购入库订单)</td></tr>
 *   <tr><td>receiver</td><td>{@link String}</td><td>receiver</td><td>varchar</td><td>收货人姓名</td></tr>
 *   <tr><td>mobile</td><td>{@link String}</td><td>mobile</td><td>varchar</td><td>收货人移动电话</td></tr>
 *   <tr><td>telephone</td><td>{@link String}</td><td>telephone</td><td>varchar</td><td>收货人固定电话</td></tr>
 *   <tr><td>province</td><td>{@link String}</td><td>province</td><td>varchar</td><td>收货人所在省</td></tr>
 *   <tr><td>city</td><td>{@link String}</td><td>city</td><td>varchar</td><td>收货人所在市</td></tr>
 *   <tr><td>county</td><td>{@link String}</td><td>county</td><td>varchar</td><td>收货人所在区/县</td></tr>
 *   <tr><td>address</td><td>{@link String}</td><td>address</td><td>varchar</td><td>收货人地址</td></tr>
 *   <tr><td>status</td><td>{@link Integer}</td><td>status</td><td>smallint</td><td>采购订单状态，100：新建 200：完成</td></tr>
 *   <tr><td>createTime</td><td>{@link Date}</td><td>create_time</td><td>datetime</td><td>创建时间</td></tr>
 *   <tr><td>updateTime</td><td>{@link Date}</td><td>update_time</td><td>datetime</td><td>更新时间</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-6-8
 * @email yudi@sina.com
 */
public class PurchaseOrder implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -7101753819719613702L;
    private Integer           poId;

    /**
     * 获取 自增ID。
     */
    public Integer getPoId() {
        return this.poId;
    }

    /**
     * 设置 自增ID。
     *
     * @param value 属性值
     */
    public void setPoId(Integer value) {
        this.poId = value;
    }

    private String poNo;

    /**
     * 获取 采购订单编号。
     */
    public String getPoNo() {
        return this.poNo;
    }

    /**
     * 设置 采购订单编号。
     *
     * @param value 属性值
     */
    public void setPoNo(String value) {
        this.poNo = value;
    }

    private String channelCode;

    public String getChannelCode() {
        return this.channelCode;
    }

    public void setChannelCode(String value) {
        this.channelCode = value;
    }

    private String soldTo;

    /**
     * 获取 售达方。
     */
    public String getSoldTo() {
        return this.soldTo;
    }

    /**
     * 设置 售达方。
     *
     * @param value 属性值
     */
    public void setSoldTo(String value) {
        this.soldTo = value;
    }

    private String deliveryTo;

    /**
     * 获取 送达方。
     */
    public String getDeliveryTo() {
        return this.deliveryTo;
    }

    /**
     * 设置 送达方。
     *
     * @param value 属性值
     */
    public void setDeliveryTo(String value) {
        this.deliveryTo = value;
    }

    private BigDecimal amount;

    /**
     * 获取 采购订单金额（2位小数）。
     */
    public BigDecimal getAmount() {
        return this.amount;
    }

    /**
     * 设置 采购订单金额（2位小数）。
     *
     * @param value 属性值
     */
    public void setAmount(BigDecimal value) {
        this.amount = value;
    }

    private String type;

    /**
     * 获取 采购订单类型(ZBCR，采购入库订单)。
     */
    public String getType() {
        return this.type;
    }

    /**
     * 设置 采购订单类型(ZBCR，采购入库订单)。
     *
     * @param value 属性值
     */
    public void setType(String value) {
        this.type = value;
    }

    private String receiver;

    /**
     * 获取 收货人姓名。
     */
    public String getReceiver() {
        return this.receiver;
    }

    /**
     * 设置 收货人姓名。
     *
     * @param value 属性值
     */
    public void setReceiver(String value) {
        this.receiver = value;
    }

    private String mobile;

    /**
     * 获取 收货人移动电话。
     */
    public String getMobile() {
        return this.mobile;
    }

    /**
     * 设置 收货人移动电话。
     *
     * @param value 属性值
     */
    public void setMobile(String value) {
        this.mobile = value;
    }

    private String telephone;

    /**
     * 获取 收货人固定电话。
     */
    public String getTelephone() {
        return this.telephone;
    }

    /**
     * 设置 收货人固定电话。
     *
     * @param value 属性值
     */
    public void setTelephone(String value) {
        this.telephone = value;
    }

    private String province;

    /**
     * 获取 收货人所在省。
     */
    public String getProvince() {
        return this.province;
    }

    /**
     * 设置 收货人所在省。
     *
     * @param value 属性值
     */
    public void setProvince(String value) {
        this.province = value;
    }

    private String city;

    /**
     * 获取 收货人所在市。
     */
    public String getCity() {
        return this.city;
    }

    /**
     * 设置 收货人所在市。
     *
     * @param value 属性值
     */
    public void setCity(String value) {
        this.city = value;
    }

    private String county;

    /**
     * 获取 收货人所在区/县。
     */
    public String getCounty() {
        return this.county;
    }

    /**
     * 设置 收货人所在区/县。
     *
     * @param value 属性值
     */
    public void setCounty(String value) {
        this.county = value;
    }

    private String address;

    /**
     * 获取 收货人地址。
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * 设置 收货人地址。
     *
     * @param value 属性值
     */
    public void setAddress(String value) {
        this.address = value;
    }

    private Integer status;

    /**
     * 获取 采购订单状态，100：新建 200：完成。
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置 采购订单状态，100：新建 200：完成。
     *
     * @param value 属性值
     */
    public void setStatus(Integer value) {
        this.status = value;
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

    private String suplier;

    public String getSuplier() {
        return this.suplier;
    }

    public void setSuplier(String value) {
        this.suplier = value;
    }

    /**
     * 采购类型：3PL RRD
     */
    private String poType;

    public String getPoType() {
        return poType;
    }

    public void setPoType(String poType) {
        this.poType = poType;
    }

    /**
     * 送达方名称
     */
    private String deliveryToName;

    public String getDeliveryToName() {
        return deliveryToName;
    }

    public void setDeliveryToName(String deliveryToName) {
        this.deliveryToName = deliveryToName;
    }

    @Override
    public String toString() {
        return "PurchaseOrder [poId=" + poId + ", poNo=" + poNo + ",suplier" + suplier
               + ", soldTo=" + soldTo + ", deliveryTo=" + deliveryTo + ", amount=" + amount
               + ", type=" + type + ", receiver=" + receiver + ", mobile=" + mobile
               + ", telephone=" + telephone + ", province=" + province + ", city=" + city
               + ", county=" + county + ", address=" + address + ", status=" + status
               + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
    }
}