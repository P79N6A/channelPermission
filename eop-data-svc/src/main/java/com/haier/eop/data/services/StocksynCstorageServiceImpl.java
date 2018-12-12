package com.haier.eop.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.eop.data.dao.StocksynCstorageDao;
import com.haier.eop.data.dao.StocksyncproductsDao;
import com.haier.eop.data.model.StocksynCstorage;
import com.haier.eop.data.model.Stocksyncproducts;
import com.haier.eop.data.service.StocksynCstorageService;
import com.haier.eop.data.service.StocksyncproductsService;
@Service
public class StocksynCstorageServiceImpl implements StocksynCstorageService {
@Autowired
StocksynCstorageDao stocksynCstorageDao;

@Override
public StocksynCstorage getId(StocksynCstorage entity) {
	// TODO Auto-generated method stub
	return stocksynCstorageDao.getId(entity);
}

@Override
public List<StocksynCstorage> getsCode(String source) {
	// TODO Auto-generated method stub
	return stocksynCstorageDao.getsCode(source);
}

@Override
public int deleteByPrimaryKey(Integer id) {
	// TODO Auto-generated method stub
	return stocksynCstorageDao.deleteByPrimaryKey(id);
}

@Override
public int insert(StocksynCstorage record) {
	// TODO Auto-generated method stub
	return stocksynCstorageDao.insertSelective(record);
}

@Override
public int updateByPrimaryKeySelective(StocksynCstorage record) {
	// TODO Auto-generated method stub
	return stocksynCstorageDao.updateByPrimaryKeySelective(record);
}

@Override
public List<StocksynCstorage> Listf(StocksynCstorage entity, int start, int rows) {
	// TODO Auto-generated method stub
	return stocksynCstorageDao.Listf(entity, start, rows);
}

@Override
public int getPagerCountS(StocksynCstorage entity) {
	// TODO Auto-generated method stub
	return stocksynCstorageDao.getPagerCountS(entity);
}
	
    
}