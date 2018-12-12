package com.haier.stock.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.stock.dao.stock.StockTransactionWriteDao;
import com.haier.stock.service.StockTransactionWriteService;
@Service
public class StockTransactionWriteServiceImpl implements StockTransactionWriteService{
	@Autowired
	private StockTransactionWriteDao stockTransactionWriteDao;
	@Override
	public void updateStockTransaction(Map<String, Object> params) {
		// TODO Auto-generated method stub
		stockTransactionWriteDao.updateStockTransaction(params);
	}

}
