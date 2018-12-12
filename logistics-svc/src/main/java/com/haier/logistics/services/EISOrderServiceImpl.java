package com.haier.logistics.services;

import com.haier.common.ServiceResult;
import com.haier.eis.service.EisLesStatusItemService;
import com.haier.logistics.Model.EISOrderModel;
import com.haier.logistics.service.EISOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service("eISOrderServiceImpl")
@Configuration
@EnableScheduling
public class EISOrderServiceImpl implements EISOrderService {
    private Logger log = LoggerFactory.getLogger(EISOrderServiceImpl.class);
    @Autowired
    private EISOrderModel eisOrderModel;
    @Autowired
    private EisLesStatusItemService eisLesStatusItemService;
    @Override
    //@Scheduled(cron = "*/10 * * * * ?")
    public ServiceResult<Boolean> processAfterLesShipped() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            //第一步
            System.out.println("定时任务开始执行");
            ServiceResult<Boolean> a=eisOrderModel.processAfterLesShipped();
            System.out.println("定时任务执行完毕");
            return a;
        } catch (Exception e) {
            log.error("LES出入库后，同步到CBS时发生未知异常:", e);
            result.setResult(false);
            result.setSuccess(false);
            result.setMessage("LES出入库后，同步到CBS时发生未知异常:" + e.getMessage());
            return result;
        }
    }
   //@Scheduled(cron = "*/10 * * * * ?")
    public ServiceResult<Boolean> processShippedQueue() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            System.out.println("开始执行");
            ServiceResult<Boolean> a=eisOrderModel.processShippedQueue();
            System.out.println("执行完毕");
            return a;
        } catch (Exception e) {
            log.error("处理出库队列时发生未知异常:", e);
            result.setResult(false);
            result.setSuccess(false);
            result.setMessage("处理出库队列时发生未知异常:" + e.getMessage());
            return result;
        }
    }
}
