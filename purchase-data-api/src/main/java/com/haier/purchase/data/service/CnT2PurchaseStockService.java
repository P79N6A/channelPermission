package com.haier.purchase.data.service;

import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.CnT2PurchaseStock;

/**
 * Created by yangchangliang on 2018/1/19.
 */
public interface CnT2PurchaseStockService {

	public void addPurchaseStock(CnT2PurchaseStock cnT2PurchaseStock);

	public void updateCnT2PurchaseStock(CnT2PurchaseStock cnT2PurchaseStock);

	public List<CnT2PurchaseStock> queryCnT2PurchaseStock(
			Map<String, Object> map);

	/**
	 * 根据ID更新
	 * @param cnT2PurchaseStock
	 */
	public void updateCnT2PurchaseStockById(CnT2PurchaseStock cnT2PurchaseStock);

	int getCnT2PurchaseStockCNT(Map<String, Object> params);

	List<CnT2PurchaseStock> getPurchaseStockList(Map<String, Object> params);
}
