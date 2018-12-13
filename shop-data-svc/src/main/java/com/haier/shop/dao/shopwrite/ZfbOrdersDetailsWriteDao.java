package com.haier.shop.dao.shopwrite;

import org.apache.ibatis.annotations.Mapper;

import com.haier.shop.model.ZfbOrdersDetails;
@Mapper
public interface ZfbOrdersDetailsWriteDao {
    int deleteByPrimaryKey(Long id);

    int insert(ZfbOrdersDetails record);

    int insertSelective(ZfbOrdersDetails record);


    int updateByPrimaryKeySelective(ZfbOrdersDetails record);

    int updateByPrimaryKey(ZfbOrdersDetails record);
}