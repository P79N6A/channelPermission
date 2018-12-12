package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.CorderStatusToLes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CorderStatusToLesWriteDao {

    int updateByPrimaryKey(CorderStatusToLes record);

    int insert(CorderStatusToLes corderStatusToLes);
}