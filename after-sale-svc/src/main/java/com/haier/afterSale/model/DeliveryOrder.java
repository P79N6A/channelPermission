package com.haier.afterSale.model;

public class DeliveryOrder {
    private String totalOrderLines="";
    private String deliveryOrderCode="";
    private String orderType="";
    private String createTime="";
    private String scheduleDate="";
    private String logisticsCode="";
    private String logisticsName="";
    private String supplierCode="";

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    private String warehouseCode;
    public void setTotalOrderLines(String totalOrderLines) {
        this.totalOrderLines = totalOrderLines;
    }

    public void setDeliveryOrderCode(String deliveryOrderCode) {
        this.deliveryOrderCode = deliveryOrderCode;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setTransportMode(String transportMode) {
        this.transportMode = transportMode;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setRelatedOrders(RelatedOrders relatedOrders) {
        this.relatedOrders = relatedOrders;
    }

    public void setPickerInfo(PickerInfo pickerInfo) {
        this.pickerInfo = pickerInfo;
    }

    public void setSenderInfo(SenderInfo senderInfo) {
        this.senderInfo = senderInfo;
    }

    public void setReceiverInfo(ReceiverInfo receiverInfo) {
        this.receiverInfo = receiverInfo;
    }

    public String getTotalOrderLines() {

        return totalOrderLines;
    }

    public String getDeliveryOrderCode() {
        return deliveryOrderCode;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getTransportMode() {
        return transportMode;
    }

    public String getRemark() {
        return remark;
    }

    public RelatedOrders getRelatedOrders() {
        return relatedOrders;
    }

    public PickerInfo getPickerInfo() {
        return pickerInfo;
    }

    public SenderInfo getSenderInfo() {
        return senderInfo;
    }

    public ReceiverInfo getReceiverInfo() {
        return receiverInfo;
    }

    private String supplierName="";
    private String transportMode="";
    private String remark="";
    private RelatedOrders relatedOrders;
    private PickerInfo pickerInfo;
    private SenderInfo senderInfo;
    private ReceiverInfo receiverInfo;
}
