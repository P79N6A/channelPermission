package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.SgRealtimeStockReadDao;
import com.haier.shop.dao.shopwrite.SgRealtimeStockWriteDao;
import com.haier.shop.model.SgRealtimeStock;
import com.haier.shop.service.SgRealtimeStockService;

@Service
public class SgRealtimeStockServiceImpl implements SgRealtimeStockService {

    @Autowired
    SgRealtimeStockWriteDao sgRealtimeStockWriteDao;
    @Autowired
    SgRealtimeStockReadDao sgRealtimeStockReadDao;

    @Override
    public SgRealtimeStock selectByParams(SgRealtimeStock record) {
        return sgRealtimeStockReadDao.selectByParams(record);
    }

    @Override
    public int insert(SgRealtimeStock record) {
        return sgRealtimeStockWriteDao.insert(record);
    }

    @Override
    public int updateByParams(SgRealtimeStock record) {
        return sgRealtimeStockWriteDao.updateByParams(record);
    }

    @Override
    public String findStoreCodeByStoreId(Integer id) {
        return sgRealtimeStockReadDao.findStoreCodeByStoreId(id);
    }
}