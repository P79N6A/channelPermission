package com.haier.shop.dao.shopwrite;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderRepairTcsWriteDao {

	int updateTcExtStatus(@Param("orderRepairTcsId") Integer orderRepairTcsId, @Param("caiNiaoTcExtStatus") Integer caiNiaoTcExtStatus);
}
