package com.haier.purchase.data.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.purchase.Cn3wReplenishOrdersDao;
import com.haier.purchase.data.model.Cn3wReplenishOrders;
import com.haier.purchase.data.service.Cn3wReplenishOrdersService;

@Service
public class Cn3wReplenishOrdersServiceImpl implements Cn3wReplenishOrdersService {

	@Autowired
	private Cn3wReplenishOrdersDao cn3wReplenishOrdersDao;

	@Override
	public Cn3wReplenishOrders getByLBX(String storeCode) {
		return cn3wReplenishOrdersDao.getByLBX(storeCode);
	}
}
