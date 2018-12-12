package com.haier.shop.services;

import java.util.List;
import java.util.Map;

import com.haier.shop.dao.shopread.OrderProductsAttributesReadDao;
import com.haier.shop.dao.shopwrite.OrderProductsAttributesWriteDao;
import com.haier.shop.model.OrderProductsAttributes;
import com.haier.shop.service.OrderProductsAttributesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderProductsAttributesServiceImpl implements OrderProductsAttributesService{

	@Autowired
	private OrderProductsAttributesReadDao productsAttributesReadDao;

	@Autowired
	private OrderProductsAttributesWriteDao productsAttributesWriteDao;

	@Override
	public OrderProductsAttributes get(Integer id) {
		// TODO Auto-generated method stub
		return productsAttributesReadDao.get(id);
	}

	@Override
	public int insert(OrderProductsAttributes orderProductsAttributes) {
		// TODO Auto-generated method stub
		return productsAttributesWriteDao.insert(orderProductsAttributes);
	}

	@Override
	public int update(OrderProductsAttributes orderProductsAttributes) {
		// TODO Auto-generated method stub
		return productsAttributesWriteDao.update(orderProductsAttributes);
	}
	//根据网单id获取网单扩展属性表
	@Override
	public OrderProductsAttributes getByOrderProductId(Integer orderProductId) {
		// TODO Auto-generated method stub
		return productsAttributesReadDao.getByOrderProductId(orderProductId);
	}
	 //根据订单id和天猫子网单号获取网单扩展信息
	@Override
	public List<OrderProductsAttributes> getByOrderIdAndOid(Integer orderId, String oid) {
		// TODO Auto-generated method stub
		return productsAttributesReadDao.getByOrderIdAndOid(orderId, oid);
	}

	@Override
	public List<OrderProductsAttributes> getByCondition(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return productsAttributesReadDao.getByCondition(queryMap);
	}
	 //根据网单号判断是否为自营单据
	@Override
	public Integer isSelfSellByOrderSn(String orderSn) {
		// TODO Auto-generated method stub
		return productsAttributesReadDao.isSelfSellByOrderSn(orderSn);
	}

	@Override
	public List<OrderProductsAttributes> getByOrderOrderId(Integer orderId) {
		// TODO Auto-generated method stub
		return productsAttributesReadDao.getByOrderOrderId(orderId);
	}

}
