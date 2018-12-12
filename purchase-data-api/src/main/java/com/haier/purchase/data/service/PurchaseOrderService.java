package com.haier.purchase.data.service;

import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.PurchaseOrder;
import com.haier.purchase.data.model.PurchaseOrderInfoItem;


public interface PurchaseOrderService {
	 PurchaseOrder get(int poId);
	 public List<PurchaseOrderInfoItem> getOrderInfoByDnSi(Map params);
}
