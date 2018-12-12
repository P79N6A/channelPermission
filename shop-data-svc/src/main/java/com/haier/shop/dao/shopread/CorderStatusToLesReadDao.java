package com.haier.shop.dao.shopread;

import com.haier.shop.model.CorderStatusToLes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CorderStatusToLesReadDao {

    List<CorderStatusToLes> findNeedSendToLes(@Param("limit") Integer limit);
}