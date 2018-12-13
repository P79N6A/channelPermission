package com.haier.system.dao;

import java.util.List;
import java.util.Map;

import com.haier.system.model.SyncOrderConfigs;
import org.apache.ibatis.annotations.Param;



public interface SyncOrderConfigsDao {
	List<SyncOrderConfigs> selectSyncOrderConfigs();

    String selectShopNameById(Integer configId);

	int getTotal();

	List<SyncOrderConfigs> findSyncOrderConfigByPage(Map<String, Object> params);

	Integer addSyncOrderConfigs(SyncOrderConfigs syncOrderConfigs);

    SyncOrderConfigs findOrderConfigsById(@Param("id") String id);

    Integer updateSyncOrderConfigsById(SyncOrderConfigs syncOrderConfigs);

    Integer deleteSyncOrderConfigsById(@Param("id") String id);

    SyncOrderConfigs findOrderConfigsByShopName(@Param("shopName") String shopName);
}
