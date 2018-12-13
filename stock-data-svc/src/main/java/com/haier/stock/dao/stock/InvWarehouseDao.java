package com.haier.stock.dao.stock;

import java.util.List;
import java.util.Map;

import com.haier.stock.model.InvWarehouse;
import com.haier.stock.model.InvWarehouseInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 *                       
 * @Filename: InvWarehouseDao.java
 * @Version: 1.0
 * @Author: lizhen
 * @Email: zhen1.li@dhc.com.cn
 *
 */
@Mapper
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

    List<InvWarehouse> getInvWarehouseInfo(Map<String, Object> param);

    Integer getInvWarehouseCount(Map<String, Object> param);

    int checkMainKey(Map<String, Object> param);

    void createInvWarehouse(Map<String, Object> param);

    void updateInvWarehouse(Map<String, Object> param);

    void deleteInvWareHouse(Map<String, Object> param);

    void openStatusInvWarehouse(Map<String, Object> param);

    void closeStatusInvWarehouse(Map<String, Object> param);

    List<InvWarehouse> getInvWarehouseExport(Map<String, Object> param);
}
