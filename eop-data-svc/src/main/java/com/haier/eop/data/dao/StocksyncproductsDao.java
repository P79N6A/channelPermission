package com.haier.eop.data.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.eop.data.model.Stocksyncproducts;

public interface StocksyncproductsDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Stocksyncproducts record);

    int insertSelective(Stocksyncproducts record);

    Stocksyncproducts selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Stocksyncproducts record);

    int updateByPrimaryKey(Stocksyncproducts record);
    List<Stocksyncproducts> Listf(@Param("entity")Stocksyncproducts entity,@Param("start") int start, @Param("rows") int rows);
    int getPagerCountS(@Param("entity")Stocksyncproducts entity);
    Stocksyncproducts getId(@Param("sku")String sku,@Param("source")String source);
    Stocksyncproducts getBySourceAndSku(@Param("source") String source,@Param("sku") String sku);
}