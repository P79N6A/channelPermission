package com.haier.stock.service;

import com.haier.common.ServiceResult;

public interface EisProduceDailyService {

  /**
   * 更新生产信息
   * 从OMS同步日日单生产信息
   * @return boolean
   */
  ServiceResult<Boolean> updateProduceInformationFromOMS();

  /**
   * 根据日日单网单生成日日单采购单队列
   * @return boolean
   */
  ServiceResult<Boolean> createPurchaseOrderQueue4Daily();

  /**
   * 生成采购订单和采购网单
   * @return boolean
   */
  ServiceResult<Boolean> createPurchaseOrder();

  /**
   * 更新日日单网单的日日单状态
   * @return boolean
   */
  ServiceResult<Boolean> updateOrderProductPDStatus();

  /**
   * 同步生产信息
   * @return
   */
  ServiceResult<Boolean> syncProduceInformation();

  /**
   * 从LES获取更新CRM开提单信息并更新
   * @return
   */
  ServiceResult<Boolean> updateCRMLogisticsInfoFromLes();

  /**
   * 从EDW同步产品生产/下线日期
   * @return
   */
  ServiceResult<Boolean> syncProdDateFromEDW();

}
