package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrderRepairTcLogs;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderRepairTcLogsWriteDao {

	Integer insert(OrderRepairTcLogs orderRepairTcLogs); 
}
