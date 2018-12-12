package com.haier.distribute.data.service;


import java.util.List;

import com.haier.distribute.data.model.TAdjustData;

public interface TAdjustDataService {
    int deleteByPrimaryKey(Integer id);

    int insert(TAdjustData record);

    int insertSelective(TAdjustData record);

    TAdjustData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TAdjustData record);

    int updateByPrimaryKey(TAdjustData record);

    String getVehicleAdjustNo(String begin);

    int updateSelectiveByAdjustNo(TAdjustData entity);

    List<TAdjustData> exportAdjustList(TAdjustData entity);

    List<TAdjustData> getPageByCondition(TAdjustData entity, int start, int rows);

    long getPagerCount(TAdjustData entity);


}