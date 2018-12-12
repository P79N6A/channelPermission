package com.haier.shop.model;

import java.io.Serializable;

/**
 * 订单全流程监控。
 *
 * <p>Table: <strong>OrderWorkflows</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>orderId</td><td>{@link Integer}</td><td>orderId</td><td>int</td><td>订单id</td></tr>
 *   <tr><td>orderProductId</td><td>{@link Integer}</td><td>orderProductId</td><td>int</td><td>订单商品id</td></tr>
 *   <tr><td>addTime</td><td>{@link Integer}</td><td>addTime</td><td>int</td><td>下单时间</td></tr>
 *   <tr><td>payTime</td><td>{@link Integer}</td><td>payTime</td><td>int</td><td>付款时间</td></tr>
 *   <tr><td>confirmTime</td><td>{@link Integer}</td><td>confirmTime</td><td>int</td><td>确认时间</td></tr>
 *   <tr><td>confirmPeople</td><td>{@link String}</td><td>confirmPeople</td><td>varchar</td><td>确认人</td></tr>
 *   <tr><td>sendHpTime</td><td>{@link Integer}</td><td>sendHpTime</td><td>int</td><td>同步hp时间</td></tr>
 *   <tr><td>hpAllotNetPointTime</td><td>{@link Integer}</td><td>hpAllotNetPointTime</td><td>int</td><td>hp分配网点时间</td></tr>
 *   <tr><td>lesShipping</td><td>{@link Integer}</td><td>lesShipping</td><td>int</td><td>les开提单时间</td></tr>
 *   <tr><td>lesShipTime</td><td>{@link Integer}</td><td>lesShipTime</td><td>int</td><td>Les出库时间</td></tr>
 *   <tr><td>netPointAcceptTime</td><td>{@link Integer}</td><td>netPointAcceptTime</td><td>int</td><td>网点接单时间</td></tr>
 *   <tr><td>netPointRefuseTime</td><td>{@link Integer}</td><td>netPointRefuseTime</td><td>int</td><td>网点拒绝时间</td></tr>
 *   <tr><td>netPointShipTime</td><td>{@link Integer}</td><td>netPointShipTime</td><td>int</td><td>网点出库时间</td></tr>
 *   <tr><td>userAcceptTime</td><td>{@link Integer}</td><td>userAcceptTime</td><td>int</td><td>用户签收时间</td></tr>
 *   <tr><td>userRefuseTime</td><td>{@link Integer}</td><td>userRefuseTime</td><td>int</td><td>用户拒绝时间</td></tr>
 *   <tr><td>receiptTime</td><td>{@link String}</td><td>receiptTime</td><td>varchar</td><td>开票时间</td></tr>
 *   <tr><td>finishTime</td><td>{@link Integer}</td><td>finishTime</td><td>int</td><td>订单完成时间</td></tr>
 *   <tr><td>finishPeople</td><td>{@link String}</td><td>finishPeople</td><td>varchar</td><td>完成关闭操作人</td></tr>
 *   <tr><td>cancelTime</td><td>{@link Integer}</td><td>cancelTime</td><td>int</td><td>订单取消时间</td></tr>
 *   <tr><td>cancelPeople</td><td>{@link String}</td><td>cancelPeople</td><td>varchar</td><td>取消操作人</td></tr>
 *   <tr><td>mustHpAllotNetPointTime</td><td>{@link Integer}</td><td>mustHpAllotNetPointTime</td><td>int</td><td>应派工完成时间</td></tr>
 *   <tr><td>ewHpAllotNetPointTime</td><td>{@link Integer}</td><td>ewHpAllotNetPointTime</td><td>int</td><td>预警派工完成时间</td></tr>
 *   <tr><td>mustLesShipping</td><td>{@link Integer}</td><td>mustLesShipping</td><td>int</td><td>应开单完成时间</td></tr>
 *   <tr><td>ewLesShipping</td><td>{@link Integer}</td><td>ewLesShipping</td><td>int</td><td>预警开单完成时间</td></tr>
 *   <tr><td>mustNetPointAcceptTime</td><td>{@link Integer}</td><td>mustNetPointAcceptTime</td><td>int</td><td>应送达网点时间</td></tr>
 *   <tr><td>ewNetPointAcceptTime</td><td>{@link Integer}</td><td>ewNetPointAcceptTime</td><td>int</td><td>预警送达网点时间</td></tr>
 *   <tr><td>mustUserAcceptTime</td><td>{@link Integer}</td><td>mustUserAcceptTime</td><td>int</td><td>应送达用户时间</td></tr>
 *   <tr><td>ewUserAcceptTime</td><td>{@link Integer}</td><td>ewUserAcceptTime</td><td>int</td><td>预警送达用户时间</td></tr>
 *   <tr><td>shippingMode</td><td>{@link String}</td><td>shippingMode</td><td>varchar</td><td>配送模式 B2C B2B2C</td></tr>
 *   <tr><td>shippingTime</td><td>{@link Integer}</td><td>shippingTime</td><td>int</td><td>配送时效 天</td></tr>
 *   <tr><td>isTimeoutFree</td><td>{@link Integer}</td><td>isTimeoutFree</td><td>tinyint</td><td>是否超时免单 0未设置 1是 2否</td></tr>
 *   <tr><td>netPointArriveTime</td><td>{@link Integer}</td><td>netPointArriveTime</td><td>int</td><td>物流送达网点时间</td></tr>
 *   <tr><td>mustConfirmTime</td><td>{@link Integer}</td><td>mustConfirmTime</td><td>int</td><td>订单应确认时间</td></tr>
 *   <tr><td>ewConfirmTime</td><td>{@link Integer}</td><td>ewConfirmTime</td><td>int</td><td>订单预警时间</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-5-23
 * @email yudi@sina.com
 */
