package com.haier.stock.service;

import java.util.Map;

public interface StockTransactionWriteService {
	  /**
     * 重推操作 禁止推送操作
     * @param params
     */
    void updateStockTransaction(Map<String, Object> params);
}
