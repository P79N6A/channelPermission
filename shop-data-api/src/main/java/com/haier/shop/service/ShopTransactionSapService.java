package com.haier.shop.service;

import java.util.List;

import com.haier.shop.model.QueryZFBOrderParameter;
import com.haier.shop.model.TransactionSap;

public interface ShopTransactionSapService {

	List<TransactionSap> getTransactionSapList(QueryZFBOrderParameter queryOrder);

	Integer getTransactionSapCount(QueryZFBOrderParameter queryOrder);
	
	List<TransactionSap> getSummarySapList(QueryZFBOrderParameter queryOrder);

	Integer getSummarySapCount(QueryZFBOrderParameter queryOrder);
}
