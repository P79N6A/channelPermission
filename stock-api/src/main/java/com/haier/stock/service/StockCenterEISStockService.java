package com.haier.stock.service;

import com.haier.common.ServiceResult;

/**
 *
 *
 * @Filename: StockService.java
 * @Version: 1.0
 * @Author: 吴坤洋
 *
 */
public interface StockCenterEISStockService {

    /**
     * 修正LesStockTransQueue的渠道
     * @param transId
     * @param channel
     * @param user
     * @return
     */
    ServiceResult<Boolean> reviseLessStockTransChannel(Integer transId, String channel, String user);

    /**
     * JOB：根据出入库记录调用财务接口
     * @return
     */
    ServiceResult<Boolean> processLesStockTransForFinance();


    /**
     * JOB：同步预留库存记录
     * @return
     */
    ServiceResult<Boolean> syncStockReserved();
}
