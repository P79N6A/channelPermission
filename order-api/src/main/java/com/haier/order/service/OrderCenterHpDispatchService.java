package com.haier.order.service;

import com.haier.common.ServiceResult;

/**
 * Created by 胡万来 on 2018/1/11 0011.
 * description:(定时任务)
 */
public interface OrderCenterHpDispatchService {
    /**
     * 处理HP回传派工信息
     * @return
     */
    ServiceResult<Boolean> processNetPoint();

    /**
     * 处理VOM回传的VOM派工后返回的网点数据
     * @return
     */
    ServiceResult<Boolean> processNewNetPoint();
}
