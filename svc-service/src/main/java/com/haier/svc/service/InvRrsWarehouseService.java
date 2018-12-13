package com.haier.svc.service;

import com.haier.common.ServiceResult;
import com.haier.stock.model.InvRrsWarehouse;

import java.util.List;
import java.util.Map;

public interface InvRrsWarehouseService {
    public ServiceResult<List<InvRrsWarehouse>> getPurRrsWhByEstorgeId(Map<String, Object> param);

    public Integer countTotalService(Map<String, Object> param);

    public Integer checkMainKey(Map<String, Object> param);

    public void insertInvRrsWarehouseService(Map<String, Object> param);

    public Integer countT2StatusService(Map<String, Object> param);

    public void updateInvRrsWarehouseService(Map<String, Object> param);

    public void deleteInvRrsWarehouseService(Map<String, Object> param);

    public List<InvRrsWarehouse> selectInvRrsWarehouseExportService(Map<String, Object> param);
}
