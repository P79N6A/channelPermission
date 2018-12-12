package com.haier.purchase.data.services;

import com.haier.purchase.data.dao.purchase.PurchaseItemDao;
import com.haier.purchase.data.model.PurchaseItem;
import com.haier.purchase.data.service.PurchaseItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseItemServiceImpl implements PurchaseItemService{

	@Autowired
	PurchaseItemDao purchaseItemDao;

	@Override
	public PurchaseItem getPurchaseItemByPoItemNo(String poItemNo) {
		return purchaseItemDao.getPurchaseItemByPoItemNo(poItemNo);
	}
}
