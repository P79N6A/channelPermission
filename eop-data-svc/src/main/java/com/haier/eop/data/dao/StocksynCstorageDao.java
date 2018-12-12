package com.haier.eop.data.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.eop.data.model.StocksynCstorage;

public interface StocksynCstorageDao {
	int deleteByPrimaryKey(Integer id);

    int insert(StocksynCstorage record);

    int insertSelective(StocksynCstorage record);

    StocksynCstorage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StocksynCstorage record);

    int updateByPrimaryKey(StocksynCstorage record);
    List<StocksynCstorage> Listf(@Param("entity")StocksynCstorage entity,@Param("start") int start, @Param("rows") int rows);
    int getPagerCountS(@Param("entity")StocksynCstorage entity);
	StocksynCstorage getId(@Param("entity") StocksynCstorage entity);
	List<StocksynCstorage> getsCode(@Param("source") String source);
}