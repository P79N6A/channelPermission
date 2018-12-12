package com.haier.distribute.data.service;

import com.haier.distribute.data.model.TOperatorLog;

public interface TOperatorLogService  {
    int deleteByPrimaryKey(Integer id);

    int insert(TOperatorLog record);

    int insertSelective(TOperatorLog record);

    TOperatorLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TOperatorLog record);

    int updateByPrimaryKey(TOperatorLog record);
}