public class OrderWorkflows implements Serializable {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 410662485421656460L;

    private Integer           id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer orderId;

    /**
     * 获取 订单id。
     */
    public Integer getOrderId() {
        return this.orderId;
    }

    /**
     * 设置 订单id。
     *
     * @param value 属性值
     */
    public void setOrderId(Integer value) {
        this.orderId = value;
    }

    private Integer orderProductId;

    /**
     * 获取 订单商品id。
     */
    public Integer getOrderProductId() {
        return this.orderProductId;
    }

    /**
     * 设置 订单商品id。
     *
     * @param value 属性值
     */
    public void setOrderProductId(Integer value) {
        this.orderProductId = value;
    }

    private Long addTime;

    /**
     * 获取 下单时间。
     */
    public Long getAddTime() {
        return this.addTime;
    }

    /**
     * 设置 下单时间。
     *
     * @param value 属性值
     */
    public void setAddTime(Long value) {
        this.addTime = value;
    }

    private Long payTime;

    /**
     * 获取 付款时间。
     */
    public Long getPayTime() {
        return this.payTime;
    }

    /**
     * 设置 付款时间。
     *
     * @param value 属性值
     */
    public void setPayTime(Long value) {
        this.payTime = value;
    }

    private Long confirmTime = 0L;

    /**
     * 获取 确认时间。
     */
    public Long getConfirmTime() {
        return this.confirmTime;
    }

    /**
     * 设置 确认时间。
     *
     * @param value 属性值
     */
    public void setConfirmTime(Long value) {
        this.confirmTime = value;
    }

    private String confirmPeople = "";

    /**
     * 获取 确认人。
     */
    public String getConfirmPeople() {
        return this.confirmPeople;
    }

    /**
     * 设置 确认人。
     *
     * @param value 属性值
     */
    public void setConfirmPeople(String value) {
        this.confirmPeople = value;
    }

    private Long sendHpTime = 0L;

    /**
     * 获取 同步hp时间。
     */
    public Long getSendHpTime() {
        return this.sendHpTime;
    }

    /**
     * 设置 同步hp时间。
     *
     * @param value 属性值
     */
    public void setSendHpTime(Long value) {
        this.sendHpTime = value;
    }

    private Long hpAllotNetPointTime = 0L;

    /**
     * 获取 hp分配网点时间。
     */
    public Long getHpAllotNetPointTime() {
        return this.hpAllotNetPointTime;
    }

    /**
     * 设置 hp分配网点时间。
     *
     * @param value 属性值
     */
    public void setHpAllotNetPointTime(Long value) {
        this.hpAllotNetPointTime = value;
    }

    private Long lesShipping = 0L;

    /**
     * 获取 les开提单时间。
     */
    public Long getLesShipping() {
        return this.lesShipping;
    }

    /**
     * 设置 les开提单时间。
     *
     * @param value 属性值
     */
    public void setLesShipping(Long value) {
        this.lesShipping = value;
    }

    private Long lesShipTime = 0L;

    /**
     * 获取 Les出库时间。
     */
    public Long getLesShipTime() {
        return this.lesShipTime;
    }

