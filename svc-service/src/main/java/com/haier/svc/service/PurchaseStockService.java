package com.haier.svc.service;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.CnT2PurchaseStock;

import java.util.List;
import java.util.Map;

public interface PurchaseStockService {
    ServiceResult<List<CnT2PurchaseStock>> getPurchaseStockList(Map<String, Object> params);

    List<CnT2PurchaseStock> getPurchaseStocDatakList(Map<String, Object> params);
}
