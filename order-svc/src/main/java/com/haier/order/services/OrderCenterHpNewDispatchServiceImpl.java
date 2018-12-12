package com.haier.order.services;

import com.haier.common.ServiceResult;
import com.haier.order.dateSource.ReadWriteRoutingDataSourceHolder;
import com.haier.order.model.HpNewDispatchModel;
import com.haier.order.service.OrderCenterHpNewDispatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 胡万来 on 2018/1/11 0011.
 */
//@Configuration
//@EnableScheduling
@Service
public class OrderCenterHpNewDispatchServiceImpl implements OrderCenterHpNewDispatchService {

    private static Logger log = LoggerFactory.getLogger(OrderCenterHpNewDispatchServiceImpl.class);
    @Autowired
    private HpNewDispatchModel hpNewDispatchModel;

//    @Scheduled(cron = "0 0/3 * * * ?")

    @Override
    public ServiceResult<Boolean> sendHpNewBatchToDispatch() {

        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
            hpNewDispatchModel.sendHpNewBatchToDispatch();
            result.setResult(true);
        } catch (Exception e) {
            result.setResult(false);
            result.setSuccess(false);
            result.setMessage("[HpNewDispatchServiceImpl]推送信息到HP派工时发生未知异常:" + e.getMessage());
            log.error("[HpNewDispatchServiceImpl]推送信息到HP派工时发生未知异常:", e);
        } finally {
            ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
        }
        return result;
    }
}
