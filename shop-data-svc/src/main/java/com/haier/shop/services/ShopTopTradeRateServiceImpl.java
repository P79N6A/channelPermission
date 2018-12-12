package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.TopTradeRateReadDao;
import com.haier.shop.model.QueryTopTradeRateParameter;
import com.haier.shop.service.ShopTopTradeRateService;
@Service
public class ShopTopTradeRateServiceImpl implements ShopTopTradeRateService {
    @Autowired
    private TopTradeRateReadDao topTradeRateReadDao;


    public List<QueryTopTradeRateParameter> getAllData(QueryTopTradeRateParameter queryTopTradeRateParameter){
        return topTradeRateReadDao.getAllData(queryTopTradeRateParameter);
    }


    public int getCount(QueryTopTradeRateParameter queryTopTradeRateParameter){
        return topTradeRateReadDao.getCount(queryTopTradeRateParameter);
    }

}
