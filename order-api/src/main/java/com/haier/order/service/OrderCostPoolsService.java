package com.haier.order.service;

import com.haier.common.ServiceResult;
import com.haier.shop.model.CostPools;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrderCostPoolsService {

    Map<String,Object> findCostPoolsByPage(Map<String, Object> params);

    Map<String,Object> findCostPoolsUsedLogsByPage(Map<String, Object> params);

    List<Map<String,Object>> getExportCostPoolsList(Map<String, Object> paramMap);

    List<Map<String,Object>> getExportCostPoolsUsedLogsList(Map<String, Object> paramMap);

    int addCostPool(CostPools costPools);

    ServiceResult<CostPools> findCostPoolsById(String id);

    Boolean updateCostPools(CostPools costPools);

    Boolean deleteCostPools(String id);

    CostPools findcostPoolsByTYBC(CostPools costPools);
}
