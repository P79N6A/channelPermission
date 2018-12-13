package com.haier.shop.services;

import com.haier.shop.dao.shopwrite.OrderPriceSourceIndustryWriteDao;
import java.util.List;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.OrderPriceSourceIndustryReadDao;
import com.haier.shop.model.OrderPriceSourceIndustry;
import com.haier.shop.service.OrderPriceSourceIndustryService;

/**
 * Created by 李超 on 2018/1/10.
 */
@Service
public class OrderPriceSourceIndustryServiceImpl implements OrderPriceSourceIndustryService {

    @Autowired
    OrderPriceSourceIndustryReadDao orderPriceSourceIndustryReadDao;
    @Autowired
    OrderPriceSourceIndustryWriteDao orderPriceSourceIndustryWriteDao;

    @Override
    public List<OrderPriceSourceIndustry> getSourceIndustryList() {
        return orderPriceSourceIndustryReadDao.getSourceIndustryList();
    }

    @Override
    public List<Map<String, String>> getOrderPriceIndustryBySource(String source) {
        return orderPriceSourceIndustryReadDao.getOrderPriceIndustryBySource(source);
    }

    @Override
    public int getOrderPriceSourceIndustryListCount(Map<String, Object> params) {
        return orderPriceSourceIndustryReadDao.getOrderPriceSourceIndustryListCount(params);
    }

    @Override
    public List<OrderPriceSourceIndustry> getOrderPriceSourceIndustryList(
        Map<String, Object> params) {
        return orderPriceSourceIndustryReadDao.getOrderPriceSourceIndustryList(params);
    }

    @Override
    public int createOrderPriceSourceIndustry(OrderPriceSourceIndustry orderPriceSourceIndustry) {
        return orderPriceSourceIndustryWriteDao.createOrderPriceSourceIndustry(orderPriceSourceIndustry);
    }

    @Override
    public int updateOrderPriceSourceIndustry(OrderPriceSourceIndustry orderPriceSourceIndustry) {
        return orderPriceSourceIndustryWriteDao.updateOrderPriceSourceIndustry(orderPriceSourceIndustry);
    }

    @Override
    public int deleteOrderPriceSourceIndustryById(Integer id) {
        return orderPriceSourceIndustryWriteDao.deleteOrderPriceSourceIndustryById(id);
    }
}
