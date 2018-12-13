package com.haier.invoice.service;

import java.util.List;

import com.haier.common.ServiceResult;
import com.haier.shop.model.QueryZFBOrderParameter;
import com.haier.shop.model.TradingFlow;

public interface TradingFlowSerivce {
	
	ServiceResult<List<TradingFlow>> selectTradingFlowByParam(QueryZFBOrderParameter queryOrder);
}
