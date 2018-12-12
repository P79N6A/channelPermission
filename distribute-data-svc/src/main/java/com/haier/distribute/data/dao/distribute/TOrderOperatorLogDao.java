package com.haier.distribute.data.dao.distribute;



import java.util.List;

import com.haier.distribute.data.model.TOrderOperatorLog;


public interface TOrderOperatorLogDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TOrderOperatorLog record);

    int insertSelective(TOrderOperatorLog record);

    TOrderOperatorLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TOrderOperatorLog record);

    int updateByPrimaryKey(TOrderOperatorLog record);

	List<TOrderOperatorLog> historyList(String orderSn);
}