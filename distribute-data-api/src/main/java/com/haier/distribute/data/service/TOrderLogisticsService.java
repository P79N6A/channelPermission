package com.haier.distribute.data.service;


import com.haier.distribute.data.model.TOrderLogistics;

import java.util.List;

public interface TOrderLogisticsService {
    int deleteByPrimaryKey(Integer id);

    int insert(TOrderLogistics record);

    int insertSelective(TOrderLogistics record);

    TOrderLogistics selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TOrderLogistics record);

    int updateByPrimaryKey(TOrderLogistics record);

	List<TOrderLogistics> checkExpressNo(  String expressNo,   String orderSn);

    int editExpressNo( String expressNo,  String orderSn);
}