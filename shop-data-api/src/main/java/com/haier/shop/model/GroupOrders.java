package com.haier.shop.model;

import java.io.Serializable;

/**
 * <p>Table: <strong>GroupOrders</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>siteId</td><td>{@link Integer}</td><td>siteId</td><td>int</td><td>站点ID</td></tr>
 *   <tr><td>type</td><td>{@link Integer}</td><td>type</td><td>tinyint</td><td>类型,1:普通的双订单的订金-尾款;2:一个订单的定金-尾款</td></tr>
 *   <tr><td>tailOrderId</td><td>{@link Integer}</td><td>tailOrderId</td><td>int</td><td>尾款订单ID</td></tr>
 *   <tr><td>depositOrderId</td><td>{@link Integer}</td><td>depositOrderId</td><td>int</td><td>订金订单ID</td></tr>
 *   <tr><td>tailCOrderSn</td><td>{@link String}</td><td>tailCOrderSn</td><td>varchar</td><td>尾款网单号</td></tr>
 *   <tr><td>depositOrderProductId</td><td>{@link Integer}</td><td>depositOrderProductId</td><td>int</td><td>定金网单ID</td></tr>
 *   <tr><td>depositCOrderSn</td><td>{@link String}</td><td>depositCOrderSn</td><td>varchar</td><td>订单网单号</td></tr>
 *   <tr><td>status</td><td>{@link Integer}</td><td>status</td><td>tinyint</td><td>状态 0 匹配不成功 1 匹配成功 2 成功同步到HP</td></tr>
 *   <tr><td>addTime</td><td>{@link Integer}</td><td>addTime</td><td>int</td><td>添加时间</td></tr>
 *   <tr><td>syncHpTime</td><td>{@link Integer}</td><td>syncHpTime</td><td>int</td><td>同步到HP时间</td></tr>
 *   <tr><td>member</td><td>{@link String}</td><td>member</td><td>varchar</td><td>支付宝账号</td></tr>
 *   <tr><td>groupId</td><td>{@link Integer}</td><td>groupId</td><td>int</td><td>淘宝活动ID</td></tr>
 *   <tr><td>errorMessage</td><td>{@link String}</td><td>errorMessage</td><td>text</td><td>错误信息</td></tr>
 *   <tr><td>from</td><td>{@link String}</td><td>from</td><td>varchar</td><td>来源 1 淘宝 2 官网</td></tr>
 *   <tr><td>extensions</td><td>{@link String}</td><td>extensions</td><td>text</td><td>扩展信息</td></tr>
 *   <tr><td>params</td><td>{@link String}</td><td>params</td><td>text</td><td>传给hp的参数</td></tr>
 *   <tr><td>hpResult</td><td>{@link String}</td><td>hpResult</td><td>text</td><td>hp返回结果</td></tr>
 *   <tr><td>tryCombineNum</td><td>{@link Integer}</td><td>tryCombineNum</td><td>int</td><td>尝试合并的次数</td></tr>
 *   <tr><td>lastErrorMsg</td><td>{@link String}</td><td>lastErrorMsg</td><td>varchar</td><td>上次的错误信息</td></tr>
 * </table>
 * @author rongmai
 */
public class GroupOrders implements Serializable {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 3621743852994109231L;
    private Integer           id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer siteId = 1;

    /**
     * 获取 站点ID
     */
    public Integer getSiteId() {
        return this.siteId;
    }

    /**
     * 设置 站点ID
     * @param value 属性值
     */
    public void setSiteId(Integer value) {
        this.siteId = value;
    }

    private Integer type = 2;

    /**
     * 获取 类型,1:一个订单的定金-尾款;2:普通的双订单的订金-尾款
     */
    public Integer getType() {
        return this.type;
    }

    /**
     * 设置 类型,1:一个订单的定金-尾款;2:普通的双订单的订金-尾款
     * @param value 属性值
     */
    public void setType(Integer value) {
        this.type = value;
    }

    private Integer tailOrderId = 0;

    /**
     * 获取 尾款订单ID
     */
    public Integer getTailOrderId() {
        return this.tailOrderId;
    }

    /**
     * 设置 尾款订单ID
     * @param value 属性值
     */
    public void setTailOrderId(Integer value) {
        this.tailOrderId = value;
    }

    private Integer depositOrderId;

    /**
     * 获取 订金订单ID
     */
    public Integer getDepositOrderId() {
        return this.depositOrderId;
    }

    /**
     * 设置 订金订单ID
     * @param value 属性值
     */
    public void setDepositOrderId(Integer value) {
        this.depositOrderId = value;
    }

    private String tailCOrderSn = "";

    /**
     * 获取 尾款网单号
     */
    public String getTailCOrderSn() {
        return this.tailCOrderSn;
    }

    /**
     * 设置 尾款网单号
     * @param value 属性值
     */
    public void setTailCOrderSn(String value) {
        this.tailCOrderSn = value;
    }

    private Integer depositOrderProductId;

