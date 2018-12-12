package com.haier.stock.service;

import com.haier.common.ServiceResult;

public interface StockDelayProcessingService {
	
	 ServiceResult<Boolean> processForDelay();

}
