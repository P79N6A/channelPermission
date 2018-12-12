package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class MemberMoneyLogs implements Serializable {
    private Integer id;
    private Integer memberId;
    private String memberName;
    private Integer orderId;
    private String orderSn;
    private Integer cOrderId;
    private String cOrderSn;
    private Integer createTime;
    private BigDecimal expense;
    private Integer logType;
    private BigDecimal money;
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取 用户ID
     */
    public Integer getMemberId() {
        return memberId;
    }

    /**
     * 设置 用户ID
     */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取 用户名
     */
    public String getMemberName() {
        return memberName;
    }

    /**
     * 设置 用户名
     */
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    /**
     * 获取 订单ID
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * 设置 订单ID
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取 订单号
     */
    public String getOrderSn() {
        return orderSn;
    }

    /**
     * 设置 订单号
     */
    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    /**
     * 获取 子订单ID
     */
    public Integer getcOrderId() {
        return cOrderId;
    }

    /**
     * 设置 子订单ID
     */
    public void setcOrderId(Integer cOrderId) {
        this.cOrderId = cOrderId;
    }

    /**
     * 获取 子订单号
     */
    public String getcOrderSn() {
        return cOrderSn;
    }

    /**
     * 设置 子订单号
     */
    public void setcOrderSn(String cOrderSn) {
        this.cOrderSn = cOrderSn;
    }

    /**
     * 获取 记录时间
     */
    public Integer getCreateTime() {
        return createTime;
    }

    /**
     * 设置 记录时间
     */
    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取 发生额
     */
    public BigDecimal getExpense() {
        return expense;
    }

    /**
     * 设置 发生额
     */
    public void setExpense(BigDecimal expense) {
        this.expense = expense;
    }

    /**
     * 获取 记录类型
     */
    public Integer getLogType() {
        return logType;
    }

    /**
     * 设置 记录类型
     */
    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    /**
     * 获取 账户余额
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * 设置 账户余额
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * 获取 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}