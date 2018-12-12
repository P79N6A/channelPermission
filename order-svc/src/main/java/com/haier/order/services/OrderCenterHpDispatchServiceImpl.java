package com.haier.order.services;

import com.haier.common.ServiceResult;
import com.haier.order.model.HpAllotNetPointModel;
import com.haier.order.service.OrderCenterHpDispatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 胡万来 on 2018/1/11 0011.
 */
@Service
//@Configuration
//@EnableScheduling
public class OrderCenterHpDispatchServiceImpl implements OrderCenterHpDispatchService {

    @Autowired
    private HpAllotNetPointModel hpAllotNetPointModel;

    private static Logger log = LoggerFactory.getLogger(OrderCenterHpDispatchServiceImpl.class);

    //    @Scheduled(cron = "0 0/3 * * * ?")
    @Override
    public ServiceResult<Boolean> processNetPoint() {
        log.debug("HpDispatchTiming 定时任务");
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            hpAllotNetPointModel.allotNetPoint();
            result.setSuccess(true);
            result.setResult(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(false);
            log.error("接收HP分配网点信息出现异常!", e);
        }
        return result;
    }
}
