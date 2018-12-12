package com.haier.distribute.data.dao.distribute;

import com.haier.distribute.data.model.TAdjustData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TAdjustDataDao extends BaseDao<TAdjustData>{
    int deleteByPrimaryKey(Integer id);

    int insert(TAdjustData record);

    int insertSelective(TAdjustData record);

    TAdjustData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TAdjustData record);

    int updateByPrimaryKey(TAdjustData record);
    
    String getVehicleAdjustNo(@Param("begin") String begin);

    int updateSelectiveByAdjustNo(@Param("entity") TAdjustData entity);

    List<TAdjustData> exportAdjustList(@Param("entity") TAdjustData entity);

}