    /**
     * 设置 Les出库时间。
     *
     * @param value 属性值
     */
    public void setLesShipTime(Long value) {
        this.lesShipTime = value;
    }

    private Long netPointAcceptTime = 0L;

    /**
     * 获取 网点接单时间。
     */
    public Long getNetPointAcceptTime() {
        return this.netPointAcceptTime;
    }

    /**
     * 设置 网点接单时间。
     *
     * @param value 属性值
     */
    public void setNetPointAcceptTime(Long value) {
        this.netPointAcceptTime = value;
    }

    private Long netPointRefuseTime = 0L;

    /**
     * 获取 网点拒绝时间。
     */
    public Long getNetPointRefuseTime() {
        return this.netPointRefuseTime;
    }

    /**
     * 设置 网点拒绝时间。
     *
     * @param value 属性值
     */
    public void setNetPointRefuseTime(Long value) {
        this.netPointRefuseTime = value;
    }

    private Long netPointShipTime = 0L;

    /**
     * 获取 网点出库时间。
     */
    public Long getNetPointShipTime() {
        return this.netPointShipTime;
    }

    /**
     * 设置 网点出库时间。
     *
     * @param value 属性值
     */
    public void setNetPointShipTime(Long value) {
        this.netPointShipTime = value;
    }

    private Long userAcceptTime = 0L;

    /**
     * 获取 用户签收时间。
     */
    public Long getUserAcceptTime() {
        return this.userAcceptTime;
    }

    /**
     * 设置 用户签收时间。
     *
     * @param value 属性值
     */
    public void setUserAcceptTime(Long value) {
        this.userAcceptTime = value;
    }

    private Long userRefuseTime = 0L;

    /**
     * 获取 用户拒绝时间。
     */
    public Long getUserRefuseTime() {
        return this.userRefuseTime;
    }

    /**
     * 设置 用户拒绝时间。
     *
     * @param value 属性值
     */
    public void setUserRefuseTime(Long value) {
        this.userRefuseTime = value;
    }

    private String receiptTime = "";

    /**
     * 获取 开票时间。
     */
    public String getReceiptTime() {
        return this.receiptTime;
    }

    /**
     * 设置 开票时间。
     *
     * @param value 属性值
     */
    public void setReceiptTime(String value) {
        this.receiptTime = value;
    }

    private Long finishTime = 0L;

    /**
     * 获取 订单完成时间。
     */
    public Long getFinishTime() {
        return this.finishTime;
    }

    /**
     * 设置 订单完成时间。
     *
     * @param value 属性值
     */
    public void setFinishTime(Long value) {
        this.finishTime = value;
    }

    private String finishPeople = "";

    /**
     * 获取 完成关闭操作人。
     */
    public String getFinishPeople() {
        return this.finishPeople;
    }

    /**
     * 设置 完成关闭操作人。
     *
     * @param value 属性值
     */
    public void setFinishPeople(String value) {
        this.finishPeople = value;
    }

    private Long cancelTime = 0L;

    /**
     * 获取 订单取消时间。
     */
    public Long getCancelTime() {
        return this.cancelTime;
    }

    /**
     * 设置 订单取消时间。
     *
     * @param value 属性值
     */
    public void setCancelTime(Long value) {
        this.cancelTime = value;
    }

    private String cancelPeople = "";

    /**
     * 获取 取消操作人。
     */
    public String getCancelPeople() {
        return this.cancelPeople;
    }

    /**
     * 设置 取消操作人。
     *
     * @param value 属性值
     */
    public void setCancelPeople(String value) {
        this.cancelPeople = value;
    }

    private Long mustHpAllotNetPointTime = 0L;

    /**
     * 获取 应派工完成时间。
     */
    public Long getMustHpAllotNetPointTime() {
        return this.mustHpAllotNetPointTime;
    }

    /**
     * 设置 应派工完成时间。
     *
     * @param value 属性值
     */
    public void setMustHpAllotNetPointTime(Long value) {
        this.mustHpAllotNetPointTime = value;
    }

    private Long ewHpAllotNetPointTime = 0L;

    /**
     * 获取 预警派工完成时间。
     */
    public Long getEwHpAllotNetPointTime() {
        return this.ewHpAllotNetPointTime;
    }

    /**
     * 设置 预警派工完成时间。
     *
     * @param value 属性值
     */
    public void setEwHpAllotNetPointTime(Long value) {
        this.ewHpAllotNetPointTime = value;
    }

    private Long mustLesShipping = 0L;

