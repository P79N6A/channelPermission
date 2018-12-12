package com.haier.shop.service;


import java.util.List;

import com.haier.shop.model.OrderOperateLogs;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
public interface ShopOrderOperateLogsService {

    /**
     * 新增订单操作日志
     * @param orderOperateLogs
     * @return
     */
    Integer insert(OrderOperateLogs orderOperateLogs);
    
    void batchInsert(List<OrderOperateLogs> orderOperateLogsList);
}
