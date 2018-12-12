package com.haier.stock.services;

import com.haier.stock.dao.stock.BaseStockDao;
import com.haier.stock.model.BaseStock;
import com.haier.stock.service.BaseStockService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class BaseStockServiceImpl implements BaseStockService {
    @Autowired
    BaseStockDao baseStockDao;

    @Override
    public BaseStock get(String sku, String code) {
        return baseStockDao.get(sku, code);
    }

    @Override
    public BaseStock getByItemProperty(String sku, String code,
                                       String itemProperty) {
        return baseStockDao.getByItemProperty(sku, code, itemProperty);
    }
}
