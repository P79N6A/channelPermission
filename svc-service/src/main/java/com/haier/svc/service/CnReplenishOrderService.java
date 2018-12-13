package com.haier.svc.service;

import java.util.Date;
import java.util.Map;

/**
 * 菜鸟补货实现（预约计划入库）
 **/
public interface CnReplenishOrderService {

    /**
     * 获取补货单列表
     *
     * @param params
     * @return
     */
    Map<String, Object> findOrderList(Map<String, Object> params);

    /**
     * 查询补货单详细信息（补货信息确认界面使用）
     *
     * @param id
     * @param replNo
     * @return
     */
    Map<String, Object> queryReplOrderDetail(Integer id, String replNo);

    /**
     * 补货单信息确认
     *
     * @param id
     * @param confirmReplQty
     * @param confirmDeadLine
     * @param contactName
     * @param contactPhone
     * @return
     */
    String confirmReplOrder(Integer id, Integer confirmReplQty, Date confirmDeadLine, String contactName, String contactPhone);
}
