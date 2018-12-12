package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;
public class GiftCardUsedLogs implements Serializable {
    private Integer id;
    private Integer siteId;
    private Integer giftCardId;
    private Integer giftCardNumberId;
    private Integer memberId;
    private String memberName;
    private String memberEmail;
    private Integer usedType;
    private BigDecimal beforeBalanceAmount;
    private BigDecimal amount;
    private BigDecimal afterBalanceAmount;
    private String systemRemark;
    private Integer addTime;
    private Integer orderProductId;
    private String orderSn;
    private Integer giftCardStoreId;
    private String giftCardStoreCode;
    private String remark;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getSiteId() {
        return siteId;
    }
    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }
    /**
     * 获取 礼品卡ID,其实也是商品ID
     */
    public Integer getGiftCardId() {
        return giftCardId;
    }
    
    /**
     * 设置 礼品卡ID,其实也是商品ID
     */
    public void setGiftCardId(Integer giftCardId) {
        this.giftCardId = giftCardId;
    }

    public Integer getGiftCardNumberId() {
        return giftCardNumberId;
    }

    public void setGiftCardNumberId(Integer giftCardNumberId) {
        this.giftCardNumberId = giftCardNumberId;
    }

    /**
     * 获取 会员ID,可能是购买礼品卡的会员ID，也可能是其它会员的ID
     */
    public Integer getMemberId() {
        return memberId;
    }
    /**
     * 设置 会员ID,可能是购买礼品卡的会员ID，也可能是其它会员的ID
     */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
    
    /**
     * 获取 会员名称
     */
    public String getMemberName() {
        return memberName;
    }
    
    /**
     * 设置 会员名称
     */
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
    
    /**
     * 获取 会员邮箱
     */
    public String getMemberEmail() {
        return memberEmail;
    }
    
    /**
     * 设置 会员邮箱
     */
    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }
    
    /**
     * 获取 1-减卡内余额,2-加卡内余额
     */
    public Integer getUsedType() {
        return usedType;
    }
    
    /**
     * 设置 1-减卡内余额,2-加卡内余额
     */
    public void setUsedType(Integer usedType) {
        this.usedType = usedType;
    }
    
    /**
     * 获取 发生前的卡内余额
     */
    public BigDecimal getBeforeBalanceAmount() {
        return beforeBalanceAmount;
    }
    
    /**
     * 设置 发生前的卡内余额
     */
    public void setBeforeBalanceAmount(BigDecimal beforeBalanceAmount) {
        this.beforeBalanceAmount = beforeBalanceAmount;
    }
    
    /**
     * 获取 发生的金额
     */
    public BigDecimal getAmount() {
        return amount;
    }
    
    /**
     * 设置 发生的金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    /**
     * 获取  发生后的卡内余额
     */
    public BigDecimal getAfterBalanceAmount() {
        return afterBalanceAmount;
    }
    
    /**
     * 设置  发生后的卡内余额
     */
    public void setAfterBalanceAmount(BigDecimal afterBalanceAmount) {
        this.afterBalanceAmount = afterBalanceAmount;
    }
    
    /**
     * 获取 日志备注-非用户输入
     */
    public String getSystemRemark() {
        return systemRemark;
    }
    
    /**
     * 设置 日志备注-非用户输入
     */
    public void setSystemRemark(String systemRemark) {
        this.systemRemark = systemRemark;
    }
    
    /**
     * 获取 发生时间
     */
    public Integer getAddTime() {
        return addTime;
    }

    /**
     * 设置 发生时间
     */
    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }
    
    /**
     * 获取 网单ID,前台使用礼品卡针对网单使用的
     */
    public Integer getOrderProductId() {
        return orderProductId;
    }

    /**
     * 设置 网单ID,前台使用礼品卡针对网单使用的
     */
    public void setOrderProductId(Integer orderProductId) {
        this.orderProductId = orderProductId;
    }

    /**
     * 获取 订单编号
     */
    public String getOrderSn() {
        return orderSn;
    }

    /**
     * 设置 订单编号
     */
    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    /**
     * 获取 礼品卡社区店ID
     */
    public Integer getGiftCardStoreId() {
        return giftCardStoreId;
    }

    /**
     * 设置 礼品卡社区店ID
     */
    public void setGiftCardStoreId(Integer giftCardStoreId) {
        this.giftCardStoreId = giftCardStoreId;
    }

    /**
     * 获取 礼品卡社区店编码
     */
    public String getGiftCardStoreCode() {
        return giftCardStoreCode;
    }

    /**
     * 设置 礼品卡社区店编码
     */
    public void setGiftCardStoreCode(String giftCardStoreCode) {
        this.giftCardStoreCode = giftCardStoreCode;
    }
    
    /**
     * 获取 户或社区店输入的备注信息
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置 户或社区店输入的备注信息
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}