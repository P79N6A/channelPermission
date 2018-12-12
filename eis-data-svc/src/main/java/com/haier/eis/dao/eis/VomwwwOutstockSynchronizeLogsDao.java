package com.haier.eis.dao.eis;

import com.haier.eis.model.VomwwwOutstockSynchronizeLogs;
import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface VomwwwOutstockSynchronizeLogsDao {
    Integer insert(VomwwwOutstockSynchronizeLogs log);

    List<VomwwwOutstockSynchronizeLogs> getVomwwwOutstockSynchronizeLogsList(@Param("status") Integer status,
                                                                             @Param("vomwwwLogsSize") Integer vomwwwLogsSize,
                                                                             @Param("vomwwwLogsErrorcount") Integer vomwwwLogsErrorcount);
}
