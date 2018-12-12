package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopwrite.SmsLogsWriteDao;
import com.haier.shop.model.SmsLogs;
import com.haier.shop.service.HPDispatchWriteService;

/*
* 作者:张波
* 2017/12/25
*/
@Service
public class HPDispatchWriteServiceImpl implements HPDispatchWriteService {
    @Autowired
    SmsLogsWriteDao smsLogsWriteDao;

    @Override
    public int insertSmsLogs(SmsLogs smsLogs) {
        // TODO Auto-generated method stub
        return smsLogsWriteDao.insertSmsLogs(smsLogs);
    }

}
