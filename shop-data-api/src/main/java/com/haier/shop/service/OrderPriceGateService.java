package com.haier.shop.service;

import java.util.List;
import java.util.Map;


import com.haier.shop.model.OrderPriceGate;

public interface OrderPriceGateService {
	 OrderPriceGate getOrderPriceGatebyCOrderSn(String cOrderSn,
             Integer gateType);

	int batchInsert(List<OrderPriceGate> orderPriceGateList);
	
	int unLockOrderPriceGatebyOrderSn(String orderSn,
	    String operator,
	    String responsiblePerson,
	    String unlockReason);
	
	List<Map<String, Object>> getUnLockbyOrderSn(String orderSn);
	
	String getBrandNameByBrandId(Integer id);
	
	String getCateNameByCateId(Integer id);
}
