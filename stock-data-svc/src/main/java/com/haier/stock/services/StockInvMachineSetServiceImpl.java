package com.haier.stock.services;

import java.util.List;
import java.util.Map;

import com.haier.stock.model.InvStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.stock.dao.stock.InvMachineSetDao;
import com.haier.stock.model.InvMachineSet;
import com.haier.stock.service.StockInvMachineSetService;
@Service
public class StockInvMachineSetServiceImpl implements StockInvMachineSetService{
	@Autowired
	private InvMachineSetDao invMachineSetDao;
	@Override
	public List<InvMachineSet> getByMainSku(String mainSku) {
		// TODO Auto-generated method stub
		return invMachineSetDao.getByMainSku(mainSku);
	}

	@Override
	public List<Map<String, Object>> select_sku(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		return invMachineSetDao.select_sku(list);
	}

	@Override
	public List<InvMachineSet> getBySubSku(String subSku) {
		// TODO Auto-generated method stub
		return invMachineSetDao.getBySubSku(subSku);
	}
	/**
     * 根据主sku和bom项目号获取
     * @param mainSku
     * @param bomNum
     * @return
     */
	@Override
	public InvMachineSet getByMainSkuAndBomNum(String mainSku, String bomNum) {
		// TODO Auto-generated method stub
		return invMachineSetDao.getByMainSkuAndBomNum(mainSku, bomNum);
	}

	@Override
	public Integer insert(InvMachineSet machineSet) {
		// TODO Auto-generated method stub
		return invMachineSetDao.insert(machineSet);
	}

	@Override
	public Integer update(InvMachineSet machineSet) {
		// TODO Auto-generated method stub
		return invMachineSetDao.update(machineSet);
	}

	@Override
	public List<InvMachineSet> getPageByCondition(InvMachineSet condition, int start, int pageSize) {
		return invMachineSetDao.getPageByCondition(condition,start,pageSize);
	}

	@Override
	public long getPagerCount(InvMachineSet condition) {
		return invMachineSetDao.getPagerCount(condition);
	}

	@Override
	public Integer updateSubSku(String sku, Integer s, String currentUser) {
		return invMachineSetDao.updateSubSku(sku,s,currentUser);
	}

}
