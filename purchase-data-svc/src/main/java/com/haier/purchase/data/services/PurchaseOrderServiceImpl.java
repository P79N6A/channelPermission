package com.haier.purchase.data.services;

import java.util.List;
import java.util.Map;

import com.haier.purchase.data.dao.purchase.PurchaseOrderDao;
import com.haier.purchase.data.model.PurchaseOrder;
import com.haier.purchase.data.model.PurchaseOrderInfoItem;
import com.haier.purchase.data.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService{

	@Autowired
	PurchaseOrderDao purchaseOrderDao;

	@Override
	public PurchaseOrder get(int poId) {
		return purchaseOrderDao.get(poId);
	}

	@Override
	public List<PurchaseOrderInfoItem> getOrderInfoByDnSi(Map params) {
		return purchaseOrderDao.getOrderInfoByDnSi(params);
	}
}
