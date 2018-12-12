package com.haier.order.services;

import com.haier.common.ServiceResult;
import com.haier.order.model.OrderMarkBuilderModel;
import com.haier.order.service.OrderCenterOrderMarkBuilderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by 胡万来 on 2018/1/12 0012.
 */
@Service
public class OrderCenterOrderMarkBuilderServiceImpl implements OrderCenterOrderMarkBuilderService {

    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(OrderCenterOrderMarkBuilderServiceImpl.class);
    @Autowired
    private OrderMarkBuilderModel orderMarkBuilderModel;
    @Override
    public ServiceResult<Boolean> makeBuilderOrderProcess() {

        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            orderMarkBuilderModel.autoMarkBuilderOrder();
            result.setResult(true);
        } catch (Exception e) {
            log.error("获取标建,发生异常：", e);
            result.setMessage("获取标建服务器发生异常：" + e.getMessage());
            result.setSuccess(false);
            result.setResult(false);
        }
        return result;
    }
}
