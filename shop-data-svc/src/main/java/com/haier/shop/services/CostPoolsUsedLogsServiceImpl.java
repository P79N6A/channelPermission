package com.haier.shop.services;

import com.haier.shop.dao.shopread.CostPoolsUsedLogsReadDao;
import com.haier.shop.dao.shopwrite.CostPoolsUsedLogsWriteDao;
import com.haier.shop.model.CostPools;
import com.haier.shop.model.CostPoolsUsedLogs;
import com.haier.shop.service.CostPoolsUsedLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CostPoolsUsedLogsServiceImpl implements CostPoolsUsedLogsService {

    @Autowired
    private CostPoolsUsedLogsWriteDao costPoolsUsedLogsWriteDao;
    @Autowired
    private CostPoolsUsedLogsReadDao costPoolsUsedLogsReadDao;

    @Override
    public void insert(CostPoolsUsedLogs costPoolsUsedLogs) {
        costPoolsUsedLogsWriteDao.insert(costPoolsUsedLogs);
    }

    @Override
    public List<CostPools> findCostPoolsUsedLogsByPage(Map<String, Object> params) {
        return costPoolsUsedLogsReadDao.findCostPoolsUsedLogsByPage(params);
    }

    @Override
    public int getTotal() {
        return costPoolsUsedLogsReadDao.getTotal();
    }

    @Override
    public List<Map<String, Object>> getExportCostPoolsUsedLogsList(Map<String, Object> paramMap) {
        return costPoolsUsedLogsReadDao.getExportCostPoolsUsedLogsList(paramMap);
    }
}
