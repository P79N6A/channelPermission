package com.haier.purchase.data.service;

import java.util.List;

import com.haier.purchase.data.model.CnReplenishEntryOrderItem;

public interface CnReplenishEntryOrderItemService {

	List<CnReplenishEntryOrderItem> getByReplEntryOrderId(Long id);

	void updateStatusAfterInPushToSAP(CnReplenishEntryOrderItem item);

	void updateStatusAfterOutPushToSAP(CnReplenishEntryOrderItem item);

}
