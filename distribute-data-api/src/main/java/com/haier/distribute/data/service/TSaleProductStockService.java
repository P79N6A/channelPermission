package com.haier.distribute.data.service;

import java.util.List;

import com.haier.distribute.data.model.TSaleProductStock;

public interface TSaleProductStockService {
    int deleteByPrimaryKey(Integer id);

    int insert(TSaleProductStock record);

    int insertSelective(TSaleProductStock record);

    TSaleProductStock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TSaleProductStock record);

    int updateByPrimaryKey(TSaleProductStock record);

    List<TSaleProductStock> getPageByCondition(TSaleProductStock entity, int start, int rows);

    long getPagerCount(TSaleProductStock entity);

    List<TSaleProductStock> checkCode(TSaleProductStock entity);
}