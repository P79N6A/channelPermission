package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopwrite.BasChangeStockRegionWriteDao;
import com.haier.shop.model.BasChangeStockRegion;
import com.haier.shop.service.BasChangeStockRegionService;

@Service
public class BasChangeStockRegionServiceImpl implements BasChangeStockRegionService {
    @Autowired
    BasChangeStockRegionWriteDao basChangeStockRegionWriteDao;

    @Override
    public Integer insert(List<BasChangeStockRegion> insertList) {
        return basChangeStockRegionWriteDao.insert(insertList);
    }

    @Override
    public Integer update(BasChangeStockRegion changeStock) {
        return basChangeStockRegionWriteDao.update(changeStock);
    }

    @Override
    public Integer insert2(List<BasChangeStockRegion> insertList) {
        return basChangeStockRegionWriteDao.insert2(insertList);
    }

    @Override
    public Integer update2(BasChangeStockRegion changeStock) {
        return basChangeStockRegionWriteDao.update2(changeStock);
    }
}