package com.haier.shop.service;


import java.util.List;

import com.haier.shop.model.QueryZFBOrderParameter;
import com.haier.shop.model.TradingFlow;


public interface ShopTradingFlowSerivce {
	
		List<TradingFlow> getFindQueryZfbOrderList(QueryZFBOrderParameter queryOrder);

		Integer getFindQueryZfbOrderListCount(QueryZFBOrderParameter queryOrder);

}
