package com.haier.svc.model;


import com.haier.purchase.data.model.WwwStockSaveRequire;
import com.haier.purchase.data.service.PurchaseWwwStockService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 获取3w库存
 *
 * @author
 * @create 2017-03-29 13:22
 **/
@Service("wwwStockModel")
public class WwwStockModel {

  @Autowired
  private PurchaseWwwStockService purchaseWwwStockService;

  /**
   * 接口日志
   * @param message
   * @param createdDate
   * @return
   */
  public int saveLog(String message, String createdDate) {
    return purchaseWwwStockService.saveLog(message, createdDate);
  }

  /**
   *
   * @param list
   * @return
   */
  public int save(List<WwwStockSaveRequire> list) {
    return purchaseWwwStockService.save(list);
  }

  public int delete() {
    return purchaseWwwStockService.delete();
  }

  public int update(List<WwwStockSaveRequire> list) {
    return purchaseWwwStockService.update(list);
  }

  /**
   *
   * @param productTypeName
   * @param enChannleId
   * @return
   */
  public List<WwwStockSaveRequire> select(String productTypeName, String enChannleId) {
    return purchaseWwwStockService.select(productTypeName, enChannleId);
  }
}
