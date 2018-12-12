package com.haier.stock.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.stock.model.EISOrderModel;
import com.haier.stock.service.ReleaseStockOrderService;

@Service
public class ReleaseStockOrderServiceImpl implements ReleaseStockOrderService {
	private Logger log = LoggerFactory.getLogger(ReleaseStockOrderServiceImpl.class);

	@Autowired
	private EISOrderModel eisOrderModel;

	@Override
	//@Scheduled(cron = "*/10 * * * * ?")
	public ServiceResult<Boolean> releaseCancelCloseStock() {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			eisOrderModel.releaseCancelCloseStock();
		} catch (Exception e) {
			log.error("[releaseCancelCloseStock]释放取消关闭的网单的库存[商城、海朋、基地库]出现异常:" + e.getMessage());
			result.setResult(false);
			result.setSuccess(false);
			result.setMessage("[releaseCancelCloseStock]释放取消关闭的网单的库存[商城、海朋、基地库]出现异常:" + e.getMessage());
		}
		return result;
	}

}
