package com.haier.stock.services;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.PurchaseGdQueue;
import com.haier.stock.service.StockCenterPurchaseGdService;
@Service
public class StockCenterPurchaseGdServiceImpl implements StockCenterPurchaseGdService {
    private static Logger        logger = LogManager.getLogger(StockCenterPurchaseGdServiceImpl.class);
    @Autowired
    private PurchaseGdQueueModel purchaseGdQueueModel;

    @Override
    public ServiceResult<PurchaseGdQueue> getByOrderSn(String orderSn) {
        ServiceResult<PurchaseGdQueue> result = new ServiceResult<PurchaseGdQueue>();
        try {
            //记录基地采购数据
            PurchaseGdQueue queue = purchaseGdQueueModel.getByOrderSn(orderSn);
            //设置返回结果
            result.setResult(queue);
            //设置请求成功
            result.setSuccess(true);
        } catch (Exception e) {
            logger.error("getByOrderSn:查询基地库存发生异常，orderSn=" + orderSn + e.getMessage());
            //设置请求状态
            result.setSuccess(false);
            //设置请求结果
            result.setResult(null);
            result.setMessage("查询基地库存发生异常，orderSn=" + orderSn);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> insertPurchaseGdQueue(PurchaseGdQueue purchaseGDQueue) {
        //声明返回结果
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            //记录基地采购数据
            boolean isSuc = purchaseGdQueueModel.insertPurchaseQueue(purchaseGDQueue);
            //设置返回结果
            result.setResult(isSuc);
            //设置请求成功
            result.setSuccess(true);
        } catch (Exception e) {
            logger.error("insertPurchaseGdQueue:写入基地库存发生异常，orderSn=" + purchaseGDQueue.getOrdersn()
                         + e.getMessage());
            //设置请求状态
            result.setSuccess(false);
            //设置请求结果
            result.setResult(false);
            result.setMessage("写入基地库存发生异常，orderSn=" + purchaseGDQueue.getOrdersn());
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> updatePurchaseGdQueue(PurchaseGdQueue purchaseGDQueue) {
        //声明返回结果
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            boolean isSuc = purchaseGdQueueModel.updatePurchaseQueue(purchaseGDQueue);
            result.setResult(isSuc);
            result.setSuccess(true);
        } catch (Exception e) {
            logger.error("updatePurchaseGdQueue:更新基地库存发生异常，orderSn=" + purchaseGDQueue.getOrdersn()
                         + e.getMessage());
            //设置返回结果不成功
            result.setResult(false);
            //设置请求状态不成功
            result.setSuccess(false);
            result.setMessage("更新基地库存发生异常， orderSn=" + purchaseGDQueue.getOrdersn() + ":"
                              + e.getMessage());
        }

        return result;
    }

    @Override
    public ServiceResult<List<PurchaseGdQueue>> queryUnSyncWaitOut() {
        ServiceResult<List<PurchaseGdQueue>> listResult = new ServiceResult<List<PurchaseGdQueue>>();
        try {
            List<PurchaseGdQueue> queueList = purchaseGdQueueModel.queryUnSyncWaitOut();
            listResult.setResult(queueList);
            listResult.setSuccess(true);
        } catch (Exception e) {
            logger.error("queryUnSyncWaitOut:查询没有同步出库状态的基地库订单出现异常," + e.getMessage());
            //设置返回结果不成功
            listResult.setResult(null);
            //设置请求状态不成功
            listResult.setSuccess(false);
            listResult.setMessage("查询没有同步出库状态的基地库订单出现异常");
        }
        return listResult;
    }

    @Override
    public ServiceResult<Boolean> updateSyncWaitOut(PurchaseGdQueue purchaseGdQueue) {
        ServiceResult<Boolean> listResult = new ServiceResult<Boolean>();
        try {
            Boolean update = purchaseGdQueueModel.updateSyncOut(purchaseGdQueue);
            listResult.setResult(update);
            listResult.setSuccess(true);
        } catch (Exception e) {
            logger.error("updateSyncWaitOut:同步出库状态发生异常," + e.getMessage());
            //设置返回结果不成功
            listResult.setResult(false);
            //设置请求状态不成功
            listResult.setSuccess(false);
            listResult.setMessage("同步出库状态发生异常");
        }
        return listResult;
    }

    public PurchaseGdQueueModel getPurchaseGdQueueModel() {
        return purchaseGdQueueModel;
    }

    public void setPurchaseGdQueueModel(PurchaseGdQueueModel purchaseGdQueueModel) {
        this.purchaseGdQueueModel = purchaseGdQueueModel;
    }

}
