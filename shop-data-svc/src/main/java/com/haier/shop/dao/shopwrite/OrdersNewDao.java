package com.haier.shop.dao.shopwrite;

import com.haier.common.ServiceResult;
import org.apache.ibatis.annotations.Param;

import com.haier.shop.model.OrdersNew;

import java.util.List;

//TODO 与 Orders 模型类似
public interface OrdersNewDao {
    /**
     * 根据id列表，获取订单列表
     * @param ids
     * @return
     */
    List<OrdersNew> getByIds(@Param("ids") List<Integer> ids);

    /**
     * 根据id获取订单对象
     * @param id
     * @return
     */
    OrdersNew get(Integer id);
    
    Integer completeClose(@Param("id") Integer id, @Param("finishTime") Long finishTime);
    
    /**
     * 确认订单根据id列表，获取订单列表
     * @param ids
     * @return
     */
    List<OrdersNew> getByIdsForConfirm(@Param("ids") List<Integer> ids);
    
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
    
    Integer updateOrderStatus(@Param("id") Integer id, @Param("orderStatus") Integer orderStatus);

    List<OrdersNew> getBySmConfirmStatus();

    int updateSmConfirmStatusById(@Param("targetStatus") int targetStatus, @Param("id") int id,
                                  @Param("srcStatus") int srcStatus,
                                  @Param("smManualTime") Long smManualTime,
                                  @Param("smManualRemark") String smManualRemark);

    int updateSmConfirmStatusForAllProductsOrder(@Param("id") int id);

    /**
     * 查询未自动确认的订单列表
     * @return
     */
    List<OrdersNew> getNotAutoConfirmOrders();

    /**
     * 插入待同步快捷通队列
     */
    int insertKjtProofs(@Param("orderId") Integer orderId, @Param("addTime") Long addTime,
                        @Param("pushData") String pushData);

    int updateNotAutoConfirm(@Param("id") Integer id, @Param("whereValue") Integer whereValue,
                             @Param("setValue") Integer setValue);

    
    /**
     * 按网单号查询网单信息
     * @param orderSn
     * @return
     */
    OrdersNew getByOrderSn(String orderSn);

    /**
     * 根据id更新lbx单号
     * @param orders
     * @return
     */
    Integer updateLbxSn(OrdersNew orders);

    int updateForTailPayTime(OrdersNew orders);

    /**
     * 根据id将状态为部分缺货更新为未确认
     * @param orderId
     * @return
     */
    Integer updateStatus(Integer orderId);

}
