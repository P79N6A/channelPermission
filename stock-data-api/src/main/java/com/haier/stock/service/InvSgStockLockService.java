package com.haier.stock.service;


import java.util.List;

import com.haier.stock.model.InvSgStockLock;
import com.haier.stock.model.InvSgStockLockEntity;
import com.haier.stock.model.InvStockLock;

public interface InvSgStockLockService {

	/**
	 * 根据网单号查询占货表信息
	 * 
	 * @param refNo
	 *            单据号(网单号)
	 * @return
	 */
	public InvSgStockLockEntity findSgStockLockByRefNo(String refNo);

	/**
	 * 更新占货信息
	 * 
	 * @param stockLockEntity
	 * @return
	 */
	public Integer updateReleaseSgStockLock(InvSgStockLockEntity stockLockEntity);

	/**
	 * 
	 * @return
	 */
	public Integer findSgStockLockBySkuRefNoStoreCode(String sku,  String refNo,  String storeCode);

	public Integer insertInvSgStockLock(InvSgStockLock invSgStockLock);
	

}