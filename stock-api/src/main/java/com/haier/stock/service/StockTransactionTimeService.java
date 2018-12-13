package com.haier.stock.service;

import com.haier.common.ServiceResult;

public interface StockTransactionTimeService {
	 /**
     * 更新库存
     * @return
     */
    ServiceResult<Boolean> processForUpdateStock();
    ServiceResult<Boolean> processForGenerateStockAgeInOut();

    ServiceResult<Boolean> processForGenerateStockAgeInOutHistory();

	/**
     * JOB-同步库存：处理les_stock_trans_queue,生成CBS出入库记录
     * @return
     */
    ServiceResult<Boolean> processLessStockTransQueue();

}
