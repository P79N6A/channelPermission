package com.haier.stock.services;
import com.haier.common.BusinessException;
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
	
	 @Override
	    public ServiceResult<Boolean> cancelTransferLine2Les(String lineNum, String lesNum) {
	        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
	        try {
	            result.setResult(lesStockSyncModel.cancelTransferLine2Les(lineNum, lesNum));
	        } catch (Exception e) {
	            logger.error("调拨单取消到LES失败:" + e.getMessage());
	            result.setResult(false);
	            result.setMessage("取消LES提单失败：" + e.getMessage());
	        }
	        return result;
	    }
	 @Override
	    public ServiceResult<Boolean> syncTransferLinesToLes() {
	        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
	        try {
	            lesStockSyncModel.syncTransferLinesToLes();
	            result.setResult(true);
	        } catch (Exception e) {
	            result.setResult(false);
	            result.setSuccess(false);
	            logger.error("读取状态为30待传LES的调拨网单同步到LES出现错误：", e);
	        }
	        return result;
	    }


	    @Override
	    public ServiceResult<Boolean> transferLineToLesForFeeInput() {
	        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
	        try {
	            lesStockSyncModel.transferLineToLesForFeeInput();
	            result.setResult(true);
	        } catch (Exception e) {
	            result.setResult(false);
	            result.setSuccess(false);
	            logger.error("传递用于录入费用的调拨单信息到LES出现错误：", e);
	        }
	        return result;
	    }

	@Override
	public ServiceResult<Boolean> doMachineSetsSyncFromLes() {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			result.setResult(lesStockSyncModel.doMachineSetsSyncFromLes());
		} catch (BusinessException be) {
			logger.warn(be.getMessage());
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			logger.error("通过LES同步套机数据，出现未知异常：", e);
			result.setSuccess(false);
			result.setMessage("出现未知异常:" + e.getMessage());
		}
		return result;
	}

}
