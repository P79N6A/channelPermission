package com.haier.shop.dao.shopwrite;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.haier.shop.model.Orders;



@Mapper
public interface OrdersDao {
    /**
     * 根据id获取订单对象
     * @param id
     * @return
     */
    Orders get(Integer id);

    /**
     * 根据id列表，获取订单列表
     * @param ids
     * @return
     */
    List<Orders> getByIds(@Param("ids") List<Integer> ids);

    /**
         * 按网单号查询网单信息
         * @param orderSn
         * @return
         */
    Orders getByOrderSn(String orderSn);


    int insert(Orders orders);

    int updateForInvoice(Orders orders);

    int updateForPayment(Orders orders);

    int updateForTailPayTime(Orders orders);

    int updateOrderStatus(Orders orders);

    /**
     * 根据订单号查询step信息,用于创建淘宝订单后规则
     * @param orderSn
     * @return
     */
    Orders getStepMsgByOrerSn(@Param("orderSn") String orderSn);

    int updateForStep(@Param("orderId") Integer orderId,
                      @Param("stepTradeStatus") String stepTradeStatus,
                      @Param("stepPaidFee") BigDecimal stepPaidFee,
                      @Param("lastPayTime") Integer lastPayTime,
                      @Param("productAmount") BigDecimal productAmount,
                      @Param("orderAmount") BigDecimal orderAmount,
                      @Param("paidAmount") BigDecimal paidAmount);

    /**
     * 查询未自动确认的订单列表
     * @return
     */
    List<Orders> getNotAutoConfirmOrders();

    /**
     * 查询未自动确认的部分缺货订单列表
     * @return
     */
    List<Orders> getNotAutoConfirmOrdersForException(@Param("id") Integer id);

    /**
     * 确认订单更新
     * @param orders
     * @return
     */
    int updateForConfirm(Orders orders);

    /**
     * 更新订单标建确认状态
     * @param orders
     * @return
     */
    int updateSmConfirmStatus(Orders orders);

    Integer updateOrderStatus(@Param("id") Integer id, @Param("orderStatus") Integer orderStatus);

    Integer completeClose(@Param("id") Integer id, @Param("finishTime") Long finishTime);

    /**
     * 确认订单根据id列表，获取订单列表
     * @param ids
     * @return
     */
    List<Orders> getByIdsForConfirm(@Param("ids") List<Integer> ids);

    /**
     * 插入待同步快捷通队列
     */
    int insertKjtProofs(@Param("orderId") Integer orderId, @Param("addTime") Long addTime,
                        @Param("pushData") String pushData);

    List<Orders> getBySmConfirmStatus();

    List<Orders> getOrdersByStatus();

    int updateSmConfirmStatusById(@Param("targetStatus") int targetStatus, @Param("id") int id,
                                  @Param("srcStatus") int srcStatus,
                                  @Param("smManualTime") Long smManualTime,
                                  @Param("smManualRemark") String smManualRemark);

    int updateSmConfirmStatusForAllProductsOrder(@Param("id") int id);

    /**
     * 查询货到付款未审核的订单列表
     * @return
     */
    List<Orders> getCodNotConfirmOrders();

    int updateCodConfirmState(@Param("id") Integer id);

    int updateNotAutoConfirm(@Param("id") Integer id, @Param("whereValue") Integer whereValue,
                             @Param("setValue") Integer setValue);

    List<Orders> getOrdersBySourceId(@Param("params") Map<String, Object> paramMap);

    /**
     * 按来源订单号（外部订单号）查询订单信息
     * @param orderSn
     * @return
     */
    List<Orders> getBySourceOrderSn(String sourceOrderSn);

    int updateIsNotConfirm(@Param("orderSns") Set<String> orderSns);

}
