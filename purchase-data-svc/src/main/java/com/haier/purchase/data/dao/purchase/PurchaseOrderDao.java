package com.haier.purchase.data.dao.purchase;

import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.PurchaseOrder;
import com.haier.purchase.data.model.PurchaseOrderInfoItem;

public interface PurchaseOrderDao {
	 PurchaseOrder get(int poId);
	 public List<PurchaseOrderInfoItem> getOrderInfoByDnSi(Map params);
}
