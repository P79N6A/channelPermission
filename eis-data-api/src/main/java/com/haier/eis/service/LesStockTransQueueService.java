package com.haier.eis.service;

import com.haier.eis.model.LesStockTransQueue;

public interface LesStockTransQueueService {
	
	LesStockTransQueue getByLesBillNo(String lesBillNo);
	Integer insert(LesStockTransQueue stockTransQueues);

}
