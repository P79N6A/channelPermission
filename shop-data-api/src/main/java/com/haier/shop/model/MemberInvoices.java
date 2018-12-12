package com.haier.shop.model;

import java.io.Serializable;

/**
 * 会员-发票信息表。
 * <p>Table: <strong>MemberInvoices</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>invoiceType</td><td>{@link Integer}</td><td>invoiceType</td><td>tinyint</td><td>发票类型1-增票,2-普票</td></tr>
 *   <tr><td>memberId</td><td>{@link Integer}</td><td>memberId</td><td>int</td><td>会员ID</td></tr>
 *   <tr><td>memberName</td><td>{@link String}</td><td>memberName</td><td>varchar</td><td>会员名称</td></tr>
 *   <tr><td>invoiceTitle</td><td>{@link String}</td><td>invoiceTitle</td><td>varchar</td><td>发票抬头</td></tr>
 *   <tr><td>taxPayerNumber</td><td>{@link String}</td><td>taxPayerNumber</td><td>varchar</td><td>纳税人识别号</td></tr>
 *   <tr><td>registerAddress</td><td>{@link String}</td><td>registerAddress</td><td>varchar</td><td>注册地址</td></tr>
 *   <tr><td>registerPhone</td><td>{@link String}</td><td>registerPhone</td><td>varchar</td><td>注册电话</td></tr>
 *   <tr><td>bankName</td><td>{@link String}</td><td>bankName</td><td>varchar</td><td>开户银行</td></tr>
 *   <tr><td>bankCardNumber</td><td>{@link String}</td><td>bankCardNumber</td><td>varchar</td><td>银行帐户</td></tr>
 *   <tr><td>addTime</td><td>{@link Long}</td><td>addTime</td><td>int</td><td>添加时间</td></tr>
 *   <tr><td>state</td><td>{@link Integer}</td><td>state</td><td>tinyint</td><td>审核状态0-待审核,1-审核通过,2-拒绝</td></tr>
 *   <tr><td>auditTime</td><td>{@link Long}</td><td>auditTime</td><td>int</td><td>审核时间</td></tr>
 *   <tr><td>auditor</td><td>{@link String}</td><td>auditor</td><td>varchar</td><td>审核人</td></tr>
 *   <tr><td>remark</td><td>{@link String}</td><td>remark</td><td>varchar</td><td>审核备注信息</td></tr>
 *   <tr><td>isLock</td><td>{@link Integer}</td><td>isLock</td><td>tinyint</td><td>是否锁定,1-锁定,0-未锁定</td></tr>
 *   <tr><td>orderId</td><td>{@link Integer}</td><td>orderId</td><td>int</td><td>订单ID</td></tr>
 *   <tr><td>parentId</td><td>{@link Integer}</td><td>orderId</td><td>int</td><td>已审核通过的增票记录ID</td></tr>
 *   <tr><td>electricFlag</td><td>{@link Integer}</td><td>electricFlag</td><td>int</td><td>电子发票标志，1：电子发票；0：纸质发票</td></tr>
 * </table>
 * @author rongmai
 */
public class MemberInvoices implements Serializable {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 4399795785571435333L;
    
    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer invoiceType;

    /**
     * 获取 发票类型1-增票,2-普票
     */
    public Integer getInvoiceType() {
        return this.invoiceType;
    }

    /**
     * 设置 发票类型1-增票,2-普票
     * @param value 属性值
     */
    public void setInvoiceType(Integer value) {
        this.invoiceType = value;
    }

    private Integer memberId;

    /**
     * 获取 会员ID
     */
    public Integer getMemberId() {
        return this.memberId;
    }

    /**
     * 设置 会员ID
     * @param value 属性值
     */
    public void setMemberId(Integer value) {
        this.memberId = value;
    }

    private String memberName;

    /**
     * 获取 会员名称
     */
    public String getMemberName() {
        return this.memberName;
    }

    /**
     * 设置 会员名称
     * @param value 属性值
     */
    public void setMemberName(String value) {
        this.memberName = value;
    }

    private String invoiceTitle;

