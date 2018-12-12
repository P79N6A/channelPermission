package com.haier.eis.services;

import com.haier.eis.dao.eis.OrderInterfaceDataLogDao;
import com.haier.eis.model.OrderInterfaceDataLog;
import com.haier.eis.service.OrderInterfaceDataLogService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

/*
* 作者:张波
* 2017/12/26
*/
@Service
public class OrderInterfaceDataLogServiceImpl implements OrderInterfaceDataLogService {
    @Autowired
    OrderInterfaceDataLogDao orderInterfaceDataLogDao;

    @Override
    public Integer insert(OrderInterfaceDataLog log) {
        // TODO Auto-generated method stub
        return orderInterfaceDataLogDao.insert(log);
    }
}
