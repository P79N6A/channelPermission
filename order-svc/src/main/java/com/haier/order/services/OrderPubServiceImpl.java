package com.haier.order.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.order.dateSource.ReadWriteRoutingDataSourceHolder;
import com.haier.order.model.AutoCodConfirmModel;
import com.haier.order.service.OrderPubService;


/**
 * OMS订单接口实现类
 *                       
 * @Filename: OrderPubServiceImpl.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
@Service
public class OrderPubServiceImpl implements OrderPubService {
    private static Logger log = LoggerFactory.getLogger(OrderPubServiceImpl.class);

    @Autowired
    private AutoCodConfirmModel autoCodConfirmModel;

   
    @Override
    //@Scheduled(cron = "*/10 * * * * ?")
    public ServiceResult<Boolean> autoCodConfirm() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
            autoCodConfirmModel.autoCodConfirm();
            result.setResult(true);
        } catch (Exception e) {
            log.error("货到付款自动审核,发生异常：", e);
            result.setMessage("服务器发生异常：" + e.getMessage());
            result.setSuccess(false);
            result.setResult(false);
        } finally {
            ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
        }
        return result;
    }

    
}