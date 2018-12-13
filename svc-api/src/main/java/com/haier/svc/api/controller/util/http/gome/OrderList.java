package com.haier.svc.api.controller.util.http.gome;

import java.util.List;

/**
 * Created by LuJun on 16/5/11.
 */
public class OrderList {
    private String orderNumber;//配送单号
    private String masterOrderNumber;//主订单号
    private String orderType;//订单类型
    private String orderDate;//下单时间
    private String payTime;//支付时间
    private String orderStatus;//订单状态
    private String paymentType;//支付方式
    private String paidAmount;//在线支付已付金额
    private String dueAmount;//货到付款应付金额
    private String shippingCost;//运费
    private String remark;//客户前台备注
    private String customerName;//收货人
    private String cellPhone;//收货人手机
    private String telephone;//收货人座机
    private String province;//省
    private String city;//市
    private String district;//区
    private String address;//详细地址
    private String reasonCodeDesc;//取消原因代码的中文描述
    private String remark1; // 客服取消时填写的备注
    private List<OrderGoodsList> orderGoodsList;//订单详情

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(String dueAmount) {
        this.dueAmount = dueAmount;
    }

    public String getMasterOrderNumber() {
        return masterOrderNumber;
    }

    public void setMasterOrderNumber(String masterOrderNumber) {
        this.masterOrderNumber = masterOrderNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderGoodsList> getOrderGoodsList() {
        return orderGoodsList;
    }

    public void setOrderGoodsList(List<OrderGoodsList> orderGoodsList) {
        this.orderGoodsList = orderGoodsList;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }


    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(String shippingCost) {
        this.shippingCost = shippingCost;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getReasonCodeDesc() {
        return reasonCodeDesc;
    }

    public void setReasonCodeDesc(String reasonCodeDesc) {
        this.reasonCodeDesc = reasonCodeDesc;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }
}
