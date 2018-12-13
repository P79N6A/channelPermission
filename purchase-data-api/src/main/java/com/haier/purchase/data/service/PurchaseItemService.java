package com.haier.purchase.data.service;

import com.haier.purchase.data.model.ProduceDailyPurchaseItem;
import com.haier.purchase.data.model.PurchaseItem;
import java.util.List;
import java.util.Map;

public interface PurchaseItemService {
	PurchaseItem getPurchaseItemByPoItemNo(String poItemNo);

	List<ProduceDailyPurchaseItem> findPurchaseItems(Map<String, Object> params);

	int getRowCnt();

	/**
	 * 根据采购网单号查询数据是否存在
	 * @param poItemNo
	 * @return
	 */
	Integer getByPoItemNo(String poItemNo);
}
