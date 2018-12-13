package com.haier.stock.service;

import com.haier.common.PagerInfo;
import com.haier.stock.model.InvBaseStock;
import com.haier.stock.model.InvBaseStockExcel;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface InvBaseStockService {

  public List<InvBaseStockExcel> queryInvBaseStockList(Map<String, String> map,
      PagerInfo pager);

  public List<InvBaseStockExcel> queryInvBaseStockList(Map<String, String> map);

  public List<InvBaseStockExcel> queryInvBaseStockList1(InvBaseStockExcel invBaseStock,
      PagerInfo pager);

  int getRowCnt();

  public InvBaseStock getForUpdate(String sku, String secCode);

  public Integer insert(InvBaseStock baseStock);

  public Integer updateStockQty(Integer sto_id, Integer qty, Date time);
}
