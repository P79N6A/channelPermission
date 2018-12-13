package com.haier.eop.services;

import com.haier.eop.data.model.OrdersQueue;
import com.haier.eop.data.service.AgainSyncService;
import com.haier.eop.service.EopCenterAgainSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EopCenterAgainSyncServiceImpl implements EopCenterAgainSyncService {

    @Autowired
    private AgainSyncService againSyncService;

    @Override
    public OrdersQueue selectBysourceOrderSnAndsource(String sourceOrderSn, String source) {
        OrdersQueue record = new OrdersQueue();
        record.setSource(source);
        record.setSourceOrderSn(sourceOrderSn);
        return againSyncService.selectBysourceOrderSnAndsource(record);
    }

    @Override
    public boolean insert(OrdersQueue record) {
        try {
            againSyncService.insert(record);
        } catch (Exception e) {
            new  ServiceException("获取订单列表保存失败");
        }
        return true;
    }

    @Override
    public boolean update(OrdersQueue record) {
        againSyncService.update(record);
        try {
            againSyncService.update(record);
        } catch (Exception e) {
            new  ServiceException("修改订单失败");
        }
        return true;
    }

    class  ServiceException extends RuntimeException {

        public ServiceException() {
            super();
        }

        public ServiceException(String message, Throwable cause) {
            super(message, cause);
        }

        public ServiceException(String message) {
            super(message);
        }

        public ServiceException(Throwable cause) {
            super(cause);
        }

    }
}
