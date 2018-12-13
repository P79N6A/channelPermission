package com.haier.purchase.data.services;

import com.haier.purchase.data.dao.purchase.PurchaseItemDao;
import com.haier.purchase.data.model.ProduceDailyPurchaseItem;
import com.haier.purchase.data.model.PurchaseItem;
import com.haier.purchase.data.service.PurchaseItemService;
import java.util.List;
import java.util.Map;
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

	@Override
	public List<ProduceDailyPurchaseItem> findPurchaseItems(Map<String, Object> params) {
		return purchaseItemDao.findPurchaseItems(params);
	}

	@Override
	public int getRowCnt() {
		return purchaseItemDao.getRowCnt();
	}

	@Override
	public Integer getByPoItemNo(String poItemNo) {
		return purchaseItemDao.getByPoItemNo(poItemNo);
	}
}
