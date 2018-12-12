package com.haier.shop.service;


import java.util.List;

import com.haier.common.ServiceResult;
import com.haier.shop.model.OrdersNew;

public interface OrdersNewService {
    /**
     * 根据id列表，获取订单列表
     * @param ids
     * @return
     */
    List<OrdersNew> getByIds( List<Integer> ids);

    /**
     * 根据id获取订单对象
     * @param id
     * @return
     */
    OrdersNew get(Integer id);

    Integer completeClose(  Integer id,   Long finishTime);

    /**
     * 确认订单根据id列表，获取订单列表
     * @param ids
     * @return
     */
    List<OrdersNew> getByIdsForConfirm(  List<Integer> ids);

    /**
     * 更新订单标建确认状态
     * @param orders
     * @return
     */
    int updateSmConfirmStatus(OrdersNew orders);


    /**
     * 确认订单更新
     * @param orders
     * @return
     */
    int updateForConfirm(OrdersNew orders);

    Integer updateOrderStatus(  Integer id,  Integer orderStatus);

    List<OrdersNew> getBySmConfirmStatus();

    int updateSmConfirmStatusById( int targetStatus,   int id,
                                    int srcStatus,
                                    Long smManualTime,
                                    String smManualRemark);

    int updateSmConfirmStatusForAllProductsOrder(  int id);

    /**
     * 查询未自动确认的订单列表
     * @return
     */
    List<OrdersNew> getNotAutoConfirmOrders();

    /**
     * 插入待同步快捷通队列
     */
    int insertKjtProofs( Integer orderId,  Long addTime,
                          String pushData);

    int updateNotAutoConfirm( Integer id,   Integer whereValue,
                              Integer setValue);


    /**
     * 按网单号查询网单信息
     * @param orderSn
     * @return
     */
    OrdersNew getByOrderSn(String orderSn);


}
