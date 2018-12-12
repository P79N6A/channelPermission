package com.haier.stock.services;

import com.haier.stock.dao.stock.InvSgStockLogDao;
import com.haier.stock.model.InvSgStockLogEntity;
import com.haier.stock.service.InvSgStockLogService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

/**
 * 吴坤洋
 *
 * @author wukunyang
 */
@Service
public class InvSgStockLogServiceImpl implements InvSgStockLogService {
    @Autowired
    InvSgStockLogDao invSgStockLogDao;

    @Override
    public Integer insertInvSgStockLog(InvSgStockLogEntity invSgStockLog) {
        // TODO Auto-generated method stub
        return invSgStockLogDao.insertInvSgStockLog(invSgStockLog);
    }


}