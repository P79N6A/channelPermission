package com.haier.stock.services;

import com.haier.common.ServiceResult;
import com.haier.stock.model.EISPurchaseModel;
import com.haier.stock.service.EisPurchaseService;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EisPurchaseServiceImpl implements EisPurchaseService {

  private static Logger log = LoggerFactory.getLogger(EisPurchaseServiceImpl.class);

  @Resource
  private EISPurchaseModel eisPurchaseModel;

  @Override
  public ServiceResult<Integer> synPurchaseOrderToLes() {
    ServiceResult<Integer> result = new ServiceResult<Integer>();
    try {
      result.setResult(eisPurchaseModel.synPurchaseOrderToLes());
    } catch (Exception e) {
      log.error("同步采购订单到Les时，发生未知异常：", e);
      result.setMessage("服务器发生未知异常：" + e.getMessage());
      result.setSuccess(false);
    }
    return result;
  }
}
