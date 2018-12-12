package com.haier.stock.services;

import java.util.List;

import com.haier.purchase.data.model.PurchaseGdQueue;
import com.haier.purchase.data.service.PurchaseGdQueueService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PurchaseGdQueueModel {
    private static Logger      logger = LogManager.getLogger(PurchaseGdQueueModel.class);
    @Autowired
    private PurchaseGdQueueService purchaseGdQueueService;

    /**
     * 基地库存--查询采购队列
     * @param orderSn
     * @return
     */
    public PurchaseGdQueue getByOrderSn(String orderSn) {
        return purchaseGdQueueService.getByOrderSn(orderSn);
    }

    /**
     * 基地库存--查询没有同步出库的网单
     * @return
     */
    public List<PurchaseGdQueue> queryUnSyncWaitOut() {
        return this.purchaseGdQueueService.queryUnSyncWaitOut();
    }

    /**
     * 基地库存--更新同步网单状态
     * @param purchaseGdQueue
     * @return
     */
    public boolean updateSyncOut(PurchaseGdQueue purchaseGdQueue) {
        Integer rows = this.purchaseGdQueueService.updateSyncWaitOut(purchaseGdQueue);
        if (rows == null) {
            return false;
        }
        if (rows > 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("更新待出库网单成功，网单号=" + purchaseGdQueue.getOrdersn());
            }
        } else {
            logger.warn("更新待出库网单失败，网单号=" + purchaseGdQueue.getOrdersn());
        }
        return rows > 0;
    }

    /**
     * 基地库存-- 写入基地库采购
     * @param purchaseGdQueue
     * @return
     */
    public boolean insertPurchaseQueue(PurchaseGdQueue purchaseGdQueue) {
        Integer rows = purchaseGdQueueService.insert(purchaseGdQueue);
        return rows > 0;
    }

    /**
     * 基地库存-- 更新基地库存状态
     * @param purchaseGdQueue
     * @return
     */
    public boolean updatePurchaseQueue(PurchaseGdQueue purchaseGdQueue) {
        if (PurchaseGdQueue.GD_STATUS_CANCEL.equals(purchaseGdQueue.getStatus())) {
            PurchaseGdQueue tempPurchaseGdQueue = purchaseGdQueueService.getByOrderSn(purchaseGdQueue
                .getOrdersn());
            if (tempPurchaseGdQueue == null) {
                logger.info("updatePurchaseQueue:网单号" + purchaseGdQueue.getOrdersn()
                            + "更新成取消状态，无词条记录");
                return false;
            }
        }
        Integer rows = purchaseGdQueueService.update(purchaseGdQueue);
        return rows > 0;
    }

    public PurchaseGdQueueService getPurchaseGdQueueDao() {
        return purchaseGdQueueService;
    }

    public void setPurchaseGdQueueDao(PurchaseGdQueueService purchaseGdQueueService) {
        this.purchaseGdQueueService = purchaseGdQueueService;
    }

}
