package com.haier.shop.dao.shopwrite;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.haier.shop.model.Adjust;

@Mapper
public interface AdjustWriteDao extends BaseWriteDao<Adjust> {

    int updateSelectiveByAdjustNo(@Param("entity") Adjust entity);
}
