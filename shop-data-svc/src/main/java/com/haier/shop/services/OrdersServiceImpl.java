package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopwrite.OrdersDao;
import com.haier.shop.model.Orders;
import com.haier.shop.service.OrdersService;
@Service
public class OrdersServiceImpl implements OrdersService{
	
	@Autowired
	OrdersDao ordersDao;

	@Override
	public List<Orders> getCodNotConfirmOrders() {
		// TODO Auto-generated method stub
		return ordersDao.getCodNotConfirmOrders();
	}

	@Override
	public int updateCodConfirmState(Integer id) {
		// TODO Auto-generated method stub
		return ordersDao.updateCodConfirmState(id);
	}

}
