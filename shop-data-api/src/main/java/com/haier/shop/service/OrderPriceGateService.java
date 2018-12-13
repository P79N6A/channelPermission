package com.haier.shop.service;

import com.haier.shop.model.OrderPriceProductGroupIndustry;
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

	List<Map<String, String>> getOrderPriceSourceList();

	/**
	 * 获取 采购账户 下拉列表
	 * @return
	 */
	public List<Map<String, String>> getIndustryCode();

	List<OrderPriceGate> getOrderPriceGateList(Map<String, Object> paramMap);

	/**
	 * 获取 产品组 下拉列表
	 * @return
	 */
	List<Map<String, String>> getProductGroup(String industryCode);

	Integer getRows(Map<String,Object> param);

	List<OrderPriceProductGroupIndustry> getOrderPriceProductGroupIndustryList();

	List<Map<String, String>> getOrderPriceSourceChannelList();

	List<Map<String, String>> getGuaranteePriceChannel();

	/**
	 * 获取 订单来源 下拉列表
	 * @return
	 */
	public List<Map<String, String>> getGuaranteePriceSource(String channel);
}
