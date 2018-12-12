package com.haier.stock.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.stock.model.StockAgeModel;
import com.haier.stock.service.ComputingAgeService;
@Service
public class ComputingAgeServiceImpl implements ComputingAgeService {
	
	private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
			.getLogger(ComputingAgeServiceImpl.class);
	
	@Autowired
	private StockAgeModel stockAgeModel;
	
	/**
	 * 计算库龄(实时)
	 */
	@Override
   // @Scheduled(cron = "*/5 * * * * ?")
    public ServiceResult<Boolean> calculateStockAgeTimely() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            stockAgeModel.calculateStockAgeTimely();
            result.setResult(true);
        } catch (Exception e) {
            log.error("计算库龄(实时)出现错误:", e);
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

}