    /**
     * 获取 发票抬头
     */
    public String getInvoiceTitle() {
        return this.invoiceTitle;
    }

    /**
     * 设置 发票抬头
     * @param value 属性值
     */
    public void setInvoiceTitle(String value) {
        this.invoiceTitle = value;
    }

    private String taxPayerNumber;

    /**
     * 获取 纳税人识别号
     */
    public String getTaxPayerNumber() {
        return this.taxPayerNumber;
    }

    /**
     * 设置 纳税人识别号
     * @param value 属性值
     */
    public void setTaxPayerNumber(String value) {
        this.taxPayerNumber = value;
    }

    private String registerAddress;

    /**
     * 获取 注册地址
     */
    public String getRegisterAddress() {
        return this.registerAddress;
    }

    /**
     * 设置 注册地址
     * @param value 属性值
     */
    public void setRegisterAddress(String value) {
        this.registerAddress = value;
    }

    private String registerPhone;

    /**
     * 获取 注册电话
     */
    public String getRegisterPhone() {
        return this.registerPhone;
    }

    /**
     * 设置 注册电话
     * @param value 属性值
     */
    public void setRegisterPhone(String value) {
        this.registerPhone = value;
    }

    private String bankName;

    /**
     * 获取 开户银行
     */
    public String getBankName() {
        return this.bankName;
    }

    /**
     * 设置 开户银行
     * @param value 属性值
     */
    public void setBankName(String value) {
        this.bankName = value;
    }

    private String bankCardNumber;

    /**
     * 获取 银行帐户
     */
    public String getBankCardNumber() {
        return this.bankCardNumber;
    }

    /**
     * 设置 银行帐户
     * @param value 属性值
     */
    public void setBankCardNumber(String value) {
        this.bankCardNumber = value;
    }

    private Long addTime;

    /**
     * 获取 添加时间
     */
    public Long getAddTime() {
        return this.addTime;
    }

    /**
     * 设置 添加时间
     * @param value 属性值
     */
    public void setAddTime(Long value) {
        this.addTime = value;
    }

    private Integer state;

    /**
     * 获取 审核状态0-待审核,1-审核通过,2-拒绝
     */
    public Integer getState() {
        return this.state;
    }

    /**
     * 设置 审核状态0-待审核,1-审核通过,2-拒绝
     * @param value 属性值
     */
    public void setState(Integer value) {
        this.state = value;
    }

    private Long auditTime;

    /**
     * 获取 审核时间
     */
    public Long getAuditTime() {
        return this.auditTime;
    }

    /**
     * 设置 审核时间
     * @param value 属性值
     */
    public void setAuditTime(Long value) {
        this.auditTime = value;
    }

    private String auditor;

    /**
     * 获取 审核人
     */
    public String getAuditor() {
        return this.auditor;
    }

    /**
     * 设置 审核人
     * @param value 属性值
     */
    public void setAuditor(String value) {
        this.auditor = value;
    }

    private String remark;

    /**
     * 获取 审核备注信息
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置 审核备注信息
     * @param value 属性值
     */
    public void setRemark(String value) {
        this.remark = value;
    }

    private Integer isLock;

    /**
     * 获取 是否锁定,1-锁定,0-未锁定
     */
    public Integer getIsLock() {
        return this.isLock;
    }

    /**
     * 设置 是否锁定,1-锁定,0-未锁定
     * @param value 属性值
     */
    public void setIsLock(Integer value) {
        this.isLock = value;
    }

    private Integer orderId;

    /**
     * 获取 订单ID
     */
    public Integer getOrderId() {
        return this.orderId;
    }

    /**
     * 设置 订单ID
     * @param value 属性值
     */
    public void setOrderId(Integer value) {
        this.orderId = value;
    }

    /**
     * 已审核通过的增票记录ID
     */
    private Integer parentId = 0;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 电子发票标志
     * 1：电子发票；
     * 0：纸质发票
     */
    private Integer electricFlag = 0;

    public Integer getElectricFlag() {
        return electricFlag;
    }

    public void setElectricFlag(Integer electricFlag) {
        this.electricFlag = electricFlag;
    }

}