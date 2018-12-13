package com.haier.shop.service;

import com.haier.shop.model.CostPools;
import com.haier.shop.model.CostPoolsUsedLogs;

import java.util.List;
import java.util.Map;

public interface CostPoolsUsedLogsService {
    void insert(CostPoolsUsedLogs costPoolsUsedLogs);

    List<CostPools> findCostPoolsUsedLogsByPage(Map<String, Object> params);

    int getTotal();

    List<Map<String,Object>> getExportCostPoolsUsedLogsList(Map<String, Object> paramMap);
}
