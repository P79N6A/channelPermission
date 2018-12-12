package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.OrderPriceCostPoolUseInfoReadDao;
import com.haier.shop.dao.shopwrite.OrderPriceCostPoolUseInfoWriteDao;
import com.haier.shop.model.OrderPriceCostPoolUseInfo;
import com.haier.shop.service.OrderPriceCostPoolUseInfoService;

@Service
public class OrderPriceCostPoolUseInfoServiceImpl implements OrderPriceCostPoolUseInfoService {
    @Autowired
    private OrderPriceCostPoolUseInfoReadDao orderPriceCostPoolUseInfoReadDao;
    @Autowired
    private OrderPriceCostPoolUseInfoWriteDao orderPriceCostPoolUseInfoWriteDao;

    public int batchInsert(List<OrderPriceCostPoolUseInfo> orderPriceCostPoolUseInfo){
        return orderPriceCostPoolUseInfoWriteDao.batchInsert(orderPriceCostPoolUseInfo);
    }

    public int insert(OrderPriceCostPoolUseInfo orderPriceCostPoolUseInfo){
        return orderPriceCostPoolUseInfoWriteDao.insert(orderPriceCostPoolUseInfo);
    }

    public OrderPriceCostPoolUseInfo getByCorderSn(String cOrderSn){
        return orderPriceCostPoolUseInfoReadDao.getByCorderSn(cOrderSn);
    }
}
