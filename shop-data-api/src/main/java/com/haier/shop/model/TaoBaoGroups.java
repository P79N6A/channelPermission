package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>Table: <strong>TaoBaoGroups</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>siteId</td><td>{@link Integer}</td><td>siteId</td><td>int</td><td>siteId</td></tr>
 *   <tr><td>groupName</td><td>{@link String}</td><td>groupName</td><td>varchar</td><td>团购名称</td></tr>
 *   <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>varchar</td><td>团购商品</td></tr>
 *   <tr><td>depositStartTime</td><td>{@link Integer}</td><td>depositStartTime</td><td>int</td><td>订金开始时间</td></tr>
 *   <tr><td>depositEndTime</td><td>{@link Integer}</td><td>depositEndTime</td><td>int</td><td>订金结束时间</td></tr>
 *   <tr><td>balanceStartTime</td><td>{@link Integer}</td><td>balanceStartTime</td><td>int</td><td>尾款开始时间</td></tr>
 *   <tr><td>balanceEndTime</td><td>{@link Integer}</td><td>balanceEndTime</td><td>int</td><td>尾款结束时间</td></tr>
 *   <tr><td>depositAmount</td><td>{@link BigDecimal}</td><td>depositAmount</td><td>decimal</td><td>订金金额</td></tr>
 *   <tr><td>balanceAmount</td><td>{@link BigDecimal}</td><td>balanceAmount</td><td>decimal</td><td>尾款金额</td></tr>
 *   <tr><td>status</td><td>{@link Integer}</td><td>status</td><td>tinyint</td><td>0 关闭 1开启</td></tr>
 *   <tr><td>shippingOpporunity</td><td>{@link Integer}</td><td>shippingOpporunity</td><td>tinyint</td><td>活动订单发货时机，0定金发货 1尾款发货</td></tr>
 * </table>
 * @author rongmai
 */
public class TaoBaoGroups implements Serializable {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 3648527859706570882L;

    private Integer           id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer siteId;

    /**
     * 获取 siteId
     */
    public Integer getSiteId() {
        return this.siteId;
    }

    /**
     * 设置 siteId
     * @param value 属性值
     */
    public void setSiteId(Integer value) {
        this.siteId = value;
    }

    private String groupName;

    /**
     * 获取 团购名称
     */
    public String getGroupName() {
        return this.groupName;
    }

    /**
     * 设置 团购名称
     * @param value 属性值
     */
    public void setGroupName(String value) {
        this.groupName = value;
    }

    private String sku;

    /**
     * 获取 团购商品
     */
    public String getSku() {
        return this.sku;
    }

    /**
     * 设置 团购商品
     * @param value 属性值
     */
    public void setSku(String value) {
        this.sku = value;
    }

    private Long depositStartTime;

    /**
     * 获取 订金开始时间
     */
    public Long getDepositStartTime() {
        return this.depositStartTime;
    }

    /**
     * 设置 订金开始时间
     * @param value 属性值
     */
    public void setDepositStartTime(Long value) {
        this.depositStartTime = value;
    }

    private Long depositEndTime;

    /**
     * 获取 订金结束时间
     */
    public Long getDepositEndTime() {
        return this.depositEndTime;
    }

    /**
     * 设置 订金结束时间
     * @param value 属性值
     */
    public void setDepositEndTime(Long value) {
        this.depositEndTime = value;
    }

    private Integer balanceStartTime;

    /**
     * 获取 尾款开始时间
     */
    public Integer getBalanceStartTime() {
        return this.balanceStartTime;
    }

    /**
     * 设置 尾款开始时间
     * @param value 属性值
     */
    public void setBalanceStartTime(Integer value) {
        this.balanceStartTime = value;
    }

    private Long balanceEndTime;

    /**
     * 获取 尾款结束时间
     */
    public Long getBalanceEndTime() {
        return this.balanceEndTime;
    }

    /**
     * 设置 尾款结束时间
     * @param value 属性值
     */
    public void setBalanceEndTime(Long value) {
        this.balanceEndTime = value;
    }

    private BigDecimal depositAmount;

    /**
     * 获取 订金金额
     */
    public BigDecimal getDepositAmount() {
        return this.depositAmount;
    }

    /**
     * 设置 订金金额
     * @param value 属性值
     */
    public void setDepositAmount(BigDecimal value) {
        this.depositAmount = value;
    }

    private BigDecimal balanceAmount;

    /**
     * 获取 尾款金额
     */
    public BigDecimal getBalanceAmount() {
        return this.balanceAmount;
    }

    /**
     * 设置 尾款金额
     * @param value 属性值
     */
    public void setBalanceAmount(BigDecimal value) {
        this.balanceAmount = value;
    }

    private Integer status;

    /**
     * 获取 0 关闭 1开启
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置 0 关闭 1开启
     * @param value 属性值
     */
    public void setStatus(Integer value) {
        this.status = value;
    }

    private Integer shippingOpporunity;

    /**
     * 获取 活动订单发货时机
     * 0 订金发货
     * 1 尾款发货
     */
    public Integer getShippingOpporunity() {
        return this.shippingOpporunity;
    }

    /**
     * 设置 活动订单发货时机
     * 0 订金发货
     * 1 尾款发货
     * @param value 属性值
     */
    public void setShippingOpporunity(Integer value) {
        this.shippingOpporunity = value;
    }

    /**
     * 定金尾款金额，根据sku对应网单，该字段金额生效
     */
    private String productSpecs;

    /**
     * 获取 定金尾款串 json格式
     * @param value 属性值
     */
    public String getProductSpecs() {
        return productSpecs;
    }

    /**
     * 设置 定金尾款串 json格式
     * @param value 属性值
     */
    public void setProductSpecs(String productSpecs) {
        this.productSpecs = productSpecs;
    }

}