package com.haier.order.services;

import com.haier.common.ServiceResult;
import com.haier.order.model.SycnOrderToLesModel;
import com.haier.order.service.OrderCenterSyncOrderToLesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 胡万来 on 2018/1/12 0012.
 */
@Service
public class OrderCenterSyncOrderToLesServiceImpl implements OrderCenterSyncOrderToLesService {

    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(OrderCenterSyncOrderToLesServiceImpl.class);

    @Autowired
    private SycnOrderToLesModel model;
    @Override
    public ServiceResult<Boolean> syncOrderToLes() {

        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {

            model.syncOrderToLes();

            result.setMessage("");
            result.setSuccess(true);
            result.setResult(true);
        } catch (Exception e) {
            log.error("[SyncOrderToLesServiceImpl]VOM开提单时发生未知异常:" + e.getMessage());
            result.setResult(false);
            result.setSuccess(false);
            result.setMessage("[SyncOrderToLesServiceImpl]VOM开提单时发生未知异常:" + e.getMessage());
        }
        return result;
    }
}
