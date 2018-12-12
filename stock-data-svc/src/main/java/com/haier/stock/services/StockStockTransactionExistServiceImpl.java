package com.haier.stock.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.stock.dao.stock.StockTransactionExistDao;
import com.haier.stock.model.StockTransactionExist;
import com.haier.stock.service.StockStockTransactionExistService;
@Service
public class StockStockTransactionExistServiceImpl implements StockStockTransactionExistService{
	@Autowired
	private StockTransactionExistDao stockTransactionExistDao;
	@Override
	public StockTransactionExist getByLesStockTransQueueId(Integer lesStockTransQueueId) {
		// TODO Auto-generated method stub
		return stockTransactionExistDao.getByLesStockTransQueueId(lesStockTransQueueId);
	}

	@Override
	public int insert(StockTransactionExist stockTransactionExist) {
		// TODO Auto-generated method stub
		return stockTransactionExistDao.insert(stockTransactionExist);
	}

}
