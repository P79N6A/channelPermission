package com.haier.stock.services;

import com.haier.stock.model.InvSgStockLock;
import com.haier.stock.model.InvSgStockLockEntity;
import com.haier.stock.model.InvSgStockLogEntity;
import com.haier.stock.service.InvSgStockLockService;
import com.haier.stock.service.InvSgStockLogService;
import com.haier.stock.service.InvSgStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SgStockLockModel {
	@Autowired
	private InvSgStockLockService invSgStockLockService;
	@Autowired
	private InvSgStockService invSgStockService;
	@Autowired
	private InvSgStockLogService invSgStockLogService;

	public InvSgStockLogService getInvSgStockLogDao() {
		return invSgStockLogService;
	}

	public void setInvSgStockLogDao(InvSgStockLogService invSgStockLogService) {
		this.invSgStockLogService = invSgStockLogService;
	}

	public InvSgStockService getInvSgStockDao() {
		return invSgStockService;
	}

	public void setInvSgStockDao(InvSgStockService invSgStockService) {
		this.invSgStockService = invSgStockService;
	}

	public InvSgStockLockService getInvSgStockLockDao() {
		return invSgStockLockService;
	}

	public void setInvSgStockLockDao(InvSgStockLockService invSgStockLockService) {
		this.invSgStockLockService = invSgStockLockService;
	}

	/**
	 * 根据网单号查询占货表信息
	 * 
	 * @param refNo
	 *            单据号(网单号)
	 * @return
	 */
	public InvSgStockLockEntity findSgStockLockByRefNo(String refNo) {
		InvSgStockLockEntity result = invSgStockLockService.findSgStockLockByRefNo(refNo);
		return result;
	}

	/**
	 * 更新占货信息
	 * 
	 * @param stockLockEntity
	 * @return
	 */
	public Integer updateReleaseSgStockLock(InvSgStockLockEntity stockLockEntity) {
		Integer result = invSgStockLockService.updateReleaseSgStockLock(stockLockEntity);
		return result;
	}

	/**
	 * 根据sku，refNo，storeCode查询数据
	 * @param sku
	 * @param refNo
	 * @param storeCode
	 * @return
	 */
	public Integer findSgStockLockBySkuRefNoStoreCode(String sku, String refNo, String storeCode) {
		Integer result = invSgStockLockService.findSgStockLockBySkuRefNoStoreCode(sku, refNo, storeCode);
		return result;
	}

	/**
	 * 插入数据
	 * @param invSgStockLock
	 * @return
	 */
	public Integer insertInvSgStockLock(InvSgStockLock invSgStockLock) {
		Integer result = invSgStockLockService.insertInvSgStockLock(invSgStockLock);
		return result;
	}

	/**
	 * 插入日志
	 * @param invSgStockLogEntity
	 * @return
	 */
	public Integer insertInvSgStockLog(InvSgStockLogEntity invSgStockLogEntity) {
		Integer result = invSgStockLogService.insertInvSgStockLog(invSgStockLogEntity);
		return result;
		
	}

}
