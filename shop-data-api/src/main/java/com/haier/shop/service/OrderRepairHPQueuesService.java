package com.haier.shop.service;

import com.haier.shop.model.OrderRepairHPQueues;

/*
*
* 作者:张波
* 2017/12/20
* */
public interface OrderRepairHPQueuesService {
    /**
     * 创建HP退货队列
     * @param orderRepairHPQueues
     * @return
     */
    Integer insert(OrderRepairHPQueues orderRepairHPQueues);
}
