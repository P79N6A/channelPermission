package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrderhpRejectionLogs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderhpRejectionLogsWriteDao {

    int insert(OrderhpRejectionLogs record);

    int insertSelective(OrderhpRejectionLogs record);
    int updateHpRejectionLogs(OrderhpRejectionLogs record);
    
    int updataEmptyOut(@Param("id")String id,@Param("emptyOut")String emptyOut);
}