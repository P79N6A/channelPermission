package com.haier.stock.service;

import com.haier.common.ServiceResult;

public interface ExpiryReleaseService {
    /**
     * 定时任务---自动释放锁定渠道库存
     * @return
     */
    ServiceResult<Boolean> autoInnerTransferForReservedStock();

}
