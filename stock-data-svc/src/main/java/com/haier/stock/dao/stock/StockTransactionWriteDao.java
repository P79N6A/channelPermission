package com.haier.stock.dao.stock;

import java.util.Map;

public interface StockTransactionWriteDao {
	  /**
     * 重推操作 禁止推送操作
     * @param params
     */
    void updateStockTransaction(Map<String, Object> params);

}
