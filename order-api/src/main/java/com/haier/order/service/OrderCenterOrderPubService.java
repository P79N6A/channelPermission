package com.haier.order.service;

import com.haier.common.ServiceResult;

/**
 * Created by 胡万来 on 2017/12/25 0025.
 */
public interface OrderCenterOrderPubService {
    /**
     * 确认订单,供定时任务调用
     * @return
     */
    ServiceResult<Boolean> autoConfirmOrder();
}
