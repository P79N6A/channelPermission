package com.haier.order.services;

import com.haier.common.ServiceResult;
import com.haier.order.model.CostPoolsModel;
import com.haier.order.service.OrderCostPoolsService;
import com.haier.shop.model.CostPools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderCostPoolsServiceImpl implements OrderCostPoolsService{


    @Autowired
    private CostPoolsModel costPoolsModel;


    @Override
    public Map<String, Object> findCostPoolsByPage(Map<String, Object> params) {
        return costPoolsModel.findCostPoolsByPage(params);
    }

    @Override
    public Map<String, Object> findCostPoolsUsedLogsByPage(Map<String, Object> params) {
        return costPoolsModel.findCostPoolsUsedLogsByPage(params);
    }

    @Override
    public List<Map<String, Object>> getExportCostPoolsList(Map<String, Object> paramMap) {
        return costPoolsModel.getExportCostPoolsList(paramMap);
    }

    @Override
    public List<Map<String, Object>> getExportCostPoolsUsedLogsList(Map<String, Object> paramMap) {
        return costPoolsModel.getExportCostPoolsUsedLogsList(paramMap);
    }

    @Override
    public int addCostPool(CostPools costPools) {
        return costPoolsModel.addCostPool(costPools);
    }

    @Override
    public ServiceResult<CostPools> findCostPoolsById(String id) {
        return costPoolsModel.findCostPoolsById(id);
    }

    @Override
    public Boolean updateCostPools(CostPools costPools) {
        return costPoolsModel.updateCostPools(costPools);
    }

    @Override
    public Boolean deleteCostPools(String id) {
        return costPoolsModel.deleteCostPools(id);
    }

    @Override
    public CostPools findcostPoolsByTYBC(CostPools costPools) {
        return costPoolsModel.findcostPoolsByTYBC(costPools);
    }
}
