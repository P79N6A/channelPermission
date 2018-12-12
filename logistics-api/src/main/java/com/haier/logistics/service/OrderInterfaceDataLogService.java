package com.haier.logistics.service;

import com.haier.common.ServiceResult;
import com.haier.eis.model.OrderInterfaceDataLog;

/*
* 作者:张波
* 2017/12/26
*/
public interface OrderInterfaceDataLogService {
    /**
     * 记录一条访问log
     * @param log
     * @return
     */
    ServiceResult<Boolean> record(OrderInterfaceDataLog log);
}
