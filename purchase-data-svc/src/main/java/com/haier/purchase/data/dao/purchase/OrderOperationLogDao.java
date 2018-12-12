package com.haier.purchase.data.dao.purchase;

import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.OrderOperationLog;


/**
 * 
 *                       
 * @Filename: OrderOperationLogDao.java
 * @Version: 1.0
 * @Author: lizhen
 * @Email: zhen1.li@dhc.com.cn
 *
 */
public interface OrderOperationLogDao {
    /**
     * 
     * @title getOrderOperationLogInfo
     * @description 通过字段order_id和sub_order_id查询数据库表order_operation_log_t中数据
     */
    public List<OrderOperationLog> getOrderOperationLogInfo(Map<String, Object> params);

    /**
     * 
     * @title createOrderOperationLog
     * @description 往数据库表order_operation_log_t中添加单条数据
     */
    public Object createOrderOperationLog(Map<String, Object> params);

    /**
     * 
     * @Title insertOrderOperationLog
     * @Description 往数据库表order_operation_log_t中多条从插入数据
     * @param 需要多条插入的List数据
     * @return 
     */
    public Object insertOrderOperationLog(List<OrderOperationLog> insertList);
}
