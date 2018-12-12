package com.haier.stock.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.stock.model.LesStockSyncModel;
import com.haier.stock.service.LesStockSyncService;
@Service
public class LesStockSyncServiceImpl implements LesStockSyncService{

	private Logger     logger = LoggerFactory.getLogger(LesStockSyncServiceImpl.class);
	@Autowired
	private LesStockSyncModel lesStockSyncModel;
	
	/**
	 * Job：根据时间范围向LES请求自有库存数据。由于LES的系统时间和CBS的系统时间不同步，默认延后5分钟；最大时间范围6小时。
     * @return 成功-true 失败-false
	 */
	@Override
	//@Scheduled(cron = "*/10 * * * * ?")
	public ServiceResult<Boolean> getInventoryTransFromLes() {
		 ServiceResult<Boolean> result = new ServiceResult<Boolean>();
	        try {
	            result.setResult(lesStockSyncModel.doInventoryTransFromLesNew());
	        } catch (Exception e) {
	            logger.error("从LES获取自有库存的出入库记录和库存记录未知异常:", e);
	            result.setSuccess(false);
	            result.setMessage("从LES获取自有库存的出入库记录和库存记录发生未知异常:" + e.getMessage());
	        }
	        return result;
	}
	
	
	

}
