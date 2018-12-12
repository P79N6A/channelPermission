package com.haier.eis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.eis.dao.eis.LesStockTransQueueDao;
import com.haier.eis.model.LesStockTransQueue;
import com.haier.eis.service.LesStockTransQueueService;
@Service
public class LesStockTransQueueServiceImpl implements LesStockTransQueueService {

	@Autowired
	LesStockTransQueueDao lesStockTransQueueDao;
	@Override
	public LesStockTransQueue getByLesBillNo(String billNo) {
		// TODO Auto-generated method stub
		return lesStockTransQueueDao.getByLesBillNo(billNo);
	}

	@Override
	public Integer insert(LesStockTransQueue stockTransQueues) {
		// TODO Auto-generated method stub
		return lesStockTransQueueDao.insert(stockTransQueues);
	}

}
