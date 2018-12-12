package com.haier.shop.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopwrite.LesStockSyncsDao;
import com.haier.shop.model.LesStockSyncs;
import com.haier.shop.service.LesStockSyncsService;

@Service
public class LesStockSyncsServiceImpl implements LesStockSyncsService{
	
	@Autowired
	LesStockSyncsDao lesStockSyncsDao;
	

	@Override
	public void insert(LesStockSyncs lesStockSyncs) {
		lesStockSyncsDao.insert(lesStockSyncs);
		
	}


	@Override
	public int getIdbyDonetime(String donetime) {
		// TODO Auto-generated method stub
		return lesStockSyncsDao.getIdbyDonetime(donetime);
	}
	
	

}
