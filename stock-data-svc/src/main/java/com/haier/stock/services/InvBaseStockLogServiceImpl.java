package com.haier.stock.services;

import com.haier.stock.dao.stock.InvBaseStockLogDao;
import com.haier.stock.model.InvBaseStockLog;
import com.haier.stock.service.InvBaseStockLogService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class InvBaseStockLogServiceImpl implements InvBaseStockLogService {
    @Autowired
    InvBaseStockLogDao invBaseStockLogDao;

    @Override
    public Integer insert(InvBaseStockLog log) {
        // TODO Auto-generated method stub
        return invBaseStockLogDao.insert(log);
    }
}
