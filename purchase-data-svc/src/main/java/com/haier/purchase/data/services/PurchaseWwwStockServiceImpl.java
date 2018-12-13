package com.haier.purchase.data.services;

import com.haier.purchase.data.dao.purchase.WwwStockDao;
import com.haier.purchase.data.model.WwwStockSaveRequire;
import com.haier.purchase.data.service.PurchaseWwwStockService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:
 * @description:
 * @date:created in 2018/4/24 11:25
 * @modificed by:
 */
@Service
public class PurchaseWwwStockServiceImpl implements PurchaseWwwStockService {

  @Autowired
    private WwwStockDao wwwStockDao;
  @Override
  public int saveLog(String message, String createdDate) {
    return wwwStockDao.saveLog(message, createdDate);
  }

  @Override
  public int save(List<WwwStockSaveRequire> list) {
    return wwwStockDao.save(list);
  }

  @Override
  public int delete() {
    return wwwStockDao.delete();
  }

  @Override
  public int update(List<WwwStockSaveRequire> list) {
    return wwwStockDao.update(list);
  }

  @Override
  public List<WwwStockSaveRequire> select(String productTypeName, String enChannleId) {
    return wwwStockDao.select(productTypeName, enChannleId);
  }
}
