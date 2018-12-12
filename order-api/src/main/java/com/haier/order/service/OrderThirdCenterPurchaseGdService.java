//package com.haier.orderthird.service;
//
//import java.util.List;
//
//import com.haier.common.ServiceResult;
//import com.haier.purchase.data.model.PurchaseGdQueue;
//
//public interface OrderThirdCenterPurchaseGdService {
//
//    /**
//     * CBS基地库存采购--根据网单查询可用网单
//     * @param orderSn
//     * @return
//     */
//    ServiceResult<PurchaseGdQueue> getByOrderSn(String orderSn);
//
//    /**
//     * CBS基地库存采购--新增基地库采购数据
//     * @return
//     */
//    ServiceResult<Boolean> insertPurchaseGdQueue(PurchaseGdQueue purchaseGDQueue);
//
//    /**
//     * CBS基地库存采购--更新基地库采购数据状态为已出库
//     * 3：已更新快递单号
//     * @param purchaseGDQueue
//     * @return
//     */
//    ServiceResult<Boolean> updatePurchaseGdQueue(PurchaseGdQueue purchaseGDQueue);
//
//    /**
//     * CBS基地库存采购--查询没有同步的基地库存订单
//     * @return
//     */
//    ServiceResult<List<PurchaseGdQueue>> queryUnSyncWaitOut();
//
//    /**
//     * CBS基地库存采购--更新同步状态
//     * @return
//     */
//    ServiceResult<Boolean> updateSyncWaitOut(PurchaseGdQueue purchaseGdQueue);
//
//}
