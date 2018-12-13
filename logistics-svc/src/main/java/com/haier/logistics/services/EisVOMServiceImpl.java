package com.haier.logistics.services;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.eis.model.VomReceivedQueue;
import com.haier.logistics.Model.EIS3WReturnWarehouseModel;
import com.haier.logistics.Model.EisVOMModel;
import com.haier.logistics.service.EisVOMService;

@Service
public class EisVOMServiceImpl implements EisVOMService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EisVOMServiceImpl.class);
    @Autowired
    private EisVOMModel eisVOMModel;
    @Autowired
    private EIS3WReturnWarehouseModel eIS3WReturnWarehouseModel;
    @Override
    //@Scheduled(cron = "*/10 * * * * ?")
    public ServiceResult<Boolean> processVomShippingStatus() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            Boolean a=eisVOMModel.processVomShippingStatus();
            result.setResult(a);
        } catch (Exception e) {
            result.setResult(false);
            result.setSuccess(false);
            result.setMessage("处理VOM物流状态信息失败：" + e.getMessage());
            LOGGER.error("处理VOM物流状态信息失败：", e);
        }
        return result;
    }
    
    /***
     * 处理VOM出入库记录 （定时任务）
     *
     */
//    @Override
//    @Scheduled(cron="0/5 * *  * * ?")
    public ServiceResult<Boolean> reprocessingContent() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(eisVOMModel.reprocessingContent());
        } catch (Exception e) {
            result.setResult(false);
            result.setSuccess(false);
            result.setMessage("预处理VOM消息失败：" + e.getMessage());
            LOGGER.error("预处理VOM消息失败：", e);
        }
        return result;
    }

//  @Override
//    @Scheduled(cron="0/5 * *  * * ?")
    @Override
  public ServiceResult<Boolean> processOutInStoreForGenerateStockTransaction() {
      ServiceResult<Boolean> result = new ServiceResult<Boolean>();
      try {
          result.setResult(eisVOMModel.processVomInOutStore());
      } catch (Exception e) {
          result.setResult(false);
          result.setSuccess(false);
          result.setMessage("处理VOM出入库记录-生成库存交易失败：" + e.getMessage());
          LOGGER.error("处理VOM出入库记录-生成库存交易失败：", e);
      }
      return result;
  }
	public void test() {
		System.out.println("测试定时任务，当前时间："+ new Date());
	}
    /**
     * 预处理VOM信息失败后再处理
     * @return
     */
    //@Scheduled(cron="0/5 * *  * * ?")
    public ServiceResult<Boolean> processVomReceivedQueueFailStatus(){
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            //目前只处理正品退货
            eisVOMModel.processQualityGoods();
            result.setResult(true);
        } catch (Exception e) {
            result.setResult(false);
            result.setSuccess(false);
            result.setMessage("处理正品退货拒单情况下失败的数据出现异常：" + e.getMessage());
            LOGGER.error("处理正品退货拒单情况下失败的数据出现异常：", e);
        }
        return result;
    }
}
