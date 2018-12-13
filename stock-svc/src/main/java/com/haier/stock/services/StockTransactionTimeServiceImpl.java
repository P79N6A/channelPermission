package com.haier.stock.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.stock.service.StockTransactionTimeService;
@Configuration
@EnableScheduling
@Service
public class StockTransactionTimeServiceImpl implements StockTransactionTimeService{
	  private static final String LOG_MARK = "[Stock][StockTransactionTimeServiceImpl] ";
	  @Autowired
	    private EISStockModel eisStockModel;
	private final static Logger logger   = LoggerFactory
	        .getLogger(StockTransactionTimeServiceImpl.class);
	 @Autowired
	    private StockTransactionModel stockTransactionModel;
	   /**
     * 根据库存交易生成库龄计算数据
     */
    @Override
//	 @Scheduled(cron="0/5 * *  * * ?")
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
   * 根据库存交易生成库龄计算数据
   */
  @Override
//	 @Scheduled(cron="0/5 * *  * * ?")
  public ServiceResult<Boolean> processForGenerateStockAgeInOutHistory() {
    ServiceResult<Boolean> result = new ServiceResult<Boolean>();
    try {
      stockTransactionModel.processForGenerateStockAgeInOutHistory();
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
    @Override
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
    
    /**JOB-同步库存：处理les_stock_trans_queue,生成CBS出入库记录
	 * 
		库龄计算分渠道出入库信息
	 */
//    @Scheduled(cron="0/5 * *  * * ?")
    @Override
    public ServiceResult<Boolean> processLessStockTransQueue() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            eisStockModel.processLessStockTransJob();
            result.setResult(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage("处理les_stock_trans_queue失败：" + e.getMessage());
        }
        return result;
    }
}
