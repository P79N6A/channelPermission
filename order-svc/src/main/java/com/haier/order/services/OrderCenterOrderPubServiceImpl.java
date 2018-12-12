package com.haier.order.services;

import com.haier.common.ServiceResult;
import com.haier.order.dateSource.ReadWriteRoutingDataSourceHolder;
import com.haier.order.model.ConfirmOrderModel;
import com.haier.order.service.OrderCenterOrderPubService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 胡万来 on 2017/12/25 0025.
 */
@Service
//@Configuration
//@EnableScheduling
public class OrderCenterOrderPubServiceImpl implements OrderCenterOrderPubService {

    private static Logger log = LoggerFactory.getLogger(OrderCenterOrderPubServiceImpl.class);

    @Autowired
    private ConfirmOrderModel confirmOrderModel;

    @Override
//    @Scheduled(cron = "0 0/3 * * * ?")
    public ServiceResult<Boolean> autoConfirmOrder() {

        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
            confirmOrderModel.autoConfirmOrder();
            result.setResult(true);
        } catch (Exception e) {
            log.error("自动确认订单,发生异常：", e);
            result.setMessage("服务器发生异常：" + e.getMessage());
            result.setSuccess(false);
            result.setResult(false);
        } finally {
            ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
        }
        return result;
    }
}
