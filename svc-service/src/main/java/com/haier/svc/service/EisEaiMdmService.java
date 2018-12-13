package com.haier.svc.service;

import com.haier.common.ServiceResult;

public interface EisEaiMdmService {

    /**
     * 手动同步MDM信息
     * @return
     */
    ServiceResult<Boolean> manualSyncMdmBySku(String sku, String modifier);
    
}
