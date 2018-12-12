package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/*
*
* 作者:张波
* 2017/12/20
* */
public class ExternalOrders implements Serializable {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1241054316074789039L;
    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer configId;

    /**
     * 获取 配置ID
     */
    public Integer getConfigId() {
        return this.configId;
    }

    /**
     * 设置 配置ID
     *
     * @param value 属性值
     */
    public void setConfigId(Integer value) {
        this.configId = value;
    }

    private String orderSn;

    /**
     * 获取 系统订单使用的SN
     */
    public String getOrderSn() {
        return this.orderSn;
    }

    /**
     * 设置 系统订单使用的SN
     *
     * @param value 属性值
     */
    public void setOrderSn(String value) {
        this.orderSn = value;
    }

    private String sourceOrderSn;

    /**
     * 获取 外部订单号
     */
    public String getSourceOrderSn() {
        return this.sourceOrderSn;
    }

    /**
     * 设置 外部订单号
     *
     * @param value 属性值
     */
    public void setSourceOrderSn(String value) {
        this.sourceOrderSn = value;
    }

    private Integer orderId;

    /**
     * 获取 系统订单ID
     */
    public Integer getOrderId() {
        return this.orderId;
    }

    /**
     * 设置 系统订单ID
     *
     * @param value 属性值
     */
    public void setOrderId(Integer value) {
        this.orderId = value;
    }

    private Integer orderState;

    /**
     * 获取 同步状态
     */
    public Integer getOrderState() {
        return this.orderState;
    }

    /**
     * 设置 同步状态
     *
     * @param value 属性值
     */
    public void setOrderState(Integer value) {
        this.orderState = value;
    }

    private String errorLog;

    /**
     * 获取 错误日志
     */
    public String getErrorLog() {
        return this.errorLog;
    }

    /**
     * 设置 错误日志
     *
     * @param value 属性值
     */
    public void setErrorLog(String value) {
        this.errorLog = value;
    }

    private Long syncTime;

    /**
     * 获取 最后同步时间
     */
    public Long getSyncTime() {
        return this.syncTime;
    }

    /**
     * 设置 最后同步时间
     *
     * @param value 属性值
     */
    public void setSyncTime(Long value) {
        this.syncTime = value;
    }

    private Integer syncCount;

    /**
     * 获取 同步的次数
     */
    public Integer getSyncCount() {
        return this.syncCount;
    }

    /**
     * 设置 同步的次数
     *
     * @param value 属性值
     */
    public void setSyncCount(Integer value) {
        this.syncCount = value;
    }

    private String addTime;

    /**
     * 获取 写入时间
     */
    public String getAddTime() {
        return this.addTime;
    }

    /**
     * 设置 写入时间
     *
     * @param value 属性值
     */
    public void setAddTime(String value) {
        this.addTime = value;
    }

    private String creator;

    /**
     * 获取 写入人
     */
    public String getCreator() {
        return this.creator;
    }

    /**
     * 设置 写入人
     *
     * @param value 属性值
     */
    public void setCreator(String value) {
        this.creator = value;
    }

    private String type;

    /**
     * 获取 交易类型,值参考淘宝
     */
    public String getType() {
        return this.type;
    }

    /**
     * 设置 交易类型,值参考淘宝
     *
     * @param value 属性值
     */
    public void setType(String value) {
        this.type = value;
    }

    private String stepTradeStatus;

    /**
     * 获取 分阶段付款的订单状态,值参考淘宝
     */
    public String getStepTradeStatus() {
        return this.stepTradeStatus;
    }

    /**
     * 设置 分阶段付款的订单状态,值参考淘宝
     *
     * @param value 属性值
     */
    public void setStepTradeStatus(String value) {
        this.stepTradeStatus = value;
    }

    private BigDecimal stepPaidFee;

    /**
     * 获取 分阶段付款的已付金额
     */
    public BigDecimal getStepPaidFee() {
        return this.stepPaidFee;
    }

    /**
     * 设置 分阶段付款的已付金额
     *
     * @param value 属性值
     */
    public void setStepPaidFee(BigDecimal value) {
        this.stepPaidFee = value;
    }

    private Integer taobaoGroupId;

    /**
     * 获取 商城淘宝万人团设置ID
     */
    public Integer getTaobaoGroupId() {
        return this.taobaoGroupId;
    }

    /**
     * 设置 商城淘宝万人团设置ID
     *
     * @param value 属性值
     */
    public void setTaobaoGroupId(Integer value) {
        this.taobaoGroupId = value;
    }

    private BigDecimal shouldPaidFee;

    /**
     * 获取 商城淘宝万人团定金加尾款金额
     */
    public BigDecimal getShouldPaidFee() {
        return this.shouldPaidFee;
    }

    /**
     * 设置 商城淘宝万人团定金加尾款金额
     *
     * @param value 属性值
     */
    public void setShouldPaidFee(BigDecimal value) {
        this.shouldPaidFee = value;
    }

    private Date taobaoModifiedTime;

    /**
     * 获取 淘宝交易修改时间
     */
    public Date getTaobaoModifiedTime() {
        return this.taobaoModifiedTime;
    }

    /**
     * 设置 淘宝交易修改时间
     *
     * @param value 属性值
     */
    public void setTaobaoModifiedTime(Date value) {
        this.taobaoModifiedTime = value;
    }

    private Integer hasShipped;

    /**
     * 获取 LES是否已发货
     */
    public Integer getHasShipped() {
        return this.hasShipped;
    }

    /**
     * 设置 LES是否已发货
     *
     * @param value 属性值
     */
    public void setHasShipped(Integer value) {
        this.hasShipped = value;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    private Date taobaoAddTime;

    private Date taobaoPayTime;

    public Date getTaobaoAddTime() {
        return taobaoAddTime;
    }

    public void setTaobaoAddTime(Date taobaoAddTime) {
        this.taobaoAddTime = taobaoAddTime;
    }

    public Date getTaobaoPayTime() {
        return taobaoPayTime;
    }

    public void setTaobaoPayTime(Date taobaoPayTime) {
        this.taobaoPayTime = taobaoPayTime;
    }

    private int orderStatus;//订单处理状态 1:待处理发票信息 2:已处理发票信息


    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    private Long fenxiaoId; //分销ID

    public Long getFenxiaoId() {
        return fenxiaoId;
    }

    public void setFenxiaoId(Long fenxiaoId) {
        this.fenxiaoId = fenxiaoId;
    }
}
