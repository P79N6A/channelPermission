package com.haier.svc.service;

import com.haier.common.ServiceResult;
import com.haier.stock.model.InventoryBusinessTypes;

public interface HopStockService {
    ServiceResult<String> frozeStockQty(String materials_id, String storage_id, Integer quantity, String wp_order_id, String ed_channel_id, InventoryBusinessTypes frozenByZbcc);
}
