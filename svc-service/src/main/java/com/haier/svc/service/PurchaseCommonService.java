package com.haier.svc.service;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.PrivilegeItem;

public interface PurchaseCommonService {
	/**
	 * 登录者权限情报取得
	 * @param String> userId    --登录UserID
	 * @return
	 */
	ServiceResult<PrivilegeItem> getPrivilege(String userId);
	
	/**订单流水号取得
	 */
	public ServiceResult<Integer> getNextVal();

	public String getCrmSubOrderId(String order_id, int num);
}
