package com.haier.purchase.data.service;

import com.haier.purchase.data.model.PurchaseItem;

public interface PurchaseItemService {
	PurchaseItem getPurchaseItemByPoItemNo(String poItemNo);
}
