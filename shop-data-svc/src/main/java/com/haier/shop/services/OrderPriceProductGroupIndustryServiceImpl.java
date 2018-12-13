package com.haier.shop.services;

import com.haier.shop.dao.shopwrite.OrderPriceProductGroupIndustryWriteDao;
import java.util.List;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.OrderPriceProductGroupIndustryReadDao;
import com.haier.shop.model.OrderPriceProductGroupIndustry;
import com.haier.shop.service.OrderPriceProductGroupIndustryService;

/**
 * Created by 李超 on 2018/1/10.
 */
@Service
public class OrderPriceProductGroupIndustryServiceImpl implements OrderPriceProductGroupIndustryService {

    @Autowired
    OrderPriceProductGroupIndustryReadDao orderPriceProductGroupIndustryReadDao;
    @Autowired
    OrderPriceProductGroupIndustryWriteDao orderPriceProductGroupIndustryWriteDao;

    @Override
    public List<OrderPriceProductGroupIndustry> getProductGroupIndustryList() {
        return orderPriceProductGroupIndustryReadDao.getProductGroupIndustryList();
    }

    @Override
    public int getOrderPriceProductGroupIndustryListCount(Map<String, Object> params) {
        return orderPriceProductGroupIndustryReadDao.getOrderPriceProductGroupIndustryListCount(params);
    }

    @Override
    public List<OrderPriceProductGroupIndustry> getOrderPriceProductGroupIndustryList(
        Map<String, Object> params) {
        return orderPriceProductGroupIndustryReadDao.getOrderPriceProductGroupIndustryList(params);
    }

    @Override
    public List<Map<String, String>> getOrderPriceIndustry() {
        return orderPriceProductGroupIndustryReadDao.getOrderPriceIndustry();
    }

    @Override
    public List<Map<String, String>> getOrderPriceProductGroup(String industry) {
        return orderPriceProductGroupIndustryReadDao.getOrderPriceProductGroup(industry);
    }

    @Override
    public int createOrderPriceProductGroupIndustry(
        OrderPriceProductGroupIndustry orderPriceProductGroupIndustry) {
        return orderPriceProductGroupIndustryWriteDao.createOrderPriceProductGroupIndustry(orderPriceProductGroupIndustry);
    }

    @Override
    public int updateOrderPriceProductGroupIndustry(
        OrderPriceProductGroupIndustry orderPriceProductGroupIndustry) {
        return orderPriceProductGroupIndustryWriteDao.updateOrderPriceProductGroupIndustry(orderPriceProductGroupIndustry);
    }

    @Override
    public int deleteOrderPriceProductGroupIndustryById(Integer id) {
        return orderPriceProductGroupIndustryWriteDao.deleteOrderPriceProductGroupIndustryById(id);
    }
}
