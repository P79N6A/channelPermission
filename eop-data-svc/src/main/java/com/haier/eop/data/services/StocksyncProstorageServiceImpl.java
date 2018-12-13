package com.haier.eop.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.eop.data.dao.StocksyncProstorageDao;
import com.haier.eop.data.model.StocksyncProstorage;
import com.haier.eop.data.service.StocksyncProstorageService;
import com.haier.eop.data.service.StocksyncProstorageService;
@Service
public class StocksyncProstorageServiceImpl implements StocksyncProstorageService {
@Autowired
StocksyncProstorageDao stocksyncProstorageDao;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return stocksyncProstorageDao.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteBySourceAndSku(String source, String sku) {
		return stocksyncProstorageDao.deleteBySourceAndSku(source,sku);
	}

	@Override
	public int insert(StocksyncProstorage record) {
		// TODO Auto-generated method stub
		return stocksyncProstorageDao.insert(record);
	}

	@Override
	public int updateByPrimaryKeySelective(StocksyncProstorage record) {
		// TODO Auto-generated method stub
		return stocksyncProstorageDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<StocksyncProstorage> Listf(StocksyncProstorage entity, int start, int rows) {
		// TODO Auto-generated method stub
		return stocksyncProstorageDao.Listf(entity, start, rows);
	}

	@Override
	public int getPagerCountS(StocksyncProstorage entity) {
		// TODO Auto-generated method stub
		return stocksyncProstorageDao.getPagerCountS(entity);
	}
    
}