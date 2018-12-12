package com.haier.stock.services;

import com.haier.stock.dao.stock.InvBaseStockDiffLogDao;
import com.haier.stock.model.InvBaseStockDiffLog;
import com.haier.stock.service.InvBaseStockDiffLogService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class InvBaseStockDiffLogServiceImpl implements InvBaseStockDiffLogService {
    @Autowired
    InvBaseStockDiffLogDao invBaseStockDiffLogDao;

    @Override
    public Object insert(InvBaseStockDiffLog invBaseStockDiffLog) {
        // TODO Auto-generated method stub
        return invBaseStockDiffLogDao.insert(invBaseStockDiffLog);
    }

    @Override
    public int batchDelete(String date, Integer day) {
        // TODO Auto-generated method stub
        return invBaseStockDiffLogDao.batchDelete(date, day);
    }

    @Override
    public String getMaxTime() {
        // TODO Auto-generated method stub
        return invBaseStockDiffLogDao.getMaxTime();
    }

}
