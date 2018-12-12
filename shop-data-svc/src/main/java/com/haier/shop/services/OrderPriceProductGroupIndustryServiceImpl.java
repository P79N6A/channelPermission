package com.haier.shop.services;

import java.util.List;

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

    @Override
    public List<OrderPriceProductGroupIndustry> getProductGroupIndustryList() {
        return orderPriceProductGroupIndustryReadDao.getProductGroupIndustryList();
    }
}
