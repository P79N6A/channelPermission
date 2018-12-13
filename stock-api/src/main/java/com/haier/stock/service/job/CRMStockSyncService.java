package com.haier.stock.service.job;

import com.haier.common.ServiceResult;


/**
 * 与CRM系统同步库存相关信息
 * Created by 钊 on 14-3-14.
 */
public interface CRMStockSyncService {
    /**
     * JOB:更新采购价格到CBS
     * 从CRM系统获取采购价格，更新到库龄表中
     * @return
     */
    ServiceResult<Boolean> updatePurchasePriceToCBS();
}
