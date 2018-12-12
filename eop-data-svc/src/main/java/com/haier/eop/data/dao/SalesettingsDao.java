package com.haier.eop.data.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.eop.data.model.Salesettings;

public interface SalesettingsDao {
    int deleteByPrimaryKey(Integer id);


    int insertSelective(Salesettings record);


    int updateByPrimaryKeySelective(Salesettings record);
    List<Salesettings> Listf(@Param("entity")Salesettings entity,@Param("start") int start, @Param("rows") int rows);
    int getPagerCountS(@Param("entity")Salesettings entity);
}