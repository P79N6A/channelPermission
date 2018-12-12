package com.haier.logistics.service;

import com.haier.common.ServiceResult;

public interface EISOrderService {
    /**
     * LES出入库后，同步到CBS
     * @return
     */
    ServiceResult<Boolean> processAfterLesShipped();
    public ServiceResult<Boolean> processShippedQueue();
}
