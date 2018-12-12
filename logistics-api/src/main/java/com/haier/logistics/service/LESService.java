package com.haier.logistics.service;

import com.haier.common.ServiceResult;

public interface LESService {
    /**
     * LES开提单数据接口
     * @param foreignKey 记录接口日志的数据id,一般记录订单或网单id或网单号
     * @param content 调用接口数据
     * @return
     */
    ServiceResult<String> orderToLes(String foreignKey, String content);
}
