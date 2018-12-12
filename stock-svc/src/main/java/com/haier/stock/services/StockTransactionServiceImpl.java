package com.haier.stock.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.stock.model.InvStockTransaction;
import com.haier.stock.service.StockTransactionService;

/**
 * 库存交易服务实现
 * Created by 钊 on 2014/4/1.
 */
@Configuration
@EnableScheduling
@Service
public class StockTransactionServiceImpl implements StockTransactionService {
    private final static Logger logger   = LoggerFactory
        .getLogger(StockTransactionServiceImpl.class);
    private static final String LOG_MARK = "[Stock][StockTransactionServiceImpl] ";
    @Autowired
    private StockTransactionModel stockTransactionModel;

    public void setStockTransactionModel(StockTransactionModel stockTransactionModel) {
        this.stockTransactionModel = stockTransactionModel;
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
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
    
   
}
