package com.haier.purchase.data.service;

import com.haier.purchase.data.model.ReplenishOrder;
import com.haier.purchase.data.model.ReplenishOrderConfirmDisplayItem;

import java.util.Date;
import java.util.Map;

/**
 * 菜鸟预约计划入库
 **/
public interface PurchaseCnReplOrderService {

    /**
     * 获取补货单列表
     *
     * @param paramMap
     * @return
     */
    Map<String, Object> getReplOrdersByPage(Map<String, Object> paramMap);

    /**
     * 通过id获取需要确认的补货单信息
     *
     * @param id
     * @return
     */
    ReplenishOrderConfirmDisplayItem getById4Confirm(Integer id);

    /**
     * 根据id获取补货单信息
     *
     * @param id
     * @return
     */
    ReplenishOrder getById(Integer id);

    /**
     * 保存补货单确认信息
     *
     * @param order
     * @return
     */
    int confirmOrder(ReplenishOrder order);
}
