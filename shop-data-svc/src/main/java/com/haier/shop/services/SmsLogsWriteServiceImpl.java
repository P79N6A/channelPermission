package com.haier.shop.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopwrite.SmsLogsWriteDao;
import com.haier.shop.model.SmsLogs;
import com.haier.shop.service.SmsLogsWriteService;

@Service
public class SmsLogsWriteServiceImpl implements SmsLogsWriteService {

    @Autowired
    SmsLogsWriteDao smsLogsWriteDao;

    @Override
    public Integer insertSmsLogs(SmsLogs smsLogs) {
        return smsLogsWriteDao.insertSmsLogs(smsLogs);
    }

    @Override
    public Integer batchInsertSmsLogs(List<SmsLogs> smsLogsList) {
        return smsLogsWriteDao.batchInsertSmsLogs(smsLogsList);
    }
}
