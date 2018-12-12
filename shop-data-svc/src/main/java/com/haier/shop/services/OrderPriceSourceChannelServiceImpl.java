package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.OrderPriceSourceChannelReadDao;
import com.haier.shop.model.OrderPriceSourceChannel;
import com.haier.shop.service.OrderPriceSourceChannelService;
@Service
public class OrderPriceSourceChannelServiceImpl implements OrderPriceSourceChannelService{
	@Autowired
	private OrderPriceSourceChannelReadDao orderPriceSourceChannelReadDao;
	@Override
	public List<OrderPriceSourceChannel> getOrderPriceSourceChannelList() {
		// TODO Auto-generated method stub
		return orderPriceSourceChannelReadDao.getOrderPriceSourceChannelList();
	}

}
