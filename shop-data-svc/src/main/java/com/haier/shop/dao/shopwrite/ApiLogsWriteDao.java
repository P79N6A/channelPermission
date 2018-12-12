package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.ApiLogs;
import org.apache.ibatis.annotations.Param;

public interface ApiLogsWriteDao {
    public int insert(ApiLogs apiLogs);
    public int updateReturnDataById(@Param("id") Integer id, @Param("returnData") String returnData);
}
