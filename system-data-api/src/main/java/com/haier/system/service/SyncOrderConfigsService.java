package com.haier.system.service;

import java.util.List;
import java.util.Map;

import com.haier.common.ServiceResult;
import com.haier.system.model.SyncOrderConfigs;


public interface SyncOrderConfigsService {
	List<SyncOrderConfigs> selectSyncOrderConfigs();

    String selectShopNameById(Integer configId);

	Map<String,Object> findSyncOrderConfigByPage(Map<String, Object> params);

	Integer addSyncOrderConfigs(SyncOrderConfigs syncOrderConfigs);

	ServiceResult<SyncOrderConfigs> findOrderConfigsById(String id);

    Integer updateSyncOrderConfigsById(SyncOrderConfigs syncOrderConfigs);

	Integer deleteSyncOrderConfigsById(String id);


    SyncOrderConfigs findOrderConfigsByShopName(String shopName);
}
