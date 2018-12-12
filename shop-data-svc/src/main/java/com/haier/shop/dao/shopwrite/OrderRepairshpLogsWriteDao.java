package com.haier.shop.dao.shopwrite;


import com.haier.shop.model.OrderRepairshpLogs;
import org.apache.ibatis.annotations.Mapper;

/**
 * 接收HP返回原始数据
 * 吴坤洋 2017-12-2
 * @author wukunyang
 *
 */
@Mapper
public interface OrderRepairshpLogsWriteDao {

    int insert(OrderRepairshpLogs record);
}