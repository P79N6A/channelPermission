package com.haier.logistics.service;

import com.haier.common.ServiceResult;

/**
 * Created by 胡万来 on 2018/1/16 0016.
 */
public interface LesStockSyncService {
    ServiceResult<Boolean> getInventoryTransFromLes();
}
