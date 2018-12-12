package com.haier.stock.service;

import com.haier.stock.model.InvWarehouse;

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
}
