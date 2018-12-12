package com.haier.stock.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.PagerInfo;
import com.haier.stock.dao.stock.MemStockLockDao;
import com.haier.stock.model.InvStockLock;
import com.haier.stock.model.InvStockLockEx;
import com.haier.stock.service.MemStockLockService;
@Service
public class MemStockLockServiceImpl implements MemStockLockService{
	@Autowired
	private MemStockLockDao  memStockLockDao;
	@Override
	public InvStockLock getLastMemStockLock() {
		// TODO Auto-generated method stub
		return memStockLockDao.getLastMemStockLock();
	}

	@Override
	public List<InvStockLockEx> queryMemStockLockList(InvStockLockEx stockLock, PagerInfo pager) {
		// TODO Auto-generated method stub
		return memStockLockDao.queryMemStockLockList(stockLock, pager);
	}

	@Override
	public List<InvStockLockEx> queryMemStockWDLockList(InvStockLockEx stockLock, PagerInfo pager) {
		// TODO Auto-generated method stub
		return memStockLockDao.queryMemStockWDLockList(stockLock, pager);
	}

	@Override
	public InvStockLockEx getMemStockLock(InvStockLockEx stockLock) {
		// TODO Auto-generated method stub
		return memStockLockDao.getMemStockLock(stockLock);
	}

	@Override
	public Integer updateStockLock(InvStockLockEx stockLock) {
		// TODO Auto-generated method stub
		return memStockLockDao.updateStockLock(stockLock);
	}

	@Override
	public int getRowCnt() {
		// TODO Auto-generated method stub
		return memStockLockDao.getRowCnt();
	}

}
