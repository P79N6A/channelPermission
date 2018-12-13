package com.haier.shop.services;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.TradingFlowReadDao;
import com.haier.shop.model.QueryZFBOrderParameter;
import com.haier.shop.model.TradingFlow;
import com.haier.shop.service.ShopTradingFlowSerivce;
@Service
public class ShopTradingFlowServiceImpl implements ShopTradingFlowSerivce{
	
	@Autowired
	private TradingFlowReadDao tradingFlowReadDao;
	
	private static final Logger logger = LogManager.getLogger(ShopTradingFlowServiceImpl.class);
	@Override
	public List<TradingFlow> getFindQueryZfbOrderList(QueryZFBOrderParameter queryOrder) {
		return tradingFlowReadDao.getFindQueryOrderList(queryOrder);
	}
	@Override
	public Integer getFindQueryZfbOrderListCount(QueryZFBOrderParameter queryOrder) {
		return tradingFlowReadDao.getFindQueryOrderListCount(queryOrder);
	}
}
