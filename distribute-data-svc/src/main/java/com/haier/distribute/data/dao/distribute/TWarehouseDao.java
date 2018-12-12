package com.haier.distribute.data.dao.distribute;

import com.haier.distribute.data.model.TWarehouse;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TWarehouseDao extends BaseDao<TWarehouse> {
    int deleteByPrimaryKey(Integer id);

    int insert(TWarehouse record);

    int insertSelective(TWarehouse record);

    TWarehouse selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TWarehouse record);

    int updateByPrimaryKey(TWarehouse record);

	List<TWarehouse> getAll();

	List<TWarehouse> getWareHouseServiceStart();

	List<Map<String, Object>> autoLoadPid(@Param("channelId") Integer channelId, @Param("id") Integer id);

}