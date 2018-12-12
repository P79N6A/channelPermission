package com.haier.stock.service;

import com.haier.common.ServiceResult;

public interface ComputingAgeService {

	/**
     * 根据出入库记录(InvStockInOut)增量计算库龄(实时).<br/>
     * <b>JOB:</b> {@code 0 0/5 * * * ? *} ,每5分钟一次
     * 
     * @return ture:成功 false:失败
     */
    ServiceResult<Boolean> calculateStockAgeTimely();
}
