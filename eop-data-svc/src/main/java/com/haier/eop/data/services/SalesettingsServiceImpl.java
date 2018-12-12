package com.haier.eop.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.eop.data.dao.SalesettingsDao;
import com.haier.eop.data.model.Salesettings;
import com.haier.eop.data.service.SalesettingsService;
import com.haier.eop.data.service.StocksyncProstorageService;
@Service
public class SalesettingsServiceImpl implements SalesettingsService {
@Autowired
SalesettingsDao salesettingsDao;

@Override
public int deleteByPrimaryKey(Integer id) {
	// TODO Auto-generated method stub
	return salesettingsDao.deleteByPrimaryKey(id);
}

@Override
public int insert(Salesettings record) {
	// TODO Auto-generated method stub
	return salesettingsDao.insertSelective(record);
}

@Override
public int updateByPrimaryKeySelective(Salesettings record) {
	// TODO Auto-generated method stub
	return salesettingsDao.updateByPrimaryKeySelective(record);
}

@Override
public List<Salesettings> Listf(Salesettings entity, int start, int rows) {
	// TODO Auto-generated method stub
	return salesettingsDao.Listf(entity, start, rows);
}

@Override
public int getPagerCountS(Salesettings entity) {
	// TODO Auto-generated method stub
	return salesettingsDao.getPagerCountS(entity);
}
	
    
}