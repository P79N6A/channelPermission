package com.haier.shop.service;


import java.util.List;

import com.haier.shop.model.OrderShippedQueue;

/*
* 作者:张波
* 2017/12/20
* */
public interface OrderShippedQueueService {
    /**
     * 新增出库队列
     * @param queue
     * @return
     */
    Integer insert(OrderShippedQueue queue);
    /**
     * 获取待处理的出库队列
     * @param topX 前x条
     * @return
     */
    List<OrderShippedQueue> getListToProcess( Integer topX);
    /**
     * 根据主键，删除出库队列
     * @param id
     * @return
     */
    Integer delete(Integer id);
}
