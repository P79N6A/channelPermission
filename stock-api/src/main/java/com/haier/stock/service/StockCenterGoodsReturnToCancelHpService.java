package com.haier.stock.service;

import java.util.Map;

import com.haier.common.ServiceResult;

public interface StockCenterGoodsReturnToCancelHpService {

    public ServiceResult<Map<String, String>> pushOrderCancelToHp(String cOrderSn,
                                                                  Map<String, String> pushData);
}
