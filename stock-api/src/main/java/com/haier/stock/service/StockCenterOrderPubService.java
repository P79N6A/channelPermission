package com.haier.stock.service;

import com.haier.common.ServiceResult;

/**
 * Created by 胡万来 on 2017/12/25 0025.
 */
public interface StockCenterOrderPubService {
    /**
     * 确认订单,供定时任务调用
     * @return
     */
    ServiceResult<Boolean> autoConfirmOrder();
    
    /**
     * 系统后台正常自动锁定库存
     * @return
     */
    ServiceResult<Boolean> autoFrozenStockByOrder();
}
