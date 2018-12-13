package com.haier.stock.service;

import com.haier.common.ServiceResult;

public interface LesStockSyncService {
	
	 public ServiceResult<Boolean> getInventoryTransFromLes();

	 /**
	     * 向LES取消调拨单
	     * @param lineNum 调拨单号
	     * @param lesNum LES提单号
	     * @return
	     */
	    ServiceResult<Boolean> cancelTransferLine2Les(String lineNum, String lesNum);
	    
	    public ServiceResult<Boolean> syncTransferLinesToLes();
	    
	    public ServiceResult<Boolean> transferLineToLesForFeeInput();

	/**
	 * JOB:从LES同步套机数据
	 * @return
	 */
	ServiceResult<Boolean> doMachineSetsSyncFromLes();
}
