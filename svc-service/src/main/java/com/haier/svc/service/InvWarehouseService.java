package com.haier.svc.service;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.InvBaseCityCode;
import com.haier.stock.model.InvWarehouse;

import java.util.List;
import java.util.Map;

public interface InvWarehouseService {
    public ServiceResult<List<InvWarehouse>> getInvWarehouseInfo(Map<String, Object> param);

    public Integer getInvWareHouseCount(Map<String, Object> params);

    public int checkMainKey(Map<String, Object> params);

    public void createInvWareHouse(Map<String, Object> params);

    public void updateInvWareHouse(Map<String, Object> params);

    public void deleteInvWareHouse(Map<String, Object> params);

    public void openStatusInvWarehouse(Map<String, Object> params);

    public void closeStatusInvWarehouse(Map<String, Object> params);

    public ServiceResult<List<InvWarehouse>> getInvWarehouseExport(Map<String, Object> params);

    public ServiceResult<List<InvBaseCityCode>> getInvBaseCityCode(Map<String, Object> params);
}
