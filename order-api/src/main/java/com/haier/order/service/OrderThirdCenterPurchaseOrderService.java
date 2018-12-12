//package com.haier.orderthird.service;
//
//import java.util.List;
//
//import com.haier.common.ServiceResult;
//import com.haier.purchase.data.model.PurchaseItem;
//import com.haier.purchase.data.model.PurchaseOrder;
//import com.haier.purchase.data.model.PurchaseOrderInfoItem;
//
//public interface OrderThirdCenterPurchaseOrderService {
//	/**
//	 * 根据85DN或者SI单号获得对应的订单信息
//	 *
//	 * @param dn_si_list
//	 * @return
//	 */
//	ServiceResult<List<PurchaseOrderInfoItem>> getOrderInfoByDnSi(List<String> dn_si_list);
//
//	ServiceResult<PurchaseItem> getPurchaseItemByPoItemNo(String poItemNo);
//
//	ServiceResult<PurchaseOrder> getPurchaseOrder(int poId);
//
//}
