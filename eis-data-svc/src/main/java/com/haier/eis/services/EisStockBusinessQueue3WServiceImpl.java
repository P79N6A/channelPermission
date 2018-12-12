package com.haier.eis.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.eis.dao.eis.EisStockBusinessQueue3WDao;
import com.haier.eis.model.EisStockBusinessQueue3W;
import com.haier.eis.service.EisStockBusinessQueue3WService;

@Service
public class EisStockBusinessQueue3WServiceImpl implements EisStockBusinessQueue3WService {
    @Autowired
    EisStockBusinessQueue3WDao eisStockBusinessQueue3WDao;

    @Override
    public Integer insert(EisStockBusinessQueue3W stockBusinessQueue3W) {
        // TODO Auto-generated method stub
        return eisStockBusinessQueue3WDao.insert(stockBusinessQueue3W);
    }

    @Override
    public Integer delete(EisStockBusinessQueue3W stockBusinessQueue3W) {
        // TODO Auto-generated method stub
        return eisStockBusinessQueue3WDao.delete(stockBusinessQueue3W);
    }

    @Override
    public List<EisStockBusinessQueue3W> getTops(Integer topx) {
        // TODO Auto-generated method stub
        return eisStockBusinessQueue3WDao.getTops(topx);
    }
}
