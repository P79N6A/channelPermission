package com.haier.order.service;

import com.haier.common.ServiceResult;

/**
 * Created by 胡万来 on 2018/1/11 0011.
 */
public interface OrderCenterHpNewDispatchService {
    ServiceResult<Boolean> sendHpNewBatchToDispatch();
}
