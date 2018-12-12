package com.haier.stock.services;

import com.haier.stock.dao.stock.InvLesStockDao;
import com.haier.stock.model.InvLesStock;
import com.haier.stock.service.InvLesStockService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class InvLesStockServiceImpl implements InvLesStockService {
    @Autowired
    InvLesStockDao invLesStockDao;

    @Override
    public InvLesStock getBySecCodeAndSku(String secCode, String sku) {
        // TODO Auto-generated method stub
        return invLesStockDao.getBySecCodeAndSku(secCode, sku);
    }

    @Override
    public Integer insert(InvLesStock stock) {
        // TODO Auto-generated method stub
        return invLesStockDao.insert(stock);
    }

    @Override
    public Integer update(InvLesStock stock) {
        // TODO Auto-generated method stub
        return invLesStockDao.update(stock);
    }

}
