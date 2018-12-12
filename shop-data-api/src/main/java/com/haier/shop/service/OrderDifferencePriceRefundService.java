package com.haier.shop.service;


public interface OrderDifferencePriceRefundService {

    /**
     * 根据网单号获取有效申请差价单数量
     *
     * @param cOrderSn
     * @return
     */
    int getValidCountByCorderSn(String cOrderSn);

}