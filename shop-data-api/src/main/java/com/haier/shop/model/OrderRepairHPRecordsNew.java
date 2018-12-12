package com.haier.shop.model;

import java.io.Serializable;

/*
* 作者:张波
* 2017/12/19
* */
public class OrderRepairHPRecordsNew implements Serializable{
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -1310947197821298668L;
    private Integer           id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer siteId;

    public Integer getSiteId() {
        return this.siteId;
    }

    public void setSiteId(Integer value) {
        this.siteId = value;
    }

    private Long addTime;

    /**
     * 获取 创建时间。
     */
    public Long getAddTime() {
        return this.addTime;
    }

    /**
     * 设置 创建时间。
     *
     * @param value 属性值
     */
    public void setAddTime(Long value) {
        this.addTime = value;
    }

    private Integer orderProductId;

    /**
     * 获取 网单ID。
     */
    public Integer getOrderProductId() {
        return this.orderProductId;
    }

    /**
     * 设置 网单ID。
     *
     * @param value 属性值
     */
    public void setOrderProductId(Integer value) {
        this.orderProductId = value;
    }

    private Integer orderRepairId;

    /**
     * 获取 退货申请单ID。
     */
    public Integer getOrderRepairId() {
        return this.orderRepairId;
    }

    /**
     * 设置 退货申请单ID。
     *
     * @param value 属性值
     */
    public void setOrderRepairId(Integer value) {
        this.orderRepairId = value;
    }

    private String netPointCode;

    /**
     * 获取 网点代码。
     */
    public String getNetPointCode() {
        return this.netPointCode;
    }

    /**
     * 设置 网点代码。
     *
     * @param value 属性值
     */
    public void setNetPointCode(String value) {
        this.netPointCode = value;
    }

    private Integer checkResult;

    /**
     * 获取 检验结果，符合条件；不符合条件。
     */
    public Integer getCheckResult() {
        return this.checkResult;
    }

    /**
     * 设置 检验结果，符合条件；不符合条件。
     *
     * @param value 属性值
     */
    public void setCheckResult(Integer value) {
        this.checkResult = value;
    }

    private Integer quality;

    /**
     * 获取 质检结果，正品；开箱正品；不良品。
     */
    public Integer getQuality() {
        return this.quality;
    }

    /**
     * 设置 质检结果，正品；开箱正品；不良品。
     *
     * @param value 属性值
     */
    public void setQuality(Integer value) {
        this.quality = value;
    }

    private String machineNum;

    /**
     * 获取 机编代码。
     */
    public String getMachineNum() {
        return this.machineNum;
    }

    /**
     * 设置 机编代码。
     *
     * @param value 属性值
     */
    public void setMachineNum(String value) {
        this.machineNum = value;
    }

    private String inspector;

    /**
     * 获取 质检员。
     */
    public String getInspector() {
        return this.inspector;
    }

    /**
     * 设置 质检员。
     *
     * @param value 属性值
     */
    public void setInspector(String value) {
        this.inspector = value;
    }

    private Long inspectTime;

    /**
     * 获取 质检时间。
     */
    public Long getInspectTime() {
        return this.inspectTime;
    }

    /**
     * 设置 质检时间。
     *
     * @param value 属性值
     */
    public void setInspectTime(Long value) {
        this.inspectTime = value;
    }

    private Integer success;

    /**
     * 获取 是否成功。
     */
    public Integer getSuccess() {
        return this.success;
    }

    /**
     * 设置 是否成功。
     *
     * @param value 属性值
     */
    public void setSuccess(Integer value) {
        this.success = value;
    }

    private Integer checkType;

    /**
     * 获取 质检类型，一次/二次。
     */
    public Integer getCheckType() {
        return this.checkType;
    }

    /**
     * 设置 质检类型，一次/二次。
     *
     * @param value 属性值
     */
    public void setCheckType(Integer value) {
        this.checkType = value;
    }

    private Integer packResult;

    /**
     * 获取 包装结果。
     */
    public Integer getPackResult() {
        return this.packResult;
    }

    /**
     * 设置 包装结果。
     *
     * @param value 属性值
     */
    public void setPackResult(Integer value) {
        this.packResult = value;
    }

    private Integer response;

    /**
     * 获取 非正品买单方。
     */
    public Integer getResponse() {
        return this.response;
    }

    /**
     * 设置 非正品买单方。
     *
     * @param value 属性值
     */
    public void setResponse(Integer value) {
        this.response = value;
    }

    private Integer hpOrderFail;

    /**
     * 获取 生成工单是否失败，无值代表成功。
     */
    public Integer getHpOrderFail() {
        return this.hpOrderFail;
    }

    /**
     * 设置 生成工单是否失败，无值代表成功。
     *
     * @param value 属性值
     */
    public void setHpOrderFail(Integer value) {
        this.hpOrderFail = value;
    }

    private String hpOrderRemark;

    /**
     * 获取 备注。
     */
    public String getHpOrderRemark() {
        return this.hpOrderRemark;
    }

    /**
     * 设置 备注。
     *
     * @param value 属性值
     */
    public void setHpOrderRemark(String value) {
        this.hpOrderRemark = value;
    }

    private String source;

    private String woId;

    /**
     * 获取 来源。
     */
    public String getSource() {
        return source;
    }

    /**
     * 设置 来源。
     *
     * @param value 属性值
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 获取 工单号。
     */
    public String getWoId() {
        return woId;
    }

    /**
     * 设置 工单号。
     *
     * @param value 属性值
     */
    public void setWoId(String woId) {
        this.woId = woId;
    }
}
