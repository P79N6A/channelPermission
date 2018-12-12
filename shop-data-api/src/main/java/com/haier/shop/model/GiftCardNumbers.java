package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class GiftCardNumbers implements Serializable {
    private Integer    id;

    private Integer    siteId;

    private Integer    productId;

    private Integer    orderId;

    private Integer    orderProductId;

    private String     cardNumber;

    private String     cardPassword;

    private Integer    amount;

    private BigDecimal balance;

    private Integer    tempMemberId;

    private Integer    memberId;

    private Integer    bindMemberId;

    private String     memberName;

    private String     memberEmail;

    private String     memberMobile;

    private Boolean    hasBind;

    private Integer    addTime;

    private Boolean    isActivation;

    private Integer    activationTime;

    private String     activationPerson;

    private Integer    exportTime;

    private String     exportPerson;

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
     * 获取 所属的礼品卡ID,即商品ID
     */
    public Integer getProductId() {
        return productId;
    }
    /**
     * 设置 所属的礼品卡ID,即商品ID
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(Integer orderProductId) {
        this.orderProductId = orderProductId;
    }
    /**
     * 获取 礼品卡号码
     */
    public String getCardNumber() {
        return cardNumber;
    }
    /**
     * 设置  礼品卡号码
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * 获取 礼品卡密码
     */
    public String getCardPassword() {
        return cardPassword;
    }

    /**
     * 设置 礼品卡密码
     */
    public void setCardPassword(String cardPassword) {
        this.cardPassword = cardPassword;
    }

    /**
     * 获取 礼品卡面额(正整数,冗余字段)
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * 设置 礼品卡面额(正整数,冗余字段)
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * 获取 礼品卡当前余额
     */
    public BigDecimal getBalance() {
        return balance;
    }
    /**
     * 设置 礼品卡当前余额
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    /**
     * 获取 临时使用的礼品卡的会员ID
     */
    public Integer getTempMemberId() {
        return tempMemberId;
    }
    /**
     * 设置 临时使用的礼品卡的会员ID
     */
    public void setTempMemberId(Integer tempMemberId) {
        this.tempMemberId = tempMemberId;
    }
    /**
     * 获取 会员ID，即最初购买此卡的会员ID
     */
    public Integer getMemberId() {
        return memberId;
    }
    /**
     * 设置 会员ID，即最初购买此卡的会员ID
     */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取 最终绑定的电子礼品卡ID
     */
    public Integer getBindMemberId() {
        return bindMemberId;
    }

    /**
     * 设置 最终绑定的电子礼品卡ID
     */
    public void setBindMemberId(Integer bindMemberId) {
        this.bindMemberId = bindMemberId;
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

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }

    /**
     *  获取 是否已绑定,1-是,2-否
     */
    public Boolean getHasBind() {
        return hasBind;
    }

    /**
     * 设置 是否已绑定,1-是,2-否
     */
    public void setHasBind(Boolean hasBind) {
        this.hasBind = hasBind;
    }

    /**
     * 获取 导出时间
     */
    public Integer getAddTime() {
        return addTime;
    }

    /**
     * 设置 导出时间
     */
    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    /**
     * 获取 是否已激活1-是,0-否
     */
    public Boolean getIsActivation() {
        return isActivation;
    }

    /**
     * 设置 是否已激活1-是,0-否
     */
    public void setIsActivation(Boolean isActivation) {
        this.isActivation = isActivation;
    }

    /**
     * 获取 激活时间
     */
    public Integer getActivationTime() {
        return activationTime;
    }

    /**
     * 设置 激活时间
     */
    public void setActivationTime(Integer activationTime) {
        this.activationTime = activationTime;
    }

    /**
     * 获取 激活人
     */
    public String getActivationPerson() {
        return activationPerson;
    }

    /**
     * 设置 激活人
     */
    public void setActivationPerson(String activationPerson) {
        this.activationPerson = activationPerson;
    }

    /**
     * 获取 导出时间
     */
    public Integer getExportTime() {
        return exportTime;
    }

    /**
     * 设置 导出时间
     */
    public void setExportTime(Integer exportTime) {
        this.exportTime = exportTime;
    }

    /**
     * 获取 导出人
     */
    public String getExportPerson() {
        return exportPerson;
    }

    /**
     * 设置 导出人
     */
    public void setExportPerson(String exportPerson) {
        this.exportPerson = exportPerson;
    }
}