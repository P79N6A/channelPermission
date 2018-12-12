package com.haier.shop.service;

import com.haier.shop.model.StockChangeQueue;

import java.util.List;

import com.haier.shop.model.StockChangeQueue;


public interface StockChangeQueueService {
    /**
     * 获取待处理的队列
     * @param topX 获取前X条
     * @return
     */
    List<StockChangeQueue> getListToProcess(Integer topX);

    /**
     * 新增库存变化队列
     * @param stockChangeQueue
     * @return 影响条数
     */
    Integer insert(StockChangeQueue stockChangeQueue);

    /**
     * 删除已处理的队列
     * 注意：必须要处理后才能删除
     * @param id 队列id
     * @return 影响条数
     */
    Integer delete(Integer id);
}
