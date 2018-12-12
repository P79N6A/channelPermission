package com.haier.stock.service;

import com.haier.stock.model.StockTransactionExist;

public interface StockStockTransactionExistService {
	 public  StockTransactionExist getByLesStockTransQueueId(Integer lesStockTransQueueId);

	 public  int insert(StockTransactionExist stockTransactionExist);
}
