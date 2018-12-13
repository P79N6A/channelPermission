package com.haier.stock.service;

import com.haier.common.ServiceResult;
import com.haier.stock.model.InvWarehouse;

import java.util.List;
import java.util.Map;

public interface StockInvWarehouseService {
	  /**
     * 
     * @title getWhByEstorgeId
     * @description 暴露的接口dao层，通过estorge_id查询数据
     */
    public InvWarehouse getWhByEstorgeId(String estorge_id);
    
    /**
     * 
     * @title getAllWhByEstorgeId
     * @description 暴露的接口dao层，通过estorge_id查询所有数据
     */
    public InvWarehouse getAllWhByEstorgeId(String estorge_id);

    public  InvWarehouse getByWhCode( String whCode);
    
    public String getWhCodeByCenterCode(String centerCode);

    public Integer getInvWarehouseCount(Map<String, Object> params);

    public List<InvWarehouse> getInvWarehouseInfo(Map<String, Object> param);

    public int checkMainKey(Map<String, Object> params);

    public void createInvWarehouse(Map<String, Object> params);

    public void updateInvWarehouse(Map<String, Object> params);

    public void deleteInvWareHouse(Map<String, Object> params);

    public void openStatusInvWarehouse(Map<String, Object> params);

    public void closeStatusInvWarehouse(Map<String, Object> params);

    public List<InvWarehouse> getInvWarehouseExport(Map<String, Object> param);



}