    /**
     * 获取 定金网单ID
     */
    public Integer getDepositOrderProductId() {
        return this.depositOrderProductId;
    }

    /**
     * 设置 定金网单ID
     * @param value 属性值
     */
    public void setDepositOrderProductId(Integer value) {
        this.depositOrderProductId = value;
    }

    private String depositCOrderSn;

    /**
     * 获取 订单网单号
     */
    public String getDepositCOrderSn() {
        return this.depositCOrderSn;
    }

    /**
     * 设置 订单网单号
     * @param value 属性值
     */
    public void setDepositCOrderSn(String value) {
        this.depositCOrderSn = value;
    }

    private Integer status = 0;

    /**
     * 获取 状态 0 匹配不成功 1 匹配成功 2 成功同步到HP
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置 状态 0 匹配不成功 1 匹配成功 2 成功同步到HP
     * @param value 属性值
     */
    public void setStatus(Integer value) {
        this.status = value;
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

    private Integer syncHpTime = 0;

    /**
     * 获取 同步到HP时间
     */
    public Integer getSyncHpTime() {
        return this.syncHpTime;
    }

    /**
     * 设置 同步到HP时间
     * @param value 属性值
     */
    public void setSyncHpTime(Integer value) {
        this.syncHpTime = value;
    }

    private String member = "";

    /**
     * 获取 支付宝账号
     */
    public String getMember() {
        return this.member;
    }

    /**
     * 设置 支付宝账号
     * @param value 属性值
     */
    public void setMember(String value) {
        this.member = value;
    }

    private Integer groupId = 0;

    /**
     * 获取 淘宝活动ID
     */
    public Integer getGroupId() {
        return this.groupId;
    }

    /**
     * 设置 淘宝活动ID
     * @param value 属性值
     */
    public void setGroupId(Integer value) {
        this.groupId = value;
    }

    private String errorMessage = "";

    /**
     * 获取 错误信息
     */
    public String getErrorMessage() {
        return this.errorMessage;
    }

    /**
     * 设置 错误信息
     * @param value 属性值
     */
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    private String from = "";

    /**
     * 获取 来源 1 淘宝 2 官网
     */
    public String getFrom() {
        return this.from;
    }

    /**
     * 设置 来源 1 淘宝 2 官网
     * @param value 属性值
     */
    public void setFrom(String value) {
        this.from = value;
    }

    private String extensions = "";

    /**
     * 获取 扩展信息
     */
    public String getExtensions() {
        return this.extensions;
    }

    /**
     * 设置 扩展信息
     * @param value 属性值
     */
    public void setExtensions(String value) {
        this.extensions = value;
    }

    private String params = "";

    /**
     * 获取 传给hp的参数
     */
    public String getParams() {
        return this.params;
    }

    /**
     * 设置 传给hp的参数
     * @param value 属性值
     */
    public void setParams(String value) {
        this.params = value;
    }

    private String hpResult = "";

    /**
     * 获取 hp返回结果
     */
    public String getHpResult() {
        return this.hpResult;
    }

    /**
     * 设置 hp返回结果
     * @param value 属性值
     */
    public void setHpResult(String value) {
        this.hpResult = value;
    }

    private Integer tryCombineNum = 0;

    /**
     * 获取 尝试合并的次数
     */
    public Integer getTryCombineNum() {
        return this.tryCombineNum;
    }

    /**
     * 设置 尝试合并的次数
     * @param value 属性值
     */
    public void setTryCombineNum(Integer value) {
        this.tryCombineNum = value;
    }

    private String lastErrorMsg = "";

    /**
     * 获取 上次的错误信息
     */
    public String getLastErrorMsg() {
        return this.lastErrorMsg;
    }

    /**
     * 设置 上次的错误信息
     * @param value 属性值
     */
    public void setLastErrorMsg(String value) {
        this.lastErrorMsg = value;
    }

    private Integer lesStatus = 0;

    /**
     * 获取状态   0或空未发送les 1发送les成功  2发送les失败  3失败并停止发送les
     */
    public Integer getLestatus() {
        return this.lesStatus;
    }

    /**
     * 设置状态   0或空未发送les 1发送les成功  2发送les失败  3失败并停止发送les
     * @param value 属性值
     */
    public void setLesStatus(Integer value) {
        this.lesStatus = value;
    }

    private String lesMessage = "";

    /**
     * 获取 发送les返回的错误信息
     */
    public String getLesMessage() {
        return this.lesMessage;
    }

    /**
     * 设置 发送les返回的错误信息
     * @param value 属性值
     */
    public void setLesMessage(String value) {
        this.lesMessage = value;
    }

    private Long lesLastTime = 0L;

    /**
     * 获取 同步到Les时间
     */
    public Long getLesLastTime() {
        return this.lesLastTime;
    }

    /**
     * 设置 同步到Les时间
     * @param value 属性值
     */
    public void setLesLastTime(Long value) {
        this.lesLastTime = value;
    }

}