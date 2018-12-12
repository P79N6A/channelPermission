package com.haier.eop.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.eop.data.dao.StocksyncproductsDao;
import com.haier.eop.data.model.Stocksyncproducts;
import com.haier.eop.data.service.StocksyncproductsService;
@Service
public class StocksyncproductsServiceImpl implements StocksyncproductsService {
@Autowired
StocksyncproductsDao stocksyncproductsDao;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return stocksyncproductsDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Stocksyncproducts record) {
		// TODO Auto-generated method stub
		return stocksyncproductsDao.insert(record);
	}

	@Override
	public int updateByPrimaryKey(Stocksyncproducts record) {
		// TODO Auto-generated method stub
		return stocksyncproductsDao.updateByPrimaryKey(record);
	}

	@Override
	public List<Stocksyncproducts> Listf(Stocksyncproducts entity, int start, int rows) {
		// TODO Auto-generated method stub
		return stocksyncproductsDao.Listf(entity, start, rows);
	}

	@Override
	public int getPagerCountS(Stocksyncproducts entity) {
		// TODO Auto-generated method stub
		return stocksyncproductsDao.getPagerCountS(entity);
	}

	@Override
	public Stocksyncproducts getId(String sku, String source) {
		// TODO Auto-generated method stub
		return stocksyncproductsDao.getId(sku,source);
	}
    
}