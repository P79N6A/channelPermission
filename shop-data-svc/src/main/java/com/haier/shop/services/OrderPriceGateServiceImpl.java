package com.haier.shop.services;

import com.haier.shop.dao.shopread.OrderPriceProductGroupIndustryReadDao;
import com.haier.shop.dao.shopread.OrderPriceSourceChannelReadDao;
import com.haier.shop.model.OrderPriceProductGroupIndustry;
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
	@Autowired
	private OrderPriceSourceChannelReadDao orderPriceSourceChannelReadDao;
	@Autowired
	private OrderPriceProductGroupIndustryReadDao orderPriceProductGroupIndustryDao;

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

	/**
	 * 获取 订单来源 下拉列表
	 * @return
	 */
	@Override
	public List<Map<String, String>> getOrderPriceSourceList() {
		return orderPriceSourceChannelReadDao.getOrderPriceSourceList();
	}

	/**
	 * 获取 采购账户 下拉列表
	 * @return
	 */
	@Override
	public List<Map<String, String>> getIndustryCode() {
		return orderPriceProductGroupIndustryDao.getOrderPriceIndustry();
	}

	/**
	 * 查询订单价格闸口列表
	 * MemberInvoices
	 * @return
	 */
	@Override
	public List<OrderPriceGate> getOrderPriceGateList(Map<String, Object> paramMap) {
		List<OrderPriceGate> list = null;
		try {
			list = orderPriceGateReadDao.getOrderPriceGateList(paramMap);
		} catch (Exception e) {
			//log.error("[OrderPriceGateModel_getOrderPriceGateList]查询订单价格闸口数据异常:" + e.getMessage());
		}
		return list;
	}

	/**
	 * 获取 产品组 下拉列表
	 * @return
	 */
	@Override
	public List<Map<String, String>> getProductGroup(String industryCode) {
		return orderPriceProductGroupIndustryDao.getOrderPriceProductGroup(industryCode);
	}

	/**
	 * 获得条数[订单价格闸口列表]
	 *
	 * @return
	 */
	@Override
	public Integer getRows(Map<String,Object> param) {
		return orderPriceGateReadDao.getRows(param);
	}

	/**
	 * 获取[订单价格管控产品组产业对应表]采购账户和用户组名称对应关系
	 * @return
	 */
	@Override
	public List<OrderPriceProductGroupIndustry> getOrderPriceProductGroupIndustryList() {
		return orderPriceProductGroupIndustryDao.getOrderPriceProductGroupIndustryList(null);
	}

	/**
	 * 获取 订单来源、渠道名称对应关系
	 * @return
	 */
	@Override
	public List<Map<String, String>> getOrderPriceSourceChannelList() {
		return orderPriceSourceChannelReadDao.getOrderPriceSourceChannelMapList();
	}

	/**
	 * 获取 渠道 下拉列表
	 * @return
	 */
	@Override
	public List<Map<String, String>> getGuaranteePriceChannel() {
		return orderPriceSourceChannelReadDao.getGuaranteePriceChannel();
	}

	/**
	 * 获取 订单来源 下拉列表
	 * @return
	 */
	@Override
	public List<Map<String, String>> getGuaranteePriceSource(String channel) {
		return orderPriceSourceChannelReadDao.getGuaranteePriceSource(channel);
	}
}
