/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.purchase.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.purchase.LesRRSOutDao;
import com.haier.purchase.data.model.ZWDTABLEEntity;
import com.haier.purchase.data.service.PurchaseLesRRSOutService;

@Service
public class PurchaseLesRRSOutServiceImpl implements PurchaseLesRRSOutService{

	@Autowired
	LesRRSOutDao lesRRSOutDao; 
	
	@Override
	public void insertOutInfo(ZWDTABLEEntity info){
		lesRRSOutDao.insertOutInfo(info);
	}

	@Override
	public Integer isExist(ZWDTABLEEntity info){
		return lesRRSOutDao.isExist(info);
	}

	@Override
	public List<ZWDTABLEEntity> findOutInfoBySO(List<String> so){
		return lesRRSOutDao.findOutInfoBySO(so);
	}
}
