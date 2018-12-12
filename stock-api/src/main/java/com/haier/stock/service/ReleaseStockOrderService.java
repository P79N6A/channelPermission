package com.haier.stock.service;

import com.haier.common.ServiceResult;

public interface ReleaseStockOrderService {
	
	/**
     * 释放取消关闭的网单的库存（商城、海朋、基地库）
     * @return
     */
    ServiceResult<Boolean> releaseCancelCloseStock();

}
