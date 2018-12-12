package com.haier.shop.model;
/**
 * 	存储天猫返回的原始数据
 * @author wukunyang
 *
 */
public class OrderTmallReturnLogs {
    /***/
    private Integer id;

    private String sourceOrderSn;

    /**sku*/
    private String sku;

    private String reason;

    /***/
    private String description;

    private String count;

    private String refundFee;

    private String source;

    private Integer status;

    /***/
    private Integer addTime;

    private Integer handleCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSourceOrderSn() {
        return sourceOrderSn;
    }

    public void setSourceOrderSn(String sourceOrderSn) {
        this.sourceOrderSn = sourceOrderSn == null ? null : sourceOrderSn.trim();
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku == null ? null : sku.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count == null ? null : count.trim();
    }

    public String getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(String refundFee) {
        this.refundFee = refundFee == null ? null : refundFee.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    public Integer getHandleCount() {
        return handleCount;
    }

    public void setHandleCount(Integer handleCount) {
        this.handleCount = handleCount;
    }
}