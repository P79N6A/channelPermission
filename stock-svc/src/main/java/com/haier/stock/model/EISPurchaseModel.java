package com.haier.stock.model;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.PurchaseOrderQueueWrapper;
import com.haier.purchase.data.service.PurchaseOrderService;
import com.haier.stock.services.Helper.EAIHandler;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EISPurchaseModel {

  @Resource
  private PurchaseOrderService purchaseOrderService;
  @Resource
  private EAIHandler eaiHandler;

  public Integer synPurchaseOrderToLes() {
    ServiceResult<List<PurchaseOrderQueueWrapper>> result = purchaseOrderService
        .findPurchaseOrderQueue();

    int count = 0;
    if (result.getSuccess() && result.getResult() != null) {
      List<PurchaseOrderQueueWrapper> purchaseOrderWrapperList = result.getResult();
      for (PurchaseOrderQueueWrapper purchaseOrderWrapper : purchaseOrderWrapperList) {
        boolean flag = eaiHandler.createPurchaseOrderToLes(purchaseOrderWrapper,
            purchaseOrderService);
        if (flag) {
          count++;
        }
      }
    }
    return count;
  }
}
