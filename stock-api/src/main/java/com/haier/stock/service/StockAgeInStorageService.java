package com.haier.stock.service;

import com.haier.common.ServiceResult;

public interface StockAgeInStorageService {
	  /**
     * 按天根据出入库记录(InvStockInOut)增量计算库龄.<br/>
     * <b>JOB:</b> {@code 0 10 0 * * ? *} ,每天凌晨0:10
     * 
     * @return ture:成功 false:失败
     */
    ServiceResult<Boolean> calculateStockAgeDaily();
}
