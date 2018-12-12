package com.haier.order.services;

import java.util.List;

import com.haier.order.model.StockTransactionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.stock.model.InvStockTransaction;

/**
 * 库存交易服务实现
 * Created by 钊 on 2014/4/1.
 */
@Service
public class OrderCenterStockTransactionServiceImpl {
    private final static Logger logger = LoggerFactory
            .getLogger(OrderCenterStockTransactionServiceImpl.class);
    private static final String LOG_MARK = "[Stock][OrderThirdCenterStockTransactionServiceImpl] ";
    @Autowired
    private StockTransactionModel stockTransactionModel;

    public void setStockTransactionModel(StockTransactionModel stockTransactionModel) {
        this.stockTransactionModel = stockTransactionModel;
    }

    public ServiceResult<Boolean> addStockTransaction(InvStockTransaction stockTransaction) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(stockTransactionModel.addStockTransaction(stockTransaction, null));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage("新增库存交易错误：" + e.getMessage());
            if (e instanceof BusinessException) {
                logger.error(LOG_MARK + "新增库存交易错误：" + e.getMessage());
            } else {
                logger.error(LOG_MARK + "新增库存交易错误：", e);
            }
        }
        return result;
    }

    public ServiceResult<Boolean> addStockTransactionWithCheck(InvStockTransaction stockTransaction,
                                                               Integer lesStockTransQueueId) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(
                    stockTransactionModel.addStockTransaction(stockTransaction, lesStockTransQueueId));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage("新增库存交易错误：" + e.getMessage());
            if (e instanceof BusinessException) {
                logger.error(LOG_MARK + "[addStockTransactionWithCheck]新增库存交易错误：" + e.getMessage());
            } else {
                logger.error(LOG_MARK + "[addStockTransactionWithCheck]新增库存交易错误：", e);
            }
        }
        return result;
    }

    public ServiceResult<List<InvStockTransaction>> getNotProcessBusiness() {
        ServiceResult<List<InvStockTransaction>> result = new ServiceResult<List<InvStockTransaction>>();
        try {
            result.setResult(stockTransactionModel.getNotProcessBusiness());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("获取没有处理关联业务的库存交易记录失败：" + e.getMessage());
            logger.error(LOG_MARK + "获取没有处理关联业务的库存交易记录失败：", e);
        }
        return result;
    }

    public ServiceResult<Boolean> updateBusinessProcessStatus(Integer id,
                                                              Integer businessProcessStatus) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(stockTransactionModel.updateBusinessStatus(id, businessProcessStatus));
        } catch (Exception e) {
            result.setResult(false);
            result.setSuccess(false);
            result.setMessage("更新库存交易的业务处理状态失败：" + e.getMessage());
            logger.error(LOG_MARK + "更新库存交易的业务处理状态失败:", e);
        }
        return result;
    }

    /**
     * 根据库存交易生成库龄计算数据
     */

//    @Scheduled(cron="0/5 * *  * * ?")
    public ServiceResult<Boolean> processForGenerateStockAgeInOut() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            stockTransactionModel.processForGenerateStockAgeInOut();
        } catch (Exception e) {
            result.setResult(false);
            result.setSuccess(false);
            result.setMessage("更新库存交易的业务处理状态失败：" + e.getMessage());
            logger.info(LOG_MARK + "更新库存交易的业务处理状态失败:", e);
        }
        return result;
    }

    /**
     * 根据库存交易更新库存
     */

//    @Scheduled(cron="0/5 * *  * * ?")
    public ServiceResult<Boolean> processForUpdateStock() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            stockTransactionModel.processForUpdateStock();
            result.setResult(true);
        } catch (Exception e) {
            logger.error(LOG_MARK + "通过库存交易计算库存出现错误：", e);
            result.setResult(false);
            result.setSuccess(false);
            result.setMessage("通过库存交易计算库存出现错误：" + e.getMessage());
        }
        return result;
    }

    public ServiceResult<Boolean> processForDelay() {

        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            stockTransactionModel.processForDelay();
            result.setResult(true);
        } catch (Exception e) {
            result.setResult(false);
            result.setSuccess(false);
            result.setMessage("处理延后处理的记录出现错误：" + e.getMessage());
            logger.error(LOG_MARK + "处理延后处理的记录出现错误：", e);
        }
        return result;
    }

    public ServiceResult<List<InvStockTransaction>> queryData(Integer id, Integer num) {
        ServiceResult<List<InvStockTransaction>> result = new ServiceResult<List<InvStockTransaction>>();
        try {
            result.setResult(stockTransactionModel.queryData(id, num));
            result.setSuccess(true);
        } catch (Exception e) {
            result.setResult(null);
            result.setSuccess(false);
            result.setMessage("查询修复les数据出现错误：" + e.getMessage());
            logger.error(LOG_MARK + "查询修复les数据出现错误：", e);
        }
        return result;
    }
}
