package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopwrite.BasChangeStockWriteDao;
import com.haier.shop.model.BasChangeStock;
import com.haier.shop.service.BasChangeStockService;


@Service
public class BasChangeStockServiceImpl implements BasChangeStockService {
    @Autowired
    BasChangeStockWriteDao basChangeStockWriteDao;

    @Override
    public Integer insert(List<BasChangeStock> insertList) {
        return basChangeStockWriteDao.insert(insertList);
    }

    @Override
    public Integer update(BasChangeStock changeStock) {
        return basChangeStockWriteDao.update(changeStock);
    }

    @Override
    public Integer insert2(List<BasChangeStock> insertList) {
        return basChangeStockWriteDao.insert2(insertList);
    }

    @Override
    public Integer update2(BasChangeStock changeStock) {
        return basChangeStockWriteDao.update2(changeStock);
    }

    @Override
    public Integer deleteB2CInfoBySku(String sku) {
        return basChangeStockWriteDao.deleteB2CInfoBySku(sku);
    }
}
