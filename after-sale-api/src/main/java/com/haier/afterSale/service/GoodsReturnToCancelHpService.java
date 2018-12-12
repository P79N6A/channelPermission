package com.haier.afterSale.service;

import java.util.Map;

import com.haier.common.ServiceResult;

public interface GoodsReturnToCancelHpService {

    public ServiceResult<Map<String, String>> pushOrderCancelToHp(String cOrderSn,
                                                                  Map<String, String> pushData);
}
