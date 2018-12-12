package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.OrderGuaranteePriceInfoReadDao;
import com.haier.shop.dao.shopwrite.OrderGuaranteePriceInfoWriteDao;
import com.haier.shop.model.OrderGuaranteePriceInfo;
import com.haier.shop.service.OrderGuaranteePriceInfoService;

/**
 * Created by 李超 on 2018/1/10.
 */
@Service
public class OrderGuaranteePriceInfoServiceImpl implements OrderGuaranteePriceInfoService {

    @Autowired
    OrderGuaranteePriceInfoReadDao orderGuaranteePriceInfoReadDao;
    @Autowired
    OrderGuaranteePriceInfoWriteDao orderGuaranteePriceInfoWriteDao;

    @Override
    public OrderGuaranteePriceInfo getById(Integer id) {
        return orderGuaranteePriceInfoReadDao.getById(id);
    }

    @Override
    public OrderGuaranteePriceInfo getConditions(String channelCode, String shippingMode, String sku) {
        return orderGuaranteePriceInfoReadDao.getConditions(channelCode, shippingMode, sku);
    }

    @Override
    public List<OrderGuaranteePriceInfo> getNewConditions(String channelCode, String shippingMode, String sku) {
        return orderGuaranteePriceInfoReadDao.getNewConditions(channelCode, shippingMode, sku);
    }

    @Override
    public OrderGuaranteePriceInfo getOrderGuaranteePrice(OrderGuaranteePriceInfo orderGuaranteePriceInfo) {
        return orderGuaranteePriceInfoReadDao.getOrderGuaranteePrice(orderGuaranteePriceInfo);
    }

    @Override
    public int insert(OrderGuaranteePriceInfo orderGuaranteePriceInfo) {
        return orderGuaranteePriceInfoWriteDao.insert(orderGuaranteePriceInfo);
    }

    @Override
    public int insertNew(OrderGuaranteePriceInfo orderGuaranteePriceInfo) {
        return orderGuaranteePriceInfoWriteDao.insertNew(orderGuaranteePriceInfo);
    }

    @Override
    public int update(OrderGuaranteePriceInfo orderGuaranteePriceInfo) {
        return orderGuaranteePriceInfoWriteDao.update(orderGuaranteePriceInfo);
    }
}
