package com.haier.eis.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.haier.eis.dao.eis.EisStockTrans2ExternalDao;
import com.haier.eis.model.LesStockTransQueue;
import com.haier.eis.model.LesStockTransQueue3W;
import com.haier.eis.service.EisStockTrans2ExternalService;

@Service
public class EisStockTrans2ExternalServiceImpl implements EisStockTrans2ExternalService {
    @Autowired
    EisStockTrans2ExternalDao eisStockTrans2ExternalDao;

    @Override
    public LesStockTransQueue getByLesBillNo(String billNo) {
        // TODO Auto-generated method stub
        return eisStockTrans2ExternalDao.getByLesBillNo(billNo);
    }

    @Override
    public LesStockTransQueue getByOrderSn(String orderSn) {
        // TODO Auto-generated method stub
        return eisStockTrans2ExternalDao.getByOrderSn(orderSn);
    }

    @Override
    public Integer insert(LesStockTransQueue lesStockTransQueue) {
        // TODO Auto-generated method stub
        return eisStockTrans2ExternalDao.insert(lesStockTransQueue);
    }

	@Override
	public Integer insert2(LesStockTransQueue3W lesStockTransQueue) {
		// TODO Auto-generated method stub
		return eisStockTrans2ExternalDao.insert2(lesStockTransQueue);
	}

}
