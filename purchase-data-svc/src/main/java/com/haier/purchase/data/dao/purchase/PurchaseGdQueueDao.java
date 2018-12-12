package com.haier.purchase.data.dao.purchase;

import java.util.List;

import com.haier.purchase.data.model.PurchaseGdQueue;


public interface PurchaseGdQueueDao {

    /**
     * 基地库存--查询基地库存
     * @param ordersn
     * @return
     */
    PurchaseGdQueue getByOrderSn(String ordersn);

    /**
     * 基地库存--新增基地库存
     * @param purchaseGdQueue
     * @return
     */
    Integer insert(PurchaseGdQueue purchaseGdQueue);

    /**
     * 基地库存--更新基地 库存
     * @param purchaseGdQueue
     * @return
     */
    Integer update(PurchaseGdQueue purchaseGdQueue);

    /**
     * 基地库存--查询等待出库的记录
     * @return
     */
    List<PurchaseGdQueue> queryUnSyncWaitOut();

    /**
     * 基地库存--更新同步过的网单
     * @return
     */
    Integer updateSyncWaitOut(PurchaseGdQueue purchaseGdQueue);
}
