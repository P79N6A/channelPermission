package com.haier.shop.service;

import com.haier.shop.model.OrderRepairTcs;

public interface OrderRepairTcsService {
	
	OrderRepairTcs getById(Integer orderRepairTcsId);
	
	int updateTcExtStatus(  Integer orderRepairTcsId,  Integer caiNiaoTcExtStatus);

}
