package com.haier.shop.services;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.TransactionSapReadDao;
import com.haier.shop.model.QueryZFBOrderParameter;
import com.haier.shop.model.TransactionSap;
import com.haier.shop.service.ShopTransactionSapService;

@Service
public class ShopTransactionSapServiceImpl  implements  ShopTransactionSapService{

	 @Autowired
	 private TransactionSapReadDao transactionSapReadDao;
	 private static final Logger logger = LogManager.getLogger(ShopTransactionSapServiceImpl.class);

	@Override
	public List<TransactionSap> getTransactionSapList(QueryZFBOrderParameter queryOrder) {
		return transactionSapReadDao.getTransactionSapList(queryOrder);
	}

	@Override
	public Integer getTransactionSapCount(QueryZFBOrderParameter queryOrder) {
		return transactionSapReadDao.getTransactionSapCount(queryOrder);
	}
	
	@Override
	public List<TransactionSap> getSummarySapList(QueryZFBOrderParameter queryOrder) {
		return transactionSapReadDao.getSummarySapList(queryOrder);
	}

	@Override
	public Integer getSummarySapCount(QueryZFBOrderParameter queryOrder) {
		return transactionSapReadDao.getSummarySapCount(queryOrder);
	}
}
