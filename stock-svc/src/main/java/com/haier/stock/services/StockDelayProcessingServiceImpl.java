package com.haier.stock.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.stock.service.StockDelayProcessingService;

@Service
public class StockDelayProcessingServiceImpl implements StockDelayProcessingService {

	private final static Logger logger = LoggerFactory.getLogger(StockDelayProcessingServiceImpl.class);
	private static final String LOG_MARK = "[Stock][StockTransactionServiceImpl] ";
	@Autowired
	private StockTransactionModel stockTransactionModel;


	/**
	 * 处理延后处理的库存交易
	 */
	@Override
	//@Scheduled(cron = "*/10 * * * * ?")
	public ServiceResult<Boolean> processForDelay() {

		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			stockTransactionModel.processForDelay();
			result.setResult(true);
		} catch (Exception e) {
			result.setResult(false);
			result.setSuccess(false);
			result.setMessage("处理延后处理的记录出现错误：" + e.getMessage());
			logger.error(LOG_MARK + "处理延后处理的记录出现错误：", e);
		}
		return result;
	}

}
