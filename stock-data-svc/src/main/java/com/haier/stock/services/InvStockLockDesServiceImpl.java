package com.haier.stock.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.stock.dao.stock.InvStockLockDesDao;
import com.haier.stock.model.InvStockLockDes;
import com.haier.stock.service.InvStockLockDesService;

@Service
public class InvStockLockDesServiceImpl implements InvStockLockDesService {
	@Autowired
	private InvStockLockDesDao invStockLockDesDao;

	@Override
	public List<Map<String, Object>> queryWaLockDetails(Map<String, Object> parmas) {
		// TODO Auto-generated method stub
		return invStockLockDesDao.queryWaLockDetails(parmas);
	}

	@Override
	public List<Map<String, Object>> queryChannelLockDetails(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return invStockLockDesDao.queryChannelLockDetails(params);
	}

	@Override
	public List<Map<String, Object>> getBySecCodeAndSku(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return invStockLockDesDao.getBySecCodeAndSku(params);
	}

	@Override
	public Integer getBySecCodeAndSkuCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return invStockLockDesDao.getBySecCodeAndSkuCount(params);
	}

	@Override
	public Integer getRowCnt() {
		// TODO Auto-generated method stub
		return invStockLockDesDao.getRowCnt();
	}

	@Override
	public Integer delete() {
		//
		return invStockLockDesDao.delete();
	}

	@Override
	public List<InvStockLockDes> queryWaStockQtyByChannel(String sku, String secCode, String channel) {

		return invStockLockDesDao.queryWaStockQtyByChannel(sku, secCode, channel);
	}

	@Override
	public Integer insert(InvStockLockDes des) {
		// TODO Auto-generated method stub
		return invStockLockDesDao.insert(des);
	}

	@Override
	public Integer update(InvStockLockDes des) {
		// TODO Auto-generated method stub
		return invStockLockDesDao.update(des);
	}

}
