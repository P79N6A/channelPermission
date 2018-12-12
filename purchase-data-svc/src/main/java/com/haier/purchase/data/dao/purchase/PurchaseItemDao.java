package com.haier.purchase.data.dao.purchase;

import com.haier.purchase.data.model.PurchaseItem;

public interface PurchaseItemDao {
	PurchaseItem getPurchaseItemByPoItemNo(String poItemNo);
}
