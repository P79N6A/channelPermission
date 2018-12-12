package com.haier.shop.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.AdjustReadDao;
import com.haier.shop.dao.shopwrite.AdjustWriteDao;
import com.haier.shop.model.Adjust;
import com.haier.shop.model.Invoices;
import com.haier.shop.service.AdjustService;

@Service
public class AdjustServiceImpl implements AdjustService {
    @Autowired
    AdjustReadDao adjustReadDao;
    @Autowired
    AdjustWriteDao adjustWriteDao;

    @Override
    public String getVehicleAdjustNo(String begin) {
        return adjustReadDao.getVehicleAdjustNo(begin);
    }

    @Override
    public int updateSelectiveByAdjustNo(Adjust entity) {
        return adjustWriteDao.updateSelectiveByAdjustNo(entity);
    }

    @Override
    public List<Invoices> getAdjustByTiming(long startTime, long endTime) {
        return adjustReadDao.getAdjustByTiming(startTime, endTime);
    }

}
