package com.haier.stock.services;

import com.haier.common.PagerInfo;

import com.haier.stock.dao.stock.InvBaseStockDao;
import com.haier.stock.model.InvBaseStock;
import com.haier.stock.model.InvBaseStockExcel;
import com.haier.stock.service.InvBaseStockService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InvBaseStockServiceImpl implements InvBaseStockService {

  @Autowired
  private InvBaseStockDao invBaseStockDao;

  @Override
  public List<InvBaseStockExcel> queryInvBaseStockList(Map<String, String> map,
      PagerInfo pager) {
    List<InvBaseStockExcel> list = invBaseStockDao.queryInvBaseStockList(map, pager);
    return list;
  }

  @Override
  public List<InvBaseStockExcel> queryInvBaseStockList(Map<String, String> map) {
    List<InvBaseStockExcel> list = invBaseStockDao.queryInvBaseStockCountList(map);
    return list;
  }

  @Override
  public List<InvBaseStockExcel> queryInvBaseStockList1(InvBaseStockExcel invBaseStock,
      PagerInfo pager) {
    return invBaseStockDao.queryInvBaseStockList1(invBaseStock, pager);
  }

  @Override
  public int getRowCnt() {
    return invBaseStockDao.getRowCnt();
  }

  @Override
  public InvBaseStock getForUpdate(String sku, String secCode) {
    return this.invBaseStockDao.getForUpdate(sku, secCode);
  }

  @Override
  public Integer insert(InvBaseStock baseStock) {
    return this.invBaseStockDao.insert(baseStock);
  }

  @Override
  public Integer updateStockQty(Integer sto_id, Integer qty, Date time) {
    return this.invBaseStockDao.updateStockQty(sto_id, qty, time);
  }
}
