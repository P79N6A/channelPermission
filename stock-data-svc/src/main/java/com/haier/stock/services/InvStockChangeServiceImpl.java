package com.haier.stock.services;

import com.haier.stock.dao.stock.InvStockChangeDao;
import com.haier.stock.model.InvStockChange;
import com.haier.stock.service.InvStockChangeService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class InvStockChangeServiceImpl implements InvStockChangeService {
    @Autowired
    InvStockChangeDao invStockChangeDao;

    @Override
    public Integer insert(InvStockChange stockChange) {
        // TODO Auto-generated method stub
        return invStockChangeDao.insert(stockChange);
    }

    @Override
    public Integer update(InvStockChange stockChange) {
        // TODO Auto-generated method stub
        return invStockChangeDao.update(stockChange);
    }

    @Override
    public InvStockChange get(String sku, String secCode) {
        // TODO Auto-generated method stub
        return invStockChangeDao.get(sku, secCode);
    }

}
