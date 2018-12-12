package com.haier.shop.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.OrderPriceGateReadDao;
import com.haier.shop.dao.shopwrite.OrderPriceGateWriteDao;
import com.haier.shop.model.OrderPriceGate;
import com.haier.shop.service.OrderPriceGateService;
@Service
public class OrderPriceGateServiceImpl implements OrderPriceGateService{
	@Autowired
	private OrderPriceGateReadDao orderPriceGateReadDao;
	@Autowired
	private OrderPriceGateWriteDao orderPriceGateWriteDao;

	@Override
	public OrderPriceGate getOrderPriceGatebyCOrderSn(String cOrderSn, Integer gateType) {
		// TODO Auto-generated method stub
		return orderPriceGateReadDao.getOrderPriceGatebyCOrderSn(cOrderSn, gateType);
	}

	@Override
	public int batchInsert(List<OrderPriceGate> orderPriceGateList) {
		// TODO Auto-generated method stub
		return orderPriceGateWriteDao.batchInsert(orderPriceGateList);
	}

	@Override
	public int unLockOrderPriceGatebyOrderSn(String orderSn, String operator, String responsiblePerson,
			String unlockReason) {
		// TODO Auto-generated method stub
		return orderPriceGateWriteDao.unLockOrderPriceGatebyOrderSn(orderSn, operator, responsiblePerson, unlockReason);
	}

	@Override
	public List<Map<String, Object>> getUnLockbyOrderSn(String orderSn) {
		// TODO Auto-generated method stub
		return orderPriceGateReadDao.getUnLockbyOrderSn(orderSn);
	}

	@Override
	public String getBrandNameByBrandId(Integer id) {
		// TODO Auto-generated method stub
		return orderPriceGateReadDao.getBrandNameByBrandId(id);
	}

	@Override
	public String getCateNameByCateId(Integer id) {
		// TODO Auto-generated method stub
		return orderPriceGateReadDao.getCateNameByCateId(id);
	}

}
