package com.haier.purchase.data.service;

import java.util.List;
import java.util.Map;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.Cn3wReplenishOrders;
import com.haier.purchase.data.model.CnReplenishEntryOrder;
import com.haier.purchase.data.model.CnReplenishEntryOrderItem;

public interface CnReplenishEntryOrderService {

	/**
	 * 入3W库推送SAP，用于3W调拨
	 */
	public void orderIn3WPushToSAP();
	
	List<CnReplenishEntryOrder> getToPushSAPOrders(PagerInfo pi);

	public String genItemNum(int i);

	public ServiceResult<String> pushToSap(CnReplenishEntryOrder order, CnReplenishEntryOrderItem item,
			Cn3wReplenishOrders cn3wReplenishOrders);

	List<CnReplenishEntryOrder> getEditStatusSAPOrders(List<Map<String, Object>> list);
}
