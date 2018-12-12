package com.haier.shop.service;


import java.util.List;

import com.haier.shop.model.SmsLogs;


public interface SmsLogsWriteService {
    public Integer insertSmsLogs(SmsLogs smsLogs);

    public Integer batchInsertSmsLogs( List<SmsLogs> smsLogsList);
}
