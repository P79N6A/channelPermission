package com.haier.shop.service;


import java.util.List;
import java.util.Map;

import com.haier.shop.model.HPQueues;

public interface HpDispatchService {
    /**
     * 获取待发送HP数据
     * @return 待发送HP数据
     */
    List<Map<String, Object>> getHpQueueInfo( Integer topX);

    /**
     * 获取网单信息
     */
    Map<String, Object> getOrderProductInfo(Integer orderProductId);

    /**
     * 获取订单信息
     */
    Map<String, Object> getOrderInfo( Integer orderId);

    /**
     * 获取全流程信息
     */
    Map<String, Object> getOrderWorkFlowInfo( Integer orderProductId);

    /**
     * 获取预约信息
     */
    Map<String, Object> getReservationShippingInfo( Integer orderId);

    /**
     * 获取发票信息
     */
    Map<String, Object> getMemberInvoiceInfo( Integer orderId);

    /**
     * 根据订单ID获取订单下的网单数
     */
    List<Map<String, Object>> getOrderCountList(Map<String, Object> params);

    /**
     * 更新HPQueue信息
     * @return 更新条数
     */
    int updateHPQueue(Map<String, Object> params);

    /**
     * 更新OrderProduct信息
     * @return 更新条数
     */
    int updateOrderProductStatus( Integer id,  Integer status);

    /**
     * 更新全流程信息
     * @return 更新条数
     */
    int updateSyncHpTime(Integer orderProductId,
                         Long sendHpTime);

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
    List<HPQueues> getHpQueueUnSendInfo(Integer topX);

    /**
     * 更新HPQueue信息(新版)
     * @return 更新条数
     */
    int update(HPQueues hPQueues);

    /**
     * 批量更新HPQueue信息(新版)
     * @return 更新条数
     */
    int updateHPQueueBatch(String ids, String lastMessage);
}
