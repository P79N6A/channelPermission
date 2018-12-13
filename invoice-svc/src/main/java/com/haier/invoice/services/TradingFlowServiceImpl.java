package com.haier.invoice.services;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.invoice.service.TradingFlowSerivce;
import com.haier.shop.model.QueryZFBOrderParameter;
import com.haier.shop.model.TradingFlow;
import com.haier.shop.service.ShopTradingFlowSerivce;

@Service
public class TradingFlowServiceImpl implements TradingFlowSerivce{
	
	private static Logger logger = LogManager.getLogger(TradingFlowServiceImpl.class);
	
	@Autowired
    private ShopTradingFlowSerivce shopTradingFlowSerivce;
	
	@Override
	public ServiceResult<List<TradingFlow>> selectTradingFlowByParam(QueryZFBOrderParameter queryOrder) {
		
		ServiceResult<List<TradingFlow>> result = new ServiceResult<List<TradingFlow>>();
        try {
            if (queryOrder == null) {
                result.setSuccess(false);
                result.setMessage("【selectAllByParam】获取支付宝流水总汇信息服务参数并为null");
                logger.error("【selectAllByParam】获取支付宝流水总汇信息服务参数并为null");
                return result;
            }
            List<TradingFlow> matchings=shopTradingFlowSerivce.getFindQueryZfbOrderList(queryOrder);
            Integer count = shopTradingFlowSerivce.getFindQueryZfbOrderListCount(queryOrder);
            result.setResult(matchings);
            PagerInfo pi = new PagerInfo();
            pi.setRowsCount(count != null ? count : 0);
            result.setPager(pi);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error("获取订单信息list失败：", e);
        }
        return result;
	}

}
