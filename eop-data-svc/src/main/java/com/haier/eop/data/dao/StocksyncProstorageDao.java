package com.haier.eop.data.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.eop.data.model.StocksyncProstorage;

public interface StocksyncProstorageDao {
	 int deleteByPrimaryKey(Integer id);

	    int insert(StocksyncProstorage record);

	    int insertSelective(StocksyncProstorage record);

	    StocksyncProstorage selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(StocksyncProstorage record);

	    int updateByPrimaryKey(StocksyncProstorage record);
	    List<StocksyncProstorage> Listf(@Param("entity")StocksyncProstorage entity,@Param("start") int start, @Param("rows") int rows);
	    int getPagerCountS(@Param("entity")StocksyncProstorage entity);
}