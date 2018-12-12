package com.haier.stock.services;


import java.util.List;

import com.haier.stock.dao.stock.InvStockQtyDifLogDao;
import com.haier.stock.model.InvStockQtyDifLog;
import com.haier.stock.service.InvStockQtyDifLogService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class InvStockQtyDifLogServiceImpl implements InvStockQtyDifLogService {
    @Autowired
    InvStockQtyDifLogDao invStockQtyDifLogDao;

    @Override
    public List<InvStockQtyDifLog> queryDifStockQty() {
        // TODO Auto-generated method stub
        return invStockQtyDifLogDao.queryDifStockQty();
    }

    @Override
    public Integer insert(InvStockQtyDifLog difLog) {
        // TODO Auto-generated method stub
        return invStockQtyDifLogDao.insert(difLog);
    }

    ;

}
