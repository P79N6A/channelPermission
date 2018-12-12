package com.haier.purchase.data.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.haier.purchase.data.dao.purchase.OrderOperationLogDao;
import com.haier.purchase.data.model.OrderOperationLog;
import com.haier.purchase.data.service.OrderOperationLogService;
import org.springframework.stereotype.Service;


/**
 * @Filename: OrderOperationLogDao.java
 * @Version: 1.0
 * @Author: lizhen
 * @Email: zhen1.li@dhc.com.cn
 */
@Service
public class OrderOperationLogServiceImpl implements OrderOperationLogService {
    @Autowired
    OrderOperationLogDao operationLogDao;

    @Override
    public List<OrderOperationLog> getOrderOperationLogInfo(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return operationLogDao.getOrderOperationLogInfo(params);
    }

    @Override
    public Object createOrderOperationLog(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return operationLogDao.createOrderOperationLog(params);
    }

    @Override
    public Object insertOrderOperationLog(List<OrderOperationLog> insertList) {
        // TODO Auto-generated method stub
        return operationLogDao.insertOrderOperationLog(insertList);
    }
}
