package com.haier.shop.service;

import java.util.List;

import com.haier.shop.model.Orders;

public interface OrdersService {
	
	
	 List<Orders> getCodNotConfirmOrders();
	 int updateCodConfirmState(Integer id);

}
