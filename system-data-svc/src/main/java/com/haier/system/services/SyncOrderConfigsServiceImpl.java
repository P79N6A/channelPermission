package com.haier.system.services;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haier.common.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.haier.system.dao.SyncOrderConfigsDao;
import com.haier.system.model.SyncOrderConfigs;
import com.haier.system.service.SyncOrderConfigsService;

@Service
public class SyncOrderConfigsServiceImpl implements SyncOrderConfigsService{

	private final static Logger logger = LoggerFactory.getLogger(SyncOrderConfigsServiceImpl.class);

	@Autowired
	private SyncOrderConfigsDao syncOrderConfigsDao;
	@Override
	public List<SyncOrderConfigs> selectSyncOrderConfigs() {
		// TODO Auto-generated method stub
		return syncOrderConfigsDao.selectSyncOrderConfigs();
	}

	@Override
	public String selectShopNameById(Integer configId) {
		return syncOrderConfigsDao.selectShopNameById(configId);
	}

	public Map<String, Object> findSyncOrderConfigByPage(Map<String, Object> params) {
		//获得分页数据列表
		List<SyncOrderConfigs> result = syncOrderConfigsDao.findSyncOrderConfigByPage(params);
		//获得总数据数
		int resultcount = syncOrderConfigsDao.getTotal();
		Map<String,Object> retMap = new HashMap<String,Object>();
		retMap.put("total", resultcount);
		retMap.put("rows", result);
		return retMap;
	}

	@Override
	public Integer addSyncOrderConfigs(SyncOrderConfigs syncOrderConfigs) {
		return syncOrderConfigsDao.addSyncOrderConfigs(syncOrderConfigs);
	}

	@Override
	public ServiceResult<SyncOrderConfigs> findOrderConfigsById(String id) {
		ServiceResult<SyncOrderConfigs> serviceResult = new ServiceResult<>();
		try {
			if(id==null || id==""){
				serviceResult.setSuccess(false);
				return serviceResult;
			}
			SyncOrderConfigs syncOrderConfigs = syncOrderConfigsDao.findOrderConfigsById(id);
			if(syncOrderConfigs!=null){
				serviceResult.setSuccess(true);
				serviceResult.setResult(syncOrderConfigs);
			}else{
				serviceResult.setSuccess(false);
			}
			return serviceResult;
		} catch (Exception e) {
			logger.error("[SyncOrderConfigsServiceImpl][findOrderConfigsById]通过id查询淘宝订单同步配置信息异常，e:"+e.getMessage());
			serviceResult.setSuccess(false);
			serviceResult.setMessage("[SyncOrderConfigsServiceImpl][findOrderConfigsById]通过id查询淘宝订单同步配置信息异常，e:"+e.getMessage());
			return serviceResult;
		}
	}

	@Override
	public Integer updateSyncOrderConfigsById(SyncOrderConfigs syncOrderConfigs) {
		return syncOrderConfigsDao.updateSyncOrderConfigsById(syncOrderConfigs);
	}

	public Integer deleteSyncOrderConfigsById(String id) {
		return syncOrderConfigsDao.deleteSyncOrderConfigsById(id);
	}

	@Override
	public SyncOrderConfigs findOrderConfigsByShopName(String shopName) {
		return syncOrderConfigsDao.findOrderConfigsByShopName(shopName);
	}
}
