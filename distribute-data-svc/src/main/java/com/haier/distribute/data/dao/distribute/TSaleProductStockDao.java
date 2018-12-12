package com.haier.distribute.data.dao.distribute;


import com.haier.distribute.data.model.TSaleProductStock;

public interface TSaleProductStockDao extends BaseDao<TSaleProductStock> {
    int deleteByPrimaryKey(Integer id);

    int insert(TSaleProductStock record);

    int insertSelective(TSaleProductStock record);

    TSaleProductStock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TSaleProductStock record);

    int updateByPrimaryKey(TSaleProductStock record);
}