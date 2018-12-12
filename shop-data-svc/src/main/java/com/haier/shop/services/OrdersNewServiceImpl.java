package com.haier.shop.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopwrite.OrdersNewDao;
import com.haier.shop.model.OrdersNew;
import com.haier.shop.service.OrdersNewService;

@Service
public class OrdersNewServiceImpl implements OrdersNewService {

    @Autowired
    OrdersNewDao ordersNewDao;

    @Override
    public List<OrdersNew> getByIds(List<Integer> ids) {
        return ordersNewDao.getByIds(ids);
    }

    @Override
    public OrdersNew get(Integer id) {

        return ordersNewDao.get(id);
    }

    @Override
    public Integer completeClose(Integer id, Long finishTime) {
        return ordersNewDao.completeClose(id, finishTime);
    }

    @Override
    public List<OrdersNew> getByIdsForConfirm(List<Integer> ids) {
        return ordersNewDao.getByIdsForConfirm(ids);
    }

    @Override
    public int updateSmConfirmStatus(OrdersNew orders) {
        return ordersNewDao.updateSmConfirmStatus(orders);
    }

    @Override
    public int updateForConfirm(OrdersNew orders) {
        return ordersNewDao.updateForConfirm(orders);
    }

    @Override
    public Integer updateOrderStatus(Integer id, Integer orderStatus) {
        return ordersNewDao.updateOrderStatus(id, orderStatus);
    }

    @Override
    public List<OrdersNew> getBySmConfirmStatus() {
        return ordersNewDao.getBySmConfirmStatus();
    }

    @Override
    public int updateSmConfirmStatusById(int targetStatus, int id, int srcStatus, Long smManualTime, String smManualRemark) {
        return ordersNewDao.updateSmConfirmStatusById(targetStatus, id, srcStatus, smManualTime, smManualRemark);
    }

    @Override
    public int updateSmConfirmStatusForAllProductsOrder(int id) {
        return ordersNewDao.updateSmConfirmStatusForAllProductsOrder(id);
    }

    @Override
    public List<OrdersNew> getNotAutoConfirmOrders() {
        return ordersNewDao.getNotAutoConfirmOrders();
    }

    @Override
    public int insertKjtProofs(Integer orderId, Long addTime, String pushData) {
        return ordersNewDao.insertKjtProofs(orderId, addTime, pushData);
    }

    @Override
    public int updateNotAutoConfirm(Integer id, Integer whereValue, Integer setValue) {
        return ordersNewDao.updateNotAutoConfirm(id, whereValue, setValue);
    }

    @Override
    public OrdersNew getByOrderSn(String orderSn) {
        return ordersNewDao.getByOrderSn(orderSn);
    }
}
