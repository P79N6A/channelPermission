package com.haier.svc.service.job;

import com.haier.common.ServiceResult;

public interface T2OrderTimingService {
	public void test();

	public void testAdd();

	public ServiceResult<Boolean> syncCRMOrderType();

	public void syncCrmT2Status();

	public void syncData();

	public void GetInventoryTranFromLes();

	public void cnPurchaseStorageToSap();

}
