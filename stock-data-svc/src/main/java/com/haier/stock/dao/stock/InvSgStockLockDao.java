package com.haier.stock.dao.stock;

import com.haier.stock.model.InvSgStockLock;
import com.haier.stock.model.InvSgStockLockEntity;
import org.apache.ibatis.annotations.Param;


public interface InvSgStockLockDao {

	/**
	 * 根据网单号查询占货表信息
	 * 
	 * @param refNo
	 *            单据号(网单号)
	 * @return
	 */
	public InvSgStockLockEntity findSgStockLockByRefNo(@Param("refNo") String refNo);

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
	public Integer findSgStockLockBySkuRefNoStoreCode(@Param("sku") String sku, @Param("refNo") String refNo, @Param("storeCode") String storeCode);

	public Integer insertInvSgStockLock(InvSgStockLock invSgStockLock);

}