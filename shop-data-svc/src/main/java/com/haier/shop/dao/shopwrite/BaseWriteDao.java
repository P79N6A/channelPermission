package com.haier.shop.dao.shopwrite;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BaseWriteDao<T> {

    int insertSelective(T entity);

    int updateSelectiveById(@Param("entity") T entity);
}
