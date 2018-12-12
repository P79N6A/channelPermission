package com.haier.shop.services;

import java.util.List;

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

    @Override
    public List<OrderPriceSourceIndustry> getSourceIndustryList() {
        return orderPriceSourceIndustryReadDao.getSourceIndustryList();
    }
}
