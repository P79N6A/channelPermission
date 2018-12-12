package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.SmsLogs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SmsLogsWriteDao {
    public Integer insertSmsLogs(SmsLogs smsLogs);

    public Integer batchInsertSmsLogs(@Param("smsLogsList") List<SmsLogs> smsLogsList);
}