    /**
     * 获取 应开单完成时间。
     */
    public Long getMustLesShipping() {
        return this.mustLesShipping;
    }

    /**
     * 设置 应开单完成时间。
     *
     * @param value 属性值
     */
    public void setMustLesShipping(Long value) {
        this.mustLesShipping = value;
    }

    private Long ewLesShipping = 0L;

    /**
     * 获取 预警开单完成时间。
     */
    public Long getEwLesShipping() {
        return this.ewLesShipping;
    }

    /**
     * 设置 预警开单完成时间。
     *
     * @param value 属性值
     */
    public void setEwLesShipping(Long value) {
        this.ewLesShipping = value;
    }

    private Long mustNetPointAcceptTime = 0L;

    /**
     * 获取 应送达网点时间。
     */
    public Long getMustNetPointAcceptTime() {
        return this.mustNetPointAcceptTime;
    }

    /**
     * 设置 应送达网点时间。
     *
     * @param value 属性值
     */
    public void setMustNetPointAcceptTime(Long value) {
        this.mustNetPointAcceptTime = value;
    }

    private Long ewNetPointAcceptTime = 0L;

    /**
     * 获取 预警送达网点时间。
     */
    public Long getEwNetPointAcceptTime() {
        return this.ewNetPointAcceptTime;
    }

    /**
     * 设置 预警送达网点时间。
     *
     * @param value 属性值
     */
    public void setEwNetPointAcceptTime(Long value) {
        this.ewNetPointAcceptTime = value;
    }

    private Long mustUserAcceptTime = 0L;

    /**
     * 获取 应送达用户时间。
     */
    public Long getMustUserAcceptTime() {
        return this.mustUserAcceptTime;
    }

    /**
     * 设置 应送达用户时间。
     *
     * @param value 属性值
     */
    public void setMustUserAcceptTime(Long value) {
        this.mustUserAcceptTime = value;
    }

    private Long ewUserAcceptTime = 0L;

    /**
     * 获取 预警送达用户时间。
     */
    public Long getEwUserAcceptTime() {
        return this.ewUserAcceptTime;
    }

    /**
     * 设置 预警送达用户时间。
     *
     * @param value 属性值
     */
    public void setEwUserAcceptTime(Long value) {
        this.ewUserAcceptTime = value;
    }

    private String shippingMode;

    /**
     * 获取 配送模式 B2C B2B2C。
     */
    public String getShippingMode() {
        return this.shippingMode;
    }

    /**
     * 设置 配送模式 B2C B2B2C。
     *
     * @param value 属性值
     */
    public void setShippingMode(String value) {
        this.shippingMode = value;
    }

    private Integer shippingTime = 0;

    /**
     * 获取 配送时效 天。
     */
    public Integer getShippingTime() {
        return this.shippingTime;
    }

    /**
     * 设置 配送时效 天。
     *
     * @param value 属性值
     */
    public void setShippingTime(Integer value) {
        this.shippingTime = value;
    }

    private Integer isTimeoutFree = 0;

    /**
     * 获取 是否超时免单 0未设置 1是 2否。
     */
    public Integer getIsTimeoutFree() {
        return this.isTimeoutFree;
    }

    /**
     * 设置 是否超时免单 0未设置 1是 2否。
     *
     * @param value 属性值
     */
    public void setIsTimeoutFree(Integer value) {
        this.isTimeoutFree = value;
    }

    private Long netPointArriveTime = 0L;

    /**
     * 获取 物流送达网点时间。
     */
    public Long getNetPointArriveTime() {
        return this.netPointArriveTime;
    }

    /**
     * 设置 物流送达网点时间。
     *
     * @param value 属性值
     */
    public void setNetPointArriveTime(Long value) {
        this.netPointArriveTime = value;
    }

    private Integer mustConfirmTime = 0;

    /**
     * 获取 订单应确认时间。
     */
    public Integer getMustConfirmTime() {
        return this.mustConfirmTime;
    }

    /**
     * 设置 订单应确认时间。
     *
     * @param value 属性值
     */
    public void setMustConfirmTime(Integer value) {
        this.mustConfirmTime = value;
    }

    private Integer ewConfirmTime = 0;

    /**
     * 获取 订单预警时间。
     */
    public Integer getEwConfirmTime() {
        return this.ewConfirmTime;
    }

    /**
     * 设置 订单预警时间。
     *
     * @param value 属性值
     */
    public void setEwConfirmTime(Integer value) {
        this.ewConfirmTime = value;
    }
}