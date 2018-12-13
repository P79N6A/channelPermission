package com.haier.invoice.service;

import java.util.List;

import com.haier.common.ServiceResult;
import com.haier.shop.model.QueryZFBOrderParameter;
import com.haier.shop.model.TransactionSap;

public interface TransactionSapService {
	
	ServiceResult<List<TransactionSap>> selectZfbOrdersByParam(QueryZFBOrderParameter queryOrder);
	
	ServiceResult<List<TransactionSap>> transactionSummarySapPage(QueryZFBOrderParameter queryOrder);
}
