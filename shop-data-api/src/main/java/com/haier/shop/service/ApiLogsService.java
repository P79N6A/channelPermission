package com.haier.shop.service;

import com.haier.shop.model.ApiLogs;
import org.apache.ibatis.annotations.Param;

public interface ApiLogsService {
    public int insert(ApiLogs apiLogs);
    public int updateReturnDataById(@Param("id") Integer id, @Param("returnData") String returnData);
}
