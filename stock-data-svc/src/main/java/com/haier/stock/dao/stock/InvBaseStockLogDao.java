package com.haier.stock.dao.stock;


import com.haier.stock.model.InvBaseStockLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InvBaseStockLogDao {
    Integer insert(InvBaseStockLog log);

    List<InvBaseStockLog> getLogPageByCondition(@Param("log")InvBaseStockLog condition, @Param("start")int start, @Param("rows")int pageSize);

    long getLogPagerCount(@Param("log")InvBaseStockLog condition);
}
