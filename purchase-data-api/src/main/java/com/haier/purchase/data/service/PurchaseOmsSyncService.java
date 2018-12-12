package com.haier.purchase.data.service;

import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.OmsOrderVO;

public interface PurchaseOmsSyncService {
    /**
     * 获得需要同步状态的订单列表
     * @return
     */
    public List<String> getOrderIds(OmsOrderVO.QueryCondition condition);

    public List<OmsOrderVO> getOmsOrder(OmsOrderVO.QueryCondition condition);

    /**
     * 更新订单数据
     * @param omsOrderList
     */
    public void updateOrderStatus(OmsOrderVO omsOrderVO);

    /**
     * 更新订单状态
     * @param omsOrderList
     */
    public void updateOrderFlowFlag(OmsOrderVO omsOrderVO);

    public void callUpdateProc(Map map);

    public void replaceOrderStatus(OmsOrderVO omsOrderVO);

    public void insertUpdateTable(OmsOrderVO omsOrderVO);

    public void syncDataFromUpdateTable();

    public void deleteUpdateTable();
}
