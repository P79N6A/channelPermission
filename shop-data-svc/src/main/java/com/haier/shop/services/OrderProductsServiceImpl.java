package com.haier.shop.services;

import com.haier.shop.dao.shopread.OrderProductsReadDao;
import com.haier.shop.dao.shopwrite.OrderProductsWriteDao;
import com.haier.shop.model.OrderProducts;
import com.haier.shop.service.ShopOrderProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
@Service
public class OrderProductsServiceImpl implements ShopOrderProductsService {

    @Autowired
    OrderProductsReadDao orderProductsReadDao;

    @Autowired
    OrderProductsWriteDao orderProductsWriteDao;

    @Override
    public OrderProducts get(Integer orderProductId) {
        return orderProductsReadDao.get(orderProductId);
    }

    @Override
    public Integer getOrderIdByCOrderSn(String cOrderSn) {
        return orderProductsReadDao.getOrderIdByCOrderSn(cOrderSn);
    }

    @Override
    public OrderProducts getByCOrderSn(String cOrderSn) {
        return orderProductsReadDao.getByCOrderSn(cOrderSn);
    }

    @Override
    public Integer updateForsyncInvoice(OrderProducts orderProduct) {
        return orderProductsWriteDao.updateForsyncInvoice(orderProduct);
    }

    @Override
    public Integer updateMakeReceiptType(OrderProducts orderProduct) {
        return orderProductsWriteDao.updateMakeReceiptType(orderProduct);
    }

    @Override
    public List<OrderProducts> getOrderProductsByOrderId(Integer orderId) {
        return orderProductsReadDao.getOrderProductsByOrderId(orderId);
    }

    @Override
    public List<OrderProducts> getByIds(List<Integer> ids) {
        return orderProductsReadDao.getByIds(ids);
    }

    @Override
    public List<Integer> getOrderProductIdsInfo(Map<String, Object> cOrderSnsMap) {
        return orderProductsReadDao.getOrderProductIdsInfo(cOrderSnsMap);
    }

    @Override
    public Integer getOrderIdById(Integer orderProductId) {
        return orderProductsReadDao.getOrderIdById(orderProductId);
    }

    @Override
    public Integer getOpListByCOrderSnCount(Map<String, Object> paramMap) {
        return orderProductsReadDao.getOpListByCOrderSnCount(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOpListByCOrderSnSearch(Map<String, Object> paramMap) {
        return orderProductsReadDao.getOpListByCOrderSnSearch(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOpListByCOrderSn(Map<String, Object> paramMap) {
        return orderProductsReadDao.getOpListByCOrderSn(paramMap);
    }
}
