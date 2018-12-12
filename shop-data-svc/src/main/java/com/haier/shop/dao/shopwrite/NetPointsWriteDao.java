package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.NetPoints;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NetPointsWriteDao {
    int deleteByPrimaryKey(Integer id);

    int insert(NetPoints record);

    int insertSelective(NetPoints record);

    int updateByPrimaryKeySelective(NetPoints record);

    int updateByPrimaryKey(NetPoints record);

}