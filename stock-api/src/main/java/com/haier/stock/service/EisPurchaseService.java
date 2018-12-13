package com.haier.stock.service;

import com.haier.common.ServiceResult;

public interface EisPurchaseService {

  /**
   * 同步采购订单信息到Less
   * CBS同步采购订单到Les 同时更新purchaseOrdrQueue表状态
   * 为了调用已经被迁移的EAIHandler 只能将该类建在stcok-api下 对应旧系统类名 EisPurchaseModel
   * @return
   */
  ServiceResult<Integer> synPurchaseOrderToLes();
}
