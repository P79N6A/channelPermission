package com.haier.svc.api.controller.util.http.jingdong;

import java.util.List;

/**
 * @author hanhaoyang@ehaier.com
 * @date 2018/6/4 19:21
 */
public class JingdongOrderInfo {
    private String orderId;
    private String venderId;
    private String orderType;
    private String payType;
    private String orderTotalPrice;
    private String orderSellerPrice;
    private String orderPayment;
    private String freightPrice;
    private String sellerDiscount;
    private String orderState;
    private String orderStateRemark;
    private String deliveryType;
    private String invoiceEasyInfo;
    private String invoiceInfo;
    private String invoiceCode;
    private String orderRemark;
    private String orderStartTime;
    private String orderEndTime;
    private UserInfo consigneeInfo;
    private List<ItemInfo> itemInfoList;
    private List<CouponDetail> couponDetailList;
    private String venderRemark;
    private String balanceUsed;
    private String pin;
    private String returnOrder;
    private String paymentConfirmTime;
    private String waybill;
    private String logisticsId;
    private String vatInfo;
    private String modified;
    private String parentOrderId;
    private String customs;
    private String customsModel;
    private String orderSign;
    private String storeOrder;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getVenderId() {
        return venderId;
    }

    public void setVenderId(String venderId) {
        this.venderId = venderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(String orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public String getOrderSellerPrice() {
        return orderSellerPrice;
    }

    public void setOrderSellerPrice(String orderSellerPrice) {
        this.orderSellerPrice = orderSellerPrice;
    }

    public String getOrderPayment() {
        return orderPayment;
    }

    public void setOrderPayment(String orderPayment) {
        this.orderPayment = orderPayment;
    }

    public String getFreightPrice() {
        return freightPrice;
    }

    public void setFreightPrice(String freightPrice) {
        this.freightPrice = freightPrice;
    }

    public String getSellerDiscount() {
        return sellerDiscount;
    }

    public void setSellerDiscount(String sellerDiscount) {
        this.sellerDiscount = sellerDiscount;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getOrderStateRemark() {
        return orderStateRemark;
    }

    public void setOrderStateRemark(String orderStateRemark) {
        this.orderStateRemark = orderStateRemark;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getInvoiceEasyInfo() {
        return invoiceEasyInfo;
    }

    public void setInvoiceEasyInfo(String invoiceEasyInfo) {
        this.invoiceEasyInfo = invoiceEasyInfo;
    }

    public String getInvoiceInfo() {
        return invoiceInfo;
    }

    public void setInvoiceInfo(String invoiceInfo) {
        this.invoiceInfo = invoiceInfo;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public String getOrderStartTime() {
        return orderStartTime;
    }

    public void setOrderStartTime(String orderStartTime) {
        this.orderStartTime = orderStartTime;
    }

    public String getOrderEndTime() {
        return orderEndTime;
    }

    public void setOrderEndTime(String orderEndTime) {
        this.orderEndTime = orderEndTime;
    }

    public UserInfo getConsigneeInfo() {
        return consigneeInfo;
    }

    public void setConsigneeInfo(UserInfo consigneeInfo) {
        this.consigneeInfo = consigneeInfo;
    }

    public List<ItemInfo> getItemInfoList() {
        return itemInfoList;
    }

    public void setItemInfoList(List<ItemInfo> itemInfoList) {
        this.itemInfoList = itemInfoList;
    }

    public List<CouponDetail> getCouponDetailList() {
        return couponDetailList;
    }

    public void setCouponDetailList(List<CouponDetail> couponDetailList) {
        this.couponDetailList = couponDetailList;
    }

    public String getVenderRemark() {
        return venderRemark;
    }

    public void setVenderRemark(String venderRemark) {
        this.venderRemark = venderRemark;
    }

    public String getBalanceUsed() {
        return balanceUsed;
    }

    public void setBalanceUsed(String balanceUsed) {
        this.balanceUsed = balanceUsed;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getReturnOrder() {
        return returnOrder;
    }

    public void setReturnOrder(String returnOrder) {
        this.returnOrder = returnOrder;
    }

    public String getPaymentConfirmTime() {
        return paymentConfirmTime;
    }

    public void setPaymentConfirmTime(String paymentConfirmTime) {
        this.paymentConfirmTime = paymentConfirmTime;
    }

    public String getWaybill() {
        return waybill;
    }

    public void setWaybill(String waybill) {
        this.waybill = waybill;
    }

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getVatInfo() {
        return vatInfo;
    }

    public void setVatInfo(String vatInfo) {
        this.vatInfo = vatInfo;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getParentOrderId() {
        return parentOrderId;
    }

    public void setParentOrderId(String parentOrderId) {
        this.parentOrderId = parentOrderId;
    }

    public String getCustoms() {
        return customs;
    }

    public void setCustoms(String customs) {
        this.customs = customs;
    }

    public String getCustomsModel() {
        return customsModel;
    }

    public void setCustomsModel(String customsModel) {
        this.customsModel = customsModel;
    }

    public String getOrderSign() {
        return orderSign;
    }

    public void setOrderSign(String orderSign) {
        this.orderSign = orderSign;
    }

    public String getStoreOrder() {
        return storeOrder;
    }

    public void setStoreOrder(String storeOrder) {
        this.storeOrder = storeOrder;
    }
}
