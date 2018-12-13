package com.haier.stock.service;

import com.haier.common.ServiceResult;

public interface ItemBaseFromMdmService {
	  /**
     * 定时更新物料基本信息接口(Job触发)
     * @return
     */
    ServiceResult<Boolean> updateItemBaseFromMdm();
	
}
