package com.haier.shop.dao.shopread;

import com.haier.shop.model.SuningGroups;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface SuNingGroupsReadDao {
    int deleteByPrimaryKey(Integer id);


    int insertSelective(SuningGroups record);


    int updateByPrimaryKeySelective(SuningGroups record);
    List<SuningGroups> Listf(@Param("entity") SuningGroups entity, @Param("start") int start, @Param("rows") int rows);
    int getPagerCountS(@Param("entity") SuningGroups entity);
}
