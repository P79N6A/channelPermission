package com.haier.stock.dao.stock;

import org.apache.ibatis.annotations.Param;

import com.haier.stock.model.StockTransactionExist;


public interface StockTransactionExistDao {

    StockTransactionExist getByLesStockTransQueueId(@Param("lesStockTransQueueId") Integer lesStockTransQueueId);

    int insert(StockTransactionExist stockTransactionExist);

}