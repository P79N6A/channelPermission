package com.haier.shop.service;


import com.haier.shop.model.OrderPriceCostPoolUseInfo;

import java.util.List;

public interface OrderPriceCostPoolUseInfoService {

    int batchInsert(List<OrderPriceCostPoolUseInfo> orderPriceCostPoolUseInfo);

    int insert(OrderPriceCostPoolUseInfo orderPriceCostPoolUseInfo);

    OrderPriceCostPoolUseInfo getByCorderSn(String cOrderSn);

}