package com.haier.svc.api.controller.util.http.suning;

import java.util.List;

public class SuningOrders {

    public String orderCode; //订单号
    public String orderTotalStatus; //订单总状态。10=待发货；20=已发货；30=交易成功
    public String orderSaleTime;//订单创建时间,格式：yyyy-MM-dd HH:mm：ss。
    private List<SuningOrderDetail> orderDetail;//订单详情;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderTotalStatus() {
        return orderTotalStatus;
    }

    public void setOrderTotalStatus(String orderTotalStatus) {
        this.orderTotalStatus = orderTotalStatus;
    }

    public String getOrderSaleTime() {
        return orderSaleTime;
    }

    public void setOrderSaleTime(String orderSaleTime) {
        this.orderSaleTime = orderSaleTime;
    }


    public List<SuningOrderDetail> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<SuningOrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
    }
}
