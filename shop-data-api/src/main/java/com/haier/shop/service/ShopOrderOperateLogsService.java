package com.haier.shop.service;


import java.util.List;
import java.util.Map;

import com.haier.shop.model.OrderOperateLogs;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
public interface ShopOrderOperateLogsService {

    /**
     * 新增订单操作日志
     * @param orderOperateLogs
     * @return
     */
    Integer insert(OrderOperateLogs orderOperateLogs);
    
    void batchInsert(List<OrderOperateLogs> orderOperateLogsList);
    
    List<OrderOperateLogs> getProductIdVdiew(String productId);//查询网单操作日志

    /**
     * 根据网单id获取日志表最新一条日志
     * @param orderProductId
     * @return
     */
    OrderOperateLogs getLogsNew(Integer orderProductId);
    public void insertLog(Map<String,Object> map);

    /**
     * 根据id更新操作人
     * @param id
     * @param user
     * @return
     */
    Integer updateOperator(Integer id, String user);
}
