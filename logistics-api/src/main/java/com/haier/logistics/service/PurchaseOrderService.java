package com.haier.logistics.service;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.PurchaseItem;
import com.haier.purchase.data.model.PurchaseOrder;
import com.haier.purchase.data.model.PurchaseOrderInfoItem;

import java.util.List;

public interface PurchaseOrderService {
	/**
	 * 根据85DN或者SI单号获得对应的订单信息
	 * 
	 * @param dn_si_list
	 * @return
	 */

	
	ServiceResult<PurchaseItem> getPurchaseItemByPoItemNo(String poItemNo);
	ServiceResult<PurchaseOrder> getPurchaseOrder(int poId);




}
