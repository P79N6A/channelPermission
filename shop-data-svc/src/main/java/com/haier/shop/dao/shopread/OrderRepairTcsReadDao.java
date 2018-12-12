package com.haier.shop.dao.shopread;

import com.haier.shop.model.OrderRepairTcs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderRepairTcsReadDao {

	OrderRepairTcs getById(Integer orderRepairTcsId);
}
