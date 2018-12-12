package com.haier.svc.service;

import com.haier.common.ServiceResult;

public interface PayCenterFallBackService {

    /**
     * 支付中心回退接口
     * @param foreignKey 记录接口日志的数据id,一般记录订单或网单id
     * @param content 调用接口数据
     * @return
     */
    ServiceResult<String> fallBack(String foreignKey, String content);

}
