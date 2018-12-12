package com.haier.stock.dao.stock;

import java.util.List;

import com.haier.stock.model.InvWarehouse;
import com.haier.stock.model.InvWarehouseInfo;

/**
 * 
 *                       
 * @Filename: InvWarehouseDao.java
 * @Version: 1.0
 * @Author: lizhen
 * @Email: zhen1.li@dhc.com.cn
 *
 */
public interface InvWarehouseDao {

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

    InvWarehouse getByWhCode( String whCode);
    
    String getWhCodeByCenterCode(String centerCode);

	 List<InvWarehouseInfo> findCenter();
}
