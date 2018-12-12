package com.haier.distribute.data.service;



import java.util.List;

import com.haier.distribute.data.model.TOrderOperatorLog;


public interface TOrderOperatorLogService {
    int deleteByPrimaryKey(Integer id);

    int insert(TOrderOperatorLog record);

    int insertSelective(TOrderOperatorLog record);

    TOrderOperatorLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TOrderOperatorLog record);

    int updateByPrimaryKey(TOrderOperatorLog record);

	List<TOrderOperatorLog> historyList(String orderSn);
}