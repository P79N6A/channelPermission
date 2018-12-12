package com.haier.distribute.data.service;
/**
 * Created by Administrator on 2017/11/7 0007.
 */


import java.util.List;

import com.haier.distribute.data.model.PopOrders;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 胡万来
 * \* Date: 2017/11/7 0007
 * \* Time: 9:13
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public interface PopOrderService {
    String getVehicleOrderNo(String begin);

    int updateSelectiveByOrderNo(PopOrders entity);

//    int updateStatus(@Param("orderStatus") short orderStatus,@Param("orderId") String orderId);

    int updateStatus(String orderStatus, String Id);

    int orderOpertionToSure(String sellPeople, String orderStatus, String oId, String Id);

    int addConfirmTime(long confirmTime, String Id);

    int confirmTime(long confirmTime, String Id);

    int updateCancelStatus(String cancelStatus, String orderId);

    int editRemark(String codConfirmRemark, String orderSn);

    int editOid(String oid, String orderSn);

    int editOrigin(String consignee, String mobile,
                   String originRegionName,
                   String originAddress, String orderSn);

    List<PopOrders> checkOid(String oid, String orderSn);

    List<PopOrders> exportlist(PopOrders entity);

    /**
     * 根据id获取订单对象
     *
     * @param id
     * @return
     */
    PopOrders get(Integer id);


    List<PopOrders> getPageByCondition(PopOrders condition, int start, int rows);

    long getPagerCount(PopOrders entity);

    List<PopOrders> listByCondition(PopOrders entity);

    PopOrders getOneByCondition(PopOrders entity);

    int finishToCancel(String orderSn);
}
