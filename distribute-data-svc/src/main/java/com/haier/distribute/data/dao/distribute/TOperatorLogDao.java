package com.haier.distribute.data.dao.distribute;

import com.haier.distribute.data.model.TOperatorLog;

public interface TOperatorLogDao extends BaseDao<TOperatorLog> {
    int deleteByPrimaryKey(Integer id);

    int insert(TOperatorLog record);

    int insertSelective(TOperatorLog record);

    TOperatorLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TOperatorLog record);

    int updateByPrimaryKey(TOperatorLog record);
}