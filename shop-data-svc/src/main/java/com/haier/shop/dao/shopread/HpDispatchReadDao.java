package com.haier.shop.dao.shopread;

import com.haier.shop.model.HPQueues;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface HpDispatchReadDao {
    /**
     * 获取待发送HP数据
     * @return 待发送HP数据
     */
    List<Map<String, Object>> getHpQueueInfo(@Param("topX") Integer topX);

    /**
     * 获取网单信息
     */
    Map<String, Object> getOrderProductInfo(@Param("orderProductId") Integer orderProductId);

    /**
     * 获取订单信息
     */
    Map<String, Object> getOrderInfo(@Param("orderId") Integer orderId);

    /**
     * 获取全流程信息
     */
    Map<String, Object> getOrderWorkFlowInfo(@Param("orderProductId") Integer orderProductId);

    /**
     * 获取预约信息
     */
    Map<String, Object> getReservationShippingInfo(@Param("orderId") Integer orderId);

    /**
     * 获取发票信息
     */
    Map<String, Object> getMemberInvoiceInfo(@Param("orderId") Integer orderId);

    /**
     * 根据订单ID获取订单下的网单数
     */
    List<Map<String, Object>> getOrderCountList(Map<String, Object> params);

    /**
     * 获取区域表
     */
    List<Map<String, Object>> getRegions();

    /**
     * 获取物料编码数据
     */
    List<Map<String, Object>> getSkuMappings();

    /**
     * 获取待发送HP数据(新版)
     * @return 待发送HP数据
     */
    List<HPQueues> getHpQueueUnSendInfo(@Param("topX") Integer topX);

}
