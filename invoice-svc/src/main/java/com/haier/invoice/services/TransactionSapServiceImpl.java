package com.haier.invoice.services;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.invoice.service.TransactionSapService;
import com.haier.shop.model.QueryZFBOrderParameter;
import com.haier.shop.model.TransactionSap;
import com.haier.shop.service.ShopTransactionSapService;

@Service
public class TransactionSapServiceImpl implements TransactionSapService{

	private static Logger logger = LogManager.getLogger(TransactionSapServiceImpl.class);

	@Autowired
    private ShopTransactionSapService shopTransactionSapService; 
	
	@Override
	public ServiceResult<List<TransactionSap>> selectZfbOrdersByParam(QueryZFBOrderParameter queryOrder) {
		ServiceResult<List<TransactionSap>> result = new ServiceResult<List<TransactionSap>>();
        try {
            if (queryOrder == null) {
                result.setSuccess(false);
                result.setMessage("【selectZfbOrdersByParam】");
                logger.error("【selectZfbOrdersByParam】");
                return result;
            }
            List<TransactionSap> matchings=shopTransactionSapService.getTransactionSapList(queryOrder);
            Integer count = shopTransactionSapService.getTransactionSapCount(queryOrder);
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
	
	@Override
	public ServiceResult<List<TransactionSap>> transactionSummarySapPage(QueryZFBOrderParameter queryOrder) {
		ServiceResult<List<TransactionSap>> result = new ServiceResult<List<TransactionSap>>();
        try {
            if (queryOrder == null) {
                result.setSuccess(false);
                result.setMessage("【transactionSummarySapPage】");
                logger.error("【transactionSummarySapPage】");
                return result;
            }
            List<TransactionSap> matchings=shopTransactionSapService.getSummarySapList(queryOrder);
            Integer count = shopTransactionSapService.getSummarySapCount(queryOrder);
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
