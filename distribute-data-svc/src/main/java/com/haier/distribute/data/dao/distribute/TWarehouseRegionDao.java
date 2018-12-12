package com.haier.distribute.data.dao.distribute;

import com.haier.distribute.data.model.TWarehouseRegion;
import org.apache.ibatis.annotations.Param;

public interface TWarehouseRegionDao extends BaseDao<TWarehouseRegion> {
    int deleteByPrimaryKey(Integer id);

    int insert(TWarehouseRegion record);

    int insertSelective(TWarehouseRegion record);

    TWarehouseRegion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TWarehouseRegion record);

    int updateByPrimaryKey(TWarehouseRegion record);

	int checkRegion(@Param("channelId") int channelId, @Param("regionId") int regionId,@Param("id")int id);
}