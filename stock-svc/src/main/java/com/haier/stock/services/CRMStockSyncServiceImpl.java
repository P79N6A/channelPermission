package com.haier.stock.services;


import com.haier.common.ServiceResult;
import com.haier.stock.model.CRMStockSyncModel;
import com.haier.stock.service.job.CRMStockSyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 与CRM系统同步库存相关信息
 * Created by 钊 on 14-3-14.
 */
@Service("cRMStockSyncServiceImpl")
public class CRMStockSyncServiceImpl implements CRMStockSyncService {
    private Logger            logger = LoggerFactory.getLogger(CRMStockSyncServiceImpl.class);

    @Autowired
    private CRMStockSyncModel crmStockSyncModel;

    public void setCrmStockSyncModel(CRMStockSyncModel crmStockSyncModel) {
        this.crmStockSyncModel = crmStockSyncModel;
    }

    @Override
    public ServiceResult<Boolean> updatePurchasePriceToCBS() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            crmStockSyncModel.updatePurchasePriceToCBS();
            result.setResult(true);
        } catch (Exception e) {
            logger.error("更新采购价格到CBS时，发生未知异常:", e);
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage("发生未知异常:" + e.getMessage());
        }
        return result;
    }
}
