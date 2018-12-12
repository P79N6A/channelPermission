package com.haier.eis.service;


import com.haier.eis.model.VomwwwOutstockSynchronizeLogs;

import java.util.List;


public interface VomwwwOutstockSynchronizeLogsService {
    Integer insert(VomwwwOutstockSynchronizeLogs log);

    List<VomwwwOutstockSynchronizeLogs> getVomwwwOutstockSynchronizeLogsList(Integer status,
                                                                             Integer vomwwwLogsSize,
                                                                             Integer vomwwwLogsErrorcount);
}
