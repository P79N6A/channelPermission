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
    ServiceResult<Boolean> reviseLessStockTransChannel(Integer transId, String channel,
                                                       String user);
}
