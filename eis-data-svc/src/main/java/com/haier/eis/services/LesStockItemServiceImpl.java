package com.haier.eis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.eis.dao.eis.LesStockItemDao;
import com.haier.eis.model.LesStockItem;
import com.haier.eis.service.LesStockItemService;

@Service
public class LesStockItemServiceImpl implements LesStockItemService{

	@Autowired
	LesStockItemDao lesStockItemDao;
	
	@Override
	public void insertItem(LesStockItem lesstockitem) {
		// TODO Auto-generated method stub
		lesStockItemDao.insertItem(lesstockitem);
	}

}
